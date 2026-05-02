#Test api docs at localhost:8080
#updated by dependabot
FROM eclipse-temurin:17-jre-alpine@sha256:88c0002860cda56384d5ed3b2da4d0d9a2b44dc2ee4dc02344be985bd8b524bc
EXPOSE 8080
#RUN useradd -u 8877 server
#USER server
COPY target/server-spring-deploy.jar .
ENTRYPOINT ["java", "-jar", "server-spring-deploy.jar"]
