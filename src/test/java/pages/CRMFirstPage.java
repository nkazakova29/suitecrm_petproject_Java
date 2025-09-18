package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;

public class CRMFirstPage {

    private final Page page;
    private final String url = "https://opensource-demo.orangehrmlive.com/";
    private final Locator loginField;
    private final Locator passwordField;
    private final Locator submitBtn;
    private final Locator dropDown;


    public CRMFirstPage(Page page) {
        this.page = page;
        this.loginField = page.locator("[name='username']");
        this.passwordField = page.locator("[name='password']");
        this.submitBtn = page.locator("[type='submit']");
        this.dropDown = page.locator(".oxd-userdropdown-icon");
    }

    @Step("Open the target site")
    public void open() {
        page.navigate(url);
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
            dropDown.click();  // открыть список
            page.locator("text=Logout")
                    .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
            return true;
        } catch (PlaywrightException e) {
            return false;
        }
    }


    @Step("Checking Error after failed login")
    public boolean isErrorAppear(){
        try {
            page.locator("text=Invalid credentials")
                    .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
            return true;
        } catch (PlaywrightException e) {
            return false;
        }
    }

}


