#!/bin/bash

# MedLink Docker Build Script
# Builds and pushes Docker images

echo "🐳 Building MedLink Docker Images"
echo "===================================="

# Build backend
echo ""
echo "Building backend..."
docker build -f backend/Dockerfile -t medlink-backend:latest . || exit 1
echo "✅ Backend built successfully"

# Build frontend
echo ""
echo "Building frontend..."
docker build -f frontend/Dockerfile -t medlink-frontend:latest . || exit 1
echo "✅ Frontend built successfully"

echo ""
echo "✅ All images built successfully!"
echo ""
echo "Next steps:"
echo "1. Tag images for your registry"
echo "2. Push to Docker Hub or private registry"
echo "3. Deploy with docker-compose"
echo ""
echo "Example:"
echo "  docker tag medlink-backend:latest username/medlink-backend:latest"
echo "  docker push username/medlink-backend:latest"
