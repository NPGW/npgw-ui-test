FROM mcr.microsoft.com/playwright/java:v1.50.0-noble

USER root
WORKDIR /

COPY target/npgw-ui-test-*-jar-with-dependencies.jar /target/npgw-ui-test-jar-with-dependencies.jar
COPY config /config

ENTRYPOINT ["java", "-DconfigPath=/config/.env", "-Dtestng.suiteXml=./config/testng.xml", "-jar", "/target/npgw-ui-test-jar-with-dependencies.jar"]
