package qamarvel.framework.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class UtilityLib {

	
	// utility to capture screenshot and return the path of the captured screenshot
	public static String captureScreenshot(WebDriver driver, String testCaseName) throws IOException {

		TakesScreenshot ts = (TakesScreenshot) driver;
		File source = ts.getScreenshotAs(OutputType.FILE);

		String path = System.getProperty("user.dir") + "/reports/" + testCaseName + ".png";

		FileUtils.copyFile(source, new File(path));
		return path;
	}
}
