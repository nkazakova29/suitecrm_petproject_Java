package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.qameta.allure.Step;

public class HomePage {
    private final Page page;
    private final Locator dropDown;
    private final Locator logOutBtn;

    public HomePage(Page page) {
        this.page = page;
        this.dropDown = page.locator(".oxd-userdropdown-icon");
        this.logOutBtn = page.locator("text=Logout");
    }

    @Step("Checking that user can proceed to the next page")
    public boolean isLoggedIn() {
        // пример проверки: ищем элемент с id="user-name"
        try {
            dropDown.click();  // открыть список
            logOutBtn
                    .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
            return true;
        } catch (PlaywrightException e) {
            return false;
        }
    }

    @Step("Logout")
    public void logOut(){
        isLoggedIn();
        logOutBtn.click();

    }



}

