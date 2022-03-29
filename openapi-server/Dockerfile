#Build/run commands (from this folder)
#  cp target/samples-openapi-server-*.jar target/openapi-server-deploy.jar
#  docker build --pull -t openapi-server .
#  docker run -d -p 8080:8080 --name openapi-server --restart unless-stopped openapi-server
#Test api docs at localhost:8080
FROM eclipse-temurin:8-jre
EXPOSE 8080
RUN useradd -u 8877 server
USER server
COPY target/openapi-server-deploy.jar .
ENTRYPOINT ["java", "-jar", "openapi-server-deploy.jar"]
