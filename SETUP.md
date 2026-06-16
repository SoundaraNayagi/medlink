# MedLink - Complete Setup Guide

## 🚀 Quick Start with Docker Compose

### Prerequisites
- Docker & Docker Compose installed
- Git

### Steps

1. **Clone Repository**
```bash
git clone https://github.com/SoundaraNayagi/medlink.git
cd medlink
```

2. **Setup Environment**
```bash
cp .env.example .env
# Edit .env with your configuration
```

3. **Start Services**
```bash
docker-compose up -d
```

4. **Access Applications**
- Frontend: http://localhost:3000
- Backend API: http://localhost:8080/api/v1
- Swagger UI: http://localhost:8080/swagger-ui.html
- PostgreSQL: localhost:5432

## 📊 Database

The PostgreSQL database is automatically initialized with the schema from `database/schema.sql`.

### Default Admin Credentials
- Email: `superadmin@medlink.com`
- Password: `admin123` (hash stored in DB)

## 🔑 Default Test Credentials

### Super Admin
```
Email: superadmin@medlink.com
Password: admin123
```

### Test Patient
```
Email: patient@medlink.com
Password: patient123
```

### Test Doctor
```
Email: doctor@medlink.com
Password: doctor123
```

## 📡 API Endpoints

### Authentication
- `POST /auth/register` - Register new user
- `POST /auth/login` - Login user
- `POST /auth/refresh` - Refresh JWT token
- `POST /auth/logout` - Logout user

### Patients
- `GET /patients/{id}` - Get patient details
- `PUT /patients/{id}` - Update patient profile
- `GET /patients/search` - Search patient by phone

### Doctors
- `GET /doctors/patients` - List doctor's patients
- `POST /doctors/visit` - Create hospital visit
- `POST /doctors/prescription` - Add prescription

### Access Requests
- `POST /access-requests` - Create access request
- `POST /access-requests/{id}/verify-otp` - Verify OTP
- `GET /access-requests/pending` - Get pending requests

## 🔒 Security

### Authentication
- JWT tokens with 1-hour expiry
- Refresh tokens with 7-day expiry
- BCrypt password hashing

### Authorization
- Role-Based Access Control (RBAC)
- Method-level security with @PreAuthorize

### Data Protection
- AES-256 encryption for sensitive fields (future)
- HTTPS ready for production
- CORS configured

## 📝 Deployment

### Production Deployment

1. **Build Images**
```bash
docker build -f backend/Dockerfile -t medlink-backend:latest .
docker build -f frontend/Dockerfile -t medlink-frontend:latest .
```

2. **Push to Registry**
```bash
docker tag medlink-backend:latest your-registry/medlink-backend:latest
docker push your-registry/medlink-backend:latest
```

3. **Deploy to Kubernetes/Server**
- Use provided docker-compose.yml
- Update environment variables
- Use reverse proxy (Nginx/Apache)
- Enable SSL/TLS

## 🧪 Testing

### Backend Tests
```bash
cd backend
mvn test
```

### Frontend Tests
```bash
cd frontend
npm run test
```

## 📦 Architecture

### Backend (Spring Boot)
- **Layer 1**: Controllers (REST endpoints)
- **Layer 2**: Services (Business logic)
- **Layer 3**: Repositories (Data access)
- **Layer 4**: Entities (Domain models)

### Frontend (Next.js)
- **App Router**: Page-based routing
- **Components**: Reusable UI components
- **Services**: API integration
- **Stores**: State management (Zustand)

### Database
- PostgreSQL with 15+ tables
- Proper indexing for performance
- Foreign key constraints
- Audit logging

## 🔧 Configuration

All configuration is managed through environment variables in `.env` file.

### Key Variables
```env
DB_HOST=postgres
DB_PORT=5432
DB_NAME=medlink
DB_USER=postgres
DB_PASSWORD=medlink123
JWT_SECRET=your-secret-key
JWT_EXPIRY=3600
NEXT_PUBLIC_API_URL=http://localhost:8080/api/v1
```

## 🐛 Troubleshooting

### Database Connection Error
```bash
# Check PostgreSQL container
docker-compose logs postgres

# Verify connection
docker exec medlink-postgres psql -U postgres -d medlink -c "SELECT 1"
```

### Backend Not Starting
```bash
# Check logs
docker-compose logs backend

# Verify Java version
java -version
```

### Frontend Not Loading
```bash
# Check frontend logs
docker-compose logs frontend

# Verify API connectivity
curl http://localhost:8080/swagger-ui.html
```

## 📚 Documentation

- [Backend README](./backend/README.md)
- [Frontend README](./frontend/README.md)
- [Database Schema](./database/schema.sql)
- [API Documentation](./docs/API.md)

## 🤝 Contributing

1. Create feature branch: `git checkout -b feature/feature-name`
2. Commit changes: `git commit -m 'Add feature'`
3. Push to branch: `git push origin feature/feature-name`
4. Open Pull Request

## 📄 License

Proprietary - MedLink Healthcare System

## 📞 Support

For issues and support:
- Create an issue on GitHub
- Contact: support@medlink.com

---

**MedLink v1.0.0** - Secure Inter-Hospital EHR Exchange System