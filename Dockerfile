FROM mcr.microsoft.com/playwright/java:v1.51.0-jammy

USER root
WORKDIR /

COPY target/npgw-ui-test-1.0-SNAPSHOT-test-jar-with-dependencies.jar /target/npgw-ui-test-1.0-SNAPSHOT-test-jar-with-dependencies.jar
COPY config /config

COPY config/.env /config/.env

ENV CONFIG_PATH=/config/.env
ENV TESTNG_SUITE_XML=/config/testng.xml

RUN ls -la /

ENTRYPOINT ["java", "-DconfigPath=/config/.env", "-Dtestng.suiteXml=./config/testng.xml", "-jar", "/target/npgw-ui-test-1.0-SNAPSHOT-test-jar-with-dependencies.jar"]
