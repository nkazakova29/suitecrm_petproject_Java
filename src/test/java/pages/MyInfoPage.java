package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

public class MyInfoPage {
    private Page page;
    private Locator personalDetailsWindow;
    private Locator contactDetailsTab;
    private Locator contactAddress;

    public MyInfoPage(Page page) {
        this.page = page;
        this.personalDetailsWindow = page.getByRole(
                AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("Personal Details")
        );;
        this.contactDetailsTab = page.getByRole(AriaRole.TAB, new Page.GetByRoleOptions().setName("Contact Details"));
        this.contactAddress = page.getByRole(AriaRole.HEADING, new Page.GetByRoleOptions().setName("Address"));
    }

    public boolean isMyInfoTabOpened(){
        try {
            personalDetailsWindow
                    .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
            return true;
        } catch (PlaywrightException e) {
            return false;
        }
    }
    public void goToContactDetailsTab(){
        contactDetailsTab.click();
    }

    public boolean isContactDetailsOpened(){try {
     contactAddress
                .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
        return true;
    } catch (PlaywrightException e) {
        return false;
    }

    }
}


