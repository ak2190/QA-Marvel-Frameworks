package qamarvel.framework.listeners;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import qamarvel.framework.driver.DriverFactory;
import qamarvel.framework.logging.FrameworkLogger;
import qamarvel.framework.reporter.ExtentReporterNG;
import qamarvel.framework.utils.ScreenshotUtil;

public class TestListeners implements ITestListener {

    private static ExtentReports extent;

    private static final ThreadLocal<ExtentTest> extentTest =
            new ThreadLocal<>();

    private static final Logger log =
            FrameworkLogger.getLogger(TestListeners.class);

    @Override
    public void onStart(ITestContext context) {
        extent = ExtentReporterNG.getReportObject();
        log.info("===== EXTENT REPORT INITIALIZED =====");
    }

    @Override
    public void onTestStart(ITestResult result) {

        String testName = result.getMethod().getMethodName();

        ExtentTest test = extent.createTest(testName);
        extentTest.set(test);

        log.info("===== TEST STARTED: {} =====", testName);
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        extentTest.get().pass("Test passed");

        log.info("===== TEST PASSED: {} =====",
                result.getMethod().getMethodName());
    }

    @Override
    public void onTestFailure(ITestResult result) {

        String testName = result.getMethod().getMethodName();

        extentTest.get().fail(result.getThrowable());

        WebDriver driver = DriverFactory.getDriver();

        if (driver == null) {
            log.error("Driver was null. Screenshot not captured.");
            return;
        }

        String screenshotPath =
                ScreenshotUtil.takeScreenshot(driver, testName);

        extentTest.get()
                .addScreenCaptureFromPath(screenshotPath, testName);

        log.error("TEST FAILED: {}", testName);
        log.error("Screenshot saved at: {}", screenshotPath);
        log.error("Current URL: {}", driver.getCurrentUrl());
        log.error("Failure reason:", result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        extentTest.get().skip(result.getThrowable());

        log.warn("===== TEST SKIPPED: {} =====",
                result.getMethod().getMethodName());
    }

    @Override
    public void onFinish(ITestContext context) {

        extent.flush();

        log.info("===== TEST EXECUTION FINISHED =====");
    }
}
