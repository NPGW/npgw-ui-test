package runner;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ProjectProperties {

    private static final String ENV_ACCESS_OPTIONS = "ACCESS_OPTIONS";
    private static final String ENV_BROWSER_OPTIONS = "BROWSER_OPTIONS";
    private static Properties properties;

    static {
        initProperties();
    }

    private static void initProperties() {
        properties = new Properties();
        if (isServerRun()) {
            if (System.getenv(ENV_ACCESS_OPTIONS) != null) {
                for (String option : System.getenv(ENV_ACCESS_OPTIONS).split(";\n")) {
                    String[] webOptionArr = option.split("=");
                    properties.setProperty(webOptionArr[0], webOptionArr[1]);
                }
            }
            if (System.getenv(ENV_BROWSER_OPTIONS) != null) {
                for (String option : System.getenv(ENV_BROWSER_OPTIONS).split(";\n")) {
                    String[] browserOptionArr = option.split("=");
                    properties.setProperty(browserOptionArr[0], browserOptionArr[1]);
                }
            }
        } else {
            String[] fileNames = {"browser.properties", "access.properties"};

            for (String fileName : fileNames) {
                try {
                    FileInputStream fileInputStream = new FileInputStream("./src/test/resources/" + fileName);
                    properties.load(fileInputStream);
                } catch (IOException e) {
                    System.out.println("ERROR: The \u001B[31m" + fileName + "\u001B[0m file not found.");
                    System.out.println("You need to create it from " + fileName + ".TEMPLATE file.");
                    System.exit(1);
                }
            }
        }
    }

    public static String getPropertyValue(String propertyName) {
        if (!properties.containsKey(propertyName) || properties.getProperty(propertyName) == null
                || properties.getProperty(propertyName).trim().isEmpty()) {
            System.out.println("ERROR OCCURRED: \"" + propertyName + "\" property does not exist or it's value is invalid.");
        }
        return properties.getProperty(propertyName).trim();
    }

    public static boolean getPropertyValueAsBoolean(String propertyName) {
        if (getPropertyValue(propertyName).equalsIgnoreCase("true")
                || getPropertyValue(propertyName).equalsIgnoreCase("false")) {
            return Boolean.parseBoolean(getPropertyValue(propertyName));
        } else {
            System.out.println("ERROR OCCURRED: '" + propertyName + "' property doesn't have correct value. "
                    + "\nThe default value 'true' for '" + propertyName + "' hase been set");
            return true;
        }
    }

    static boolean isServerRun() {
        return System.getenv("CI_RUN") != null;
    }

    static boolean isHeadlessMode() {
        return getPropertyValueAsBoolean("headlessMode");
    }

    static Double getSlowMoMode() {
        return Double.valueOf(getPropertyValue("slowMoMode"));
    }

    static int isViewportWidth() {
        return Integer.parseInt(getPropertyValue("viewportWidth"));
    }

    static int getViewportHeight() {
        return Integer.parseInt(getPropertyValue("viewportHeight"));
    }

    static boolean isTracingMode() {
        return getPropertyValueAsBoolean("tracingMode");
    }

    static boolean isVideoMode() {
        return getPropertyValueAsBoolean("videoMode");
    }

    public static String getBaseUrl() {
        return getPropertyValue("baseURL");
    }

    public static String getUserEmail() {
        return getPropertyValue("userEmail");
    }

    public static String getUserPassword() {
        return getPropertyValue("userPassword");
    }
}
