package qamarvel.framework.base;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public abstract class BasePage {

    protected WebDriver driver;

    // ✅ Only constructor allowed
    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // ✅ Screenshot uses page-owned driver
    public String getScreenshot(String testCaseName,WebDriver driver) throws IOException {

        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        String path = System.getProperty("user.dir")
                + "/reports/" + testCaseName + ".png";

        FileUtils.copyFile(source, new File(path));
        return path;
    }
}
