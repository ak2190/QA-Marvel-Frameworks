package qamarvel.framework.base;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import qamarvel.framework.waits.WaitConstants;
import qamarvel.framework.waits.Waits;

public abstract class BasePage {

	protected final WebDriver driver;

	protected BasePage(WebDriver driver) {
		this.driver = driver;
		waitForPageToBeReady();
	}

	/*
	 * ========================================================= Page readiness
	 * contract =========================================================
	 */

	/**
	 * Override ONLY if the page has a stable, unique anchor element that reliably
	 * indicates the view has rendered.
	 */
	protected Optional<By> pageReadyLocator() {
		return Optional.empty();
	}

	private void waitForPageToBeReady() {
		// Structural readiness
		Waits.waitForPageLoad(driver, WaitConstants.MEDIUM);

		// Optional page-level anchor
		pageReadyLocator().ifPresent(locator -> Waits.waitForVisible(driver, locator, WaitConstants.MEDIUM));
	}

	/*
	 * ========================================================= Protected wait
	 * wrappers for page classes
	 * =========================================================
	 */

	protected void waitForVisible(By locator) {
		Waits.waitForVisible(driver, locator, WaitConstants.MEDIUM);
	}

	protected void waitForClickable(By locator) {
		Waits.waitForClickable(driver, locator, WaitConstants.MEDIUM);
	}

	protected void waitForPresent(By locator) {
		Waits.waitForPresent(driver, locator, WaitConstants.MEDIUM);
	}

	protected void waitForInvisible(By locator) {
		Waits.waitForInvisible(driver, locator, WaitConstants.MEDIUM);
	}

	// Screenshot uses page-owned driver
	public String getScreenshot(String testCaseName, WebDriver driver) throws IOException {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);

		String path = System.getProperty("user.dir") + "/reports/" + testCaseName + ".png";

		FileUtils.copyFile(source, new File(path));
		return path;
	}
}
