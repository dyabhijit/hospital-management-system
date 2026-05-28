CREATE DATABASE IF NOT EXISTS hospital;
USE hospital;

CREATE TABLE IF NOT EXISTS patients (
                                        id INT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(100),
    age INT,
    gender VARCHAR(10)
    );

CREATE TABLE IF NOT EXISTS doctors (
                                       id INT AUTO_INCREMENT PRIMARY KEY,
                                       name VARCHAR(100),
    specialization VARCHAR(100)
    );

CREATE TABLE IF NOT EXISTS appointments (
                                            id INT AUTO_INCREMENT PRIMARY KEY,
                                            patient_id INT,
                                            doctor_id INT,
                                            appointment DATE,
                                            FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
    );

CREATE TABLE IF NOT EXISTS medical_records (
                                               id INT AUTO_INCREMENT PRIMARY KEY,
                                               patient_id INT NOT NULL,
                                               doctor_id INT NOT NULL,
                                               diagnosis TEXT NOT NULL,
                                               prescription TEXT NOT NULL,
                                               record_date DATE NOT NULL,
                                               FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
    );

CREATE TABLE IF NOT EXISTS bills (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     patient_id INT NOT NULL,
                                     doctor_id INT NOT NULL,
                                     treatment TEXT NOT NULL,
                                     amount DECIMAL(10,2) NOT NULL,
    payment_status VARCHAR(20) DEFAULT 'UNPAID',
    bill_date DATE NOT NULL,
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
    );

CREATE TABLE IF NOT EXISTS users (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL
    );

-- Default users
INSERT IGNORE INTO users (username, password, role) VALUES ('admin', 'admin123', 'ADMIN');
INSERT IGNORE INTO users (username, password, role) VALUES ('doctor', 'doctor123', 'DOCTOR');
INSERT IGNORE INTO users (username, password, role) VALUES ('receptionist', 'recep123', 'RECEPTIONIST');

-- Sample doctors
INSERT IGNORE INTO doctors (id, name, specialization) VALUES (1, 'Dr. Smith', 'Cardiologist');
INSERT IGNORE INTO doctors (id, name, specialization) VALUES (2, 'Dr. Priya', 'Dermatologist');
INSERT IGNORE INTO doctors (id, name, specialization) VALUES (3, 'Dr. Khan', 'Neurologist');