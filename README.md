# iCarros backend challenge REST API

## About 

This REST API was built as a requirement to proceed in a hiring process for Itaú company. It was developed using Java 21, Spring Boot 3 and Maven. 

The API was designed to have a single endpoint that just validates a given password. The simplicity of the API was used to demonstrate how an API should be structured, tested, documented and properly monitored. The author also explored the simplicity to use several well-known libraries related to Java.

## Architecture overview 

This application was designed having a microservice in mind. It means that the author avoided to follow the Clean Architecture principles in some cases to prevent boilerplating. Some principles were followed to keep the application maintainable, testable and performant 

This application follows a layered-based architecture and the dependency flows this way: 

```
controller -> application -> dto, exception, wrappers
```

### Application layer

Has classes that are responsible for the business rules to be applied. The `AuthService` class is the main one, and is responsible for handling the business logic related to authentication. Something similar could be told about the `LogService` class 

### Configuration layer

Hold some classes that keep Spring Boot working as it should. These classes are mainly responsible for providing the beans needed by Spring Boot

### Controller layer 

These classes are responsible for handling the incoming requests and sending the respective responses 

### DTO layer

These classes defines the data that will be transferred between layers and also between client and server 

### Exception layer

As you may guess, this layer have the definitions for exceptions to be thrown by the application

### Wrapper "layer"

Created mainly for test purposes, to wrap some dependencies so they can be mocked during tests

## Running the application 

A Docker setup was made so everyone can easily run the application and its tests

- `docker compose up app` runs the application that can be reached through the port 8080 on `localhost`
- `docker compose up [tests|unit-tests|integration-tests|e2e-tests]` runs the application tests
- `docker compose run mvn` allows the developer to run Maven commands against the project (like `clean install` or `dependency:resolve`)

## More info.

- Refer to the REST API documentation: After running the application with `docker compose up app`, visit the [Swagger documentation](http://localhost:8080/swagger-ui/index.html)
- Refer to JavaDocs by following these steps: 
    1. Generate JavaDocs with `docker compose run --rm mvn javadocs:javadocs`
    2. Run `docker compose up -d javadocs`
    3. Visit [localhost:3000](http://localhost:3000) to see the docs