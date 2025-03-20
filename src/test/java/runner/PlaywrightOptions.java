package runner;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Tracing;

import java.nio.file.Path;

public class PlaywrightOptions {
    public static BrowserType.LaunchOptions browserOptions() {
        return new BrowserType.LaunchOptions()
                .setHeadless(ProjectProperties.isHeadlessMode())
                .setSlowMo(ProjectProperties.getSlowMoMode());
    }

    public static Browser.NewContextOptions contextOptions(Path recordVideoDir) {
        Browser.NewContextOptions options = new Browser.NewContextOptions()
                .setViewportSize(ProjectProperties.getViewportWidth(), ProjectProperties.getViewportHeight())
                .setBaseURL(ProjectProperties.getBaseUrl());

        if (ProjectProperties.isVideoMode()) {
            options.setRecordVideoDir(recordVideoDir)
                    .setRecordVideoSize(1280, 720);
        }
        return options;
    }

    public static Tracing.StartOptions tracingStartOptions() {
        return new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true);
    }

    public static Tracing.StopOptions tracingStopOptions(Path tracePath) {
        return new Tracing.StopOptions().setPath(tracePath);
    }
}
