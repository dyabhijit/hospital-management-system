# Hospital Management System

A console-based Hospital Management System built with Java and MySQL using JDBC.

## Features
- Add and view patients
- View doctors and specializations
- Book appointments with availability check
- Add and view medical records
- Delete medical records

## Tech Stack
- Java
- MySQL
- JDBC
- IntelliJ IDEA

## Database Setup
1. Create a database named `hospital` in MySQL
2. Run the following tables:

```sql
CREATE TABLE patients (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    age INT,
    gender VARCHAR(10)
);

CREATE TABLE doctors (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    specialization VARCHAR(100)
);

CREATE TABLE appointments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    doctor_id INT,
    appointment DATE,
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);

CREATE TABLE medical_records (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT,
    doctor_id INT,
    diagnosis TEXT,
    prescription TEXT,
    record_date DATE,
    FOREIGN KEY (patient_id) REFERENCES patients(id),
    FOREIGN KEY (doctor_id) REFERENCES doctors(id)
);
```

## Configuration
1. Copy `config.properties.example` to `config.properties` inside the `src` folder
2. Fill in your MySQL credentials
3. Run `HospitalManagementSystem.java`