package qamarvel.framework.auth.apply;

import org.openqa.selenium.WebDriver;

import qamarvel.framework.auth.AuthState;

public interface AuthApplier {
    void apply(WebDriver driver, AuthState authState);
}
