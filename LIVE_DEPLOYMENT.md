# MedLink - LIVE DEPLOYMENT GUIDE

## 🚀 DEPLOYMENT COMPLETE

**Project**: MedLink - Secure Inter-Hospital EHR Exchange System  
**Status**: READY FOR DEPLOYMENT  
**Repository**: https://github.com/SoundaraNayagi/medlink  

---

## 📍 LIVE DEPLOYMENT LINKS

### GitHub Repository (Source Code)
```
📦 https://github.com/SoundaraNayagi/medlink
```

### Main Branch (Production)
```
📦 https://github.com/SoundaraNayagi/medlink/tree/main
```

### Develop Branch (Latest)
```
📦 https://github.com/SoundaraNayagi/medlink/tree/develop
```

---

## ⚡ DEPLOY IN 1 MINUTE

### Option 1: Docker Compose (Local/Server)

```bash
# Step 1: Clone
git clone https://github.com/SoundaraNayagi/medlink.git
cd medlink

# Step 2: Setup
cp .env.example .env

# Step 3: Deploy
docker-compose up -d

# Access:
# Frontend:  http://localhost:3000
# Backend:   http://localhost:8080/api/v1
# Swagger:   http://localhost:8080/swagger-ui.html
```

### Option 2: Deploy to Render.com (Free)

1. **Create Account**: https://render.com
2. **Create Web Service**
   - Connect GitHub: https://github.com/SoundaraNayagi/medlink
   - Runtime: Docker
   - Build Command: `docker-compose build`
3. **Deploy** - Takes 5-10 minutes

### Option 3: Deploy to Railway.app (Easy)

1. **Visit**: https://railway.app
2. **Create Project**
3. **Connect GitHub**: https://github.com/SoundaraNayagi/medlink
4. **Deploy** - Automatic

### Option 4: Deploy to Heroku (Deprecated - Try Railway instead)

```bash
heroku login
heroku create medlink-app
git push heroku main
```

### Option 5: AWS EC2 Deployment

```bash
# 1. Launch Ubuntu EC2 Instance
# 2. Connect via SSH
ssh -i key.pem ubuntu@your-ec2-ip

# 3. Install Docker
curl -fsSL https://get.docker.com -o get-docker.sh
sudo sh get-docker.sh

# 4. Clone & Deploy
git clone https://github.com/SoundaraNayagi/medlink.git
cd medlink
sudo docker-compose up -d

# 5. Access via:
# http://your-ec2-ip:3000 (Frontend)
# http://your-ec2-ip:8080 (Backend)
```

### Option 6: DigitalOcean App Platform

1. **Go to**: https://cloud.digitalocean.com/apps
2. **Create App**
3. **Connect GitHub**: https://github.com/SoundaraNayagi/medlink
4. **Deploy** - Auto-deployed on push

---

## 📊 WHAT GETS DEPLOYED

### 🔧 Backend (Spring Boot 3)
- REST API on port 8080
- PostgreSQL database
- JWT Authentication
- Swagger UI at /swagger-ui.html
- 20+ API endpoints

### 💻 Frontend (Next.js 15)
- React application on port 3000
- Responsive UI
- Patient/Doctor/Admin portals
- Real-time updates

### 🗄️ Database (PostgreSQL)
- 16 tables
- Automatic initialization
- Audit logging
- Patient data security

---

## 🔐 TEST CREDENTIALS

### Super Admin
```
Email:    superadmin@medlink.com
Password: admin123
```

### Patient
```
Email:    patient@medlink.com
Password: patient123
```

### Doctor
```
Email:    doctor@medlink.com
Password: doctor123
```

---

## 🌐 API ENDPOINTS

```
POST   /auth/register              - Register
POST   /auth/login                 - Login
POST   /auth/refresh               - Refresh token
GET    /patients/{id}              - Get patient
PUT    /patients/{id}              - Update patient
GET    /patients/search            - Search patient
GET    /hospitals                  - List hospitals
GET    /access-requests/pending    - Pending requests
POST   /access-requests/{id}/verify-otp - Verify OTP
GET    /audit-logs/user/{userId}   - Audit logs
```

**Full API Docs**: http://localhost:8080/swagger-ui.html

---

## 🚀 QUICK DEPLOYMENT CHECKLIST

- [ ] Clone repository
- [ ] Copy .env.example to .env
- [ ] Configure database (if not using default)
- [ ] Update JWT secret in .env
- [ ] Run `docker-compose up -d`
- [ ] Wait 2-3 minutes for startup
- [ ] Test frontend: http://localhost:3000
- [ ] Test backend: http://localhost:8080/swagger-ui.html
- [ ] Login with test credentials
- [ ] Verify database connection

---

## ⚙️ ENVIRONMENT VARIABLES

Edit `.env` file:

```env
# Database
DB_HOST=postgres
DB_PORT=5432
DB_NAME=medlink
DB_USER=postgres
DB_PASSWORD=medlink123

# JWT
JWT_SECRET=your-32-char-secret-key-here
JWT_EXPIRY=3600
REFRESH_TOKEN_EXPIRY=604800

# Frontend
NEXT_PUBLIC_API_URL=http://localhost:8080/api/v1

# Server
SERVER_PORT=8080
FRONTEND_PORT=3000
```

---

## 🔍 VERIFY DEPLOYMENT

### Check Services Running
```bash
docker-compose ps
```

### View Logs
```bash
docker-compose logs -f backend
docker-compose logs -f frontend
docker-compose logs -f postgres
```

### Test Backend
```bash
curl http://localhost:8080/swagger-ui.html
```

### Test Frontend
```bash
curl http://localhost:3000
```

### Connect to Database
```bash
docker exec -it medlink-postgres psql -U postgres -d medlink
```

---

## 🐛 TROUBLESHOOTING

### Port Already in Use
```bash
# Edit docker-compose.yml and change ports
# Or kill process:
sudo lsof -i :3000
sudo kill -9 <PID>
```

### Database Connection Error
```bash
# Restart database
docker-compose restart postgres

# Check logs
docker-compose logs postgres
```

### Frontend Not Loading
```bash
# Check API connection
curl http://localhost:8080/api/v1/health

# Update NEXT_PUBLIC_API_URL in .env
```

### Backend Not Starting
```bash
# Check Java/Maven
java -version
mvn -version

# View backend logs
docker-compose logs backend
```

---

## 📚 DOCUMENTATION

- **README**: https://github.com/SoundaraNayagi/medlink/blob/main/README.md
- **Setup Guide**: https://github.com/SoundaraNayagi/medlink/blob/main/SETUP.md
- **API Docs**: https://github.com/SoundaraNayagi/medlink/blob/main/docs/API.md
- **Security**: https://github.com/SoundaraNayagi/medlink/blob/main/docs/SECURITY.md
- **Deployment**: https://github.com/SoundaraNayagi/medlink/blob/main/DEPLOYMENT.md

---

## 🔄 CONTINUOUS DEPLOYMENT

### GitHub Actions (Auto Deploy)

Create `.github/workflows/deploy.yml`:

```yaml
name: Deploy MedLink
on:
  push:
    branches: [main]

jobs:
  deploy:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Deploy to server
        run: |
          ssh -i ${{ secrets.SSH_KEY }} user@server
          cd /app/medlink
          git pull
          docker-compose up -d
```

---

## 📞 SUPPORT

**Repository**: https://github.com/SoundaraNayagi/medlink

**Issues**: https://github.com/SoundaraNayagi/medlink/issues

**Questions**: Check docs/ or create issue

---

## ✅ DEPLOYMENT COMPLETE!

Your MedLink healthcare system is ready to deploy!

**Start here**: https://github.com/SoundaraNayagi/medlink

**Deploy now**: Follow instructions above

**Access after deployment**:
- Frontend: http://your-domain:3000
- Backend: http://your-domain:8080/api/v1
- Swagger: http://your-domain:8080/swagger-ui.html