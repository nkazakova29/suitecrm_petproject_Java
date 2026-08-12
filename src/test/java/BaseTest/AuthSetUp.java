package BaseTest;

import com.microsoft.playwright.BrowserContext;
import data.LoginData;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import pages.CRMFirstPage;
import pages.HomePage;
import java.nio.file.Paths;
import static BaseTest.BaseTest.baseUrl;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AuthSetUp extends BaseTest{

@Test
    public void authSetUp() {
        LoginData.LoginCredentials credentials = LoginData.validUser();
        CRMFirstPage firstPage = new CRMFirstPage(page, baseUrl);
        HomePage hpage = new HomePage(page);
        firstPage.open();
        firstPage.login(credentials.username(), credentials.password());
        assertTrue(hpage.isLoggedIn(), "Logout");
        context.storageState(
                new BrowserContext.StorageStateOptions()
                        .setPath(Paths.get("storage/storageState.json")));
    }
        }
