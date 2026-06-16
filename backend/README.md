# MedLink Backend Setup

## Prerequisites
- Java 21
- Maven 3.9+
- PostgreSQL 15

## Build
```bash
mvn clean package -DskipTests
```

## Run
```bash
mvn spring-boot:run
```

## Database Setup
```bash
psql -U postgres -d medlink -f database/schema.sql
```

## API Documentation
Swagger UI: http://localhost:8080/swagger-ui.html
OpenAPI JSON: http://localhost:8080/v3/api-docs

## Environment Variables
See `.env.example` for all configuration options.