# DIO Bootcamp Decola Tech

Projeto de uma API RESTFul desenvolvida em Java com Spring boot. 
A minha API tem como objetivo fornecer um serviço onde os usuários possam armazenar, gerenciar e compartilhar pequenos trechos de código de diferentes linguagens de programação.

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- OpenAPI (Swagger)
- Railway
- Spring Security
- Maven

## Diagrama de Classes

```mermaid
classDiagram
    class User {
        +Long id
        +String username
        +String email
        +String password
        +LocalDateTime createdAt
        +List~Snippet~ snippets
    }

    class Snippet {
        +Long id
        +String title
        +String code
        +String language
        +LocalDateTime createdAt
        +LocalDateTime updatedAt
        +User user
    }

    User "1" -- "0..*" Snippet : possui
```

## Link para o deploy da aplicação

[Railway app](https://code-snippets.up.railway.app/swagger-ui/index.html)
