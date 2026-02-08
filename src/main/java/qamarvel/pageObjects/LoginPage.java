package qamarvel.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import qamarvel.framework.base.BasePage;

public class LoginPage extends BasePage {

    private By usernameInput = By.id("userEmail");
    private By userPassword  = By.id("userPassword");
    private By loginButton   = By.id("login");

    public LoginPage(WebDriver driver) {
        super(driver);
        
    }

    public void login(String username, String password) {
        clearAndType(usernameInput, username);
        clearAndType(userPassword, password);
        click(loginButton);
    }
}
