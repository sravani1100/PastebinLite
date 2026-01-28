@echo off
echo ========================================
echo Pastebin-Lite Backend Startup Script
echo ========================================

REM Set environment variables for local development
set DATABASE_URL=jdbc:postgresql://localhost:5432/pastebin
set DATABASE_USERNAME=postgres
set DATABASE_PASSWORD=postgresql
set PORT=8080
set CORS_ORIGINS=http://localhost:4200

echo.
echo Environment configured:
echo   DATABASE_URL: %DATABASE_URL%
echo   PORT: %PORT%
echo   CORS_ORIGINS: %CORS_ORIGINS%
echo.

echo Starting Spring Boot application...
mvnw.cmd spring-boot:run
