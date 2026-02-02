# Itaú Unibanco Backend Challenge -- REST API

# Índice/Table of contents

- [pt-BR](#pt-br)
  - [Sobre](#sobre)
  - [Visão geral de arquitetura](#visão-geral-de-arquitetura)
    - [Camada _Controller_](#camada-_controller_)
    - [Camada _Application_](#camada-_application_)
    - [Camada _Core_](#camada-_core_)
  - [Detalhes da solução](#detalhes-da-solução)
    - [A base do sistema de validação](#a-base-do-sistema-de-validação)
    - [Mecanismo de prioridade](#mecanismo-de-prioridade)
    - [Como os princípios de SOLID se aplicam?](#como-os-princípios-solid-se-aplicam)
    - [Implementação da validação da senha](#implementação-da-validação-da-senha)
    - [Exemplo de extensibilidade](#exemplo-de-extensibilidade)
  - [Executando a aplicação](#executando-a-aplicação)
  - [Informação adicional](#informação-adicional)
- [en-US](#en-us)
  - [About](#about)
  - [Architecture overview](#architecture-overview)
    - [Controller Layer](#controller-layer)
    - [Application Layer](#application-layer)
    - [Core Layer](#core-layer)
  - [Solution details](#solution-details)
    - [The validation system basis](#the-validation-system-basis)
    - [Priority mechanism](#priority-mechanism)
    - [How SOLID applies?](#how-solid-applies)
    - [Password validation implementation](#password-validation-implementation)
    - [Extensibility example](#extensibility-example)
  - [Running the application](#running-the-application)
  - [Additional information](#additional-information)

# pt-BR

## Sobre

Essa API REST foi desenvolvida como exigência para um processo de 
contratação na empresa Itaú Unibanco. Ela foi desenvolvida usando **Java 21**,
**Spring Boot 3** e **Maven**.

A API expõe apenas um *endpoint* responsável por validar uma dada senha. Apesar
de sua simplicidade, o projeto foi intencionalmente elaborado para demonstrar como
uma aplicação *backend* pode ser **bem-estruturada, testada e documentada**, 
aplicando princípios de engenharia de *software* sólidos.

## Visão geral de arquitetura

Por ter sido desenvolvida com um _mindset_ orientado a 
microsserviços, a aplicação evita a aderência deliberada aos 
princípios de _Clean Architecture_ em algumas áreas para reduzir
o _boilerplate_ desnecessário. Apesar disso, vários princípios
foram aplicados para manter a base de código **sustentável, testável e
eficiente**.

A aplicação segue uma **arquitetura baseada em camadas**, com as 
dependências fluindo na seguinte direção

```
controller → application → core
```

### Camada _Controller_

Responsável por lidar com as requisições HTTP diretamente, devolvendo as 
respostas apropriadas. Os _controllers_ delegam a aplicação das regras de 
negócio às classes da camada _Application_

### Camada _Application_

Contém classes responsáveis por orquestrar as regras de negócio. A 
classe `AuthService`, por exemplo, lida com a lógica relacionada a 
autenticação e coordena o processo de validação.

### Camada _Core_

É a camada que contém as regras de negócio e a lógica do domínio, 
completamente desacoplada de _frameworks_.

## Detalhes da solução

O objetivo foi contruir um **sistema de validação robusto e extensível** 
ao aplicar

- os prinícpios **SOLID**
- as práticas de ***Clean Code***

Depois de implementar a infraestrutura de validação, implementar e testar as
regras de validação de senha de fato foi bem direto. 

A solução evita o uso intensivo de bibliotecas de terceiros e de
recursos avançados da linguagem Java de propósito, com o objetivo de
demonstrar a habilidade de escrever **código claro e de alta qualidade**,
independente de linguagem ou de _framework_. Apesar disso, alguns 
recursos do Spring foram usados quando apropriado.

### A base do sistema de validação

O sistema é baseado em duas classes abstratas principais:

-   [`Validator.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/rules/ValidationRule.java): agrega múltiplas regras de validação que são implementadas como 
-   [`ValidationRule.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/validators/Validator.java): representa uma única regra de validação

Cada `ValidationRule` registra a si mesmo em uma instância de 
`Validator` através de injestão de dependências durante a construção.

A classe `Validator` expõe um método `validate` que recebe o dado a ser
validado e devolve `true` se todas as regras são validada, ou `false` 
no caso contrário.

### Mecanismo de prioridade

Para lidar com casos em que a entrada pode não ser do tipo esperado, 
um **mecanismo de prioridade** foi introduzido.

Cada regra tem um valor de prioridade de tipo `int` associado:

- Regras com **níveis menores de prioridade são executadas primeiro**
- Regras são automaticamente ordenadas dentro de `Validator`

### Como os princípios SOLID se aplicam?

O sistema desenvolvido é baseado em 

- Classes assumindo uma única responsabilidade (S) dado que as sub-classes precisam fazer apenas uma coisa
- Introdução de novo comportamento através da introdução de novos classes (O): Se for preciso introduzir uma nova regra, ou validar um novo dado, basta criar mais sub-classes em vez de alterar o código existente
- O sistema de validação executa sobre abstrações em vez de implementações (D) porque o trabalho pesado é feito pelas classes abstratas mencionadas

### Implementação da validação da senha

Baseado no sistema descrito acima, a classe [`PasswordValidator.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/validators/PasswordValidator.java) foi construída. Ela apenas extende a classe `Validator` definindo `String` como argumento de tipo.

Do outro lado, as classes a seguir representam cada regra à cerca da validação de senha:

1. [`DigitPasswordValidationRule.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/rules/password/DigitPasswordValidationRule.java) para garantir que a senha tenha dígitos
2. [`InvalidTypePasswordValidationRule.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/rules/password/InvalidTypePasswordValidationRule.java) **com prioridade menor** para garantir que a senha dada seja uma instância de `String`, evitando exceções nas regras aplicadas em sequência
3. [`LowerLetterPasswordValidationRule.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/rules/password/LowerLetterPasswordValidationRule.java) para garantir que a senha tenha pelo menos uma letra minúscula
4. [`ShortPasswordValidationRule.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/rules/password/ShortPasswordValidationRule.java) para rejeitar senhas com menos de 9 caracteres
5. [`SpecialCharPasswordValidationRule.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/rules/password/SpecialCharPasswordValidationRule.java) para garantir que a senha tenha pelo menos um caractere especial dentre os seguintes: `!@#$%^&*()-+`
6. [`UniqueCharsPasswordValidationRule.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/rules/password/UniqueCharsPasswordValidationRule.java) que rejeita senhas com caracteres repetidos
7. [`UpperLetterPasswordValidationRule.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/rules/password/UpperLetterPasswordValidationRule.java) para rejeitar senha sem letras maiúsculas
8. [`ValidCharsPasswordValidationRule.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/rules/password/ValidCharsPasswordValidationRule.java) para rejeitar senhas com caracteres desconhecidos

É claro que o autor poderia utilizar uma solução mais simples, como criar uma classe 
`PasswordValidation` com um método para cada uma das regras mencionadas acima, ou até 
mesmo usar uma biblioteca de terceiros, mas a solução descrita foi elaborada para 
demonstrar experiência em POO e SOLID.

### Exemplo de extensibilidade

Vamos supor que nós agora precisamos validar nomes de usuário também. Deve-se apenas 

1. adicionar uma classe `UsernameValidator` extendendo da classe abstrata `Validator`
2. implementar as regras de validação com sub-classes de `ValidationRule` que recebem `UsernameValidator` como argumento no método construtor

O validador:

```java
class UsernameValidator extends Validator<String> {}
```

Exemplo de regra de validação:

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

Por outro lado, se uma regra não é mais necessária, basta apagar a classe porque as 
regras são _plug-and-play_ por _design_, graças a injeção de dependências.

## Executando a aplicação

O repositório contém arquvios Docker para permitir que qualquer um execute a aplicação e seus testes facilmente

- `docker compose up app` executa a aplicação que fica exposta na porta 8080 em `localhost`
- `docker compose up [tests|unit-tests|integration-tests|e2e-tests]` executam os testes da aplicação
- `docker compose run mvn` permite que o desenvolvedor execute comandos Maven commands sobre o projeto (como `clean install` ou `dependency:resolve`)

## Informação adicional

- Vide a documentação da api rest: Depois de executar a aplicação com `docker compose up app`, visite a [documentação Swagger](http://localhost:8080/swagger-ui/index.html)
- Vide as JavaDocs através do seguintes passos:
  1. Gere a JavaDocs com o comando `docker compose run --rm mvn javadoc:javadoc`
  2. Execute `docker compose up -d javadocs`
  3. Visite [localhost:3000](http://localhost:3000) para ver a documentação

# en-US

## About

This REST API was developed as a requirement for a hiring process at
Itaú Unibanco. It was built using **Java 21**, **Spring Boot 3**, and **Maven**.

The API exposes a single endpoint responsible for validating a given
password. Despite its simplicity, the project was intentionally designed to
demonstrate how a backend application can be **well-structured, tested,
and documented**, while applying solid software engineering principles.

## Architecture overview

Although designed with a microservice mindset, the application
deliberately avoids strict adherence to *Clean Architecture* in some
areas in order to reduce unnecessary boilerplate.
Still, several principles were applied to keep the codebase
**maintainable, testable, and performant**.

The application follows a **layered architecture**, with dependencies
flowing in the following direction:

```
controller → application → core
```

### Controller layer

Responsible for handling incoming HTTP requests and returning
appropriate responses. Controllers delegate business logic execution 
to the application layer.

### Application layer

Contains classes responsible for orchestrating business rules.
For example, the `AuthService` class handles authentication-related
logic and coordinates the validation process.

### Core layer

Contains the core business rules and domain logic, completely decoupled
from framework-specific concerns.

## Solution details

The goal was to build a **robust and extensible validation system** by
applying:

-   **SOLID principles**
-   **Clean Code** practices

Once the validation infrastructure was in place, implementing and
testing the actual password rules became straightforward.

The solution avoids heavy usage of third-party libraries and advanced
Java features on purpose, aiming to demonstrate the ability to write
**clear, high-quality code**, regardless of language or framework.
That said, some Spring features were leveraged where appropriate.

### The validation system basis

The validation system is based on two main abstract classes:

-   [`Validator.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/rules/ValidationRule.java): aggregates multiple validation rules
-   [`ValidationRule.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/validators/Validator.java): represents a single validation constraint

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

Based on the system described above, the [`PasswordValidator.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/validators/PasswordValidator.java) 
class was built. It just extends the `Validator` class setting `String` as 
the type argument for `Validator`.

In the other hand, the following classes represent every single rule 
stated for the password validation:

1. [`DigitPasswordValidationRule.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/rules/password/DigitPasswordValidationRule.java) to ensure that the password has digits
2. [`InvalidTypePasswordValidationRule.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/rules/password/InvalidTypePasswordValidationRule.java) with lower priority, to ensure that the given passwords are instance of `String`, just to avoid exceptions in the following validation rules
3. [`LowerLetterPasswordValidationRule.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/rules/password/LowerLetterPasswordValidationRule.java) to ensure that the password has at least one lowercase letter
4. [`ShortPasswordValidationRule.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/rules/password/ShortPasswordValidationRule.java) to reject passwords with less than 9 characters
5. [`SpecialCharPasswordValidationRule.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/rules/password/SpecialCharPasswordValidationRule.java) to ensure that the password has at least one special character among the following: `!@#$%^&*()-+`
6. [`UniqueCharsPasswordValidationRule.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/rules/password/UniqueCharsPasswordValidationRule.java) that reject passwords with repeated characters
7. [`UpperLetterPasswordValidationRule.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/rules/password/UpperLetterPasswordValidationRule.java) to reject passwords without uppercase letters
8. [`ValidCharsPasswordValidationRule.java`](src/main/java/com/example/icarros_challenge/icarros_challenge/core/validation/rules/password/ValidCharsPasswordValidationRule.java) to reject passwords without known characters

Of course the author could use a simpler solution, like creating a `PasswordValidation` 
class with one method for each one of the constraints mentioned above, or even a 
third-party library, but the solution described was designed to be able to show experience 
in OOP and SOLID.

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

## Additional information

- Refer to the REST API documentation: After running the application with `docker compose up app`, visit the [Swagger documentation](http://localhost:8080/swagger-ui/index.html)
- Refer to JavaDocs by following these steps:
  1. Generate JavaDocs with `docker compose run --rm mvn javadoc:javadoc`
  2. Run `docker compose up -d javadocs`
  3. Visit [localhost:3000](http://localhost:3000) to see the docs