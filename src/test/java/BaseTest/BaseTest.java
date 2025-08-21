package BaseTest;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;


public class BaseTest {
    protected static Playwright playwright;
    protected static Browser browser;
    protected Page page;
    protected BrowserContext context;

    @BeforeAll
    public static void setUpAll() {
        Playwright playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(true));
    }

    @BeforeEach
    public void setUp() {
        context = browser.newContext();
        page = context.newPage();
    }

    @AfterEach
    public void tearDown() {
        context.close();
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
