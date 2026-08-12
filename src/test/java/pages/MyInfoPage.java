package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.WaitForSelectorState;

public class MyInfoPage {
    private Page page;
    private Locator personalDetailsTab;

    public MyInfoPage(Page page) {
        this.page = page;
        this.personalDetailsTab = page.getByRole(
                AriaRole.HEADING,
                new Page.GetByRoleOptions().setName("Personal Details")
        );;
    }

    public boolean isMyInfoTabOpened(){
        try {
            personalDetailsTab
                    .waitFor(new Locator.WaitForOptions().setState(WaitForSelectorState.VISIBLE).setTimeout(5000));
            return true;
        } catch (PlaywrightException e) {
            return false;
        }
    }
    }


