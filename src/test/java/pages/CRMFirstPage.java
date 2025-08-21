package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;

public class CRMFirstPage {

    private final Page page;
    private final String url = "https://suitecrm.com/demo/";
    private final Locator goToLogInBtn;
    private final Locator loginField;
    private final Locator passwordField;
    private final Locator submitBtn;


    public CRMFirstPage(Page page) {
        this.page = page;
        this.goToLogInBtn = page.locator("text=ACCESS THE SUITECRM 7 ESR DEMO");
        this.loginField = page.locator("#user_name");
        this.passwordField = page.locator("#username_password");
        this.submitBtn = page.locator("#bigbutton");
    }

    @Step("Open the target site")
    public void open() {
        page.navigate(url);
    }

    @Step("Go to the Login Page")
    public void goTOLogInPage(){
        goToLogInBtn.click();
    }

    @Step("Login process")
    public void login(String username, String password) {
        loginField.fill(username);
        passwordField.fill(password);
        submitBtn.click();
    }
    @Step("Checking that user can proceed to the next page")
    public boolean isLoggedIn() {
        // пример проверки: ищем элемент с id="user-name"
        try {
            page.locator("text=Welcome to the SuiteCRM 7 Demo")
                    .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
            return true;
        } catch (PlaywrightException e) {
            return false;
        }
    }

    @Step("Checking Error after failed login")
    public boolean isErrorAppear(){
        try {
            page.locator("text=You must specify a valid username and password.")
                    .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
            return true;
        } catch (PlaywrightException e) {
            return false;
        }
    }
}
