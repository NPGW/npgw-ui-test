FROM mcr.microsoft.com/playwright/java:v1.51.0-noble

# Usage:
#
# build image:
#   cd ../
#   docker build -t ui-test:latest -f ./docker/Dockerfile .
#
# run container:
#   docker run
#       -e ENV_FILE="./config/.env"
#       ui-test:latest

USER root
WORKDIR /

COPY target/npgw-ui-test-*-jar-with-dependencies.jar /npgw-ui-test-jar-with-dependencies.jar
COPY config/testng.xml config/testng.xml

ENTRYPOINT ["java", "-jar", "npgw-ui-test-jar-with-dependencies.jar"]
