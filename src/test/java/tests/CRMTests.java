package tests;

import BaseTest.BaseTest;
import com.microsoft.playwright.*;
import io.qameta.allure.Description;
import org.junit.jupiter.api.*;
import pages.CRMFirstPage;

import static org.junit.jupiter.api.Assertions.*;
public class CRMTests extends BaseTest {
    @Test
    @Description("Successful login")

    public void testLogin() {
        CRMFirstPage firstPage = new CRMFirstPage(page);
        firstPage.open();
        firstPage.goTOLogInPage();
        firstPage.login("will", "will"); // Demo creds
        assertTrue(firstPage.isLoggedIn(), "Welcome to the SuiteCRM 7 Demo");
    }
}
