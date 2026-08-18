package BaseTest;

import com.microsoft.playwright.Tracing;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestWatcher;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class TestResultLoggerExtension implements TestWatcher {

    @Override
    public void testFailed(ExtensionContext context, Throwable cause) {
        try {
            if (BaseTest.page != null && !BaseTest.page.isClosed()) {
                byte[] screenshot = BaseTest.page.screenshot();
                Allure.addAttachment("Screenshot on Failure", "image/png",
                        new ByteArrayInputStream(screenshot), "png");
            }

            if (BaseTest.context != null) {
                Path tracePath = Paths.get("target/trace_" + context.getRequiredTestMethod().getName() + ".zip");

                BaseTest.context.tracing().stop(new Tracing.StopOptions()
                        .setPath(tracePath));

                try (InputStream is = Files.newInputStream(tracePath)) {
                    Allure.addAttachment("Playwright Trace", "application/zip", is, "zip");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } finally {
            closeContext();
        }
    }

    @Override
    public void testSuccessful(ExtensionContext context) {
        try {
            if (BaseTest.context != null) {
                BaseTest.context.tracing().stop();
            }
        } finally {
            closeContext();
        }
    }

    private void closeContext() {
        if (BaseTest.context != null) {
            BaseTest.context.close();
            BaseTest.context = null;
        }
    }
}