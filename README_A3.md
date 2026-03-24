# EventHub - Assignment 3 (Security and Authentication)

## Overview
This assignment extends Assignment 2 by adding authentication, authorization, role-based access control, password reset, security best practices, and security tests.

## Implemented Features

### 1. JWT Authentication
- User registration endpoint
- User login endpoint
- JWT token generation
- Bearer-token authentication for protected endpoints

### 2. Role-Based Access Control (RBAC)
Roles implemented:
- `ROLE_USER`
- `ROLE_ADMIN`

### 3. Endpoint Authorization Rules
Public endpoints:
- `POST /api/auth/register`
- `POST /api/auth/login`
- `POST /api/auth/password-reset-request`
- `POST /api/auth/password-reset-confirm`
- `GET /api/events`
- `GET /api/events/{id}`

User/Admin endpoints:
- `POST /api/registrations`
- `GET /api/registrations`
- `GET /api/registrations/{id}`

Admin-only endpoints:
- `POST /api/events`
- `PUT /api/events/{id}`
- `DELETE /api/events/{id}`
- `GET /api/events/{id}/registration-count`
- `GET /api/events/{id}/revenue`
- `GET /api/users`
- `GET /api/users/{id}`
- `POST /api/users`

### 4. Password Reset Flow
- Generate password reset token
- Validate reset token
- Reset password

### 5. Security Best Practices
- Stateless JWT-based authentication
- Password hashing with BCrypt
- CORS configuration
- Security headers
- Input validation using Jakarta Validation

## Database Changes
Added:
- `roles` table
- `user_roles` join table
- `password_hash` column on users
- `enabled` column on users
- `reset_token` column on users
- `reset_token_expiry` column on users

## Seeded Users
Default seeded accounts:
- `cantor@example.com` / `password`
- `mason@example.com` / `password`
- `sam@example.com` / `password`

Admin account:
- `sam@example.com`

## Testing
Added test classes for:
- authentication
- password reset
- security and role-based access rules

## Example Login Request
```json
{
  "email": "cantor@example.com",
  "password": "password"
}