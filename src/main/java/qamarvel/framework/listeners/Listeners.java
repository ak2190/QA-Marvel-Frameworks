package qamarvel.framework.listeners;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.*;

import qamarvel.framework.reporter.ExtentReporterNG;
import qamarvel.framework.utils.*;

public class Listeners implements ITestListener {

	private ExtentReports extent = ExtentReporterNG.getReportObject();
	private ThreadLocal<ExtentTest> extentTest = new ThreadLocal<>();

	// REQUIRED by TestNG
	public Listeners() {
	}

	@Override
	public void onTestStart(ITestResult result) {
		ExtentTest test = extent.createTest(result.getMethod().getMethodName());
		extentTest.set(test);
	}

	@Override
	public void onTestFailure(ITestResult result) {
		extentTest.get().fail(result.getThrowable());

		try {
			// get driver instance which test is using
			WebDriver driver = (WebDriver) result.getTestClass().getRealClass().getField("driver")
					.get(result.getInstance());

			String screenshotPath = UtilityLib.captureScreenshot(driver, result.getMethod().getMethodName());

			extentTest.get().addScreenCaptureFromPath(screenshotPath, result.getMethod().getMethodName());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}
}
