package qamarvel.framework.auth.verify;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class DashboardAuthVerifier implements AuthVerifier {

    @Override
    public void verify(WebDriver driver) {

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Replace with a stable dashboard element
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.xpath("//button[@routerlink='/dashboard/myorders']")
            )
        );
    }
}
