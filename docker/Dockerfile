FROM mcr.microsoft.com/playwright/java:v1.50.0-noble

# Usage:
#
# build image:
#   cd ../
#   docker build -t ui-test:latest -f ./docker/Dockerfile .
#
# run container:
#   docker run ui-test:latest
#
# required in .env:
# local.baseURL=<>
# local.email=<>
# local.password=<>
# local.headlessMode=true

USER root
WORKDIR /

COPY target/npgw-ui-test-*-jar-with-dependencies.jar /npgw-ui-test-jar-with-dependencies.jar
COPY config/ config/

ENTRYPOINT ["java", "-jar", "npgw-ui-test-jar-with-dependencies.jar"]
