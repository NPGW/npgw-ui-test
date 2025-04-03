package xyz.npgw.test.run;

import com.microsoft.playwright.Locator;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.TmsLink;
import org.testng.annotations.Test;
import xyz.npgw.test.common.base.BaseTest;
import xyz.npgw.test.page.AddCompanyDialog;
import xyz.npgw.test.page.DashboardPage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import static org.testng.internal.junit.ArrayAsserts.assertArrayEquals;

public class AddCompanyDialogTest extends BaseTest {

    @Test
    @TmsLink("160")
    @Epic("Companies and business units")
    @Feature("Title Verification")
    @Description("Verify that the 'Add Company' window displays the correct title in the header.")
    public void testVerifyAddCompanyWindowTitle() {
        AddCompanyDialog addCompanyPage = new DashboardPage(getPage())
                .getHeader()
                .clickSystemAdministrationLink()
                .clickCompaniesAndBusinessUnitsTabButton()
                .clickAddCompanyButton();

        Allure.step("Verify: the header contains the expected title text");
        assertThat(addCompanyPage.getAddCompanyDialogHeader()).hasText("Add company");
    }

    @Test
    public void testVerifyPlaceholders() {
        List<String> expectedPlaceholders = List.of(
                "Enter company name",
                "Enter type",
                "Enter company description",
                "Enter company website",
                "Enter company primary contact",
                "Enter company email",
                "Enter country",
                "Enter state",
                "Enter ZIP",
                "Enter city",
                "Enter phone",
                "Enter mobile",
                "Enter fax"
        );

        AddCompanyDialog addCompanyPage = new DashboardPage(getPage())
                .clickSystemAdministrationLink()
                .clickCompaniesAndBusinessUnitsTabButton()
                .clickAddCompanyButton();

        assertEquals(expectedPlaceholders, addCompanyPage.getFieldPlaceholders());
    }
//        System.out.println("Количество найденных полей: " + inputFields.count());
//        // Извлекаем все плейсхолдеры в список
//        List<String> actualPlaceholders = inputFields.all()
//                .stream()
//                .map(input -> input.getAttribute("placeholder"))
//                .toList();
//
//     actualPlaceholders.forEach(System.out::println);

    // Проверяем, что извлеченные плейсхолдеры соответствуют ожидаемым
    // assertThat(actualPlaceholders).containsExactly(expectedPlaceholders);
}

//        Map<Locator, String> fields = new HashMap<>();
//        fields.put(addCompanyPage.getCompanyNameField(), "Enter company name");
//        fields.put(addCompanyPage.getCompanyTypeField(), "Enter type");
//        fields.put(addCompanyPage.getDescriptionField(), "Enter company description");
//        fields.put(addCompanyPage.getWebsiteField(), "Enter company website");
//        fields.put(addCompanyPage.getPrimaryContactField(), "Enter company primary contact");
//        fields.put(addCompanyPage.getCompanyEmailField(), "Enter company email");
//        fields.put(addCompanyPage.getCountryField(), "Enter country");
//        fields.put(addCompanyPage.getStateField(), "Enter state");
//        fields.put(addCompanyPage.getZipField(), "Enter ZIP");
//        fields.put(addCompanyPage.getCityField(), "Enter city");
//        fields.put(addCompanyPage.getPhoneField(), "Enter phone");
//        fields.put(addCompanyPage.getMobileField(), "Enter mobile");
//        fields.put(addCompanyPage.getFaxField(), "Enter fax");
//
//        for (Map.Entry<Locator, String> entry : fields.entrySet()) {
//            Locator field = entry.getKey();
//            String expectedPlaceholder = entry.getValue();
//
//            assertThat(field).hasAttribute("placeholder", expectedPlaceholder);
//        }

//        assertThat(addCompanyPage.getCompanyNameField()).hasAttribute("placeholder", "Enter company name");
//        assertThat(addCompanyPage.getCompanyTypeField()).hasAttribute("placeholder", "Enter type");
//        assertThat(addCompanyPage.getDescriptionField()).hasAttribute("placeholder", "Enter company description");
//        assertThat(addCompanyPage.getWebsiteField()).hasAttribute("placeholder", "Enter company website");
//        assertThat(addCompanyPage.getPrimaryContactField()).hasAttribute("placeholder", "Enter company primary contact");
//        assertThat(addCompanyPage.getCompanyEmailField()).hasAttribute("placeholder", "Enter company email");
//        assertThat(addCompanyPage.getCountryField()).hasAttribute("placeholder", "Enter country");
//        assertThat(addCompanyPage.getStateField()).hasAttribute("placeholder", "Enter state");
//        assertThat(addCompanyPage.getZipField()).hasAttribute("placeholder", "Enter ZIP");
//        assertThat(addCompanyPage.getCityField()).hasAttribute("placeholder", "Enter city");
//        assertThat(addCompanyPage.getPhoneField()).hasAttribute("placeholder", "Enter phone");
//        assertThat(addCompanyPage.getMobileField()).hasAttribute("placeholder", "Enter mobile");
//        assertThat(addCompanyPage.getFaxField()).hasAttribute("placeholder", "Enter fax");
//    }
//    }
//}