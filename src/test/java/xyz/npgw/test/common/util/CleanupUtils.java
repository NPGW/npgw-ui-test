package xyz.npgw.test.common.util;

import com.google.gson.Gson;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.APIResponse;
import lombok.extern.log4j.Log4j2;
import xyz.npgw.test.common.UserRole;
import xyz.npgw.test.common.entity.BusinessUnit;
import xyz.npgw.test.common.entity.Company;
import xyz.npgw.test.common.entity.User;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class CleanupUtils {

    private static final String doNotDelete = "CompanyForTestRunOnly Inc.";

    public static void cleanCompanies(APIRequestContext request) {

        APIResponse response = request.get("portal-v1/company");
        Company[] comp = new Gson().fromJson(response.text(), Company[].class);

        List<Company> companyList = Arrays.stream(comp).filter(c -> !c.companyName().equals(doNotDelete)).toList();
        for (Company item : companyList) {

            BusinessUnit[] businessUnits = TestUtils.getAllMerchants(request, item.companyName());
            User[] users = TestUtils.getAllUsers(request, item.companyName());

            if (users.length == 0 && businessUnits.length == 0) {
                log.info("---will delete ---> |{}|", item.companyName());
                TestUtils.deleteCompany(request, item.companyName());
            }
        }
    }

    public static void cleanOldMerchants(APIRequestContext request) {

        APIResponse response = request.get("portal-v1/company");
        Company[] comp = new Gson().fromJson(response.text(), Company[].class);

        List<Company> companyList = Arrays.stream(comp).filter(c -> !c.companyName().equals(doNotDelete)).toList();
        for (Company item : companyList) {

            BusinessUnit[] businessUnits = TestUtils.getAllMerchants(request, item.companyName());
            for (BusinessUnit businessUnit : businessUnits) {
                if (businessUnit.merchantName().isBlank()
                        || !businessUnit.merchantId().startsWith("id.merchant.")
                        || businessUnit.merchantId().matches("^id.merchant.[0-9a-f]{32}$")) {
                    log.info("---will delete merchant ---> |{}| from {}", businessUnit.merchantId(), item.companyName());
                    TestUtils.deleteMerchant(request, item.companyName(), businessUnit);
                }
            }
        }
    }


    public static void cleanMerchants(APIRequestContext request) {

        APIResponse response = request.get("portal-v1/company");
        Company[] comp = new Gson().fromJson(response.text(), Company[].class);

        List<Company> companyList = Arrays.stream(comp).filter(c -> !c.companyName().equals(doNotDelete)).toList();
        for (Company item : companyList) {

            User[] users = TestUtils.getAllUsers(request, item.companyName());
            if (users.length == 0) {
                log.info("delete all merchants from company without users");
                BusinessUnit[] businessUnits = TestUtils.getAllMerchants(request, item.companyName());
                for (BusinessUnit businessUnit : businessUnits) {
                    log.info("---will delete merchant ---> |{}| from {}", businessUnit.merchantId(), item.companyName());
                    TestUtils.deleteMerchant(request, item.companyName(), businessUnit);
                }
            }
        }
    }

    public static void cleanUsers(APIRequestContext request) {

        APIResponse response = request.get("portal-v1/company");
        Company[] comp = new Gson().fromJson(response.text(), Company[].class);

        List<Company> companyList = Arrays.stream(comp).filter(c -> !c.companyName().equals(doNotDelete)).toList();
        for (Company item : companyList) {

            User[] users = TestUtils.getAllUsers(request, item.companyName());
            for (User user : users) {

                //all company admins will be removed
                if (user.userRole() == UserRole.ADMIN) {
                    log.info("all company admins will be removed");
                    log.info("--- will delete user ---> |{}| from {}", user.email(), item.companyName());
                    TestUtils.deleteUser(request, user);
                }

                //company analyst should have some merchantId
                if (user.userRole() == UserRole.USER && user.merchantIds().length == 0) {
                    log.info("company analyst should have some merchantId");
                    log.info("--- will delete user ---> |{}| from {}", user.email(), item.companyName());
                    TestUtils.deleteUser(request, user);
                }

                //any user role is linked to invalid merchantId
                boolean deleteUser = false;
                for (String merchantId : user.merchantIds()) {
                    if (merchantId.isBlank()
                            || !merchantId.startsWith("id.merchant.")
                            || merchantId.matches("^id.merchant.[0-9a-f]{32}$")) {

                        log.info("--- will delete merchant ---> |{}| from {}", merchantId, item.companyName());
                        TestUtils.deleteMerchant(request, item.companyName(), new BusinessUnit(merchantId, ""));
                        deleteUser = true;
                    }
                }
                if (deleteUser) {
                    log.info("any user role is linked to invalid merchantId");
                    log.info("--- will delete user ---> |{}| from {}", user.email(), item.companyName());
                    TestUtils.deleteUser(request, user);
                }

                // SUPER user outside SUPER company
                if (user.userRole() == UserRole.SUPER && !"super".equals(user.companyName())) {
                    log.info("SUPER user outside SUPER company");
                    log.info("--- will delete user ---> |{}| from {}", user.email(), item.companyName());
                    TestUtils.deleteUser(request, user);
                }
            }
        }
    }
}
