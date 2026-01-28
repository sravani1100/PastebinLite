-- Pastebin-Lite Database Setup Script
-- Run this script in PostgreSQL to create the required database

-- Create database (run as superuser)
CREATE DATABASE pastebin;

-- Connect to the database
\c pastebin

-- The pastes table will be created automatically by Spring Boot JPA
-- But here's the schema for reference:

-- CREATE TABLE pastes (
--     id VARCHAR(8) PRIMARY KEY,
--     content TEXT NOT NULL,
--     created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
--     expires_at TIMESTAMP,
--     max_views INTEGER,
--     current_views INTEGER DEFAULT 0
-- );

-- CREATE INDEX idx_expires_at ON pastes(expires_at);

-- Grant permissions (adjust username as needed)
-- GRANT ALL PRIVILEGES ON DATABASE pastebin TO your_username;
