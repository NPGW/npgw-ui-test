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

USER root
WORKDIR /

ENV DOCKER_RUN=1

ENV PROJECT_PROPERTIES="setBaseURL=https://test.npgw.xyz;setEmail=test@email.com;setPassword=Qwerty123!;setTracingMode=true;setVideoMode=true;setCloseBrowserIfError=true;setColorScheme=DARK;setArtefactDir=target/artefact;setDefaultTimeout=5000;setAdditionalRetries=0"
ENV PLAYWRIGHT_BROWSER="chromium"
ENV PLAYWRIGHT_OPTIONS_CREATE="setEnv=PLAYWRIGHT_JAVA_SRC:src/test/java"
ENV PLAYWRIGHT_OPTIONS_LAUNCH="setHeadless=false;setSlowMo=0.0;setArgs=;setEnv="
ENV PLAYWRIGHT_OPTIONS_CONTEXT="setLocale=en-GB;setTimezoneId=Europe/Paris;setColorScheme=DARK;setViewportSize=1920,964;setBaseURL=https://test.npgw.xyz;setRecordVideoDir=target/artefact;setRecordVideoSize=1920,957"
ENV PLAYWRIGHT_OPTIONS_TRACING="setName=;setScreenshots=true;setSnapshots=true;setSources=true;setTitle="

COPY target/npgw-ui-test-*-jar-with-dependencies.jar /npgw-ui-test-jar-with-dependencies.jar
COPY testng.xml .

ENTRYPOINT ["java", "-jar", "npgw-ui-test-jar-with-dependencies.jar"]
