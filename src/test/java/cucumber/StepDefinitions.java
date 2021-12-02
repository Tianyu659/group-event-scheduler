package cucumber;

import csci310.Database;
import csci310.models.UserTest;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    }
    
    @Then("I click on logout in top right corner")
    public void i_click_on_logout() {
        waitForElement(By.id("logout")).click();
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
        pause();
    }

    @Then("I log in with {string}, {string}")
    public void login(String username, String password) {
        waitForElement(By.linkText("Login")).click();
        waitForElement(By.id("username")).sendKeys(username);
        waitForElement(By.id("password")).sendKeys(password);
        waitForElement(By.tagName("button")).click();
        pause();
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
        pause();
    }

    @Then("I click logout")
    public void i_click_logout() {
        waitForElement(By.id("logout")).click();
        pause();
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
    
    private static void pause() {
        try {Thread.sleep(StepDefinitions.BTN_DELAY);}
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
        pause();
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
        e.findElement(By.cssSelector("div.form-group>input")).sendKeys(Keys.chord(Keys.CONTROL,"a"),s);
        e.findElement(By.cssSelector("ul>li:first-of-type")).click();
    }
    @Then("I click the finalize button")
    public void i_click_the_finalize_button() {
        waitForElement(By.cssSelector("#content>div.form>div:nth-of-type(5)>button"))
            .click();
        pause();
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
            waitForElements(By.cssSelector("#content>div>div:nth-of-type(2)>.event"))
                .size()
        );
    }
    @Then("I should see {int} invitees")
    public void i_should_see_invitees(final int i) {
        assertEquals(
            i,
            waitForElements(By.cssSelector("#content>div>div:nth-of-type(3)>.invitation"))
                .size()
        );
    }
    @Then("I click the logo in the top left")
    public void i_click_the_logo_in_the_top_left() {
        waitForElement(By.cssSelector("#navigation>a:first-of-type"))
            .click();
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
        pause();
    }
    @Then("I should see information for the group date {string}")
    public void i_should_see_information_for_the_group_date(final String s) {
        i_should_see_header("Invite: "+s);
    }
    @Then("I decline the group date")
    public void i_decline_the_group_date() {
        waitForElement(By.cssSelector("#content>div:first-of-type>button"))
            .click();
        pause();
    }
    
    @Then("I should see {int} open invites")
    public void i_should_see_open_invites(final int i) {
        if(i == 0)
            assertTrue(
                waitForElement(By.cssSelector("#content>div:nth-of-type(2)>ul"))
                    .findElements(By.tagName("li"))
                    .parallelStream()
                    .filter(e -> e.findElements(By.className("tag")).isEmpty())
                    .findAny()
                    .isEmpty()
            );
        else
            assertEquals(
                i,
                waitForElement(By.cssSelector("#content>div:nth-of-type(2)>ul"))
                    .findElements(By.tagName("li"))
                    .parallelStream()
                    .filter(e -> e.findElements(By.className("tag")).isEmpty())
                    .count()
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
    
    @Then("I register {int} minions")
    public void i_register_minions(int i) {
        while(i-- != 0)
        {
            i_click_on_register();
            i_enter_my_registration_info("minion"+i,"asdfjkl1","asdfjkl1","minion_"+i,"helper");
            i_click_register();
        }
    }
    @Then("I invite {int} minions")
    public void i_invite_minions(final int i) {
        for(int j = 0;j < i;++j)
            i_invite_user("minion_"+j);
    }
    @Then("Minion {int} logs in")
    public void minion_logs_in(final int i) {
        login("minion"+i,"asdfjkl1");
    }
    @Then("The minion clicks the invitation to {string}")
    public void the_minion_clicks_the_invitation_to(final String s) {
        i_click_the_invitation_to_the_group_date(s);
    }
    @Then("The minion is available for event {int} with interest {int}")
    public void the_minion_is_available_for_event_with_interest(final int i,final int i1) {
        final WebElement event = waitForElements(
            By.cssSelector(
                "#content>div>div.form>div.event.form>div.group.flex"
            )
        ).get(i);
        new Select(
            event.findElement(By.tagName("select"))
        ).selectByVisibleText("Available");
        event.findElement(By.tagName("input"))
             .sendKeys(Keys.chord(Keys.CONTROL,"a"),String.valueOf(i1));
    }
    @Then("The minion accepts invitation")
    public void the_minion_accepts_invitation() {
        waitForElement(
            By.cssSelector(
                "#content>div>div.form>button"
            )
        ).click();
        pause();
    }
    @Then("The minion logs out")
    public void the_minion_logs_out() {
        i_click_logout();
    }
    @Then("I click the event {string}")
    public void i_click_the_event(final String s) {
        final Optional<WebElement> we = waitForElements(
            By.cssSelector(
                "#content>div:first-of-type>ul>li"
            )
        ).parallelStream()
            .filter(e -> e.getText().contentEquals(s))
            .findFirst();
        assertTrue(we.isPresent());
        we.get().click();
        pause();
    }
    private static final Pattern CLS_BEST = Pattern.compile(".*best.*"),
                                 INTEREST = Pattern.compile(".*(\\d+)/\\d+\\D+((?:\\d*\\.)?\\d+).*");
    @Then("Event {int} should be highlighted with availability {int} and interest {float}")
    public void event_should_be_highlighted_with_availability_and_interest(final int i,final int i1,final double d) {
        final Iterator<WebElement> events = waitForElements(By.className("event")).iterator();
        for(int j = 0;events.hasNext();++j) {
            final WebElement e = events.next();
            assertEquals(i == j,CLS_BEST.matcher(e.getAttribute("class")).matches());
            if(i == j) {
                final Matcher m = INTEREST.matcher(
                    e.findElement(By.cssSelector("p:first-of-type"))
                        .getText()
                );
                assertTrue(m.find());
                assertEquals(i1,Integer.parseInt(m.group(1)));
                assertEquals(d,Double.parseDouble(m.group(2)),0.01);
            }
        }
    }
    @Then("I should see {int} responses")
    public void i_should_see_responses(final int i) {
        assertEquals(
            i,
            waitForElements(By.className("invitation")).size()
        );
    }
    private void alert(final boolean accept) {
        final Alert alert = driver.switchTo().alert();
        if(accept) alert.accept();
        else alert.dismiss();
    }
    private static final Pattern INVITATION = Pattern.compile(".*Invitation to (\\w+).*");
    @Then("I delete the invitation to minion {int}")
    public void i_delete_the_invitation_to_minion(final int i) {
        final String minion = "minion_"+i;
        final Optional<WebElement> we =
            waitForElements(By.className("invitation"))
                .parallelStream()
                .filter(e -> {
                    final Matcher m = INVITATION.matcher(
                        e.findElement(
                            By.cssSelector("h3>span:first-of-type")
                        ).getText()
                    );
                    return m.matches() && m.group(1).contentEquals(minion);
                })
                .findFirst();
        assertTrue(we.isPresent());
        we.get().findElement(By.cssSelector("h3>span:last-of-type")).click();
        alert(true);
        pause();
    }
    private void clickDeleteEvent(final int i,final boolean accept) {
        waitForElements(By.className("event"))
            .get(i)
            .findElement(By.cssSelector("h4>span.cursor-pointer"))
            .click();
        alert(accept);
        pause();
    }
    @Then("I delete event {int}")
    public void i_delete_event(final int i) {
        clickDeleteEvent(i,true);
    }
    @Then("The minion should see {int} invites")
    public void the_minion_should_see_invites(final int i) {
        i_should_see_open_invites(i);
    }
    
    private void clickDeleteInvite(final int i,final boolean accept) {
        waitForElements(By.className("invitation"))
            .get(i)
            .findElement(By.cssSelector("h3>span:last-of-type"))
            .click();
        alert(accept);
        pause();
    }
    @Then("I cancel deleting event {int}")
    public void i_cancel_deleting_event(final int i) {
        clickDeleteEvent(i,false);
    }
    @Then("I cancel deleting invite {int}")
    public void i_cancel_deleting_invite(final int i) {
        clickDeleteInvite(i,false);
    }
    
    @Then("I should see {int} group dates")
    public void i_should_see_group_dates(final int i) {
        if(i == 0)
            assertTrue(
                waitForElement(By.cssSelector("#content>div:first-of-type>ul"))
                    .findElements(By.tagName("li"))
                    .isEmpty()
            );
        else
            assertEquals(
                i,
                waitForElements(By.cssSelector("#content>div:first-of-type>ul>li"))
                    .size()
            );
    }
    
    @Then("I delete invite {int}")
    public void i_delete_invite(final int i) {
        clickDeleteInvite(i,true);
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
    @Then("I attempt to invite {string}")
    public void i_attempt_to_invite_user(String user) {
        waitForElement(By.id("search-users-invite")).sendKeys(user);
    }
    @Then("{string} should be a disabled invite option")
    public void user_should_be_disabled_invite_option(String user) {
    	List<WebElement> userList = waitForElements(By.id("invited-users"));
    	for(WebElement u : userList) {
    		if(u.findElement(By.cssSelector("span:first-child")).getText().trim().equals(user)) {
    			assertEquals("unavailable", u.findElement(By.cssSelector("span.float-right")).getText().trim());
    			return;
    		}
    	}
    	assertTrue(false);
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
