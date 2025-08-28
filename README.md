# Movie Rating Microservice System

A comprehensive microservice-based movie rating system built with Spring Boot, Spring Cloud Gateway, Kafka, and JWT authentication. This system allows users to register, authenticate, rate movies, and provides analytics on movie ratings.

## Architecture Overview

The system follows a microservice architecture pattern with the following services:

### 1. Config Server
- **Purpose**: Centralized configuration management
- **Features**:
  - Externalized configuration for all microservices
  - Environment-specific configurations
  - Runtime configuration updates
  - Spring Cloud Config integration
- **Implementation**: Enabled with `@EnableConfigServer` annotation

### 2. Discovery Service
- **Purpose**: Service registry and discovery
- **Features**:
  - Netflix Eureka Server implementation
  - Dynamic service registration
  - Load-balanced service discovery
  - Health monitoring of registered services
- **Implementation**: Enabled with `@EnableEurekaServer` annotation

### 3. Gateway Service
- **Purpose**: API Gateway and authentication entry point
- **Features**:
  - JWT token validation and filtering
  - Request routing to appropriate microservices
  - User authentication and authorization
  - Adds user context headers (`X-User-Email`, `X-User-Role`) to downstream requests
- **Implementation**: Spring Cloud Gateway with custom JWT filter

### 4. Users Service
- **Purpose**: User management and authentication
- **Features**:
  - User registration and login
  - JWT token generation and validation
  - Role-based access control (USER, ADMIN)
  - User existence validation for other services
- **Security**: BCrypt password encryption, Spring Security integration

### 5. Movies Service
- **Purpose**: Movie catalog management
- **Features**:
  - CRUD operations for movies
  - Movie information storage and retrieval
  - Integration with reviews service for rating data
- **Authorization**: Admin-only access for modifications, authenticated users for viewing

### 6. Reviews Service
- **Purpose**: Movie review and rating management
- **Features**:
  - Create, read movie reviews and ratings
  - Rating validation (1-5 scale)
  - User-movie review uniqueness enforcement
  - Kafka event publishing for new reviews
  - Cross-service user validation

### 7. Analytics Service
- **Purpose**: Review analytics and insights
- **Features**:
  - Consumes review events from Kafka
  - Processes rating statistics and trends
  - Provides analytical data on movie ratings

## Technology Stack

- **Framework**: Spring Boot 3.x
- **Service Discovery**: Netflix Eureka
- **Configuration Management**: Spring Cloud Config Server
- **Security**: Spring Security with JWT authentication
- **Database**: JPA/Hibernate with relational database
- **Message Queue**: Apache Kafka for event-driven communication
- **API Gateway**: Spring Cloud Gateway
- **Build Tool**: Maven
- **Documentation**: Lombok for boilerplate reduction

## Key Features

### Centralized Configuration
- Externalized configuration via Config Server
- Environment-specific configurations
- Dynamic configuration updates without service restarts
- Consistent configuration across services

### Service Discovery and Registration
- Automatic service registration with Eureka Server
- Dynamic scaling with service discovery
- Load balancing across service instances
- Self-healing through health checks and automatic de-registration

### Authentication & Authorization
- JWT-based stateless authentication
- Role-based access control (USER/ADMIN roles)
- Secure password storage with BCrypt
- Token validation across all services via gateway

### Data Consistency
- Cross-service validation (user existence before creating reviews)
- Unique constraints (one review per user per movie)
- Data integrity through proper entity relationships

### Event-Driven Architecture
- Kafka integration for asynchronous processing
- Review creation events published to `review-created-topic`
- Movie deletion events published to `movie-deleted-topic`
- Analytics service consumes events for real-time processing

### Security Features
- CORS configuration
- JWT token expiration (15 minutes)
- Stateless session management
- Authorization header forwarding between services

## Service Communication

### Service Discovery Pattern
1. Services register themselves with Eureka Server at startup
2. Services query Eureka to locate other services
3. Gateway routes requests to appropriate services using Eureka registry
4. Load balancing handled automatically through Eureka

### Inter-Service Communication
- **Synchronous**: REST API calls between services using `RestTemplate`
- **Asynchronous**: Kafka messaging for event-driven processes
- **Headers Propagation**: Authorization headers and user context forwarded across services

### Configuration Management
- Services pull configurations from Config Server at startup
- Environment-specific profiles supported (dev, test, prod)
- Sensitive properties (like database credentials, JWT secrets) centrally managed

## API Endpoints

### Gateway Routes
- All requests go through the gateway service
- Authentication endpoints (`/auth/login`, `/auth/register`) bypass JWT validation
- All other endpoints require valid JWT token

### Users Service
- `POST /api/v1/users/register` - User registration
- `POST /api/v1/users/login` - User authentication
- `GET /api/v1/users/exists/{id}` - Check user existence (internal)

### Reviews Service
- `POST /api/v1/reviews` - Create new review (authenticated users)
- `GET /api/v1/reviews` - Get all reviews (authenticated users)
- `GET /api/v1/reviews/{id}` - Get review by ID (authenticated users)

### Movies Service
- `GET /api/v1/movies/**` - View movies (USER/ADMIN)
- `POST /api/v1/movies/**` - Create movies (ADMIN only)
- `PUT /api/v1/movies/**` - Update movies (ADMIN only)
- `DELETE /api/v1/movies/**` - Delete movies (ADMIN only)

## Security Implementation

### JWT Token Structure
- **Subject**: User email
- **Claims**: User role, User ID
- **Expiration**: 15 minutes
- **Algorithm**: HMAC SHA-256

### Gateway Security Filter
The `JWTFilter` class handles:
- Token validation for all non-auth endpoints
- User context extraction and forwarding
- Authorization header preservation
- Request routing with user information

## Event-Driven Processing

### Kafka Integration
- **Topics**: 
  - `review-created-topic`: New review events
  - `movie-deleted-topic`: Movie deletion events
- **Producer**: Services publish domain events
- **Consumer**: Analytics Service processes data
- **Message Format**: DTOs with relevant entity information

## Getting Started

1. **Prerequisites**:
   - Java 17+
   - Maven 3.6+
   - Database (PostgreSQL/MySQL)
   - Apache Kafka
   - Docker (optional)

2. **Environment Setup**:
   - Configure database connections for each service
   - Set up Kafka broker
   - Configure JWT secret keys
   - Set service URLs for inter-service communication

3. **Build and Run**:
   ```bash
   # Build all services
   mvn clean install
   
   # Run services in order:
   # 1. Config Server
   # 2. Discovery Service
   # 3. Gateway Service
   # 4. Users Service
   # 5. Movies Service
   # 6. Reviews Service
   # 7. Analytics Service
   ```

4. **Testing**:
   - Register a new user via gateway
   - Authenticate to receive JWT token
   - Use token to access protected endpoints

## System Flow

1. **Service Startup**: Services register with Discovery Server and fetch configurations from Config Server
2. **User Registration/Login**: Users authenticate via gateway → users service
3. **Movie Operations**: CRUD operations through gateway → movies service
4. **Review Creation**: User creates review → gateway → reviews service → validates user existence → publishes Kafka event
5. **Analytics Processing**: Analytics service consumes review events → processes data
6. **Cross-Service Communication**: Services discover each other through Eureka and communicate with authentication propagation

## Resilience Features

- **Service Discovery**: Automatic handling of service instances going up/down
- **Load Balancing**: Automatic distribution of traffic across service instances
- **Configuration Management**: Centralized configuration with environment-specific profiles
- **Event-Driven Design**: Loose coupling between services through Kafka messaging
- **Stateless Authentication**: JWT-based authentication without shared sessions

This microservice architecture provides scalability, maintainability, and separation of concerns while ensuring data consistency and security across all services.
