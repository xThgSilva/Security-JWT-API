# Expenses Management API

## Description

This API was developed with Spring Boot, with the goal of managing a user's expenses using CRUD operations. The API can **create**, **read/list**, **delete**, and **update** expenses, and can **create** and **authenticate** users. For security, the API uses authentication based on **JWT (JSON Web Token)** and **Spring Security**.

## Technologies Used

* Java
* Spring Boot
* Spring Security
* Spring Data JPA
* MySQL
* JWT (JSON Web Token)
* BCrypt (password encryption)
* Maven

## Security and Authentication

### Security Technologies

* **Spring Security** - Security and Authentication Framework
* **JWT (JSON Web Token)** - Stateless tokens for authentication
* **BCrypt** - Hash algorithm for password encryption
* **JJWT** - Java library for generating and validating tokens

### How to send the token

After logging in, the JWT token is automatically stored in an HTTP cookie. For requests via tools such as Postman or Insomnia, you can send the token in two ways:

**Option 1: Cookie (automatic in the browser)**

```
Cookie: jwt=your_token_here
```

**Option 2: Authorization Header**

```
Authorization: Bearer your_token_here
```

## Endpoints

## Authentication (`/auth`)

**⚠️ Public Routes** - Do not require authentication

### 1. POST `/auth/register`

Registers a new user in the system.

**Request**

* **URL:** `/auth/register`
* **Method:** POST
* **Header:**

  * Content-Type: application/json
* **Body:**

```json
{
    "name": "João Silva",
    "email": "joao@email.com",
    "password": "password123"
}
```

**Response**

* **Status Code:** 200 OK
* **Body:**

```json
{
    "id": 1,
    "name": "João Silva",
    "email": "joao@email.com",
    "password": "$2a$10$hashedPassword..."
}
```

**Error - Email Already Registered**

* **Status Code:** 409 Conflict
* **Body:**

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

Authenticates a user and returns a JWT token.

**Request**

* **URL:** `/auth/login`
* **Method:** POST
* **Header:**

  * Content-Type: application/json
* **Body:**

```json
{
    "email": "joao@email.com",
    "password": "password123"
}
```

**Response**

* **Status Code:** 200 OK
* **Cookie:** `jwt=eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...` (HttpOnly, valid for 1 hour)
* **Body:**

```
Login successful!
```

**Error - Invalid Credentials**

* **Status Code:** 401 Unauthorized
* **Body:**

```json
{
    "timestamp": "2025-01-04T10:30:00",
    "status": 401,
    "error": "Unauthorized.",
    "message": "Invalid credentials."
}
```

---

## Expenses (`/expense`)

### **All routes below require authentication**

### 1. GET `/expense/list`

Lists all expenses of the authenticated user.

**Request**

* **URL:** `/expense/list`
* **Method:** GET
* **Authentication:** JWT Cookie or Authorization Header

**Response**

* **Status Code:** 200 OK
* **Body:**

```json
[
    {
        "id": 1,
        "title": "Supermarket",
        "description": "Monthly groceries",
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
        "title": "Gym",
        "description": "Monthly fee",
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

Creates a new expense for the authenticated user.

**Request**

* **URL:** `/expense/register`
* **Method:** POST
* **Authentication:** JWT Cookie or Authorization Header
* **Header:**

  * Content-Type: application/json
* **Body:**

```json
{
    "title": "Rent",
    "description": "Monthly apartment rent",
    "amount": 1500.00
}
```

**Response**

* **Status Code:** 200 OK
* **Body:**

```json
{
    "id": 3,
    "title": "Rent",
    "description": "Monthly apartment rent",
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

Updates an existing expense of the authenticated user.

**Request**

* **URL:** `/expense/update/{id}`
* **Method:** PUT
* **URL Parameter:**

  * `id` - Expense ID
* **Authentication:** JWT Cookie or Authorization Header
* **Header:**

  * Content-Type: application/json
* **Body:**

```json
{
    "title": "Updated Rent",
    "description": "New rent value",
    "amount": 1600.00
}
```

**Response**

* **Status Code:** 200 OK
* **Body:**

```json
{
    "id": 3,
    "title": "Updated Rent",
    "description": "New rent value",
    "amount": 1600.00,
    "user": {
        "id": 1,
        "name": "João Silva",
        "email": "joao@email.com",
        "password": "$2a$10$..."
    }
}
```

**Error - Expense Not Found**

* **Status Code:** 404 Not Found
* **Body:**

```json
{
    "timestamp": "2025-01-04T10:30:00",
    "status": 404,
    "error": "Not Found.",
    "message": "Expense not found."
}
```

**Error - No Permission**

* **Status Code:** 403 Forbidden
* **Body:**

```json
{
    "timestamp": "2025-01-04T10:30:00",
    "status": 403,
    "error": "Forbidden.",
    "message": "User not authorized to modify this expense."
}
```

---

### 4. DELETE `/expense/delete/{id}`

Deletes an expense of the authenticated user.

**Request**

* **URL:** `/expense/delete/{id}`
* **Method:** DELETE
* **URL Parameter:**

  * `id` - Expense ID
* **Authentication:** JWT Cookie or Authorization Header

**Response**

* **Status Code:** 200 OK (no body)

**Error - Expense Not Found**

* **Status Code:** 404 Not Found
* **Body:**

```json
{
    "timestamp": "2025-01-04T10:30:00",
    "status": 404,
    "error": "Not Found.",
    "message": "Expense with Id 1 not found"
}
```

---

## Error Handling

The API uses a global exception handling system that returns standardized responses:

### Error Structure

```json
{
    "timestamp": "2025-01-04T10:30:00",
    "status": 404,
    "error": "Not Found.",
    "message": "Descriptive error message"
}
```

## Important Notes

1. **Required Authentication:** All expense routes require a valid JWT token
2. **Data Isolation:** Each user only has access to their own expenses
3. **Permission Validation:** When attempting to update an expense, the system verifies if it belongs to the authenticated user
4. **JWT Token:** Valid for 1 hour after login; after that, it is necessary to log in again
5. **Automatic Cookie:** In the browser, the JWT cookie is automatically sent with all requests

## Author

* [@Thiago](https://www.github.com/xThgSilva)
