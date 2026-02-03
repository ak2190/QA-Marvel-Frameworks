package qamarvel.framework.base;

import java.util.Set;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import qamarvel.framework.auth.AuthState;
import qamarvel.framework.auth.acquire.AuthAcquirer;
import qamarvel.framework.auth.acquire.ApiAuthAcquirer;
import qamarvel.framework.auth.apply.AuthApplier;
import qamarvel.framework.auth.apply.LocalStorageAuthApplier;
import qamarvel.framework.auth.verify.DashboardAuthVerifier; 
import qamarvel.framework.auth.verify.AuthVerifier;
import qamarvel.framework.auth.session.SessionManager;
import qamarvel.framework.auth.session.SessionStore;
import qamarvel.framework.config.ConfigReader;
import qamarvel.framework.driver.DriverFactory;
import qamarvel.pageObjects.LoginPage;

public abstract class BaseTest {

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
				System.out.println(cookie.getName() + " = " + cookie.getValue());
			}

			/*
			 * JavascriptExecutor js = (JavascriptExecutor) setupDriver;
			 * 
			 * System.out.println("===== LOCAL STORAGE =====");
			 * System.out.println(js.executeScript("return window.localStorage;"));
			 */

		} catch (Exception e) {
			
			e.printStackTrace();
		} finally {
			DriverFactory.quitDriver();
		}
	}

	/*
	 * =======TEST-LEVEL DRIVER SETUP (RUNS PER TEST) ====================
	 */
	@BeforeTest(alwaysRun = true)
	public void setUp() {

		DriverFactory.initDriver();
		driver = DriverFactory.getDriver();

		String authMode = ConfigReader.get("auth.mode");
		// Inject authenticated session
		// SessionManager.injectSession(driver, ConfigReader.get("baseUrl"));
		if ("SESSION".equalsIgnoreCase(authMode)) {

			// existing behavior (unchanged)
			SessionManager.injectSession(driver, ConfigReader.get("DashboardUrl"));

		} else if ("API_HEADER".equalsIgnoreCase(authMode)) {

		// new path (no session reuse)
			AuthAcquirer acquirer = new ApiAuthAcquirer();
		    AuthApplier applier = new LocalStorageAuthApplier();
			
			AuthState state = acquirer.acquire();
			 // blank page (no app JS yet)
			driver.get("https://rahulshettyacademy.com");
		    
		    //inject auth BEFORE app loads
		    applier.apply(driver, state);

		    //Load the application


		    driver.get(ConfigReader.get("DashboardUrl"));

		    //Verify authenticated state
		    AuthVerifier verifier = new DashboardAuthVerifier();
		    verifier.verify(driver);

		} else {

			throw new RuntimeException("Unsupported auth.mode: " + authMode);
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
