package xyz.npgw.test.utils;

import io.qameta.allure.internal.shadowed.jackson.databind.ObjectMapper;
import xyz.npgw.test.common.dto.Company;

import java.io.IOException;
import java.io.InputStream;

public class CompanyUtils {

    public static Company readCompanyInformationFromJson(String filePath) throws IOException {
        InputStream inputStream = CompanyUtils.class.getClassLoader().getResourceAsStream(filePath);
        ObjectMapper objectMapper = new ObjectMapper();

        return objectMapper.readValue(inputStream, Company.class);
    }
}
