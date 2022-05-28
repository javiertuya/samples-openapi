#Test api docs at localhost:8080
#FROM eclipse-temurin:8-jre
#updated by dependabot
FROM eclipse-temurin:11-jre-alpine@sha256:44984e6bd1a3d394494ccfe8347f09595c2410fd4b345373bbd4b3b69d8cac50
EXPOSE 8080
#RUN useradd -u 8877 server
#USER server
COPY target/server-spring-deploy.jar .
ENTRYPOINT ["java", "-jar", "server-spring-deploy.jar"]
