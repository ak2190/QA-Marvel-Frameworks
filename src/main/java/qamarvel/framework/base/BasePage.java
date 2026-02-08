package qamarvel.framework.base;

import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.slf4j.Logger;

import qamarvel.framework.config.ConfigManager;
import qamarvel.framework.config.ConfigReader;
import qamarvel.framework.logging.FrameworkLogger;
import qamarvel.framework.waits.WaitConstants;
import qamarvel.framework.waits.Waits;

public abstract class BasePage {

    protected final WebDriver driver;
    protected final Logger log = FrameworkLogger.getLogger(this.getClass());

    /*
     * =========================================================
     * Constructor
     * =========================================================
     */
    protected BasePage(WebDriver driver) {
        this.driver = driver;
        waitForPageToBeReady();
    }

    /*
     * =========================================================
     * Page readiness contract
     * =========================================================
     */

    /**
     * Override ONLY if the page has a stable, unique anchor element
     * that indicates the page is functionally ready.
     */
    protected Optional<By> pageReadyLocator() {
        return Optional.empty();
    }

    private void waitForPageToBeReady() {
        // Structural readiness
        Waits.waitForPageLoad(driver, WaitConstants.MEDIUM);

        // Functional readiness (optional)
        pageReadyLocator()
                .ifPresent(locator ->
                        Waits.waitForVisible(driver, locator, WaitConstants.MEDIUM));
    }

    /*
     * =========================================================
     * Low-level find (single choke point)
     * =========================================================
     */

    protected WebElement find(By locator) {
        return driver.findElement(locator);
    }

    protected List<WebElement> findAll(By locator) {
        return driver.findElements(locator);
    }

    /*
     * =========================================================
     * Viewport & Highlight helpers
     * =========================================================
     */

    private void scrollIntoView(WebElement element) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({behavior:'instant', block:'center'});",
                element
        );
    }

    private void highlight(WebElement element) {
        if (!ConfigManager.isHighlightEnabled()) {
            return;
        }

        ((JavascriptExecutor) driver).executeScript(
            "const el = arguments[0];" +
            "const originalBorder = el.style.border;" +
            "const originalBg = el.style.backgroundColor;" +

            "el.style.transition='all 0.2s ease';" +
            "el.style.border='2px solid #FFD700';" +
            "el.style.backgroundColor='rgba(255, 215, 0, 0.25)';" +

            "setTimeout(() => {" +
            "  el.style.border = originalBorder;" +
            "  el.style.backgroundColor = originalBg;" +
            "}, 500);",
            element
        );
    }


    /*
     * =========================================================
     * Element preparation 
     * =========================================================
     */

    private WebElement prepareElement(By locator) {
        Waits.waitForVisible(driver, locator, WaitConstants.MEDIUM);
        Waits.waitForClickable(driver, locator, WaitConstants.MEDIUM);

        WebElement element = find(locator);
        scrollIntoView(element);
        highlight(element);

        return element;
    }

    /*
     * =========================================================
     * Interaction helpers
     * =========================================================
     */

    protected void click(By locator) {
        log.info("Clicking element: {}", locator);
        prepareElement(locator).click();
    }

    protected void type(By locator, String text) {
        log.info("Typing '{}' into element: {}", text, locator);
        prepareElement(locator).sendKeys(text);
    }

    protected void clearAndType(By locator, String text) {
        log.info("Clear and type '{}' into element: {}", text, locator);
        WebElement element = prepareElement(locator);
        element.clear();
        element.sendKeys(text);
    }

    /*
     * =========================================================
     * Read helpers
     * =========================================================
     */

    protected String getText(By locator) {
        log.info("Getting text from element: {}", locator);
        Waits.waitForVisible(driver, locator, WaitConstants.MEDIUM);

        WebElement element = find(locator);
        scrollIntoView(element);
        highlight(element);

        return element.getText();
    }

    protected String getAttribute(By locator, String attribute) {
        log.info("Getting '{}' attribute from element: {}", attribute, locator);
        Waits.waitForPresent(driver, locator, WaitConstants.MEDIUM);

        WebElement element = find(locator);
        scrollIntoView(element);
        highlight(element);

        return element.getAttribute(attribute);
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
     * =========================================================
     * Checkbox helpers
     * =========================================================
     */

    protected boolean isChecked(By locator) {
        Waits.waitForPresent(driver, locator, WaitConstants.MEDIUM);
        return find(locator).isSelected();
    }

    protected void check(By locator) {
        log.info("Checking checkbox: {}", locator);
        WebElement element = prepareElement(locator);
        if (!element.isSelected()) {
            element.click();
        }
    }

    protected void uncheck(By locator) {
        log.info("Unchecking checkbox: {}", locator);
        WebElement element = prepareElement(locator);
        if (element.isSelected()) {
            element.click();
        }
    }

    /*
     * =========================================================
     * Protected wait wrappers (for page-specific flows)
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
}
