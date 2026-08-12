package tests;

import BaseTest.BaseTest;
import com.microsoft.playwright.Browser;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import pages.HomePage;
import pages.MyInfoPage;

import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MyInfoTests extends BaseTest {

    @Test()
    @Disabled
    public void openMyInfoPage() {
        if (context != null) {
            context.close();
        }

        context = browser.newContext(
                new Browser.NewContextOptions()
                        .setStorageStatePath(Paths.get("storage/storageState.json")));
        page = context.newPage();
        page.navigate(baseUrl);

        HomePage hpage = new HomePage(page);
        MyInfoPage miPage = new MyInfoPage(page);
        hpage.goToMyInfo();
        assertTrue(miPage.isMyInfoTabOpened());

    }

}
