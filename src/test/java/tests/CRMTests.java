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
        String username = "Admin";
        String password = "admin123";
        Allure.step("Open site and login", () -> {
        firstPage.open();
        firstPage.login(username, password);
            assertTrue(firstPage.isLoggedIn(), "Logout");
        });
    }

    @Test
    @Description("Unsuccessful login")
    public void testFailedLogin() {
        CRMFirstPage firstPage = new CRMFirstPage(page);
        String username = "Admin";
        String password = "Admin";
        Allure.step("Open the target site", firstPage::open);
        Allure.step("Perform login", () -> firstPage.login(username, password));
        assertFalse(firstPage.isLoggedIn(), "Dashboard");
        assertTrue(firstPage.isErrorAppear(), "Invalid Credentials");
    }
}
