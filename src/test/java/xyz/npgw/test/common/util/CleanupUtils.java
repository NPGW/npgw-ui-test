package xyz.npgw.test.common.util;

import com.microsoft.playwright.APIRequestContext;
import xyz.npgw.test.common.entity.acquirer.Acquirer;
import xyz.npgw.test.common.entity.company.Company;
import xyz.npgw.test.common.entity.control.Control;
import xyz.npgw.test.common.entity.user.User;

import java.util.List;

public class CleanupUtils {

    private static final List<String> COMPANY = List.of(
            "Amazon", "CompanyForTestRunOnly Inc.", "super", "Luke Company");
    private static final List<String> USER = List.of("test@email.com", "supertest@email.com");
    private static final List<String> ACQUIRER = List.of("Luke EUR MID 1");
    private static final List<String> CONTROL = List.of("control-for-test", "control-for-test-2");

    public static void clean(APIRequestContext request) {
        deleteCompanies(request);
        deleteAcquirers(request);
        deleteUsersFromSuper(request);
        deleteFraudControls(request);
    }

    public static void deleteCompanies(APIRequestContext request) {
        Company.getAll(request).stream()
                .filter(c -> !COMPANY.contains(c.companyName()))
                .filter(c -> c.companyName().matches("^\\d{4}\\.\\d{6}.*$"))
                .filter(c -> TestUtils.isOneHourOld(c.companyName()))
                .forEach(item -> Company.delete(request, item.companyName()));
    }

    public static void deleteAcquirers(APIRequestContext request) {
        Acquirer.getAllAcquirers(request).stream()
                .filter(acquirer -> !ACQUIRER.contains(acquirer.getAcquirerName()))
                .filter(acquirer -> acquirer.getAcquirerName().matches("^\\d{4}\\.\\d{6}.*$"))
                .filter(acquirer -> !acquirer.getAcquirerName().startsWith("acquirerName "))
                .filter(acquirer -> TestUtils.isOneHourOld(acquirer.getAcquirerName()))
                .forEach(item -> Acquirer.deleteAcquirer(request, item.getAcquirerName()));
    }

    private static void deleteUsersFromSuper(APIRequestContext request) {
        User.getAll(request, "super").stream()
                .filter(user -> !USER.contains(user.getEmail()))
                .filter(user -> user.getEmail().matches("^\\d{4}\\.\\d{6}.*$"))
                .filter(user -> TestUtils.isOneHourOld(user.getEmail()))
                .forEach(user -> User.delete(request, user.getEmail()));
    }

    private static void deleteFraudControls(APIRequestContext request) {
        Control.getAll(request).stream()
                .filter(control -> !CONTROL.contains(control.getControlName()))
                .filter(control -> control.getControlName().matches("^\\d{4}\\.\\d{6}.*$"))
                .filter(control -> TestUtils.isOneHourOld(control.getControlName()))
                .forEach(control -> Control.delete(request, control.getControlName()));
    }
}
