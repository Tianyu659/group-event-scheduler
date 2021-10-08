package cucumber;

import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;

import java.util.List;
import java.util.logging.Level;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

/**
 * Step definitions for Cucumber tests.
 */
public class StepDefinitions {
    private static final String ROOT_URL = "http://localhost:8080/";
    
    private static boolean init = true;
    private static WebDriver driver;
    @Before
    public static void setUp() {
        if(init) {
            init = false;
            final LoggingPreferences lp = new LoggingPreferences();
            lp.enable(LogType.PERFORMANCE,Level.ALL);
            final ChromeOptions o = new ChromeOptions();
            o.setCapability(CapabilityType.LOGGING_PREFS,lp);
            o.setCapability("goog:loggingPrefs",lp);
            o.addArguments();
            driver = new ChromeDriver(o);
            driver.manage().window().maximize();
            Runtime.getRuntime().addShutdownHook(new Thread(() -> driver.quit()));
        }
    }
    
    @Given("I am on the {string} page")
    public void i_am_on_the_page(String page) {
        driver.get(ROOT_URL + (page.contentEquals("index")? "" : (page+".jsp")));
        // Disable alerts.
        ((JavascriptExecutor)driver).executeScript("window.alert = () => {}");
    }
    
    @When("I click the link {string}")
    public void i_click_the_link(String linkText) {
        driver.findElement(By.linkText(linkText)).click();
    }
    @When("I enter the username {string}")
    public void i_enter_the_username(String username) {
        driver.findElement(By.cssSelector("#login_box_container>div>form>div>input[type='text']"))
              .sendKeys(username);
    }
    
    @And("I enter the password {string}")
    public void i_enter_the_password(String password) {
        driver.findElement(By.cssSelector("#login_box_container>div>form>div>input[type='password']"))
              .sendKeys(password);
    }
    @And("I click the {string} button")
    public void i_click_the_button(String button) {
        final List<WebElement> l = driver.findElements(By.tagName("button"));
        for(final WebElement e : l)
            if(e.getText().equalsIgnoreCase(button)) {
                e.click();
                return;
            }
    }
    
    @Then("I should see header {string}")
    public void i_should_see_header(String header) {
        assertTrue(driver.findElement(By.cssSelector("h2")).getText().contains(header));
    }
    @Then("I should see text {string}")
    public void i_should_see_text(String text) {
        assertTrue(driver.getPageSource().contains(text));
    }
    @Then("I am redirected to the {string} page")
    public void i_am_redirected_to_the_page(String page) {
        assertTrue(driver.getCurrentUrl().matches(ROOT_URL + page + "/?"));
    }
    
    @SuppressWarnings("unused") // 'success' and 'failure' are used implicitly.
    enum ReturnPacket {
        success,failure,unrelated;
        
        static ReturnPacket of(final String s) {
            try {return ReturnPacket.valueOf(s.substring(0,s.length() - 1));}
            catch(final IllegalArgumentException e) {return unrelated;}
        }
    }
    @Then("The login is a {string}")
    public void the_login_is_a(final String success) {
        // This function checks the packets specifically
        final LogEntries entries = driver.manage().logs().get(LogType.PERFORMANCE);
        final ReturnPacket target = ReturnPacket.of(success);
        for(final LogEntry e : entries) {
            final ReturnPacket entry = ReturnPacket.of(e.getMessage());
            if(entry != ReturnPacket.unrelated)
                assertSame(entry,target);
        }
    }
}
