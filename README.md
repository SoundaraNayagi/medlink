# MedLink - Secure Inter-Hospital Electronic Health Record Exchange System

A production-grade full-stack healthcare application that allows hospitals to securely share patient medical records with patient consent through OTP verification.

## 🏥 Project Overview

MedLink is a centralized Health Information Exchange (HIE) platform that enables:
- Secure patient medical record sharing between hospitals
- Patient consent management through OTP verification
- Role-based access control (RBAC) with 4 roles
- Comprehensive audit logging for compliance
- DICOM and medical file storage on AWS S3
- End-to-end encryption with AES-256

## 🏗️ Tech Stack

### Backend
- **Framework**: Spring Boot 3
- **Language**: Java 21
- **Security**: Spring Security with JWT & OTP
- **ORM**: Hibernate with Spring Data JPA
- **Build Tool**: Maven

### Frontend
- **Framework**: Next.js 15 (App Router)
- **Language**: TypeScript
- **Styling**: Tailwind CSS + Shadcn UI
- **State Management**: TanStack Query
- **Forms**: React Hook Form
- **HTTP Client**: Axios

### Database
- **Primary**: PostgreSQL 15
- **Storage**: AWS S3 Compatible

### Infrastructure
- **Containerization**: Docker & Docker Compose
- **API Documentation**: Swagger/OpenAPI

## 👥 User Roles

1. **SUPER_ADMIN** - System-wide management
2. **HOSPITAL_ADMIN** - Hospital management
3. **DOCTOR** - Patient record access and management
4. **PATIENT** - Personal health record access

## 🔐 Security Features

✅ JWT Authentication with Refresh Tokens  
✅ OTP Verification for consent  
✅ AES-256 encryption for sensitive data  
✅ Role-Based Access Control (RBAC)  
✅ Comprehensive Audit Logging  
✅ SQL Injection Protection  
✅ XSS Protection  
✅ CSRF Protection  
✅ Secure password hashing (bcrypt)  

## 📋 Core Features

### Authentication Module
- User registration and login
- JWT token management
- Refresh token rotation
- Forgot/Change password
- OTP generation and verification

### Patient Management
- Patient profile with health information
- Medical history tracking
- Allergy management
- Vaccination records
- Hospital visit history

### Medical Records
- Lab reports (PDF storage)
- Scan reports (DICOM, MRI, CT, X-Ray, Ultrasound)
- Prescriptions
- Treatment history

### Cross-Hospital Access
- Patient search by mobile number
- OTP-based access requests
- Temporary read-only access
- Automatic access expiration
- Audit trail for all access

### Hospital Management
- Hospital registration and profile
- Doctor management
- Patient records management
- Analytics and reporting

## 📁 Project Structure

```
medlink/
├── backend/                 # Spring Boot application
│   ├── src/
│   ├── pom.xml
│   ├── Dockerfile
│   └── README.md
├── frontend/                # Next.js application
│   ├── app/
│   ├── components/
│   ├── services/
│   ├── package.json
│   ├── Dockerfile
│   └── README.md
├── database/                # PostgreSQL schema
│   └── schema.sql
├── docker-compose.yml       # Multi-container orchestration
├── .env.example             # Environment variables template
└── docs/                    # Documentation
```

## 🚀 Quick Start

### Prerequisites
- Docker & Docker Compose
- Git
- Node.js 18+ (for frontend development)
- Java 21 (for backend development)
- PostgreSQL 15

### Option 1: Docker Compose (Recommended)

```bash
# Clone the repository
git clone https://github.com/SoundaraNayagi/medlink.git
cd medlink

# Copy environment file
cp .env.example .env

# Start all services
docker-compose up -d

# Services will be available at:
# Frontend: http://localhost:3000
# Backend: http://localhost:8080
# API Docs: http://localhost:8080/swagger-ui.html
# PostgreSQL: localhost:5432
```

### Option 2: Local Development

#### Backend Setup
```bash
cd backend
mvn clean install
mvn spring-boot:run
```

#### Frontend Setup
```bash
cd frontend
npm install
npm run dev
```

## 🌐 API Endpoints

### Authentication
- `POST /api/v1/auth/register` - User registration
- `POST /api/v1/auth/login` - User login
- `POST /api/v1/auth/refresh` - Refresh token
- `POST /api/v1/auth/logout` - User logout

### Patients
- `GET /api/v1/patients/search` - Search patient by mobile
- `GET /api/v1/patients/{id}` - Get patient details
- `PUT /api/v1/patients/{id}` - Update patient profile

### Doctors
- `GET /api/v1/doctors/patients` - List doctor's patients
- `POST /api/v1/doctors/visit` - Create hospital visit
- `POST /api/v1/doctors/prescription` - Add prescription

### Medical Records
- `GET /api/v1/medical-records/{patientId}` - Get patient records
- `POST /api/v1/lab-reports/upload` - Upload lab report
- `POST /api/v1/scan-reports/upload` - Upload scan report

### Access Requests
- `POST /api/v1/access-requests` - Request patient access
- `POST /api/v1/access-requests/{id}/verify-otp` - Verify OTP
- `GET /api/v1/access-requests/pending` - Get pending requests

### Audit
- `GET /api/v1/audit-logs` - Get audit logs

## 📊 Database Schema

The system includes the following main entities:
- **Users** - System users with roles
- **Hospitals** - Hospital information
- **Patients** - Patient profiles and health data
- **Doctors** - Doctor profiles linked to hospitals
- **Medical Records** - Prescriptions, reports, history
- **Access Requests** - Cross-hospital access management
- **Audit Logs** - Compliance tracking

See `database/schema.sql` for complete schema.

## 🔧 Configuration

### Environment Variables

```env
# Database
DB_HOST=localhost
DB_PORT=5432
DB_NAME=medlink
DB_USER=postgres
DB_PASSWORD=medlink123

# JWT
JWT_SECRET=your-secret-key-min-32-chars
JWT_EXPIRY=3600
REFRESH_TOKEN_EXPIRY=604800

# AWS S3
AWS_S3_BUCKET=medlink-records
AWS_S3_REGION=us-east-1
AWS_ACCESS_KEY=your-key
AWS_SECRET_KEY=your-secret

# Email (OTP)
EMAIL_HOST=smtp.gmail.com
EMAIL_PORT=587
EMAIL_USER=your-email@gmail.com
EMAIL_PASSWORD=your-password
```

## 📚 API Documentation

After starting the backend, access Swagger UI:
```
http://localhost:8080/swagger-ui.html
```

Postman Collection: See `docs/postman-collection.json`

## 🧪 Testing

### Backend
```bash
cd backend
mvn test
```

### Frontend
```bash
cd frontend
npm run test
```

## 📝 Documentation

- [Backend Setup Guide](./backend/README.md)
- [Frontend Setup Guide](./frontend/README.md)
- [Database Schema](./database/README.md)
- [API Documentation](./docs/API.md)
- [Security Guide](./docs/SECURITY.md)
- [Deployment Guide](./docs/DEPLOYMENT.md)

## 🔒 Security Considerations

1. **Never commit `.env` files** - Use `.env.example` for templates
2. **Always use HTTPS** in production
3. **Rotate JWT secrets regularly**
4. **Enable database encryption** in production
5. **Implement rate limiting** on authentication endpoints
6. **Regular security audits** and penetration testing
7. **Keep dependencies updated**

## 🤝 Contributing

1. Create a feature branch: `git checkout -b feature/feature-name`
2. Commit changes: `git commit -m 'Add feature'`
3. Push to branch: `git push origin feature/feature-name`
4. Open a Pull Request

## 📄 License

This project is proprietary and intended for healthcare organizations only.

## 🆘 Support

For issues and questions, please open an issue on GitHub or contact the development team.

---

**Built with ❤️ for secure healthcare data exchange**
