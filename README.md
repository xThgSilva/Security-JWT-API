# Expenses Management API

## Descrição

Essa API foi desenvolvida com Spring Boot, com o objetivo de gerenciar despesas de um usuário, utilizando das operações CRUD. A API pode **criar**, **ler/listar**, **deletar** e **atualizar** despesas e pode **criar** e **autenticar** usuários. Para a segurança, a API utiliza autenticação baseada em **JWT (JSON Web Token)** e **Spring Security**.

## Tecnologias Utilizadas

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- JWT (JSON Web Token)
- BCrypt (criptografia de senhas)
- Maven

## Segurança e Autenticação

### Tecnologias de Segurança

- **Spring Security** - Framework de segurança e autenticação
- **JWT (JSON Web Token)** - Tokens stateless para autenticação
- **BCrypt** - Algoritmo de hash para criptografia de senhas
- **JJWT** - Biblioteca Java para geração e validação de tokens

### Como Enviar o Token

Após fazer login, o token JWT é armazenado automaticamente em um cookie HTTP. Para requisições via ferramentas como Postman ou Insomnia, você pode enviar o token de duas formas:

**Opção 1: Cookie (automático no navegador)**
```
Cookie: jwt=token_aqui
```

**Opção 2: Header Authorization**
```
Authorization: Bearer token_aqui
```

## Endpoints

## Autenticação (`/auth`)

**⚠️ Rotas Públicas** - Não requerem autenticação

### 1. POST `/auth/register`
Registra um novo usuário no sistema.

**Requisição**
- **URL:** `/auth/register`
- **Método:** POST
- **Cabeçalho:**
  - Content-Type: application/json
- **Corpo:**
```json
{
    "name": "João Silva",
    "email": "joao@email.com",
    "password": "senha123"
}
```

**Resposta**
- **Código de Status:** 200 OK
- **Corpo:**
```json
{
    "id": 1,
    "name": "João Silva",
    "email": "joao@email.com",
    "password": "$2a$10$hashedPassword..."
}
```

**Erro - Email já Cadastrado**
- **Código de Status:** 409 Conflict
- **Corpo:**
```json
{
    "timestamp": "2025-01-04T10:30:00",
    "status": 409,
    "error": "Conflict.",
    "message": "Email already registered."
}
```

---

### 2. POST `/auth/login`
Autentica um usuário e retorna um token JWT.

**Requisição**
- **URL:** `/auth/login`
- **Método:** POST
- **Cabeçalho:**
  - Content-Type: application/json
- **Corpo:**
```json
{
    "email": "joao@email.com",
    "password": "senha123"
}
```

**Resposta**
- **Código de Status:** 200 OK
- **Cookie:** `jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...` (HttpOnly, válido por 1 hora)
- **Corpo:**
```
Login realizado com sucesso!
```

**Erro - Credenciais Inválidas**
- **Código de Status:** 401 Unauthorized
- **Corpo:**
```json
{
    "timestamp": "2025-01-04T10:30:00",
    "status": 401,
    "error": "Unauthorized.",
    "message": "Invalid Credentials."
}
```

---

## Despesas (`/expense`)

### **Todas as rotas abaixo requerem autenticação**

### 1. GET `/expense/list`
Lista todas as despesas do usuário autenticado.

**Requisição**
- **URL:** `/expense/list`
- **Método:** GET
- **Autenticação:** Cookie JWT ou Header Authorization

**Resposta**
- **Código de Status:** 200 OK
- **Corpo:**
```json
[
    {
        "id": 1,
        "title": "Supermercado",
        "description": "Compras do mês",
        "amount": 450.50,
        "user": {
            "id": 1,
            "name": "João Silva",
            "email": "joao@email.com",
            "password": "$2a$10$..."
        }
    },
    {
        "id": 2,
        "title": "Academia",
        "description": "Mensalidade",
        "amount": 120.00,
        "user": {
            "id": 1,
            "name": "João Silva",
            "email": "joao@email.com",
            "password": "$2a$10$..."
        }
    }
]
```

---

### 2. POST `/expense/register`
Cria uma nova despesa para o usuário autenticado.

**Requisição**
- **URL:** `/expense/register`
- **Método:** POST
- **Autenticação:** Cookie JWT ou Header Authorization
- **Cabeçalho:**
  - Content-Type: application/json
- **Corpo:**
```json
{
    "title": "Aluguel",
    "description": "Aluguel mensal do apartamento",
    "amount": 1500.00
}
```

**Resposta**
- **Código de Status:** 200 OK
- **Corpo:**
```json
{
    "id": 3,
    "title": "Aluguel",
    "description": "Aluguel mensal do apartamento",
    "amount": 1500.00,
    "user": {
        "id": 1,
        "name": "João Silva",
        "email": "joao@email.com",
        "password": "$2a$10$..."
    }
}
```

---

### 3. PUT `/expense/update/{id}`
Atualiza uma despesa existente do usuário autenticado.

**Requisição**
- **URL:** `/expense/update/{id}`
- **Método:** PUT
- **Parâmetro de URL:**
  - `id` - ID da despesa
- **Autenticação:** Cookie JWT ou Header Authorization
- **Cabeçalho:**
  - Content-Type: application/json
- **Corpo:**
```json
{
    "title": "Aluguel Atualizado",
    "description": "Novo valor do aluguel",
    "amount": 1600.00
}
```

**Resposta**
- **Código de Status:** 200 OK
- **Corpo:**
```json
{
    "id": 3,
    "title": "Aluguel Atualizado",
    "description": "Novo valor do aluguel",
    "amount": 1600.00,
    "user": {
        "id": 1,
        "name": "João Silva",
        "email": "joao@email.com",
        "password": "$2a$10$..."
    }
}
```

**Erro - Despesa Não Encontrada**
- **Código de Status:** 404 Not Found
- **Corpo:**
```json
{
    "timestamp": "2025-01-04T10:30:00",
    "status": 404,
    "error": "Not Found.",
    "message": "Expense not found."
}
```

**Erro - Sem Permissão**
- **Código de Status:** 403 Forbidden
- **Corpo:**
```json
{
    "timestamp": "2025-01-04T10:30:00",
    "status": 403,
    "error": "Forbidden.",
    "message": "User not authorized to change this expense."
}
```

---

### 4. DELETE `/expense/delete/{id}`
Deleta uma despesa do usuário autenticado.

**Requisição**
- **URL:** `/expense/delete/{id}`
- **Método:** DELETE
- **Parâmetro de URL:**
  - `id` - ID da despesa
- **Autenticação:** Cookie JWT ou Header Authorization

**Resposta**
- **Código de Status:** 200 OK (sem corpo)

**Erro - Despesa Não Encontrada**
- **Código de Status:** 404 Not Found
- **Corpo:**
```json
{
    "timestamp": "2025-01-04T10:30:00",
    "status": 404,
    "error": "Not Found.",
    "message": "Expense with Id 1 not found"
}
```

---

## Tratamento de Erros

A API utiliza um sistema de tratamento global de exceções que retorna respostas padronizadas:

### Estrutura de Erro
```json
{
    "timestamp": "2025-01-04T10:30:00",
    "status": 404,
    "error": "Not Found.",
    "message": "Mensagem descritiva do erro"
}
```
## Observações Importantes

1. **Autenticação Obrigatória:** Todas as rotas de despesas requerem um token JWT válido
2. **Isolamento de Dados:** Cada usuário só tem acesso às suas próprias despesas
3. **Validação de Permissões:** Ao tentar atualizar uma despesa, o sistema verifica se ela pertence ao usuário autenticado
4. **Token JWT:** Válido por 1 hora após o login, depois é necessário fazer login novamente
5. **Cookie Automático:** No navegador, o cookie JWT é enviado automaticamente em todas as requisições

## Autor
- [@Thiago](https://www.github.com/xThgSilva)
