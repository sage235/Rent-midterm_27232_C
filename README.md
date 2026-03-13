Rent House Management System
A Spring Boot-based web application for managing rental properties, users, locations, and amenities. This project was developed as a midterm assignment for a Java backend development course.

 Overview
The Rent House Management System allows property owners to list houses, manage amenities, and organize locations hierarchically (province → district → sector → cell → village). Users can register, create profiles, and link to houses they own. The system is built with a clean architecture separating controllers, services, and repositories.

 Technologies Used
Java 17

Spring Boot 4.0.3 (Web MVC, Data JPA)

PostgreSQL (production database)

Hibernate ORM 7.2.4

Maven (build tool)

JUnit 5 (testing)

 Project Structure
text
src/main/java/com/rent/
├── RentApplication.java          # Main entry point
├── controller/                    # REST controllers
├── service/                       # Business logic layer
├── repository/                    # Spring Data JPA repositories
├── modal/                          # JPA entities
│   ├── User.java
│   ├── House.java
│   ├── Location.java
│   ├── Amenity.java
│   ├── Profile.java
│   └── ...
└── resources/
    └── application.properties     # Database and JPA configuration
 Features
User registration and role-based access (Role enum: OWNER, TENANT, ADMIN)

House management with price, description, and location

Hierarchical location system (province → district → sector → cell → village)

Amenities (e.g., WiFi, parking) can be attached to houses

Profile pictures and contact info for users

RESTful API endpoints for all entities

 Setup Instructions
Prerequisites
Java 17 JDK

PostgreSQL (or H2 for quick testing)

Maven (or use the included mvnw wrapper)

Database Configuration
Create a PostgreSQL database named rent-db (or change the name in application.properties).

Update src/main/resources/application.properties with your PostgreSQL credentials:

properties
spring.datasource.url=jdbc:postgresql://localhost:5432/rent-db
spring.datasource.username=your_username
spring.datasource.password=your_password
Running the Application
bash
# Clone the repository (if not already done)
git clone https://github.com/sage235/Rent-midterm_27232_C.git
cd Rent-midterm_27232_C

# Build with Maven
./mvnw clean install

# Run the Spring Boot application
./mvnw spring-boot:run
The application will start on http://localhost:8080.

 Testing
Run unit tests with:

bash
./mvnw test
 Notes
The database schema is automatically created/updated when the app starts (spring.jpa.hibernate.ddl-auto=update).

If you encounter the error No property 'provinceCode' found for type 'User', ensure the UserRepository query method is correctly implemented (see issue #1).

 License
This project is for educational purposes only. All rights reserved.
