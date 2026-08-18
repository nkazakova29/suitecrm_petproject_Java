package tests;

import BaseTest.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.MyInfoPage;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyInfoTests extends BaseTest{

    @Test()
    @Description("Go to My info Page")
    public void openMyInfoPage() {
        page.navigate(dashboardUrl);
        HomePage hpage = new HomePage(page);
        MyInfoPage miPage = new MyInfoPage(page);
        Allure.step("Go to My info tab", ()-> { hpage.goToMyInfo();
                    assertTrue(miPage.isMyInfoTabOpened());
                });
    }

        @Test
    @Description("Go to Contact Details tab")
    public void gotoContactDetails() {
            page.navigate(dashboardUrl);
            HomePage hpage = new HomePage(page);
            hpage.goToMyInfo();
            MyInfoPage miPage = new MyInfoPage(page);
            assertTrue(miPage.isMyInfoTabOpened());
            Allure.step("Go to Contact Details tab", () -> {
                    miPage.goToContactDetailsTab();
            assertTrue(miPage.isContactDetailsOpened());
        });
        }
}