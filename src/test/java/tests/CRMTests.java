package tests;

import BaseTest.BaseTest;
import data.LoginData;
import com.microsoft.playwright.*;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import pages.CRMFirstPage;
import pages.HomePage;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class CRMTests extends BaseTest {

    @Test
    @Description("Successful login")


    public void testLogin() {
        LoginData.LoginCredentials credentials = LoginData.validUser();
        Allure.step("Open site and login", () -> {
            CRMFirstPage firstPage = new CRMFirstPage(page, baseUrl);
            HomePage hpage = new HomePage(page);
            firstPage.open();
            firstPage.login(credentials.username(), credentials.password());
            assertTrue(hpage.isLoggedIn(), "Logout");
            context.storageState(
                    new BrowserContext.StorageStateOptions()
                            .setPath(Paths.get("storage/storageState.json")));
        });
    }

    @Test
    @Description("Unsuccessful login")
    public void testFailedLogin() {
        LoginData.LoginCredentials credentials = LoginData.invalidUser();
        CRMFirstPage firstPage = new CRMFirstPage(page, baseUrl);
        HomePage hpage = new HomePage(page);
        Allure.step("Open the target site", firstPage::open);
        Allure.step("Perform login", () -> firstPage.login(credentials.username(), credentials.password()));
        assertFalse(hpage.isLoggedIn(), "Dashboard");
        assertTrue(firstPage.isErrorAppear(), "Invalid Credentials");
    }

    @Test
    @Description("Logout")
    public void testLogout() {
        LoginData.LoginCredentials credentials = LoginData.validUser();
        CRMFirstPage firstPage = new CRMFirstPage(page,baseUrl);
        HomePage hpage = new HomePage(page);
        Allure.step("Open site and login", () -> {
            firstPage.open();
            firstPage.login(credentials.username(), credentials.password());
            hpage.logOut();
            assertTrue(firstPage.isLoggedOut(), "User hasn't been logged out");

        });
    }
}
