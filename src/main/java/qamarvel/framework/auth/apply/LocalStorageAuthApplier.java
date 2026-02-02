package qamarvel.framework.auth.apply;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import qamarvel.framework.auth.AuthState;

public class LocalStorageAuthApplier implements AuthApplier {

    @Override
    public void apply(WebDriver driver, AuthState authState) {

        String token = authState.getToken();
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript(
            "window.localStorage.setItem('token', arguments[0]);",
            token
        );
    }
}
