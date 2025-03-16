package TestData;

import runner.ProjectProperties;

public class Constants {

    public static final String USER_EMAIL = ProjectProperties.getUserEmail();
    public static final String USER_PASSWORD = ProjectProperties.getUserPassword();

    public static final String BASE_URL = ProjectProperties.getBaseUrl();
    public static final String LOGIN_PAGE_URL = BASE_URL + "/";
    public static final String DASHBOARD_PAGE_URL = BASE_URL + "/dashboard";

    public static final String BASE_URL_TITLE = "NPGW";
    public static final String DASHBOARD_URL_TITLE = "NPGW";
}
