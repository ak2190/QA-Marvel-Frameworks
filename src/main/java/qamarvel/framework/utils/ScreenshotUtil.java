package qamarvel.framework.utils;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import qamarvel.framework.context.RunContext;

public final class ScreenshotUtil {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    private ScreenshotUtil() {}

    public static String takeScreenshot(WebDriver driver, String testCaseName) {

        try {
            String timestamp = LocalDateTime.now().format(FORMATTER);

         //   String baseDir = System.getProperty("user.dir");
            Path screenshotDir = Path.of(
                    RunContext.getRunDirectory(),
                    "screenshots"
            );
            // create folders if not exist
            Files.createDirectories(screenshotDir);

            String fileName = testCaseName + "_" + timestamp + ".png";
            Path destination = screenshotDir.resolve(fileName);

            File source = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

            Files.copy(source.toPath(), destination);

            return destination.toAbsolutePath().toString();

        } catch (Exception e) {
            return "Screenshot capture failed: " + e.getMessage();
        }
    }
}
