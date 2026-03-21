# EventHub API

## Overview

EventHub API is a Spring Boot RESTful application for managing events, users, and registrations.
It supports event browsing with filtering, user management, and tracking event registrations including revenue and attendance.

---

## Technologies Used

* Java 17
* Spring Boot
* Spring Data JPA (Hibernate)
* MySQL
* Flyway (database migrations)
* Swagger / OpenAPI (API documentation)
* Postman (API testing)

---

## Features

### User Management

* Create users
* Retrieve all users
* Retrieve user by ID

### Event Management

* Create events
* Retrieve events with:

    * Pagination
    * Sorting
    * Category filtering
    * Price range filtering
    * Keyword search
    * Active status filtering

### Registration Management

* Create registrations with multiple items
* Retrieve all registrations
* Filter registrations by:

    * User
    * Date range

### Analytics

* Get total revenue for an event
* Get registration count for an event

---

## API Endpoints

### Users

* `GET /api/users`
* `GET /api/users/{id}`
* `POST /api/users`

### Events

* `GET /api/events`
* `GET /api/events/{id}`
* `POST /api/events`
* `GET /api/events/{id}/revenue`
* `GET /api/events/{id}/registration-count`

### Registrations

* `GET /api/registrations`
* `GET /api/registrations/{id}`
* `POST /api/registrations`

### Categories

* `GET /api/categories`
* `POST /api/categories`

---

## Example Requests

### Create User

```json
{
  "name": "Test User",
  "email": "testuser@example.com"
}
```

### Create Event

```json
{
  "title": "Sample Event",
  "description": "Example event",
  "ticketPrice": 25.00,
  "eventDate": "2026-04-25",
  "active": true,
  "categoryId": 1
}
```

### Create Registration

```json
{
  "userId": 1,
  "status": "CONFIRMED",
  "items": [
    {
      "eventId": 1,
      "quantity": 2
    }
  ]
}
```

---

## Setup Instructions

### 1. Clone the repository

```bash
git clone <your-repo-url>
cd eventhub-api
```

### 2. Configure database

Update `application.properties`:

```
spring.datasource.url=jdbc:mysql://localhost:3306/eventhubdb
spring.datasource.username=root
spring.datasource.password=yourpassword
```

### 3. Run the application

```bash
./mvnw spring-boot:run
```

---

## API Documentation

Swagger UI is available at:

```
http://localhost:8080/swagger-ui.html
```

or

```
http://localhost:8080/swagger-ui/index.html
```

---

## Testing

* Swagger was used for interactive API testing
* Postman collection is included in the submission for full endpoint coverage

---

## Notes

* Database schema is managed using Flyway migrations
* Relationships:

    * Users ↔ Registrations
    * Registrations ↔ RegistrationItems ↔ Events
    * Events ↔ Categories
* Revenue is calculated dynamically based on registration items

---

## Author

Cantor
