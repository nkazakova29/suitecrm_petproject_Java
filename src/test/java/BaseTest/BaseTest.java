package BaseTest;

import com.microsoft.playwright.*;
import data.LoginData;
import io.qameta.allure.junit5.AllureJunit5;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import pages.CRMFirstPage;


import java.nio.file.Paths;

@ExtendWith({AllureJunit5.class, TestResultLoggerExtension.class})
public class BaseTest {
    protected static Playwright playwright;
    protected static Browser browser;
    protected static Page page;
    protected static BrowserContext context;
    public static final String baseUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/auth/login";
    public static final String dashboardUrl = "https://opensource-demo.orangehrmlive.com/web/index.php/dashboard/index";
    public static final String STORAGE_PATH = "storage/storageState.json";

    @BeforeAll
    public static void setUpAll() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));

        try(BrowserContext authContext = browser.newContext() ){
            Page authPage = authContext.newPage();
            LoginData.LoginCredentials credentials = LoginData.validUser();
            CRMFirstPage firstPage = new CRMFirstPage(authPage, baseUrl);
            firstPage.open();
            firstPage.login(credentials.username(), credentials.password());
            authPage.waitForURL(dashboardUrl);
            authContext.storageState(new BrowserContext.StorageStateOptions()
                    .setPath(Paths.get(STORAGE_PATH)));
        }
    }



    @BeforeEach
    public void setUp() {
        createAuthorizedContext();
    }

    protected void createAuthorizedContext(){
        if (context != null) context.close();

        context = browser.newContext(new Browser.NewContextOptions()
                .setStorageStatePath(Paths.get(STORAGE_PATH)));
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        page = context.newPage();
    }

    protected void cleanContext(){
        if (context != null) context.close();
        context = browser.newContext();
        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true));
        page = context.newPage();
    }

    @AfterAll
    public static void tearDownAll() {
        if (browser != null) {
            browser.close();
        }
        if(playwright!=null){
            playwright.close();
        }
    }

}
