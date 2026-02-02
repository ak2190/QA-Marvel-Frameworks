package qamarvel.framework.utils;

import qamarvel.framework.config.ConfigReader;
import qamarvel.framework.driver.DriverFactory;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class WaitUtils {

    private static WebDriverWait getWait() {
        int timeout =
                Integer.parseInt(ConfigReader.get("timeout"));

        return new WebDriverWait(
                DriverFactory.getDriver(),
                Duration.ofSeconds(timeout)
        );
    }
    
    public static WebElement waitForVisibility(WebElement element) {
        return getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForClickable(WebElement element) {
        return getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForInvisibility(By locator) {
        getWait().until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public static void waitForUrlContains(String value) {
        getWait().until(ExpectedConditions.urlContains(value));
    }

	public static void waitForDocumentReadyState() {
		// TODO Auto-generated method stub
		
	}

	public static void waitForFrameworkIdle() {
		// TODO Auto-generated method stub
		
	}

	public static void waitForGlobalLoaderToDisappear() {
		// TODO Auto-generated method stub
		
	}
    
}
