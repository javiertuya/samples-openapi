# Samples of generation of rest server and client api components with OpenApi and Swagger 

This is a multimodule maven project that includes samples to generate rest services 
based on a OpenApi V3 specification:

- OpenApi Generator:
  - *openapi-server*: Rest server (Jersey and Jetty)
  - *openapi-client*: Java client api (jersey and Fasterxml)
- Swagger Codegen:
  - *swagger-server*: Rest server (Spring Boot)
  - *swagger-client*: Java client api (OkHttp and Gson)

## Notes
- To run each module with Eclipse, first generate the api using `mvn generate-sources` and refresh the workspace
- The OpenApi schema is common for all modules, located at [schema/api.yml](schema/api.yml)
- Endpoints of services are `localhost:8080`
- Servers are tested in a temurin java 8 jre container
- Swagger modules are updated to the most recent version when possible using dependbot, openapi modules only for security updates