FROM mcr.microsoft.com/playwright/java:v1.51.0-noble

# Usage:
# 1. build jar:
# mvn clean package -DskipTests
#
# 2. build image:
#   cd ../
#   docker build -t ui-test:latest -f ./docker/Dockerfile .
#
# 3. run container:
#   docker run ui-test:latest
#
#
# required env:
# LOCAL_EMAIL =
# LOCAL_PASSWORD =
# LOCAL_BASE_URL =

USER root
WORKDIR /

COPY target/npgw-ui-test-*-jar-with-dependencies.jar /npgw-ui-test-jar-with-dependencies.jar
COPY testng.xml .

ENTRYPOINT ["java", "-jar", "npgw-ui-test-jar-with-dependencies.jar"]
