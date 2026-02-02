package tests;

import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.response.Response;
import qamarvel.framework.auth.AuthApiClient;
import qamarvel.framework.base.BaseTest;
import qamarvel.pageObjects.LandingPage;
import qamarvel.pageObjects.LoginPage;

public class LandingPageTest extends BaseTest {

	LoginPage lp;
	LandingPage lp1;
	
	@BeforeMethod
	public void setup() {
		lp = new LoginPage(driver);
		lp1 = new LandingPage(driver);
		
	}
	
	/*
	 * @Test public void goToDashboard() { System.out.println("Go To DashBoard");
	 * lp1.goTo();
	 * 
	 * }
	 */
	@Test
	public void goToApp() {
		
		System.out.println("Inside App");
		
		
	}
	
	
	
	
}
