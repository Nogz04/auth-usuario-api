# Auth API

Um projeto Spring Boot para autenticação e gerenciamento de usuários usando JWT (JSON Web Tokens).

## Visão Geral

Este projeto é uma API RESTful construída com Spring Boot com padrão de arquitetura MVC que fornece endpoints para registro e login de usuários. A autenticação é baseada em token, utilizando JWT para proteger os endpoints e garantir que apenas usuários autorizados possam acessar recursos específicos. Além disso, a autenticação tem criptografia de senha para segurança do usuário.

## Funcionalidades

* **Registro de Usuário**: Novos usuários podem se cadastrar fornecendo nome, e-mail e senha.
* **Autenticação de Usuário**: Usuários registrados podem fazer login para obter um token de acesso JWT.
* **Autorização baseada em Token**: O acesso aos endpoints protegidos é verificado usando um token JWT enviado no cabeçalho `Authorization`.
* **Gerenciamento de Usuários**: A API permite listar, buscar por ID, atualizar e remover usuários.
* **Validação de Dados**: Os dados de entrada são validados para garantir a integridade das informações.
* **Segurança de Senha**: As senhas dos usuários são codificadas usando `BCryptPasswordEncoder` antes de serem armazenadas no banco de dados.

## Estrutura do Projeto

O projeto segue a estrutura padrão do Maven para aplicações Spring Boot:

```text
auth/
├── .mvn/
├── src/
│   ├── main/
│   │   ├── java/br/auth/auth/
│   │   │   ├── controller/      # Controladores REST
│   │   │   │   ├── AuthController.java
│   │   │   │   └── UsuarioController.java
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   │   ├── LoginUsuarioRequestDTO.java
│   │   │   │   ├── LoginUsuarioResponseDTO.java
│   │   │   │   ├── UsuarioRequestDTO.java
│   │   │   │   └── UsuarioResponseDTO.java
│   │   │   ├── model/           # Entidades JPA
│   │   │   │   ├── Role.java
│   │   │   │   └── Usuario.java
│   │   │   ├── repository/      # Repositórios Spring Data JPA
│   │   │   │   └── UsuarioRepository.java
│   │   │   ├── security/        # Configuração de Segurança
│   │   │   │   ├── FiltroDeAutenticacao.java
│   │   │   │   └── SecurityConfig.java
│   │   │   └── service/         # Lógica de Negócio
│   │   │       ├── AuthService.java
│   │   │       ├── DetalhesDoUsuarioService.java
│   │   │       ├── JwtService.java
│   │   │       └── UsuarioService.java
│   │   └── resources/
│   │       └── application.properties  # Configurações da Aplicação
│   └── test/
├── .gitignore
├── pom.xml                     # Dependências e Build do Projeto
└── README.md

```
### Principais Componentes

* **`AuthController.java`**: Expõe os endpoints `/auth/cadastrar` e `/auth/login` para registro e autenticação.
* **`UsuarioController.java`**: Fornece as operações de CRUD para os usuários nos endpoints `/usuarios`.
* **`Usuario.java`**: Modelo de entidade que representa um usuário no banco de dados.
* **`AuthService.java`**: Contém a lógica de negócio para o cadastro e autenticação de usuários.
* **`UsuarioService.java`**: Implementa a lógica para as operações de CRUD de usuários.
* **`JwtService.java`**: Responsável por gerar e validar os tokens JWT.
* **`SecurityConfig.java`**: Configura as regras de segurança do Spring Security, define os endpoints públicos e protegidos e configura o `PasswordEncoder`.
* **`FiltroDeAutenticacao.java`**: Intercepta as requisições para validar o token JWT e autenticar o usuário no contexto de segurança do Spring.
* **`pom.xml`**: Arquivo de configuração do Maven que define as dependências do projeto, como Spring Boot Starter Web, Security, Data JPA, JJWT e MySQL Connector.
* **`application.properties`**: Arquivo de configuração que contém as propriedades do banco de dados (MySQL), a porta do servidor e a chave secreta para a geração do JWT.

## Tecnologias Utilizadas

* **Java 17**: Versão da linguagem Java utilizada no projeto.
* **Spring Boot**: Framework principal para a criação da aplicação.
* **Spring Security**: Para a implementação da autenticação, autorização e criptografia de senhas.
* **Spring Data JPA**: Para a persistência de dados e interação com o banco de dados.
* **Maven**: Para o gerenciamento de dependências e build do projeto.
* **MySQL**: Banco de dados relacional utilizado para armazenar os dados dos usuários.
* **JSON Web Tokens (JWT)**: Para a criação de tokens de autenticação.
* **Lombok**: Para reduzir a verbosidade do código das entidades e DTOs.

## Endpoints da API

A seguir estão os principais endpoints disponíveis na API:

### Autenticação

* `POST /auth/cadastrar`: Registra um novo usuário.
    * **Corpo da Requisição**:
        ```json
        {
          "nome": "Seu Nome",
          "email": "seuemail@exemplo.com",
          "senha": "sua_senha"
        }
        ```
* `POST /auth/login`: Autentica um usuário e retorna um token JWT.
    * **Corpo da Requisição**:
        ```json
        {
          "email": "seuemail@exemplo.com",
          "senha": "sua_senha"
        }
        ```

### Usuários (Protegido por JWT)

* `GET /usuarios`: Lista todos os usuários cadastrados.
* `GET /usuarios/{id}`: Busca um usuário específico pelo seu ID.
* `PUT /usuarios/{id}`: Atualiza os dados de um usuário.
* `DELETE /usuarios/{id}`: Remove um usuário do sistema.

## Configuração

Antes de executar o projeto, certifique-se de configurar as seguintes propriedades no arquivo `src/main/resources/application.properties`:

* **Banco de Dados**:
    * `spring.datasource.url`: A URL de conexão com o seu banco de dados MySQL.
    * `spring.datasource.username`: O nome de usuário do banco de dados.
    * `spring.datasource.password`: A senha do banco de dados.
* **JWT Secret**:
    * `jwt.secret`: Uma chave secreta em Base64 para assinar os tokens JWT. É fundamental que esta chave seja segura e mantida em segredo.

## Como Executar

1.  **Clone o repositório:**
    ```bash
    git clone <url-do-repositorio>
    ```
2.  **Configure o `application.properties`** com as suas credenciais do banco de dados e uma chave secreta JWT.
3.  **Execute a aplicação** usando o Maven:
  

A aplicação estará disponível por padrão em `http://localhost:8080`.
