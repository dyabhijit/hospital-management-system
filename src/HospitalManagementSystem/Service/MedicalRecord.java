package HospitalManagementSystem.Service;

import java.sql.*;
import java.util.Scanner;

public class MedicalRecord {
    private Connection connection;
    private Scanner scanner;

    public MedicalRecord(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

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

        String query = "INSERT INTO medical_records(patient_id, doctor_id, diagnosis, prescription, record_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, patientId);
            ps.setInt(2, doctorId);
            ps.setString(3, diagnosis);
            ps.setString(4, prescription);
            ps.setString(5, recordDate);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Medical record added successfully.");
            } else {
                System.out.println("Failed to add medical record.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewRecordsByPatient() {
        System.out.print("Enter Patient ID: ");
        while (!scanner.hasNextInt()) { scanner.next(); }
        int patientId = scanner.nextInt();

        String query = "SELECT mr.id, p.name AS patient_name, d.name AS doctor_name, " +
                "mr.diagnosis, mr.prescription, mr.record_date " +
                "FROM medical_records mr " +
                "JOIN patients p ON mr.patient_id = p.id " +
                "JOIN doctors d ON mr.doctor_id = d.id " +
                "WHERE mr.patient_id = ? " +
                "ORDER BY mr.record_date DESC";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;
                System.out.println("\n+-----+----------------+----------------+----------------------+----------------------+------------+");
                System.out.println("| ID  |  Patient Name  |  Doctor Name   |      Diagnosis       |     Prescription     |    Date    |");
                System.out.println("+-----+----------------+----------------+----------------------+----------------------+------------+");
                while (rs.next()) {
                    found = true;
                    System.out.printf("| %-3d | %-14s | %-14s | %-20s | %-20s | %-10s |\n",
                            rs.getInt("id"),
                            rs.getString("patient_name"),
                            rs.getString("doctor_name"),
                            rs.getString("diagnosis"),
                            rs.getString("prescription"),
                            rs.getString("record_date"));
                    System.out.println("+-----+----------------+----------------+----------------------+----------------------+------------+");
                }
                if (!found) {
                    System.out.println("No medical records found for this patient.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteRecord() {
        System.out.print("Enter Medical Record ID to delete: ");
        while (!scanner.hasNextInt()) { scanner.next(); }
        int recordId = scanner.nextInt();

        String query = "DELETE FROM medical_records WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, recordId);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Record deleted successfully.");
            } else {
                System.out.println("No record found with that ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}