#Test api docs at localhost:8080
#FROM eclipse-temurin:8-jre
#updated by dependabot
FROM eclipse-temurin:11-jre-alpine@sha256:aea6ff5a31148a9dc80c65fad592d5681c61f73dab8ad2f5b8fb08de256899dc
EXPOSE 8080
#RUN useradd -u 8877 server
#USER server
COPY target/server-spring-deploy.jar .
ENTRYPOINT ["java", "-jar", "server-spring-deploy.jar"]
