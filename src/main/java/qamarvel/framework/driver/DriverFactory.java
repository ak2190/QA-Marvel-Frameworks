package qamarvel.framework.driver;

import io.github.bonigarcia.wdm.WebDriverManager;
import qamarvel.framework.config.ConfigReader;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverFactory {
	
	/*
	 * DriverFactory is responsible for:
	 * Creating a browser instance (based on value read from config file)
	 * Making sure each test thread gets its own driver
	 * Providing a global way to access the driver
	 * Cleaning up when the test finishes
	 * 
	 * Additional Notes: static makes the driver manager globally accessible, and 
	 * ThreadLocal makes the actual browser private per thread — together they enable clean, safe parallel execution.
	 */
	private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

	public static void initDriver() {

		// preferred browser selection
		String browser = ConfigReader.get("browser");

		switch (browser.toLowerCase()) {
		case "firefox":
			WebDriverManager.firefoxdriver().setup();
			driver.set(new FirefoxDriver());
			break;

		case "edge":
			WebDriverManager.edgedriver().setup();
			driver.set(new EdgeDriver());
			break;

		case "chrome":

		default:
			WebDriverManager.chromedriver().setup();
			// stores driver in the current thread
			driver.set(new ChromeDriver());
			break;

		}

		// maximize window
		getDriver().manage().window().maximize();
	}

	// returns driver for the current thread only
	public static WebDriver getDriver() {

		return driver.get();
	}

	public static void quitDriver() {
		// closes the browser & ends the WebDriver session
		getDriver().quit();
		// clears the thread’s stored driver & prevents memory leaks in long test runs
		driver.remove();
	}
}
