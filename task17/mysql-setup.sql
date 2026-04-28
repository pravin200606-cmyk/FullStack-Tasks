-- ============================================================
--  Task 17 - MySQL Workbench Setup Script
--  Run this script in MySQL Workbench BEFORE starting the app
-- ============================================================

-- STEP 1: Create the database
CREATE DATABASE IF NOT EXISTS studentdb;

-- STEP 2: Use the database
USE studentdb;

-- STEP 3: Create the student table
--  (Spring JPA with ddl-auto=update will auto-create it,
--   but run this manually if you want to pre-create it)
CREATE TABLE IF NOT EXISTS student (
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100) NOT NULL,
    department VARCHAR(100) NOT NULL
);

-- STEP 4: Insert sample data
INSERT INTO student (name, department) VALUES ('John', 'AI');
INSERT INTO student (name, department) VALUES ('Alice', 'DS');
INSERT INTO student (name, department) VALUES ('Bob', 'CS');
INSERT INTO student (name, department) VALUES ('Priya', 'IT');

-- STEP 5: Verify
SELECT * FROM student;

-- ============================================================
-- NOTE: In application.properties, update:
--   spring.datasource.password=your_mysql_password
-- ============================================================
