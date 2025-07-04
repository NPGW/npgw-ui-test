package xyz.npgw.test.common;

import com.microsoft.playwright.options.ColorScheme;
import lombok.extern.log4j.Log4j2;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

@Log4j2
public final class ProjectProperties {

    private static final Properties properties;

    private static final String CI_RUN = "CI_RUN";
    private static final String DOCKER_RUN = "DOCKER_RUN";
    private static final String ENV_APP_OPTIONS = "APP_OPTIONS";

    private static final String PREFIX_PROP = "local.";

    private static final String BASE_URL = PREFIX_PROP + "baseURL";
    private static final String EMAIL = PREFIX_PROP + "email";
    private static final String PASSWORD = PREFIX_PROP + "password";

    private static final String BROWSER_TYPE = PREFIX_PROP + "browserType";
    private static final String HEADLESS_MODE = PREFIX_PROP + "headlessMode";
    private static final String SLOW_MO_MODE = PREFIX_PROP + "slowMoMode";
    private static final String VIEWPORT_WIDTH = PREFIX_PROP + "viewportWidth";
    private static final String VIEWPORT_HEIGHT = PREFIX_PROP + "viewportHeight";
    private static final String TRACING_MODE = PREFIX_PROP + "tracingMode";

    private static final String VIDEO_MODE = PREFIX_PROP + "videoMode";
    private static final String VIDEO_WIDTH = PREFIX_PROP + "videoWidth";
    private static final String VIDEO_HEIGHT = PREFIX_PROP + "videoHeight";

    private static final String CLOSE_BROWSER_IF_ERROR = PREFIX_PROP + "closeBrowserIfError";
    private static final String ARTEFACT_DIR = PREFIX_PROP + "artefactDir";
    private static final String DEFAULT_TIMEOUT = PREFIX_PROP + "defaultTimeout";
    private static final String ADDITIONAL_RETRIES = PREFIX_PROP + "additionalRetries";
    private static final String COLOR_SCHEME = PREFIX_PROP + "colorScheme";
    private static final String DEBUG = PREFIX_PROP + "DEBUG";

    static {
        properties = new Properties();
        if ((System.getenv(CI_RUN) != null) || (System.getenv(DOCKER_RUN) != null)) {
            if (System.getenv(ENV_APP_OPTIONS) != null) {
                for (String option : System.getenv(ENV_APP_OPTIONS).split(";")) {
                    String[] optionArr = option.trim().split("=");
                    properties.setProperty(PREFIX_PROP + optionArr[0], optionArr[1]);
                }
            }
        } else {
            String configPath = System.getProperty("configPath", ".properties");
            try (InputStream inputStream = Files.newInputStream(Paths.get(configPath))) {
                properties.load(inputStream);
            } catch (IOException e) {
                log.error("The '.properties' file not found in project directory.");
                log.error("You need to create it from .properties.TEMPLATE file.");
                throw new RuntimeException(e);
            }
        }
    }

    private ProjectProperties() {
        throw new UnsupportedOperationException();
    }

    private static String getValue(String propName) {
        return System.getenv(propName.replace('.', '_').toUpperCase());
    }

    public static String getBaseUrl() {
        return properties.getProperty(BASE_URL,
                getValue(BASE_URL));
    }

    public static String getBrowserType() {
        return properties.getProperty(BROWSER_TYPE,
                getValue(BROWSER_TYPE));
    }

    public static boolean isHeadlessMode() {
        return Boolean.parseBoolean(properties.getProperty(HEADLESS_MODE,
                getValue(HEADLESS_MODE)));
    }

    public static Double getSlowMoMode() {
        return Double.valueOf(properties.getProperty(SLOW_MO_MODE,
                getValue(SLOW_MO_MODE)));
    }

    public static int getViewportWidth() {
        return Integer.parseInt(properties.getProperty(VIEWPORT_WIDTH,
                getValue(VIEWPORT_WIDTH)));
    }

    public static int getViewportHeight() {
        return Integer.parseInt(properties.getProperty(VIEWPORT_HEIGHT,
                getValue(VIEWPORT_HEIGHT)));
    }

    public static boolean isTracingMode() {
        return Boolean.parseBoolean(properties.getProperty(TRACING_MODE,
                getValue(TRACING_MODE)));
    }

    public static boolean isVideoMode() {
        return Boolean.parseBoolean(properties.getProperty(VIDEO_MODE,
                getValue(VIDEO_MODE)));
    }

    public static int getVideoWidth() {
        return Integer.parseInt(properties.getProperty(VIDEO_WIDTH,
                getValue(VIDEO_WIDTH)));
    }

    public static int getVideoHeight() {
        return Integer.parseInt(properties.getProperty(VIDEO_HEIGHT,
                getValue(VIDEO_HEIGHT)));
    }

    public static boolean closeBrowserIfError() {
        return Boolean.parseBoolean(properties.getProperty(CLOSE_BROWSER_IF_ERROR,
                getValue(CLOSE_BROWSER_IF_ERROR)));
    }

    public static String getArtefactDir() {
        return properties.getProperty(ARTEFACT_DIR,
                getValue(ARTEFACT_DIR));
    }

    public static double getDefaultTimeout() {
        return Double.parseDouble(properties.getProperty(DEFAULT_TIMEOUT,
                getValue(DEFAULT_TIMEOUT)));
    }

    public static String getEmail() {
        return properties.getProperty(EMAIL,
                getValue(EMAIL));
    }

    public static String getPassword() {
        return properties.getProperty(PASSWORD,
                getValue(PASSWORD));
    }

    public static int getAdditionalRetries() {
        return Integer.parseInt(properties.getProperty(ADDITIONAL_RETRIES,
                getValue(ADDITIONAL_RETRIES)));
    }

    public static ColorScheme getColorScheme() {
        return ColorScheme.valueOf(properties.getProperty(COLOR_SCHEME,
                getValue(COLOR_SCHEME)).toUpperCase());
    }

    public static Map<String, String> getEnv() {
        String debug = properties.getProperty(DEBUG,
                getValue(DEBUG));
        return debug.isEmpty() ? Map.of() : Map.of("DEBUG", debug);
    }
}
