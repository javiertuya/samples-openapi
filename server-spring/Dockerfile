#Test api docs at localhost:8080
#FROM eclipse-temurin:8-jre
FROM eclipse-temurin:11.0.14.1_1-jre-alpine
EXPOSE 8080
#RUN useradd -u 8877 server
#USER server
COPY target/server-spring-deploy.jar .
ENTRYPOINT ["java", "-jar", "server-spring-deploy.jar"]
