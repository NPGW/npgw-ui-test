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

    private static final List<String> doNotDeleteCompanyList = List.of(
            "Luke Payments", "CompanyForTestRunOnly Inc.", "super");
    private static final List<String> err500List = List.of(
            "A2 info", "Company 112172", "Smitham-Johnson", "new company", "testframework");
    private static final List<String> doNotDeleteUserList = List.of(
            "test@email.com", "supertest@email.com", "admintest@email.com", "usertest@email.com");
    private static final List<String> doNotDeleteAcquirersList = List.of(
            "Luke EUR MID 1");

    public static void deleteCompaniesWithoutUsersAndMerchants(APIRequestContext request) {

        APIResponse response = request.get("portal-v1/company");
        Company[] comp = new Gson().fromJson(response.text(), Company[].class);

        Arrays.stream(comp)
                .filter(c -> !doNotDeleteCompanyList.contains(c.companyName()))
                .filter(c -> !err500List.contains(c.companyName()))
                .forEach(item -> {
                    BusinessUnit[] businessUnits = TestUtils.getAllMerchants(request, item.companyName());
                    User[] users = TestUtils.getAllUsers(request, item.companyName());

                    if (users.length == 0 && businessUnits.length == 0) {
                        log.info("---will delete company---> |{}|", item.companyName());
                        TestUtils.deleteCompany(request, item.companyName());
                    }
                });
    }

    public static void deleteMerchantsForCompaniesWithoutUsersAndWithDefaultValues(APIRequestContext request) {

        APIResponse response = request.get("portal-v1/company");
        Company[] comp = new Gson().fromJson(response.text(), Company[].class);

        Arrays.stream(comp)
                .filter(c -> !doNotDeleteCompanyList.contains(c.companyName()))
                .filter(c -> !err500List.contains(c.companyName()))
                .forEach(item -> {
                    User[] users = TestUtils.getAllUsers(request, item.companyName());
                    if (users.length == 0) {
                        log.info("delete all merchants from |{}| without users", item.companyName());
                        BusinessUnit[] businessUnits = TestUtils.getAllMerchants(request, item.companyName());
                        for (BusinessUnit businessUnit : businessUnits) {
                            log.info("---will delete merchant ---> |{}|", businessUnit.merchantId());
                            TestUtils.deleteMerchant(request, item.companyName(), businessUnit);
                        }
                    }
                });
    }

    public static void deleteUnneededUsers(APIRequestContext request) {

        APIResponse response = request.get("portal-v1/company");
        Company[] comp = new Gson().fromJson(response.text(), Company[].class);

        Arrays.stream(comp)
                .filter(c -> !doNotDeleteCompanyList.contains(c.companyName()))
                .filter(c -> !err500List.contains(c.companyName()))
                .forEach(item -> {
                    log.info("delete users from |{}|", item.companyName());
                    User[] users = TestUtils.getAllUsers(request, item.companyName());
                    for (User user : users) {

                        //any user role is linked to invalid merchantId
                        boolean deleteUser = false;
                        for (String merchantId : user.merchantIds()) {
                            if (merchantId.isBlank()
                                    || !merchantId.startsWith("id.merchant.")
                                    || merchantId.matches("^id.merchant.[0-9a-f]{32}$")) {

                                log.info("---will delete invalid merchant ---> |{}|", merchantId);
                                TestUtils.deleteMerchant(request,
                                        item.companyName(),
                                        new BusinessUnit(merchantId, ""));
                                deleteUser = true;
                            }
                        }
                        if (deleteUser) {
                            log.info("any user role is linked to invalid merchantId");
                            log.info("--- will delete user with invalid merchantId ---> |{}|", user.email());
                            TestUtils.deleteUser(request, user);
                        }

                        if (user.userRole() == UserRole.ADMIN && user.merchantIds().length != 0) {
                            log.info("company admins with merchant");
                            for (String merchantId : user.merchantIds()) {
                                log.info("---will delete admin merchant ---> |{}|", merchantId);
                                TestUtils.deleteMerchant(request,
                                        item.companyName(),
                                        new BusinessUnit(merchantId, ""));
                            }
                        }

                        if (user.userRole() == UserRole.ADMIN) {
                            log.info("all company admins will be removed");
                            log.info("---will delete admin ---> |{}|", user.email());
                            TestUtils.deleteUser(request, user);
                        }


                        if (user.userRole() == UserRole.USER && user.merchantIds().length == 0) {
                            log.info("company analyst should have some merchantId");
                            log.info("---will delete analyst ---> |{}|", user.email());
                            TestUtils.deleteUser(request, user);
                        }

                        if (user.userRole() == UserRole.SUPER && !"super".equals(user.companyName())) {
                            log.info("SUPER user outside SUPER company");
                            log.info("--- will delete outside SUPER ---> |{}|", user.email());
                            TestUtils.deleteUser(request, user);
                        }

                        if (user.userRole() == UserRole.SUPER && user.merchantIds().length != 0) {
                            log.info("super admins with merchant");
                            for (String merchantId : user.merchantIds()) {
                                log.info("---will delete super merchant ---> |{}|", merchantId);
                                TestUtils.deleteMerchant(request,
                                        item.companyName(),
                                        new BusinessUnit(merchantId, ""));
                            }
                        }

                        if (user.userRole() == UserRole.SUPER && !doNotDeleteUserList.contains(user.email())) {
                            log.info("unprotected SUPER user");
                            log.info("--- will delete unprotected SUPER ---> |{}|", user.email());
                            TestUtils.deleteUser(request, user);
                        }
                    }
                });
    }
}
