package qamarvel.pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import qamarvel.framework.base.BasePage;

public class LoginPage extends BasePage {

    public LoginPage(WebDriver driver) {
		super(driver);
		PageFactory.initElements(driver, this);
	}

	@FindBy(id = "userEmail")
    private WebElement usernameInput;

    @FindBy(id = "userPassword")
    private WebElement passwordInput;

    @FindBy(id = "login")
    private WebElement loginButton;

    public void login(String username, String password) {
        clearAndType(usernameInput, username);
        clearAndType(password);
        loginButton.click();
    }
}
