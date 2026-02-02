package tests;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import qamarvel.framework.base.BaseTest;
import qamarvel.pageObjects.CartPage;
import qamarvel.pageObjects.LandingPage;
import qamarvel.pageObjects.ProductCatalogue;

public class ErrorValidationsTest extends BaseTest {

	@Test(groups= {"ErrorHandling"})
	public void LoginErrorValidation() throws IOException, InterruptedException {

		LandingPage lp = new LandingPage(driver);
		lp.loginApplication("anshika@gmail.com", "Iamki000");
		Assert.assertEquals("Incorrect email or password.", lp.getErrorMessage());

	}
	

	@Test
	public void ProductErrorValidation() throws IOException, InterruptedException
	{

//		String productName = "ZARA COAT 3";
//	//	ProductCatalogue productCatalogue = lp.loginApplication("rahulshetty@gmail.com", "Iamking@000");
//		List<WebElement> products = productCatalogue.getProductList();
//		productCatalogue.addProductToCart(productName);
//	//	CartPage cartPage = productCatalogue.goToCartPage();
//	//	Boolean match = cartPage.VerifyProductDisplay("ZARA COAT 33");
//		Assert.assertFalse(match);
		
	

	}

	
	

}
