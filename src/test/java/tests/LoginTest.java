package tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import qamarvel.framework.base.BaseTest;
import qamarvel.pageObjects.LandingPage;
import qamarvel.pageObjects.LoginPage;

public class LoginTest extends BaseTest {

	LoginPage lp;
	LandingPage landingPage;

	@BeforeMethod
	public void setup() {
		landingPage = new LandingPage(driver);
		lp = new LoginPage(driver);

	}

	/*
	 * @Test public void goToDashboard() { System.out.println("Go To DashBoard");
	 * lp1.goTo();
	 * 
	 * }
	 */
	@Test
	public void LoginTestEmail() {

		log.info("Navigating to Web App");

	}

}
