# 🚀 RideShare Backend API

A mini Ride Sharing backend built with Spring Boot, MongoDB, and JWT Authentication.

## 📋 Features

- ✅ User Registration & Login with JWT Authentication
- ✅ BCrypt Password Encoding
- ✅ Role-based Authorization (ROLE_USER, ROLE_DRIVER)
- ✅ Create Ride Requests (Passengers)
- ✅ View & Accept Ride Requests (Drivers)
- ✅ Complete Rides
- ✅ Input Validation
- ✅ Global Exception Handling

## 🛠️ Tech Stack

- **Framework:** Spring Boot 3.2.0
- **Database:** MongoDB
- **Security:** Spring Security + JWT
- **Validation:** Jakarta Validation

## 📁 Project Structure

```
src/main/java/org/example/rideshare/
├── model/           # Entity classes (User, Ride)
├── repository/      # MongoDB repositories
├── service/         # Business logic
├── controller/      # REST endpoints
├── config/          # Security configuration
├── dto/             # Data Transfer Objects
├── exception/       # Custom exceptions & handler
└── util/            # JWT utility
```

## 🚦 Prerequisites

- Java 17+
- Maven
- MongoDB running on localhost:27017

## 🏃 Running the Application

1. **Start MongoDB:**
   ```bash
   mongod
   ```

2. **Build and Run:**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

The server will start on `http://localhost:8081`

## 📡 API Endpoints

### Authentication (Public)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/auth/register` | Register new user |
| POST | `/api/auth/login` | Login and get JWT token |

### User Endpoints (ROLE_USER)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/rides` | Create a ride request |
| GET | `/api/v1/user/rides` | Get my rides |

### Driver Endpoints (ROLE_DRIVER)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/driver/rides/requests` | View pending ride requests |
| POST | `/api/v1/driver/rides/{id}/accept` | Accept a ride |

### Shared Endpoints (Authenticated)

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/api/v1/rides/{id}/complete` | Complete a ride |

## 🧪 CURL Commands for Testing

### 1. Register a User (Passenger)
```bash
curl -X POST http://localhost:8081/api/auth/register \
-H "Content-Type: application/json" \
-d '{"username":"john","password":"1234","role":"ROLE_USER"}'
```

### 2. Register a Driver
```bash
curl -X POST http://localhost:8081/api/auth/register \
-H "Content-Type: application/json" \
-d '{"username":"driver1","password":"abcd","role":"ROLE_DRIVER"}'
```

### 3. Login
```bash
curl -X POST http://localhost:8081/api/auth/login \
-H "Content-Type: application/json" \
-d '{"username":"john","password":"1234"}'
```

### 4. Create a Ride (User must be logged in)
```bash
curl -X POST http://localhost:8081/api/v1/rides \
-H "Content-Type: application/json" \
-H "Authorization: Bearer <YOUR_JWT_TOKEN>" \
-d '{"pickupLocation":"Koramangala","dropLocation":"Indiranagar"}'
```

### 5. View Pending Rides (Driver)
```bash
curl -X GET http://localhost:8081/api/v1/driver/rides/requests \
-H "Authorization: Bearer <DRIVER_JWT_TOKEN>"
```

### 6. Accept a Ride (Driver)
```bash
curl -X POST http://localhost:8081/api/v1/driver/rides/{rideId}/accept \
-H "Authorization: Bearer <DRIVER_JWT_TOKEN>"
```

### 7. Complete a Ride
```bash
curl -X POST http://localhost:8081/api/v1/rides/{rideId}/complete \
-H "Authorization: Bearer <YOUR_JWT_TOKEN>"
```

### 8. Get My Rides (User)
```bash
curl -X GET http://localhost:8081/api/v1/user/rides \
-H "Authorization: Bearer <USER_JWT_TOKEN>"
```

## 📝 Request/Response Examples

### Register Request
```json
{
  "username": "john",
  "password": "1234",
  "role": "ROLE_USER"
}
```

### Login Response
```json
{
  "token": "eyJhbGciOiJIUzI1NiJ9...",
  "username": "john",
  "role": "ROLE_USER"
}
```

### Create Ride Request
```json
{
  "pickupLocation": "Koramangala",
  "dropLocation": "Indiranagar"
}
```

### Ride Response
```json
{
  "id": "65a8b3c4d5e6f7g8h9i0j1k2",
  "userId": "65a8b3c4d5e6f7g8h9i0j1k1",
  "driverId": null,
  "pickupLocation": "Koramangala",
  "dropLocation": "Indiranagar",
  "status": "REQUESTED",
  "createdAt": "2025-01-20T12:00:00.000+00:00"
}
```

### Error Response
```json
{
  "error": "VALIDATION_ERROR",
  "message": "Pickup location is required",
  "timestamp": "2025-01-20T12:00:00Z"
}
```

## 🔐 JWT Token Usage

All protected endpoints require the JWT token in the Authorization header:

```
Authorization: Bearer <your_jwt_token>
```

The JWT token contains:
- `username` - User's username
- `role` - User's role (ROLE_USER or ROLE_DRIVER)
- `userId` - User's MongoDB ID
- `issuedAt` - Token creation time
- `expiration` - Token expiry time (24 hours)

## ⚠️ Error Handling

The API uses a global exception handler that returns consistent error responses:

| Error Type | HTTP Status | Description |
|------------|-------------|-------------|
| VALIDATION_ERROR | 400 | Input validation failed |
| BAD_REQUEST | 400 | Invalid request |
| UNAUTHORIZED | 401 | Authentication failed |
| NOT_FOUND | 404 | Resource not found |
| INTERNAL_ERROR | 500 | Server error |

## 👨‍💻 Author

Built as part of the Spring Boot RideShare Mini Project.

