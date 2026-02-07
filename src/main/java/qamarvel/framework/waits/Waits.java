package qamarvel.framework.waits;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public final class Waits {

    private static final Duration DEFAULT_POLLING = Duration.ofMillis(500);

    private Waits() {
        // utility class – no instances
    }

    /* =========================================================
       Core wait builder (single point of control)
       ========================================================= */
    private static WebDriverWait buildWait(WebDriver driver, Duration timeout) {
        WebDriverWait wait = new WebDriverWait(driver, timeout);
        wait.pollingEvery(DEFAULT_POLLING);
        wait.ignoring(StaleElementReferenceException.class);
        return wait;
    }

    /* =========================================================
       Page / document level waits
       ========================================================= */

    public static void waitForPageLoad(WebDriver driver, Duration timeout) {
        buildWait(driver, timeout).until(webDriver ->
            "complete".equals(
                ((JavascriptExecutor) webDriver)
                    .executeScript("return document.readyState")
            )
        );
    }

    /* =========================================================
       Element level waits
       ========================================================= */

    public static void waitForVisible(
            WebDriver driver,
            By locator,
            Duration timeout
    ) {
        buildWait(driver, timeout)
            .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public static void waitForClickable(
            WebDriver driver,
            By locator,
            Duration timeout
    ) {
        buildWait(driver, timeout)
            .until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static void waitForPresent(
            WebDriver driver,
            By locator,
            Duration timeout
    ) {
        buildWait(driver, timeout)
            .until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static void waitForInvisible(
            WebDriver driver,
            By locator,
            Duration timeout
    ) {
        buildWait(driver, timeout)
            .until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    /* =========================================================
       JavaScript condition wait (advanced / opt-in)
       ========================================================= */

    public static void waitForJsCondition(
            WebDriver driver,
            String jsCondition,
            Duration timeout
    ) {
        buildWait(driver, timeout).until(webDriver ->
            Boolean.TRUE.equals(
                ((JavascriptExecutor) webDriver)
                    .executeScript(jsCondition)
            )
        );
    }
}
