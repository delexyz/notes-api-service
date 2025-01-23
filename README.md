# Getting Started

## Overview
The Notes API Service is a Spring Boot application designed to manage user-authenticated notes. The API supports user authentication, CRUD operations for notes, sharing notes, and searching notes based on keywords. It uses PostgreSQL as the database and incorporates JWT for authentication and authorization.


## Frameworks, Database, and Tools
### Framework: Spring Boot
My preference for Spring Boot is due to its ease of use, flexibility, and the vast ecosystem it provides for building REST APIs. Spring Boot simplifies configuration and reduces boilerplate code, making it an excellent choice for building microservices.
#### Key Advantages:
* Provides a comprehensive ecosystem for building REST APIs.
* Includes out-of-the-box support for dependency injection, security, and database interactions.
* Simplifies configuration and reduces boilerplate code.

### Database: PostgreSQL
I used PostgreSQL because it was required for the project. I am flexible with all kinds of databases and my choice depends on requirement for each project.

### 3rd Party Tools
#### SpringDoc OpenAPI:
* Simplifies the creation of API documentation.
* Provides a user-friendly Swagger UI for testing APIs.

#### Lombok (warning: Lombok failed on my Intellij after I did an update. I had to quickly switch to generating constructors, getters and setters with Intellij):
* Reduces boilerplate code by providing annotations for getters, setters, and other utility methods.

#### JSON Web Token (JWT):
* Implements secure authentication and authorization.
* Ensures stateless session management.

## Setup and Configuration
### Prerequisites
* Java 21 installed.
* Maven 3.9.6 installed.
* PostgreSQL 16 or 17 installed and configured.

### Environment Variables:
Set the following environment variables in your system or a .env file or IntelliJ Run Configuration:
```
DB_HOST=your_database_host
DB_PORT=your_database_port
DB_NAME=your_database_name
DB_USERNAME=your_database_username
DB_PASSWORD=your_database_password
JWT_SECRET_KEY=your_jwt_secret_key
```

### Application Properties:
The application.yml file includes configuration for the database, server, and other properties.

### How to Run Locally:
* Step 1: Clone the repository:
```
git clone <repository-url>
cd notes-api-service
```
* Step 2: Build the project:
```
mvn clean install
```
* Step 3: Run the application:
```
mvn clean install
```
* Step 4: Access Swagger UI:
  Navigate to http://localhost:8080/swagger-ui/index.html

