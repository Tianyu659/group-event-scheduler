package cucumber;

import csci310.Database;
import csci310.models.UserTest;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

/**
 * Run all the cucumber tests in the current package.
 */
@RunWith(Cucumber.class)
@CucumberOptions(strict = true)
//@CucumberOptions(strict = true, features = {"src/test/resources/cucumber/x.feature", "src/test/resources/cucumber/y.feature"}) will run only feature files x.feature and y.feature.
public class RunCucumberTests {
	@BeforeClass
	public static void setup() throws IOException, SQLException {
		if (Files.exists(Path.of("run.sqlite3"))) {
			if (Files.exists(Path.of("run.original.sqlite3"))) {
				Files.delete(Path.of("run.original.sqlite3"));
			}

			Files.copy(Path.of("run.sqlite3"), Path.of("run.original.sqlite3"));
			Database database = Database.load();
			database.drop();
			database.create();

			database.users.dao().create(UserTest.createUser(
					"ttrojan",
					"asdfjkl1",
					"Tommy",
					"Trojan"));
		}

		WebDriverManager.chromedriver().setup();
	}
}