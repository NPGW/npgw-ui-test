package xyz.npgw.test.common.util;

public record CompanyTestData(

        String companyName,
        String companyType,
        String description,
        String website,
        String primaryContact,
        String companyEmail,
        String country,
        String state,
        String zip,
        String city,
        String phone,
        String mobile,
        String fax,
        boolean apiActive,
        boolean portalActive
) {

    public static CompanyTestData validCompanyTestData() {
        return new CompanyTestData(
                "CompanyNameTest",
                "Company Type Test",
                "Description Test",
                "https://www.test.com",
                "James Smith",
                "test@yahoo.com",
                "USA",
                "PA",
                "19876",
                "Warwick",
                "2151111111",
                "2152222222",
                "222333444",
                true,
                true
        );
    }
}
