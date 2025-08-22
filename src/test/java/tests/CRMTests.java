package tests;

import BaseTest.BaseTest;
import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import pages.CRMFirstPage;

import static org.junit.jupiter.api.Assertions.*;
public class CRMTests extends BaseTest {
    @Test
    @Description("Successful login")

    public void testLogin() {
        CRMFirstPage firstPage = new CRMFirstPage(page);
/*        firstPage.open();
        firstPage.goTOLogInPage();
        firstPage.login("will", "will"); // Demo creds
       ;*/
        Allure.step("Open the target site", firstPage::open);
        Allure.step("Go to the Login Page", firstPage::goTOLogInPage);
        Allure.step("Perform login", () -> firstPage.login("will", "will"));
        assertTrue(firstPage.isLoggedIn(), "Welcome to the SuiteCRM 7 Demo");
    }

    @Test
    @Description("Unsuccessful login")
    public void testFailedLogin() {
        CRMFirstPage firstPage = new CRMFirstPage(page);
       /* firstPage.open();
        firstPage.goTOLogInPage();
        firstPage.login("login", "will"); // Demo creds*/
        Allure.step("Open the target site", firstPage::open);
        Allure.step("Go to the Login Page", firstPage::goTOLogInPage);
        Allure.step("Perform login", () -> firstPage.login("login", "will"));
        assertFalse(firstPage.isLoggedIn(), "Welcome to the SuiteCRM 7 Demo");
        assertTrue(firstPage.isErrorAppear(), "You must specify a valid username and password.");
    }
}
