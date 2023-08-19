#Test api docs at localhost:8080
#FROM eclipse-temurin:8-jre
#updated by dependabot
FROM eclipse-temurin:11-jre-alpine@sha256:1b3a73a450d23ba66d5854febd7a59230b389799a47af52eaa248e1fbfabd733
EXPOSE 8080
#RUN useradd -u 8877 server
#USER server
COPY target/server-spring-deploy.jar .
ENTRYPOINT ["java", "-jar", "server-spring-deploy.jar"]
