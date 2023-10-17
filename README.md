# Samples of generation of rest server and client api components with OpenApi 

This is a multimodule maven project that includes samples to generate rest services 
based on a OpenApi V3 specification:

- *server-spring*: Rest server (Java 17)
- *client-resttemplate*: Java client api (Resttemplate and Fasterxml, Java 17)
- *client-httpclient*: Java client api (Apache Httpclient and Fasterxml, Java 8)
- *client-netcore*: .NET Core client api (RestSharp and Newtonsosft.Json, net 6.0)

## Notes
- To generate the api for all modules (including .NET Core) run `mvn generate-sources` from the project root
- The server is located in *server-spring* module at `giis.samples.openapi.invoker.OpenAPI2SpringBoot` or in an executable jar (if generated with `mvn package`)
- To run from Eclipse, ensure that the generated sources (`target/generated-sources/copenapi/src/main/java`) are included in the build path
- The .NET Core client can be run from Visual Studio at `client-netcore/client-netcore.sln` (project is linked with the generated api code)
- The OpenApi schema is common for all modules, located at [schema/api.yml](schema/api.yml)
- Endpoints of services are `localhost:8080`
- Server is tested in a temurin java jre container

## Useful links
- [OpenApi overview](https://swagger.io/docs/specification/about/)
- [OpenApi generator](https://github.com/OpenAPITools/openapi-generator)
- [OpenApi generator maven plugin](https://github.com/OpenAPITools/openapi-generator/tree/master/modules/openapi-generator-maven-plugin)
- [Generators list and platform specific configuration](https://openapi-generator.tech/docs/generators/)
