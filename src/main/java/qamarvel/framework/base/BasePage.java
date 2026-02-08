package qamarvel.framework.base;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

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
	 * ========================================================= Low-level find
	 * (single choke point)
	 * =========================================================
	 */
	
	protected WebElement find(By locator) {
		return driver.findElement(locator);
	}

	protected List<WebElement> findAll(By locator) {
		return driver.findElements(locator);
	}

	protected WebElement findVisible(By locator) {
		Waits.waitForVisible(driver, locator, WaitConstants.MEDIUM);
		return find(locator);
	}

	protected List<WebElement> findAllPresent(By locator) {
		Waits.waitForPresent(driver, locator, WaitConstants.MEDIUM);
		return findAll(locator);
	}

	
	/*
	 * ========================================================= Interaction helpers
	 * =========================================================
	 */

	protected void click(By locator) {
		Waits.waitForVisible(driver, locator, WaitConstants.MEDIUM);
		Waits.waitForClickable(driver, locator, WaitConstants.MEDIUM);
		find(locator).click();
	}

	protected void type(By locator, String text) {
		Waits.waitForVisible(driver, locator, WaitConstants.MEDIUM);
		find(locator).sendKeys(text);
	}

	protected void clearAndType(By locator, String text) {
		Waits.waitForVisible(driver, locator, WaitConstants.MEDIUM);
		WebElement element = find(locator);
		element.clear();
		element.sendKeys(text);
	}

	/*
	 * ========================================================= Read helpers
	 * =========================================================
	 */

	protected String getText(By locator) {
		Waits.waitForVisible(driver, locator, WaitConstants.MEDIUM);
		return find(locator).getText();
	}

	protected String getAttribute(By locator, String attribute) {
		Waits.waitForPresent(driver, locator, WaitConstants.MEDIUM);
		return find(locator).getAttribute(attribute);
	}

	protected boolean isVisible(By locator) {
		try {
			Waits.waitForVisible(driver, locator, WaitConstants.SHORT);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	protected boolean isPresent(By locator) {
		try {
			Waits.waitForPresent(driver, locator, WaitConstants.SHORT);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/*
	 * ========================================================= Checkbox helpers
	 * =========================================================
	 */

	protected boolean isChecked(By locator) {
		Waits.waitForPresent(driver, locator, WaitConstants.MEDIUM);
		return find(locator).isSelected();
	}

	protected void check(By locator) {
		Waits.waitForClickable(driver, locator, WaitConstants.MEDIUM);
		if (!isChecked(locator)) {
			find(locator).click();
		}
	}

	protected void uncheck(By locator) {
		Waits.waitForClickable(driver, locator, WaitConstants.MEDIUM);
		if (isChecked(locator)) {
			find(locator).click();
		}
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
