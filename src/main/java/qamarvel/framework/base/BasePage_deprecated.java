package qamarvel.framework.base;

import java.util.Set;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import qamarvel.framework.auth.session.SessionManager;
import qamarvel.framework.auth.session.SessionStore;
import qamarvel.framework.config.ConfigReader;
import qamarvel.framework.driver.DriverFactory;
import qamarvel.pageObjects.LoginPage;

public  class BasePage_deprecated {

	protected WebDriver driver;

	/*
	 * ===================================================== SUITE-LEVEL AUTH
	 * SESSION (RUNS ONCE) =====================================================
	 */
	@BeforeSuite(alwaysRun = true)
	public void initializeAuthSession() {

		if (SessionStore.isInitialized()) {
			return;
		}

		WebDriver setupDriver = null;

		try {
			DriverFactory.initDriver();
			setupDriver = DriverFactory.getDriver();
			setupDriver.get(ConfigReader.get("baseUrl"));
			LoginPage loginPage = new LoginPage(setupDriver);
			loginPage.login(ConfigReader.get("username"), ConfigReader.get("password"));
		
			SessionManager.captureSession(setupDriver);
			
			Set<Cookie> cookies = setupDriver.manage().getCookies();

			System.out.println("===== COOKIES AFTER UI LOGIN =====");
			for (Cookie cookie : cookies) {
			    System.out.println(
			        cookie.getName() + " = " + cookie.getValue()
			    );
			}
			
			JavascriptExecutor js = (JavascriptExecutor) setupDriver;

			System.out.println("===== LOCAL STORAGE =====");
			System.out.println(
			    js.executeScript("return window.localStorage;")
			);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DriverFactory.quitDriver();
		}
	}

	/*
	 * =======TEST-LEVEL DRIVER SETUP
	 * (RUNS PER TEST) ====================
	 */
	@BeforeTest(alwaysRun = true)
	public void setUp() {

		DriverFactory.initDriver();
		driver = DriverFactory.getDriver();

		// Inject authenticated session
		// SessionManager.injectSession(driver, ConfigReader.get("baseUrl"));
		SessionManager.injectSession(driver, ConfigReader.get("DashboardUrl"));

		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/*
	 * ===================================================== CLEANUP
	 * =====================================================
	 */
	@AfterTest(alwaysRun = true)
	public void tearDown() {

		DriverFactory.quitDriver();
	}
}
