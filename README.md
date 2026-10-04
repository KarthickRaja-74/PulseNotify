# PulseNotify
Event driven notification based application
Java Version 17
Available profiles (local , dev)
Examples of activating profile using : 
	Maven : mvn spring-boot:run -Dspring-boot.run.profiles=local
	Cmd : java -jar pulsenotify.jar --spring.profiles.active=local
	
## Local Development with Docker

### Start infrastructure

```bash
docker compose up -d
```

### Stop infrastructure

```bash
docker compose down
```

### pgAdmin

http://localhost:5050

Email: admin@pulsenotify.com

Password: admin123

# PulseNotify — Authentication

## 1. Overview

PulseNotify uses Spring Security and JWT-based stateless authentication.

The authentication system provides:

- User registration
- BCrypt password hashing through Spring Security `PasswordEncoder`
- Credential verification using `PasswordEncoder.matches()`
- JWT access-token generation
- JWT-based authentication for protected APIs
- Stateless request authentication through Spring Security
- Centralized JWT validation through a security filter

Authentication logic is kept outside controllers.

---

## 2. User Registration

### Flow

```text
Client
  ↓
POST /api/users/register
  ↓
UserController
  ↓
UserService
  ↓
PasswordEncoder
  ↓
UserRepository
  ↓
PostgreSQL
```

### Endpoint

```http
POST /api/users/register
Content-Type: application/json
```

### Request

```json
{
  "email": "user@example.com",
  "password": "user-password"
}
```

The password shown above is only an example. Real credentials must never be committed to source code or documentation.

### Processing

The registration service:

1. Normalizes the email by trimming whitespace and converting it to lowercase.
2. Checks whether the email already exists.
3. Hashes the raw password using Spring Security's `PasswordEncoder`.
4. Stores the user and encoded password through `UserRepository`.
5. Returns the user's ID and email.

Raw passwords are never stored in PostgreSQL.

### Successful Response

The registration response contains the created user's identity information.

The password is never returned.

### Security Considerations

- Passwords are hashed before persistence.
- The raw password is never stored.
- Password hashes are never returned through the registration response.
- Duplicate email registration is rejected.
- The registration endpoint does not require a JWT.

---

## 3. Login

### Flow

```text
Client
  ↓
POST /auth/login
  ↓
AuthController
  ↓
AuthService
  ↓
Find user by email
  ↓
PasswordEncoder.matches()
  ↓
Generate JWT
  ↓
Return access token
```

### Endpoint

```http
POST /auth/login
Content-Type: application/json
```

### Request

```json
{
  "email": "user@example.com",
  "password": "user-password"
}
```

### Processing

The authentication service:

1. Normalizes the supplied email.
2. Finds the corresponding user.
3. Compares the supplied raw password with the stored encoded password using `PasswordEncoder.matches()`.
4. Generates a signed JWT when the credentials are valid.
5. Returns the JWT in the login response.

The stored password hash is never decrypted.

### Successful Response

```json
{
  "message": "Authentication successful",
  "accessToken": "<JWT>"
}
```

The actual JWT must not be committed to source control or included in documentation.

### Invalid Credentials

Invalid email and incorrect password use the same generic authentication error:

```text
Invalid Email or Password
```

This avoids unnecessarily revealing whether a particular email exists.

### Security Considerations

- Password verification uses `PasswordEncoder.matches()`.
- Password hashes are never returned.
- Raw passwords are never logged or stored.
- The JWT signing secret is never returned to clients.
- Login is a public endpoint and does not require an existing JWT.

---

## 4. JWT Authentication

PulseNotify uses a signed JWT access token for stateless authentication after successful login.

### Purpose

The JWT represents the authenticated user's identity on subsequent API requests.

The client sends the token with each protected request.

### JWT Structure

At a high level, a JWT consists of:

```text
Header.Payload.Signature
```

The header and payload contain encoded information, while the signature is used to verify that the token was created with the expected signing key and has not been modified.

### Signing vs Encryption

The JWT is **signed, not encrypted**.

Signing provides integrity and authenticity.

It does not make the payload confidential.

Therefore, sensitive information such as:

- passwords
- password hashes
- signing secrets
- unnecessary confidential data

must not be placed in JWT claims.

### Current Identity Claim

PulseNotify uses the JWT `sub` (subject) claim to store the user's UUID.

The token also contains:

- `sub` — authenticated user ID
- `iat` — issued-at timestamp
- `exp` — expiration timestamp

### Token Expiration

JWT expiration is configured through application configuration.

Expired tokens are rejected during JWT validation.

### Signing Key

The JWT signing key is externalized through configuration.

The actual secret must never be committed to source control or included in documentation.

---

## 5. Protected Requests

Authenticated clients send the JWT using the HTTP `Authorization` header.

### Request Format

```http
Authorization: Bearer <JWT>
```

For example:

```http
GET /api/users/me
Authorization: Bearer <JWT>
```

### Request Flow

```text
Client
  ↓
Authorization: Bearer <JWT>
  ↓
Spring Security Filter Chain
  ↓
JwtAuthenticationFilter
  ↓
Extract Bearer token
  ↓
Validate JWT
  ↓
Extract user identity
  ↓
Create Authentication
  ↓
SecurityContext
  ↓
Protected endpoint
```

The controller does not parse or validate the JWT itself.

---

## 6. Spring Security Architecture

### Security Configuration

`SecurityConfig` configures the application's Spring Security behavior.

Responsibilities include:

- disabling CSRF for the stateless API configuration
- configuring stateless session management
- defining public endpoints
- requiring authentication for other endpoints
- registering `JwtAuthenticationFilter` before Spring Security's `UsernamePasswordAuthenticationFilter`
- configuring the authentication entry point for unauthenticated requests

The application uses:

```text
SessionCreationPolicy.STATELESS
```

Authentication therefore comes from the JWT rather than an HTTP session.

### JwtAuthenticationFilter

`JwtAuthenticationFilter` extends `OncePerRequestFilter`.

Its responsibilities are:

1. Read the `Authorization` header.
2. Check for the `Bearer` authentication scheme.
3. Extract the JWT.
4. Ask `JwtService` to validate and extract the user identity.
5. Create a Spring Security `Authentication`.
6. Store the authentication in the `SecurityContext`.

Invalid JWTs do not create an authenticated `SecurityContext`.

### JwtService

`JwtService` is responsible for JWT-related operations.

It currently:

- generates signed JWTs
- sets the user UUID as the subject
- sets issued-at time
- sets expiration time
- validates signed JWTs
- extracts the user UUID from the subject claim

The service uses the configured signing key.

### Authentication

After successful JWT validation, the filter creates a Spring Security `Authentication` containing the authenticated user's UUID.

Conceptually:

```text
JWT
 ↓
user UUID
 ↓
Authentication
```

### SecurityContext

Spring Security stores the current request's authentication in the `SecurityContext`.

Protected application code can obtain the authenticated identity through Spring Security rather than parsing the JWT directly.

For example, the `/api/users/me` endpoint obtains the current `Authentication` from the `SecurityContext`.

---

## 7. Public vs Protected Endpoints

### Public Endpoints

These endpoints do not require a JWT:

```text
POST /api/users/register
POST /auth/login
```

The following API documentation endpoints are also publicly accessible:

```text
/swagger-ui/**
/v3/api-docs/**
```

### Protected Endpoint

The current protected application endpoint is:

```text
GET /api/users/me
```

It requires a valid JWT.

Other endpoints are protected by the configured:

```text
.anyRequest().authenticated()
```

rule unless explicitly permitted in the security configuration.

### Authentication Failure

Unauthenticated requests to protected APIs return:

```text
401 Unauthorized
```

Examples include:

- missing JWT
- malformed JWT
- invalid JWT signature
- expired JWT

---

## 8. Security Decisions

The following security decisions were made during Sprint 2.

### Password security

Passwords are never stored as raw text.

Spring Security's `PasswordEncoder` is used to hash passwords before persistence.

Password verification uses:

```text
PasswordEncoder.matches()
```

rather than attempting to decrypt the stored password.

### Stateless authentication

JWT is used for stateless authentication.

The server does not depend on an HTTP session to identify an authenticated API client.

### Externalized JWT configuration

JWT signing configuration is provided through application configuration rather than being hard-coded into Java source code.

Real signing secrets must be supplied securely through environment-specific configuration or secret management.

### Token expiration

JWTs contain an expiration timestamp and expired tokens are rejected.

### Minimal JWT claims

The current JWT contains the user's UUID as the subject together with issued-at and expiration timestamps.

Sensitive information is not placed in the token.

### Centralized authentication

JWT processing is centralized in the security/filter layer.

Controllers do not parse JWTs.

### SecurityContext

Once a JWT is successfully validated, the authenticated identity is stored in Spring Security's `SecurityContext`.

Application code can therefore use the authenticated identity without understanding JWT internals.

### Protected APIs

Application endpoints require valid authentication unless explicitly configured as public.

---

## 9. Configuration

Authentication requires JWT configuration in the application's environment-specific configuration.

Example local configuration:

```properties
jwt.secret=<BASE64_ENCODED_SECRET>
jwt.expiration=1h
```

The exact secret value must never be documented or committed.

### Configuration Properties

| Property         | Purpose                                   |
| ---------------- | ----------------------------------------- |
| `jwt.secret`     | Secret key used to sign and validate JWTs |
| `jwt.expiration` | JWT lifetime                              |

The project uses Spring Boot configuration properties through `JwtProperties`.

### Local Development

Use a development-only secret that satisfies the signing algorithm's key requirements.

Do not use example/test secrets in production.

### Production

Production signing secrets must be supplied through secure external configuration or a secret-management mechanism.

Never commit real signing keys to Git.

---

## 10. Authentication Flow Diagrams

### Registration

```text
Client
  ↓
POST /api/users/register
  ↓
UserController
  ↓
UserService
  ↓
PasswordEncoder
  ↓
UserRepository
  ↓
PostgreSQL
```

### Login

```text
Client
  ↓
POST /auth/login
  ↓
AuthController
  ↓
AuthService
  ↓
Find User
  ↓
PasswordEncoder.matches()
  ↓
JwtService
  ↓
Signed JWT
  ↓
Client
```

### Protected Request

```text
Client
  ↓
Authorization: Bearer <JWT>
  ↓
Spring Security Filter Chain
  ↓
JwtAuthenticationFilter
  ↓
JwtService
  ↓
JWT validated
  ↓
User UUID extracted
  ↓
Authentication
  ↓
SecurityContext
  ↓
Protected Controller
```

---

## 11. Developer Usage

### Step 1 — Register

```http
POST /api/users/register
Content-Type: application/json
```

```json
{
  "email": "developer@example.com",
  "password": "your-password"
}
```

The API creates the user and stores the password securely using `PasswordEncoder`.

---

### Step 2 — Login

```http
POST /auth/login
Content-Type: application/json
```

```json
{
  "email": "developer@example.com",
  "password": "your-password"
}
```

A successful login returns:

```json
{
  "message": "Authentication successful",
  "accessToken": "<JWT>"
}
```

---

### Step 3 — Obtain the JWT

Copy the `accessToken` from the login response.

Do not expose or commit the token.

---

### Step 4 — Send the JWT

Call a protected endpoint:

```http
GET /api/users/me
Authorization: Bearer <JWT>
```

---

### Step 5 — Access the Protected API

With a valid JWT, the request is authenticated and `/api/users/me` can access the authenticated user's identity from Spring Security's `SecurityContext`.

Without a valid JWT, the protected endpoint returns:

```text
401 Unauthorized
```

---

## 12. Sprint 2 Authentication Scope

The authentication system currently covers:

```text
Registration
    ↓
Password hashing
    ↓
Login
    ↓
JWT generation
    ↓
JWT validation
    ↓
Spring Security Authentication
    ↓
SecurityContext
    ↓
Protected endpoints
```

The following are intentionally outside the Sprint 2 authentication scope:

- OAuth2
- Refresh tokens
- Password reset
- Email verification
- Roles
- Permissions
- RBAC
- Kafka
- Redis
- Notification delivery
- Rate limiting

