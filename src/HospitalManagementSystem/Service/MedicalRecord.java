package HospitalManagementSystem.Service;

import HospitalManagementSystem.DAO.MedicalRecordDao;

import java.sql.*;
import java.util.Scanner;

public class MedicalRecord {
    private Connection connection;
    private Scanner scanner;

    public MedicalRecord(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
        this.medicalRecordDao = new MedicalRecordDao(connection);
    }

    private MedicalRecordDao medicalRecordDao;

    public void addMedicalRecord() {
        System.out.print("Enter Patient ID: ");
        while (!scanner.hasNextInt()) { scanner.next(); }
        int patientId = scanner.nextInt();

        System.out.print("Enter Doctor ID: ");
        while (!scanner.hasNextInt()) { scanner.next(); }
        int doctorId = scanner.nextInt();

        scanner.nextLine(); // consume leftover newline

        System.out.print("Enter Diagnosis: ");
        String diagnosis = scanner.nextLine();

        System.out.print("Enter Prescription: ");
        String prescription = scanner.nextLine();

        System.out.print("Enter Record Date (YYYY-MM-DD): ");
        String recordDate = scanner.next();

        medicalRecordDao.addMedicalRecordDao(patientId,doctorId,diagnosis,prescription,recordDate);
    }

    public void viewRecordsByPatient() {
        System.out.print("Enter Patient ID: ");
        while (!scanner.hasNextInt()) { scanner.next(); }
        int patientId = scanner.nextInt();

        medicalRecordDao.viewRecordsByPatientDao(patientId);
    }

    public void deleteRecord() {
        System.out.print("Enter Medical Record ID to delete: ");
        while (!scanner.hasNextInt()) { scanner.next(); }
        int recordId = scanner.nextInt();

        int rows =  medicalRecordDao.deleteRecordDao(recordId);
        if (rows > 0) {
            System.out.println("Record deleted successfully.");
        } else {
            System.out.println("No record found with that ID.");
        }
    }
}