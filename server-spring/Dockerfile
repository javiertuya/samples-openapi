#Test api docs at localhost:8080
#updated by dependabot
FROM eclipse-temurin:17-jre-alpine@sha256:532dc48a91112108f012784618213767e7458368b51b77d37a1a076cd7e70ef8
EXPOSE 8080
#RUN useradd -u 8877 server
#USER server
COPY target/server-spring-deploy.jar .
ENTRYPOINT ["java", "-jar", "server-spring-deploy.jar"]
