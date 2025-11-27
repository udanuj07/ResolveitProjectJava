-- ResolveIt - Complaint & Feedback Management System
-- Database Schema
-- MySQL Database Setup Script

-- Create Database
CREATE DATABASE IF NOT EXISTS resolveit_db;
USE resolveit_db;

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Complaints Table
CREATE TABLE IF NOT EXISTS complaints (
    complaint_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    category VARCHAR(100) NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    priority ENUM('LOW', 'MEDIUM', 'HIGH', 'URGENT') DEFAULT 'MEDIUM',
    status ENUM('OPEN', 'IN_PROGRESS', 'RESOLVED', 'CLOSED') DEFAULT 'OPEN',
    assigned_to INT,
    resolution_note TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (assigned_to) REFERENCES users(user_id) ON DELETE SET NULL,
    INDEX idx_user_id (user_id),
    INDEX idx_status (status),
    INDEX idx_priority (priority)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Feedback Table
CREATE TABLE IF NOT EXISTS feedback (
    feedback_id INT AUTO_INCREMENT PRIMARY KEY,
    complaint_id INT NOT NULL,
    rating INT CHECK (rating >= 1 AND rating <= 5),
    comments TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (complaint_id) REFERENCES complaints(complaint_id) ON DELETE CASCADE,
    INDEX idx_complaint_id (complaint_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Insert Sample Admin User
-- Password: admin123 (SHA-256 hashed)
INSERT INTO users (name, email, password_hash, role) VALUES
('Admin User', 'admin@resolveit.com', '0192023a7bbd73250516f069df18b500', 'ADMIN');

-- Insert Sample Regular Users
INSERT INTO users (name, email, password_hash, role) VALUES
('John Doe', 'john@example.com', '5d41402abc4b2a76b9719d911017c592', 'USER'),
('Jane Smith', 'jane@example.com', '6512bd43d9caa6e02c990b0a82652dca', 'USER'),
('Bob Johnson', 'bob@example.com', 'c4ca4238a0b923820dcc509a6f75849b', 'USER');

-- Insert Sample Complaints
INSERT INTO complaints (user_id, category, title, description, priority, status, assigned_to) VALUES
(2, 'Technical Issue', 'Login Not Working', 'Unable to login to the application with correct credentials', 'HIGH', 'OPEN', 1),
(2, 'Payment', 'Transaction Failed', 'Payment was deducted but order was not created', 'URGENT', 'IN_PROGRESS', 1),
(3, 'Feature Request', 'Dark Mode Feature', 'Would like to have a dark mode option in the application', 'LOW', 'OPEN', NULL),
(4, 'Bug Report', 'UI Broken on Mobile', 'The application UI is broken when accessed from mobile devices', 'HIGH', 'RESOLVED', 1);

-- Insert Sample Feedback
INSERT INTO feedback (complaint_id, rating, comments) VALUES
(4, 5, 'Issue was resolved quickly and efficiently. Great support!');

-- Create Indexes for better query performance
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_complaints_created_at ON complaints(created_at);
CREATE INDEX idx_feedback_rating ON feedback(rating);

-- Display tables info
SHOW TABLES;

-- Verify data
SELECT * FROM users;
SELECT * FROM complaints;
SELECT * FROM feedback;
