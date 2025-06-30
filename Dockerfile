FROM mcr.microsoft.com/playwright/java:v1.51.0-noble

# Usage:
# 1. build jar:
# mvn clean package -DskipTests
#
# 2. build image:
#   docker build -t ui-test:latest
#
# 3. run container:
#   docker run ui-test:latest
#       -e LOCAL_EMAIL =
#       -e LOCAL_PASSWORD =
#       -e LOCAL_BASE_URL =
# other variables have default values in ProjectProperties.java

USER root
WORKDIR /

ENV DOCKER_RUN=1

COPY target/npgw-ui-test-*-jar-with-dependencies.jar /npgw-ui-test-jar-with-dependencies.jar
COPY testng.xml .

ENTRYPOINT ["java", "-jar", "npgw-ui-test-jar-with-dependencies.jar"]
