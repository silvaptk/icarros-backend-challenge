# Itaú Unibanco Backend Challenge -- REST API

## About

This REST API was developed as a requirement for a hiring process at
Itaú Unibanco.\
It was built using **Java 21**, **Spring Boot 3**, and **Maven**.

The API exposes a single endpoint responsible for validating a given
password.\
Despite its simplicity, the project was intentionally designed to
demonstrate how a backend application can be **well-structured, tested,
and documented**, while applying solid software engineering principles.

## Architecture overview

Although designed with a microservice mindset, the application
deliberately avoids strict adherence to *Clean Architecture* in some
areas in order to reduce unnecessary boilerplate.\
Still, several principles were applied to keep the codebase
**maintainable, testable, and performant**.

The application follows a **layered architecture**, with dependencies
flowing in the following direction:

    controller → application → core

### Controller layer

Responsible for handling incoming HTTP requests and returning
appropriate responses.\
Controllers delegate business logic execution to the application layer.

### Application layer

Contains classes responsible for orchestrating business rules.\
For example, the `AuthService` class handles authentication-related
logic and coordinates the validation process.

### Core layer

Contains the core business rules and domain logic, completely decoupled
from framework-specific concerns.

## Solution details

The goal was to build a **robust and extensible validation system** by
applying:

-   **SOLID principles**
-   ***Clean Code*** practices

Once the validation infrastructure was in place, implementing and
testing the actual password rules became straightforward.

The solution avoids heavy usage of third-party libraries and advanced
Java features on purpose, aiming to demonstrate the ability to write
**clear, high-quality code**, regardless of language or framework.\
That said, some Spring features were leveraged where appropriate.

### The validation system basis

The validation system is based on two main abstract classes:

-   `Validator.java`
-   `ValidationRule.java`

In summary:

-   `Validator` aggregates multiple validation rules
-   `ValidationRule` represents a single validation constraint

Each `ValidationRule` registers itself in a `Validator` instance through
dependency injection during construction.

The `Validator` class exposes a `validate` method that receives the data
to be validated and returns `true` if all rules pass, or `false`
otherwise.

### Priority mechanism

To handle cases where the input might not be of the expected type, a
**priority mechanism** was introduced.

Each rule has an associated `int` priority value:

-   Rules with **lower priority values are executed first**
-   Rules are automatically ordered inside the `Validator`

### How SOLID applies?

The developed system is all about

- Classes having a single responsibility (S) since the child classes created must do only one thing
- Introducing new behavior by introducing new classes (O): If you have to introduce a new rule, or have a new piece of data to validate, just need to create more child classes instead of editing existing code
- The validation system runs over abstraction instead of implementation (D) because the heavy lifting is done by the abstract classes mentioned

### Password validation implementation

Based on the system described above, the [`PasswordValidator.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/validators/PasswordValidator.java) class was built. It just extends the `Validator` class setting `String` as the type argument for `Validator`.

In the other hand, the following classes represent every single rule stated for the password validation:

1. [`DigitPasswordValidationRule.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/rules/password/DigitPasswordValidationRule.java) to ensure that the password has digits
2. [`InvalidTypePasswordValidationRule.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/rules/password/InvalidTypePasswordValidationRule.java) to ensure that the given passwords are instance of `String`, just to avoid exceptions in the following validators
3. [`LowerLetterPasswordValidationRule.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/rules/password/LowerLetterPasswordValidationRule.java) to ensure that the password has at least one lowercase letter
4. [`ShortPasswordValidationRule.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/rules/password/ShortPasswordValidationRule.java) to reject passwords with less than 9 characters
5. [`SpecialCharPasswordValidationRule.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/rules/password/SpecialCharPasswordValidationRule.java) to ensure that the password has at least one special character among the following: `!@#$%^&*()-+`
6. [`UniqueCharsPasswordValidationRule.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/rules/password/UniqueCharsPasswordValidationRule.java) that reject passwords with repeated characters
7. [`UpperLetterPasswordValidationRule.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/rules/password/UpperLetterPasswordValidationRule.java) to reject passwords without uppercase letters
8. [`ValidCharsPasswordValidationRule.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/rules/password/ValidCharsPasswordValidationRule.java) to reject passwords without known characters

Of course the author could use a simpler solution, like creating a `PasswordValidation` class with one method for each one of the constraints mentioned above, or even a third-party library, but the solution described was designed to be able to show experience in OOP and SOLID.

### Extensibility example

Let's suppose that we now have to validate usernames as well. One would just need to

1. add a `UsernameValidator` class extending the `Validator` abstract class
2. implement the validation rules as child classes of `ValidationRule` that receive `UsernameValidator` as an argument for the constructor method

The validator:

```java
class UsernameValidator extends Validator<String> {}
```

Example of a validation rule:

```java
class StartsWithSlashUsernameValidationRule extends ValidationRule<String> {
    public StartsWithSlashUsernameValidationRule(UsernameValidator validator) {
        super(validator);
    }
    
    @Override
  public Boolean validate(String username) {
        /* ... */
    }
}
```

In the other hand, if a given rule is no longer needed, one just have to erase its class because they are plug-and-play by design, thanks to DI.

## Running the application

Docker is used to simplify execution:

-   `docker compose up app`
-   `docker compose up [tests|unit-tests|integration-tests|e2e-tests]`
-   `docker compose run mvn`

## Running the application

A Docker setup was made so everyone can easily run the application and its tests

- `docker compose up app` runs the application that can be reached through the port 8080 on `localhost`
- `docker compose up [tests|unit-tests|integration-tests|e2e-tests]` runs the application tests
- `docker compose run mvn` allows the developer to run Maven commands against the project (like `clean install` or `dependency:resolve`)

## Additional information

- Refer to the REST API documentation: After running the application with `docker compose up app`, visit the [Swagger documentation](http://localhost:8080/swagger-ui/index.html)
- Refer to JavaDocs by following these steps:
    1. Generate JavaDocs with `docker compose run --rm mvn javadoc:javadoc`
    2. Run `docker compose up -d javadocs`
    3. Visit [localhost:3000](http://localhost:3000) to see the docs