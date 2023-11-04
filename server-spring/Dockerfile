#Test api docs at localhost:8080
#updated by dependabot
FROM eclipse-temurin:17-jre-alpine@sha256:984703da8353d0a33eb04944b56665e84c6271e5d4f8a679e73cb5bd2b846301
EXPOSE 8080
#RUN useradd -u 8877 server
#USER server
COPY target/server-spring-deploy.jar .
ENTRYPOINT ["java", "-jar", "server-spring-deploy.jar"]
