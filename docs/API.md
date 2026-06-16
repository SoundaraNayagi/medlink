# MedLink API Documentation

## Base URL
```
http://localhost:8080/api/v1
```

## Authentication
All protected endpoints require Bearer token in Authorization header:
```
Authorization: Bearer <access_token>
```

## Endpoints

### Authentication Endpoints

#### Register User
```
POST /auth/register
Content-Type: application/json

{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@example.com",
  "phone": "+919999999999",
  "password": "SecurePassword123"
}

Response: 201 Created
{
  "accessToken": "eyJhbGc...",
  "refreshToken": "eyJhbGc...",
  "user": {
    "id": "uuid",
    "firstName": "John",
    "email": "john@example.com",
    "role": "PATIENT"
  },
  "tokenType": "Bearer"
}
```

#### Login
```
POST /auth/login
Content-Type: application/json

{
  "email": "john@example.com",
  "password": "SecurePassword123"
}

Response: 200 OK
{
  "accessToken": "eyJhbGc...",
  "refreshToken": "eyJhbGc...",
  "user": {...},
  "tokenType": "Bearer"
}
```

#### Refresh Token
```
POST /auth/refresh
Authorization: Bearer <refresh_token>

Response: 200 OK
{
  "accessToken": "new_token",
  "refreshToken": "new_refresh_token",
  "tokenType": "Bearer"
}
```

### Patient Endpoints

#### Get Patient Profile
```
GET /patients/{patientId}
Authorization: Bearer <token>

Response: 200 OK
{
  "id": "uuid",
  "patientUniqueId": "PAT123",
  "aadhaarNumber": "123456789012",
  "bloodGroup": "O+",
  "gender": "M",
  "dateOfBirth": "1990-01-01",
  "user": {...}
}
```

#### Update Patient Profile
```
PUT /patients/{patientId}
Authorization: Bearer <token>
Content-Type: application/json

{
  "bloodGroup": "O+",
  "address": "123 Main St",
  "emergencyContact": "+919999999998"
}

Response: 200 OK
```

#### Search Patient by Phone
```
GET /patients/search?phone=%2B919999999999
Authorization: Bearer <token>

Response: 200 OK
{
  "id": "uuid",
  "firstName": "John",
  "phone": "+919999999999"
}
```

### Access Request Endpoints

#### Create Access Request
```
POST /access-requests
Authorization: Bearer <token>
Content-Type: application/json

{
  "patientPhone": "+919999999999"
}

Response: 201 Created
{
  "id": "uuid",
  "requestStatus": "PENDING",
  "createdAt": "2024-01-01T10:00:00"
}
```

#### Verify OTP and Approve Access
```
POST /access-requests/{requestId}/verify-otp
Authorization: Bearer <token>
Content-Type: application/json

{
  "otpCode": "123456"
}

Response: 200 OK
{
  "message": "Access approved",
  "expiresAt": "2024-02-01T10:00:00"
}
```

#### Get Pending Access Requests
```
GET /access-requests/pending
Authorization: Bearer <token>

Response: 200 OK
[
  {
    "id": "uuid",
    "requestingDoctor": {...},
    "requestingHospital": {...},
    "requestStatus": "PENDING",
    "createdAt": "2024-01-01T10:00:00"
  }
]
```

## Error Responses

### Unauthorized (401)
```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Invalid or expired token"
}
```

### Forbidden (403)
```json
{
  "status": 403,
  "error": "Forbidden",
  "message": "Insufficient permissions"
}
```

### Not Found (404)
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Resource not found"
}
```

### Bad Request (400)
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid request parameters"
}
```

## Rate Limiting
- 100 requests per minute for authenticated users
- 20 requests per minute for unauthenticated endpoints

## Response Codes
- 200: Success
- 201: Created
- 400: Bad Request
- 401: Unauthorized
- 403: Forbidden
- 404: Not Found
- 500: Internal Server Error