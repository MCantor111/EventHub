# EventHub API

Spring Boot REST API created for CPRG220.

## Project Description
EventHub allows users to create event categories and events. Events belong to categories and are stored in a MySQL database.

## Setup Instructions
1. Clone the repository.
2. Ensure MySQL is running.
3. Create database `eventhubdb`.
4. Update `application.properties` if needed.
5. Run the application:

mvn spring-boot:run

6. Open Swagger UI:
   http://localhost:8080/swagger-ui/index.html

## API Endpoints

POST /api/categories  
Creates a category.

POST /api/events  
Creates an event linked to a category.

GET /api/events  
Returns events (supports pagination).

GET /api/categories  
Returns categories.