# EventHub API

## Project Description
EventHub API is a Spring Boot RESTful web service for managing events and event categories. The API allows users to create and retrieve events, organize them by category, and filter them by attributes such as category, ticket price, and event date.

This project demonstrates backend development concepts including REST API design, DTO validation, pagination, filtering, and global exception handling. It was developed as Assignment 1 for CPRG-220 Open Source Web Applications.

## Technologies Used
- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- H2 Database
- Maven
- Swagger / OpenAPI
- Postman

## Setup Instructions

### 1. Clone the repository
git clone <your-repository-url>

### 2. Open the project
Open the project in IntelliJ IDEA (or another Java IDE).

### 3. Build the project
Run Maven to install dependencies:

mvn clean install

### 4. Run the application
Start the Spring Boot application by running:

EventHubApplication.java

The API will start at:

http://localhost:8080

## Swagger API Documentation
Swagger provides interactive documentation for all API endpoints.

Access Swagger UI at:

http://localhost:8080/swagger-ui.html

## API Endpoints

### Categories

Create Category  
POST /api/categories

Get All Categories  
GET /api/categories

### Events

Create Event  
POST /api/events

Get Event By ID  
GET /api/events/{id}

Get Paginated Events  
GET /api/v1/events

### Filtering Endpoints

Get Events By Category  
GET /api/events/category/{categoryId}

Get Events By Price Range  
GET /api/events/price?minPrice={value}&maxPrice={value}

Get Events By Date Range  
GET /api/events/date?startDate={date}&endDate={date}

## Validation and Error Handling
The API includes request validation and global exception handling.

Examples include:
- Invalid event creation requests returning **400 Bad Request**
- Requests for non-existent events returning **404 Not Found**

Error responses include:
- error messages
- HTTP status
- timestamp

## Postman Collection
A Postman collection containing all API requests is included in the assignment submission. The collection can be imported into Postman to test the endpoints.

## Screenshots
Screenshots of Postman requests/responses and Swagger documentation are included in the assignment submission document.

## Author
Cantor  
CPRG-220 Open Source Web Applications