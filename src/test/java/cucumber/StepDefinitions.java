package cucumber;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Step definitions for Cucumber tests.
 */
public class StepDefinitions {
    private static final String ROOT_URL = "http://localhost:8080/";

    private final WebDriver driver = new ChromeDriver();

    @Given("I am on the index page")
    public void i_am_on_the_index_page() {
        driver.get(ROOT_URL);
    }

    @Then("I should see header {string}")
    public void i_should_see_header(String header) {
        assertNotNull(driver.findElement(By.cssSelector("h1")));
    }

    @Then("I click on login in top right corner")
    public void i_click_on_login() {
        driver.findElement(By.linkText("Login")).click();
    }

    @Then("I click on register")
    public void i_click_on_register() {
        driver.findElement(By.linkText("Register")).click();
    }

    @Then("I enter registration info: {string}, {string}, {string}, {string}, {string}")
    public void i_enter_my_registration_info(String username, String pwd, String pwd2, String fn, String ln) {
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(pwd);
        driver.findElement(By.id("password2")).sendKeys(pwd2);
        driver.findElement(By.id("first-name")).sendKeys(fn);
        driver.findElement(By.id("last-name")).sendKeys(ln);
    }

    @Then("I click register")
    public void i_click_register() {
        driver.findElement(By.tagName("button")).click();
    }

    @Then("I log in with {string}, {string}")
    public void login(String username, String password) {
        driver.findElement(By.linkText("Login")).click();
        driver.findElement(By.id("username")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.tagName("button")).click();
    }

    @Then("I should see login error")
    public void i_should_see_login_error() {
        assertNotNull(driver.findElement(By.className("error")));
    }
    
    @Then("I should see register page warning {string}")
    public void i_should_see_register_page_warning(String warning) throws InterruptedException {
        Thread.sleep(1000);
        assertNotNull(driver.findElement(By.className("error")));
        assertEquals(warning, driver.findElement(By.cssSelector(".error")).getAttribute("innerHTML"));
    }

    @Then("I click login")
    public void i_click_login() {
        driver.findElement(By.tagName("button")).click();
    }

    @Then("I click logout")
    public void i_click_logout() throws InterruptedException {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.findElement(By.id("logout")).click();
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
    public void i_clock_the_create_event_button() {
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
        waitForElements(By.cssSelector("#content>div.form>div"))
            .get(2)
            .findElements(By.cssSelector("div.form>div.group"))
            .get(0)
            .findElement(By.cssSelector("input"))
            .sendKeys(s);
    }
    @Then("I set the search zipcode to {word}")
    public void i_set_the_search_zipcode_to(final String s) {
        waitForElements(By.cssSelector("#content>div.form>div"))
            .get(2)
            .findElements(By.cssSelector("div.form>div.group"))
            .get(1)
            .findElement(By.cssSelector("input"))
            .sendKeys(s);
    }
    @Then("I set the search genre to {string}")
    public void i_set_the_search_genre_to(final String s) {
		new Select(
			waitForElements(By.cssSelector("#content>div.form>div"))
			    .get(2)
                .findElements(By.cssSelector("div.form>div.group"))
				.get(2)
				.findElement(By.cssSelector("select"))
		).selectByVisibleText(s);
	}
    @Then("I set the search start date to {string}")
    public void i_set_the_search_start_date_to(String s) {
        if(s.contentEquals("today"))
            s = java.time.format.DateTimeFormatter.ofPattern("yyyy\tMMdd")
                                                  .format(java.time.LocalDate.now());
        waitForElements(By.cssSelector("#content>div.form>div"))
            .get(2)
            .findElements(By.cssSelector("div.form>div.group"))
            .get(3)
            .findElements(By.cssSelector("div"))
            .get(0)
            .findElement(By.cssSelector("input"))
            .sendKeys(s);
    }
    @Then("I set the search end date to {string}")
    public void i_set_the_search_end_date_to(String s) {
        if(s.contentEquals("next year"))
            s = java.time.format.DateTimeFormatter.ofPattern("yyyy\tMMdd")
                                                  .format(java.time.LocalDate.now().plusYears(1));
        waitForElements(By.cssSelector("#content>div.form>div"))
            .get(2)
            .findElements(By.cssSelector("div.form>div.group"))
            .get(3)
            .findElements(By.cssSelector("div"))
            .get(1)
            .findElement(By.cssSelector("input"))
            .sendKeys(s);
    }
    @Then("I click the search button")
    public void i_click_the_search_button() {
        waitForElements(By.cssSelector("#content>div.form>div"))
            .get(2)
            .findElements(By.cssSelector("div.form>div.group"))
            .get(4)
            .findElement(By.cssSelector("button"))
            .click();
        pause(10000);
    }
    @Then("I click search result {int}")
    public void i_click_search_result(final int i) {
        waitForElements(By.cssSelector("#content>div.form>div"))
            .get(2)
            .findElements(By.cssSelector("div.form>ul>li"))
            .get(i)
            .click();
    }
    @Then("I invite user {string}")
    public void i_invite_user(String s) {
        if(s.contentEquals("myself"))
            s = "ttrojan";
        waitForElements(By.cssSelector("#content>div.form>div"))
            .get(3)
            .findElement(By.cssSelector("div.form>div.form-group>input"))
            .sendKeys(s);
        waitForElements(By.cssSelector("#content>div.form>div"))
            .get(3)
            .findElements(By.cssSelector("div.form>ul>li"))
            .get(0)
            .click();
    }
    @Then("I click the finalize button")
    public void i_click_the_finalize_button() {
        waitForElements(By.cssSelector("#content>div.form>div"))
            .get(4)
            .findElement(By.cssSelector("button"))
            .click();
    }
    @Then("I should see the event name {string}")
    public void i_should_see_the_event_name(final String s) {
        assertEquals(
            waitForElement(By.cssSelector("#content>div>h1")).getText(),
            s
        );
    }
    @Then("I should see the event description {string}")
    public void i_should_see_the_event_description(final String s) {
        assertEquals(
            waitForElement(By.cssSelector("#content>div>p")).getText(),
            s
        );
    }
    @Then("I should see {int} events")
    public void i_should_see_events(final int i) {
        assertEquals(
            waitForElements(By.cssSelector("#content>div>div"))
                .get(0)
                .findElements(By.className("event"))
                .size(),
            i
        );
    }
    @Then("I should see {int} invitees")
    public void i_should_see_invitees(final int i) {
        assertEquals(
            waitForElements(By.cssSelector("#content>div>div"))
                .get(1)
                .findElements(By.className("invitation"))
                .size(),
            i
        );
    }
    @Then("I click the logo in the top left")
    public void i_click_the_logo_in_the_top_left() {
        waitForElements(By.cssSelector("#navigation>a"))
            .get(1)
            .click();
    }
    @Then("I should see date with name {string}")
    public void i_should_see_date_with_name(final String s) {
        assertTrue(
            waitForElements(By.cssSelector("#content>div"))
                .get(0)
                .findElements(By.cssSelector("ul>li"))
                .parallelStream()
                .anyMatch(e -> e.getText().contentEquals(s))
        );
    }
    
    @After()
    public void after() {
        driver.quit();
    }
}
