package cucumber;

import csci310.Database;
import csci310.models.UserTest;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

/**
 * Step definitions for Cucumber tests.
 */
public class StepDefinitions {
    private static final String ROOT_URL = "http://localhost:8080/";
    private static final long BTN_DELAY = 1000;

    private final WebDriver driver = new ChromeDriver();
    
    @Given("I am on the index page")
    public void i_am_on_the_index_page() {
        driver.get(ROOT_URL);
    }

    @Then("I should see header {string}")
    public void i_should_see_header(String header) {
        assertEquals(
            header,
            waitForElement(By.tagName("h1")).getText()
        );
    }

    @Then("I click on login in top right corner")
    public void i_click_on_login() {
        waitForElement(By.cssSelector("#navigation>a:last-of-type")).click();
        pause(BTN_DELAY);
    }
    
    @Then("I click on logout in top right corner")
    public void i_click_on_logout() {
        waitForElement(By.id("#logout")).click();
        pause(BTN_DELAY);
    }
    
    @Then("I click on my name in the top right corner")
    public void i_click_on_name_top_right() {
        waitForElement(By.cssSelector("#profile-link")).click();
        pause(BTN_DELAY);
    }

    @Then("I click on register")
    public void i_click_on_register() {
        waitForElement(By.cssSelector("#content>div.form>div.buttons>a")).click();
        pause(BTN_DELAY);
    }

    @Then("I enter registration info: {string}, {string}, {string}, {string}, {string}")
    public void i_enter_my_registration_info(String username, String pwd,
                                             String pwd2, String fn, String ln) {
        waitForElement(By.id("username")).sendKeys(username);
        waitForElement(By.id("password")).sendKeys(pwd);
        waitForElement(By.id("password2")).sendKeys(pwd2);
        waitForElement(By.id("firstName")).sendKeys(fn);
        waitForElement(By.id("lastName")).sendKeys(ln);
    }

    @Then("I click register")
    public void i_click_register() {
        waitForElement(By.tagName("button")).click();
        pause(BTN_DELAY);
    }

    @Then("I log in with {string}, {string}")
    public void login(String username, String password) {
        waitForElement(By.linkText("Login")).click();
        waitForElement(By.id("username")).sendKeys(username);
        waitForElement(By.id("password")).sendKeys(password);
        waitForElement(By.tagName("button")).click();
        pause(BTN_DELAY);
    }

    @Then("I should see login error")
    public void i_should_see_login_error() {
        waitForElement(By.className("error"));
    }
    
    @Then("I should see register page warning {string}")
    public void i_should_see_register_page_warning(final String warning) {
        assertEquals(
            warning,
            waitForElement(By.cssSelector("#content>div.form>p.error"))
                .getAttribute("innerHTML")
        );
    }

    @Then("I click login")
    public void i_click_login() {
        waitForElement(By.tagName("button")).click();
        pause(BTN_DELAY);
    }

    @Then("I click logout")
    public void i_click_logout() {
        waitForElement(By.id("logout")).click();
        pause(BTN_DELAY);
    }
    
    @Then("The create user button should be disabled")
    public void the_create_user_button_should_be_disabled() {
        assertNotNull(
            waitForElement(By.cssSelector("#content>div.form>div.buttons>button"))
                .getAttribute("disabled")
        );
    }
    @Then("I reset the registration form")
    public void i_reset_the_registration_form() {
        waitForElements(By.tagName("input"))
            .parallelStream()
            .forEach(e -> e.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE)));
    }
    
    private static void pause(final long time) {
        try {Thread.sleep(time);}
        catch(final InterruptedException e) {e.printStackTrace();}
    }
    private WebElement waitForElement(final By by) {
        return new WebDriverWait(driver,10).until(ExpectedConditions.visibilityOfElementLocated(by));
    }
    private List<WebElement> waitForElements(final By by) {
        return new WebDriverWait(driver,10).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(by));
    }
    @Then("I click the create event button")
    public void i_click_the_create_event_button() {
        waitForElement(By.cssSelector("#content>div>h2>a")).click();
        pause(BTN_DELAY);
    }
    @Then("I set the event name to {string}")
    public void i_set_the_event_name_to(final String s) {
        waitForElement(By.id("name")).sendKeys(s);
    }
    @Then("I set the event description to {string}")
    public void i_set_the_event_description_to(final String s) {
        waitForElement(By.id("description")).sendKeys(s);
    }
    @Then("I set the search name to {string}")
    public void i_set_the_search_name_to(final String s) {
        waitForElement(
            By.cssSelector(
                "#content>div.form>div:nth-of-type(3)>" +
                    "div.form>div.group:first-of-type>" +
                    "input"
            )
        ).sendKeys(s);
    }
    @Then("I set the search zipcode to {word}")
    public void i_set_the_search_zipcode_to(final String s) {
        waitForElement(
            By.cssSelector(
                "#content>div.form>div:nth-of-type(3)>" +
                    "div.form>div.group:nth-of-type(2)>" +
                    "input"
            )
        ).sendKeys(s);
    }
    @Then("I set the search genre to {string}")
    public void i_set_the_search_genre_to(final String s) {
		new Select(
			waitForElement(
                By.cssSelector(
                    "#content>div.form>div:nth-of-type(3)>" +
                        "div.form>div.group:nth-of-type(3)>" +
                        "select"
                )
            )
		).selectByVisibleText(s);
	}
    @Then("I set the search start date to {string}")
    public void i_set_the_search_start_date_to(String s) {
        if(s.contentEquals("today"))
            s = java.time.format.DateTimeFormatter.ofPattern("yyyy\tMMdd")
                                                  .format(java.time.LocalDate.now());
        waitForElement(
            By.cssSelector(
                "#content>div.form>div:nth-of-type(3)>" +
                    "div.form>div.group:nth-of-type(4)>" +
                    "div:first-of-type>input"
            )
        ).sendKeys(s);
    }
    @Then("I set the search end date to {string}")
    public void i_set_the_search_end_date_to(String s) {
        if(s.contentEquals("next year"))
            s = java.time.format.DateTimeFormatter.ofPattern("yyyy\tMMdd")
                                                  .format(java.time.LocalDate.now().plusYears(1));
        waitForElement(
            By.cssSelector(
                "#content>div.form>div:nth-of-type(3)>" +
                    "div.form>div.group:nth-of-type(4)>" +
                    "div:nth-of-type(2)>input"
            )
        ).sendKeys(s);
    }
    @Then("I click the search button")
    public void i_click_the_search_button() {
        waitForElement(
            By.cssSelector("#content>div.form>div:nth-of-type(3)>div.form>div.group:nth-of-type(5)>button")
        ).click();
        pause(BTN_DELAY);
    }
    @Then("I click search result {int}")
    public void i_click_search_result(final int i) {
        waitForElement(
            By.cssSelector(
                "#content>div.form>div:nth-of-type(3)>" +
                    "div.form>ul>li:nth-of-type("+(i+1)+")"
            )
        ).click();
    }
    @Then("I invite user {string}")
    public void i_invite_user(String s) {
        if(s.contentEquals("myself"))
            s = "ttrojan";
        final WebElement e = waitForElement(
            By.cssSelector("#content>div.form>div:nth-of-type(4)>div.form")
        );
        e.findElement(By.cssSelector("div.form-group>input")).sendKeys(s);
        e.findElement(By.cssSelector("ul>li:first-of-type")).click();
    }
    @Then("I click the finalize button")
    public void i_click_the_finalize_button() {
        waitForElement(By.cssSelector("#content>div.form>div:nth-of-type(5)>button"))
            .click();
        pause(BTN_DELAY);
    }
    @Then("I should see the event name {string}")
    public void i_should_see_the_event_name(final String s) {
        assertEquals(
            s,
            waitForElement(By.cssSelector("#content>div>h1")).getText()
        );
    }
    @Then("I should see the event description {string}")
    public void i_should_see_the_event_description(final String s) {
        assertEquals(
            s,
            waitForElement(By.cssSelector("#content>div>p")).getText()
        );
    }
    @Then("I should see {int} events")
    public void i_should_see_events(final int i) {
        assertEquals(
            i,
            waitForElements(By.cssSelector("#content>div>div:first-of-type>.event"))
                .size()
        );
    }
    @Then("I should see {int} invitees")
    public void i_should_see_invitees(final int i) {
        assertEquals(
            i,
            waitForElements(By.cssSelector("#content>div>div:nth-of-type(2)>.invitation"))
                .size()
        );
    }
    @Then("I click the logo in the top left")
    public void i_click_the_logo_in_the_top_left() {
        pause(100);
        waitForElement(By.cssSelector("#navigation>a:first-of-type"))
            .click();
        pause(BTN_DELAY);
    }
    @Then("I should see date with name {string}")
    public void i_should_see_date_with_name(final String s) {
        assertTrue(
            waitForElements(By.cssSelector("#content>div:first-of-type>ul:first-of-type>li"))
                .parallelStream()
                .anyMatch(e -> e.getText().contentEquals(s))
        );
    }
    
    @Then("I should see an invitation to the date {string}")
    public void i_should_see_an_invitation_to_the_date(final String s) {
        assertTrue(
            waitForElements(By.cssSelector("#content>div:nth-of-type(2)>ul:first-of-type>li"))
                .parallelStream()
                .anyMatch(e -> e.getText().contentEquals(s))
        );
    }
    @Then("I click the invitation to the group date {string}")
    public void i_click_the_invitation_to_the_group_date(final String s) {
        final Optional<WebElement> we =
            waitForElements(By.cssSelector("#content>div:nth-of-type(2)>ul:first-of-type>li"))
                .parallelStream()
                .filter(e -> e.getText().contentEquals(s))
                .findFirst();
        assertTrue(we.isPresent());
        we.get().click();
        pause(BTN_DELAY);
    }
    @Then("I should see information for the group date {string}")
    public void i_should_see_information_for_the_group_date(final String s) {
        i_should_see_header("Invite: "+s);
    }
    @Then("I decline the group date")
    public void i_decline_the_group_date() {
        waitForElement(By.cssSelector("#content>div:first-of-type>button"))
            .click();
        pause(BTN_DELAY);
    }
    
    @Then("I should see {int} invites")
    public void i_should_see_invites(final int i) {
        if(i == 0)
            assertTrue(
                waitForElement(By.cssSelector("#content>div:nth-of-type(2)>ul"))
                    .findElements(By.tagName("li"))
                    .isEmpty()
            );
        else
            assertEquals(
                i,
                waitForElements(By.cssSelector("#content>div:nth-of-type(2)>ul>li"))
                    .size()
            );
    }
    
    @Then("The create event button should be disabled")
    public void the_create_event_button_should_be_disabled() {
        assertNotNull(
            waitForElement(By.cssSelector("#content>div.form>div.buttons>button"))
                .getAttribute("disabled")
        );
    }
    @Then("I remove the event name")
    public void i_remove_the_event_name() {
        waitForElement(By.id("name")).sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
    }
    @Then("I remove the event description")
    public void i_remove_the_event_description() {
        waitForElement(By.id("description")).sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE));
    }
    @Then("I remove the invites")
    public void i_remove_the_invites() {
        waitForElements(
            By.cssSelector(
                "#content>div.form>" +
                    "div:nth-of-type(4)>" +
                    "div.invitation>" +
                    "span.float-right.cursor-pointer"
            )
        ).forEach(WebElement::click);
    }
    
    
    // block
    @Then("I enter {string} in search to block")
    public void i_enter_in_search_to_block(String user) {
    	waitForElement(By.id("search-users-block")).sendKeys(user);
    	pause(1000);
    }
    @Then("I should see {string} as a option to block")
    public void i_should_see_block_option(String user) {
    	List<WebElement> userList = waitForElements(By.cssSelector("#blocked-users li"));
    	assertNotNull(userList);
    	for(WebElement u : userList) {
    		if(u.getText().trim().equals(user)) {
    			assertTrue(true);
    			return;
    		}
    	}
    	assertTrue(false);
    }
    @Then("I select {string} to block")
    public void i_select_user_to_block(String user) {
    	List<WebElement> userList = waitForElements(By.cssSelector("#blocked-users li"));
    	assertNotNull(userList);
    	for(WebElement u : userList) {
    		if(u.getText().trim().equals(user)) {
    			u.click();
    			return;
    		}
    	}
    	assertTrue(false);
    }
    @Then("{string} should be on my list of blocked users")
    public void user_should_be_on_my_block_list(String user) {
    	List<WebElement> userList = waitForElements(By.id("blocked-user-list"));
    	for(WebElement u : userList) {
    		if(u.findElement(By.cssSelector("span:first-child")).getText().trim().equals(user)) {
    			assertTrue(true);
    			return;
    		}
    	}
    	assertTrue(false);
    }
    @Then("{string} should not be an option to block")
    public void user_should_not_be_block_option(String user) {
    	pause(1000);
    	List<WebElement> userList = driver.findElements(By.cssSelector("#blocked-users li"));
    	if(userList == null) {
    		assertTrue(true);
    		return;
    	}
    	for(WebElement u : userList) {
    		if(u.getText().trim().equals(user)) {
    			assertTrue(false);
    			return;
    		}
    	}
    	assertTrue(true);
    }
    @Then("I click on the X besides {string} on my blocked list")
    public void i_click_x_on_blocked_list(String user) {
    	List<WebElement> userList = waitForElements(By.id("blocked-user-list"));
    	for(WebElement u : userList) {
    		if(u.findElement(By.cssSelector("span:first-child")).getText().trim().equals(user)) {
    			u.findElement(By.cssSelector("span:nth-child(2)")).click();
    			return;
    		}
    	}
    	assertTrue(false);
    }
    @Then("{string} should not be on my list of blocked users anymore")
    public void user_shouldnt_be_blocked_anymore(String user) {
    	pause(1000);
    	List<WebElement> userList = driver.findElements(By.id("blocked-user-list"));
    	if(userList == null) {
    		assertTrue(true);
    		return;
    	}
    	for(WebElement u : userList) {
    		if(u.findElement(By.cssSelector("span:first-child")).getText().trim().equals(user)) {
    			assertTrue(false);
    			return;
    		}
    	}
    	assertTrue(true);
    }
    @Then("Then I attempt to invite {string}")
    public void i_attempt_to_invite_user(String user) {
    	
    }
    @Then("{string} should be a disabled invite option")
    public void user_should_be_disabled_invite_option(String user) {
    	
    }
    
    @After()
    public void after() {
        driver.quit();
        try {
            final Database db = Database.load();
            db.drop();
            db.create();
            db.users
              .dao()
              .create(UserTest.createUser(
                "ttrojan",
                "asdfjkl1",
                "Tommy",
                "Trojan"
              ));
            db.users
            .dao()
            .create(UserTest.createUser(
              "htrojan",
              "asdfjkl1",
              "Hecuba",
              "Trojan"
            ));
        } catch(final Exception ignored) {}
    }
}
