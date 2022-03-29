#Build/run commands (from this folder)
#  cp target/samples-swagger-server-*.jar target/swagger-server-deploy.jar
#  docker build --pull -t swagger-server .
#  docker run -d -p 8080:8080 --name swagger-server --restart unless-stopped swagger-server
#Test api docs at localhost:8080
FROM eclipse-temurin:8-jre
EXPOSE 8080
RUN useradd -u 8877 server
USER server
COPY target/swagger-server-deploy.jar .
ENTRYPOINT ["java", "-jar", "swagger-server-deploy.jar"]
