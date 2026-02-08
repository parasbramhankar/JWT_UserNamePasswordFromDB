```markdown
# 🔐 Spring Boot JWT Authentication & Role-Based Authorization

A complete implementation of stateless authentication using **Spring Security**, **JWT**, and **JPA**.

The system supports:

✅ User registration  
✅ Login with username & password  
✅ JWT token generation  
✅ Role-based API access (USER / ADMIN)  
✅ Database-backed identity validation  

---

---

# 🧠 Concept Overview

Authentication and authorization are handled separately.

- **JWT** → verifies *who the user is*  
- **Spring Security** → verifies *what the user is allowed to access*

Every request must pass through the security filter before reaching controllers.

---

---

# 🏗 Request Flow

```

Client
↓
Authorization Header (Bearer Token)
↓
JwtAuthFilter
↓
Username extracted from token
↓
User loaded from DB
↓
Authentication object created
↓
SecurityContext updated
↓
Authorization rules applied
↓
Controller → Service → Repository → DB

```

---

---

# 📦 Package Structure

```

com.example.JWT_UserNamePasswordFromDB
│
├── config          → Spring Security configuration
├── controller      → REST endpoints
├── dto             → request / response payloads
├── entity          → database entity
├── repo            → JPA repository
├── filter          → JWT request filter
└── service         → business logic + token utilities

```

---

---

# ⚙ Features

- BCrypt password hashing  
- Stateless session management  
- Custom `UserDetailsService`  
- JWT signature validation  
- Role prefix handling (`ROLE_`)  
- Filter-based authentication  

---

---

# 🚀 API Endpoints

---

## 🟢 Register User

```

POST /auth/signup

````

### Request Body
```json
{
  "name": "Paras",
  "username": "paras",
  "dob": "2000-01-01",
  "password": "123",
  "role": "USER"
}
````

### Response

```
user registered
```

---

---

## 🔵 Login

```
POST /auth/login
```

### Request Body

```json
{
  "username": "paras",
  "password": "123"
}
```

### Response

```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9..."
}
```

---

---

## 🟡 User Access API

```
GET /user/balance
```

### Header

```
Authorization: Bearer <token>
```

### Access

USER or ADMIN

---

---

## 🔴 Admin Access API

```
GET /admin/allUsers
```

### Header

```
Authorization: Bearer <token>
```

### Access

ADMIN only

---

---

# 🧪 How To Test (Postman)

1. Register a new user
2. Login
3. Copy token
4. Add token in Authorization header
5. Access secured endpoints

### Negative tests (must fail)

* no token
* wrong token
* USER trying admin API

---

---

# 🔑 Security Rules

| Path        | Access      |
| ----------- | ----------- |
| `/auth/**`  | Public      |
| `/user/**`  | USER, ADMIN |
| `/admin/**` | ADMIN       |

---

---

# ❗ Common Error Guide

| Error              | Meaning            |
| ------------------ | ------------------ |
| 401                | Not authenticated  |
| 403                | Not authorized     |
| SignatureException | Token invalid      |
| UsernameNotFound   | User missing in DB |

---

---

# 🎯 What This Project Demonstrates

✔ Real JWT authentication flow
✔ Spring Security filter lifecycle
✔ Stateless API protection
✔ Role-based authorization
✔ Integration of Security + JPA

---

---

# 🛠 Tech Stack

* Java
* Spring Boot
* Spring Security
* Hibernate / JPA
* JWT (jjwt)
* MySQL / H2

---

---

# 👨‍💻 Author

**Paras**

Learning backend security by building real authentication systems.

```
