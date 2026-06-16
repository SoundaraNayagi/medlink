#!/bin/bash

# MedLink Deployment Script
# Automates deployment setup

echo "🏥 MedLink - Deployment Setup"
echo "================================"

# Check Docker
if ! command -v docker &> /dev/null; then
    echo "❌ Docker not installed. Visit https://docs.docker.com/get-docker/"
    exit 1
fi

echo "✅ Docker found: $(docker --version)"

# Check Docker Compose
if ! command -v docker-compose &> /dev/null; then
    echo "❌ Docker Compose not installed. Visit https://docs.docker.com/compose/install/"
    exit 1
fi

echo "✅ Docker Compose found: $(docker-compose --version)"

# Create .env if not exists
if [ ! -f .env ]; then
    echo ""
    echo "📝 Creating .env file..."
    cp .env.example .env
    echo "✅ .env created from .env.example"
    echo "⚠️  Please edit .env with your configuration"
else
    echo "✅ .env file exists"
fi

# Create logs directory
mkdir -p logs
echo "✅ Logs directory created"

echo ""
echo "🚀 Ready to deploy!"
echo ""
echo "Next steps:"
echo "1. Edit .env with your configuration"
echo "2. Run: docker-compose up -d"
echo ""
echo "Access:"
echo "  Frontend:  http://localhost:3000"
echo "  Backend:   http://localhost:8080/api/v1"
echo "  Swagger:   http://localhost:8080/swagger-ui.html"
echo ""
echo "Test login:"
echo "  Email:    superadmin@medlink.com"
echo "  Password: admin123"
