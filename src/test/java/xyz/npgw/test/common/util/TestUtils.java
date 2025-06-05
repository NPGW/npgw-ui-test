package xyz.npgw.test.common.util;

import com.microsoft.playwright.APIRequestContext;
import xyz.npgw.test.common.entity.Acquirer;
import xyz.npgw.test.common.entity.BusinessUnit;
import xyz.npgw.test.common.entity.Company;
import xyz.npgw.test.common.entity.User;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public final class TestUtils {

    public static String encode(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8).replaceAll("\\+", "%20");
    }

    public static void createCompanyAdmin(APIRequestContext request, String company, String email, String password) {
        User user = User.newCompanyAdmin(company, email, password);
        User.delete(request, user);
        User.create(request, user);
        User.passChallenge(request, user.email(), user.password());
    }

    public static void deleteUser(APIRequestContext request, String email) {
        User.delete(request, email);
    }

    public static void deleteUsers(APIRequestContext request, User[] users) {
        Arrays.stream(users).forEach(user -> User.delete(request, user.email()));
    }

    public static BusinessUnit createBusinessUnit(APIRequestContext request, String companyName, String merchantTitle) {
        return BusinessUnit.create(request, companyName, merchantTitle);
    }

    public static BusinessUnit[] createBusinessUnits(APIRequestContext request, String company, String[] merchants) {
        return Arrays.stream(merchants)
                .map(merchantTitle -> BusinessUnit.create(request, company, merchantTitle))
                .toArray(BusinessUnit[]::new);
    }

    public static void deleteBusinessUnits(APIRequestContext request, String company, BusinessUnit[] businessUnits) {
        Arrays.stream(businessUnits).forEach(businessUnit -> BusinessUnit.delete(request, company, businessUnit));
    }

    public static void createBusinessUnitsIfNeeded(APIRequestContext request, User user) {
        Company.create(request, user.companyName());
        for (String merchantTitle : user.merchantIds()) {
            if (!BusinessUnit.exists(request, user.companyName(), merchantTitle)) {
                BusinessUnit.create(request, user.companyName(), merchantTitle);
            }
        }
    }

    public static void createCompany(APIRequestContext request, String companyName) {
        Company.create(request, companyName);
    }

    public static void createCompanyIfNeeded(APIRequestContext request, String companyName) {
        if (!Company.exists(request, companyName)) {
            Company.create(request, companyName);
        }
    }

    public static void deleteCompany(APIRequestContext request, String companyName) {
        if (companyName.equals("super")) {
            return;
        }
        while (Company.delete(request, companyName) == 422) {
            deleteBusinessUnits(request, companyName, BusinessUnit.getAll(request, companyName));
            deleteUsers(request, User.getAll(request, companyName));
        }
    }

    public static void createAcquirer(APIRequestContext request, Acquirer acquirer) {
        Acquirer.create(request, acquirer);
    }

    public static void deleteAcquirer(APIRequestContext request, String acquirerName) {
        Acquirer.delete(request, acquirerName);
    }
}
