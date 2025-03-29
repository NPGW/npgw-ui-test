package xyz.npgw.test.common.base;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import io.qameta.allure.Allure;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import xyz.npgw.test.common.BrowserFactory;
import xyz.npgw.test.common.PlaywrightOptions;
import xyz.npgw.test.common.ProjectProperties;
import xyz.npgw.test.common.ProjectUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class BaseTest {

    protected static final Logger LOGGER = LogManager.getLogger(BaseTest.class.getName());

    private static final String ARTEFACT_DIR = "target/artefact";
    private Playwright playwright;
    private Browser browser;
    private String browserType;
    private BrowserContext context;
    private Page page;

    @Parameters("browserType")
    @BeforeClass
    protected void beforeClass(@Optional("Chromium") String browserType) {
        this.browserType = browserType.toUpperCase();
        try {
            playwright = Playwright.create();
            browser = BrowserFactory.getBrowser(playwright, this.browserType);
        } catch (IllegalArgumentException e) {
            LOGGER.error("Unsupported browser: {}", browserType);
            if (playwright != null) {
                playwright.close();
            }
            System.exit(1);
        } catch (RuntimeException e) {
            LOGGER.error("Playwright.create() failed: {}", e.getMessage());
            System.exit(2);
        }
    }

    @BeforeMethod
    protected void beforeMethod(Method method, ITestResult testResult) {
        context = browser.newContext(PlaywrightOptions.contextOptions());

        if (ProjectProperties.isTracingMode()) {
            context.tracing().start(PlaywrightOptions.tracingStartOptions());
        }

        page = context.newPage();

        Allure.step("Navigate to the base url");
        ProjectUtils.navigateToBaseURL(page);

        if (!method.getDeclaringClass().getSimpleName().contains("LoginPageTest")) {
            Allure.step("Login to the site");
            ProjectUtils.login(page);
        }
    }

    @AfterMethod(alwaysRun = true)
    protected void afterMethod(Method method, ITestResult testResult) {
        String testName = ProjectUtils.getTestClassCompleteMethodName(method, testResult);
        Path traceFilePath = Paths.get(ARTEFACT_DIR, browserType, testName + ".zip");
        Path videoFilePath = Paths.get(ARTEFACT_DIR, browserType, testName + ".webm");

        if (page != null) {
            page.close();

            if (ProjectProperties.isVideoMode()) {
                page.video().saveAs(videoFilePath);
                page.video().delete();
            }
        }

        if (context != null) {
            if (ProjectProperties.isTracingMode()) {
                context.tracing().stop(PlaywrightOptions.tracingStopOptions(traceFilePath));
            }
            context.close();
        }

        if (!testResult.isSuccess()) { // && ProjectProperties.isServerRun()) {
            try {
                if (ProjectProperties.isTracingMode()) {
                    Allure.getLifecycle().addAttachment(
                            "video", "video/webm", "webm", Files.readAllBytes(videoFilePath));
                }
                if (ProjectProperties.isTracingMode()) {
                    Allure.getLifecycle().addAttachment(
                            "tracing", "archive/zip", "zip", Files.readAllBytes(traceFilePath));
                }
            } catch (IOException e) {
                LOGGER.error("Add artefacts to allure failed: {}", e.getMessage());
            }
        }
    }

    @AfterClass(alwaysRun = true)
    protected void afterClass() {
        if (browser != null) {
            browser.close();
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    protected Page getPage() {
        return page;
    }
}
