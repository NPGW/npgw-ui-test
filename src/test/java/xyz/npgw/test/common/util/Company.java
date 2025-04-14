package xyz.npgw.test.common.util;

public record Company(

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
){}

