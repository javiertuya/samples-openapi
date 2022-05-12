# Samples of generation of rest server and client api components with OpenApi 

This is a multimodule maven project that includes samples to generate rest services 
based on a OpenApi V3 specification:

- Java:
  - *server-spring*: Rest server (Spring Boot)
  - *client-resttemplate*: Java client api (Spring Resttemplate and Fasterxml)
  - *client-httpclient*: Java client api (Apache Httpclient and Fasterxml)

## Notes
- To run each module with Eclipse, first generate the api using `mvn generate-sources` and refresh the workspace
- The OpenApi schema is common for all modules, located at [schema/api.yml](schema/api.yml)
- Endpoints of services are `localhost:8080`
- Servers are tested in a temurin java 8 jre container
- Swagger modules are updated to the most recent version when possible using dependbot, openapi modules only for security updates

## Useful links
- [OpenApi overview](https://swagger.io/docs/specification/about/)
- [OpenApi generator](https://github.com/OpenAPITools/openapi-generator)
- [OpenApi generator maven plugin](https://github.com/OpenAPITools/openapi-generator/tree/master/modules/openapi-generator-maven-plugin)
- [Generators list and platform specific configuration](https://openapi-generator.tech/docs/generators/)
