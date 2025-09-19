package tests;

import BaseTest.BaseTest;
import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import pages.CRMFirstPage;
import pages.HomePage;

import static org.junit.jupiter.api.Assertions.*;

public class CRMTests extends BaseTest {
    CRMFirstPage firstPage;
    HomePage hpage;

    @Test
    @Description("Successful login")


    public void testLogin() {
        String username = "Admin";
        String password = "admin123";
        Allure.step("Open site and login", () -> {
            CRMFirstPage firstPage = new CRMFirstPage(page);
            HomePage hpage = new HomePage(page);
            firstPage.open();
            firstPage.login(username, password);
            assertTrue(hpage.isLoggedIn(), "Logout");
        });
    }

    @Test
    @Description("Unsuccessful login")
    public void testFailedLogin() {
        CRMFirstPage firstPage = new CRMFirstPage(page);
        HomePage hpage = new HomePage(page);
        String username = "Admin";
        String password = "Admin";
        Allure.step("Open the target site", firstPage::open);
        Allure.step("Perform login", () -> firstPage.login(username, password));
        assertFalse(hpage.isLoggedIn(), "Dashboard");
        assertTrue(firstPage.isErrorAppear(), "Invalid Credentials");
    }

    @Test
    @Description("Logout")
    public void testLogout() {
        CRMFirstPage firstPage = new CRMFirstPage(page);
        HomePage hpage = new HomePage(page);
        String username = "Admin";
        String password = "admin123";
        Allure.step("Open site and login", () -> {
            firstPage.open();
            firstPage.login(username, password);
            hpage.logOut();
            assertTrue(firstPage.isLoggedOut(), "User hasn't been logged out");

        });
    }
}
