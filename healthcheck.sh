#!/bin/bash

# MedLink Health Check Script
# Verifies all services are running

echo "🏥 MedLink Health Check"
echo "======================"

echo ""
echo "Checking Docker containers..."
docker-compose ps

echo ""
echo "Checking backend..."
if curl -s http://localhost:8080/swagger-ui.html > /dev/null; then
    echo "✅ Backend API is running"
else
    echo "❌ Backend API is not responding"
fi

echo ""
echo "Checking frontend..."
if curl -s http://localhost:3000 > /dev/null; then
    echo "✅ Frontend is running"
else
    echo "❌ Frontend is not responding"
fi

echo ""
echo "Checking database..."
if docker exec medlink-postgres psql -U postgres -d medlink -c "SELECT 1" > /dev/null 2>&1; then
    echo "✅ Database is running"
else
    echo "❌ Database is not responding"
fi

echo ""
echo "Health check complete!"
