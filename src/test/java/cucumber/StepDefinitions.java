package cucumber;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

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

	@Then("I enter my registration info")
	public void i_enter_my_registration_info() {
		driver.findElement(By.id("username")).sendKeys("noahbkim");
		driver.findElement(By.id("password")).sendKeys("asdfjkl;");
		driver.findElement(By.id("password2")).sendKeys("asdfjkl;");
		driver.findElement(By.id("first-name")).sendKeys("Noah");
		driver.findElement(By.id("last-name")).sendKeys("Kim");
	}

	@Then("I click register")
	public void i_click_register() {
		driver.findElement(By.tagName("button")).click();
	}

	@Then("I enter my login info")
	public void i_enter_my_login_info() {
		driver.findElement(By.id("username")).sendKeys("noahbkim");
		driver.findElement(By.id("password")).sendKeys("asdfjkl;");
	}

	@Then("I log in with {string}, {string}")
	public void login(String username, String password) {
		driver.findElement(By.linkText("Login")).click();
		driver.findElement(By.id("username")).sendKeys(username);
		driver.findElement(By.id("password")).sendKeys(password);
		driver.findElement(By.tagName("button")).click();
	}

	@Then("I enter my login info incorrectly")
	public void i_enter_my_login_info_incorrectly() {
		driver.findElement(By.id("username")).sendKeys("noahbkim");
		driver.findElement(By.id("password")).sendKeys("oops");
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

	@After()
	public void after() {
		driver.quit();
	}
}
