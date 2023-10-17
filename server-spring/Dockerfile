#Test api docs at localhost:8080
#updated by dependabot
FROM eclipse-temurin:17-jre-alpine@sha256:642f8b0eee66d8d2fbe07028dd511a2e531a85bd190986a3fad571032371e026
EXPOSE 8080
#RUN useradd -u 8877 server
#USER server
COPY target/server-spring-deploy.jar .
ENTRYPOINT ["java", "-jar", "server-spring-deploy.jar"]
