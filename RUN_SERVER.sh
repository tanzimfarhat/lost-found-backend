#!/bin/bash
# Lost Found Backend - Startup Script
# This script finds your local IP, shows the backend URL, and starts the Spring Boot server.

echo "========================================"
echo "  Lost Found Backend - Server Starter"
echo "========================================"
echo ""

# Check if .env exists, otherwise use .env.example as reference
if [ ! -f .env ]; then
    echo "⚠️  Warning: .env file not found!"
    echo "   Please copy .env.example to .env and fill in your values:"
    echo "   cp .env.example .env"
    echo ""
fi

# Find local IP address (macOS / Linux)
echo "🔍 Detecting your local IP address..."
LOCAL_IP=$(ifconfig | grep "inet " | grep -v 127.0.0.1 | awk '{print $2}' | head -1)

if [ -z "$LOCAL_IP" ]; then
    echo "❌ Could not detect local IP. Please check your network connection."
    exit 1
fi

echo "✅ Your Local IP: $LOCAL_IP"
echo "🌐 Backend will run at: http://$LOCAL_IP:8080"
echo "📱 Share this URL with your frontend developer"
echo ""

# Load .env if it exists
if [ -f .env ]; then
    echo "📦 Loading environment variables from .env..."
    export $(grep -v '^#' .env | xargs)
fi

echo "🚀 Starting Spring Boot server..."
echo "   Press CTRL+C to stop the server"
echo "========================================"
echo ""

./mvnw spring-boot:run