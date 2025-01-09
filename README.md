# Blog Application - Spring Boot Project

## Overview
This is a Spring Boot web application demonstrating secure authentication, user management, and article creation using JWT (JSON Web Token) authentication.

## Technologies Used
- Spring Boot
- Spring Security
- MySQL Database
- JWT Authentication
- Maven

## Prerequisites
- Java 11+
- Maven
- MySQL Server

## Database Setup
1. Create MySQL database:
```sql
CREATE DATABASE blog_tp2;
CREATE USER 'blog_user'@'localhost' IDENTIFIED BY 'secure_password';
GRANT ALL PRIVILEGES ON blog_tp2.* TO 'blog_user'@'localhost';
FLUSH PRIVILEGES;
```

## Configuration
Configuration is managed in `src/main/resources/application.properties`:
- Database connection details
- JWT secret and expiration time

## Authentication Endpoints

### Login
- **URL:** `/auth/login`
- **Method:** POST
- **Request Body:**
```json
{
    "nomUtilisateur": "username",
    "motDePasse": "password"
}
```
- **Response:** JWT Token

### Signup
- **URL:** `/auth/signup`
- **Method:** POST
- **Request Body:**
```json
{
    "nomUtilisateur": "newuser",
    "motDePasse": "password",
    "role": "ROLE_USER"
}
```

## Running the Application
```bash
# Clean and install dependencies
mvn clean install

# Run the application
mvn spring-boot:run
```

## Security Features
- JWT-based authentication
- Stateless session management
- Role-based access control
- Password encoder (currently using NoOpPasswordEncoder for simplicity)

## Project Structure
- `src/main/java/com/TP/Spring/`
  - `controller/`: REST API controllers
  - `model/`: Data models
  - `repository/`: Database repositories
  - `security/`: Authentication and security components
- `src/main/resources/`: Configuration files

## Testing
Run unit tests:
```bash
mvn test
```

## Security Considerations
⚠️ **Note:** This project uses `NoOpPasswordEncoder` for demonstration. 
In a production environment, use a secure password encoder like `BCryptPasswordEncoder`.

## Troubleshooting
- Ensure MySQL is running
- Check database credentials in `application.properties`
- Verify Maven dependencies are correctly downloaded

## License
This project is for educational purposes.
