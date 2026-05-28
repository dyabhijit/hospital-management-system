package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class Billing {
    private Connection connection;
    private Scanner scanner;

    public Billing(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void generateBill() {
        System.out.print("Enter Patient ID: ");
        while (!scanner.hasNextInt()) { scanner.next(); }
        int patientId = scanner.nextInt();

        System.out.print("Enter Doctor ID: ");
        while (!scanner.hasNextInt()) { scanner.next(); }
        int doctorId = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Enter Treatment Description: ");
        String treatment = scanner.nextLine();

        System.out.print("Enter Amount: ");
        while (!scanner.hasNextDouble()) { scanner.next(); }
        double amount = scanner.nextDouble();

        System.out.print("Enter Bill Date (YYYY-MM-DD): ");
        String billDate = scanner.next();

        String query = "INSERT INTO bills(patient_id, doctor_id, treatment, amount, payment_status, bill_date) VALUES (?, ?, ?, ?, 'UNPAID', ?)";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, patientId);
            ps.setInt(2, doctorId);
            ps.setString(3, treatment);
            ps.setDouble(4, amount);
            ps.setString(5, billDate);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Bill generated successfully.");
                viewBillById(patientId);
            } else {
                System.out.println("Failed to generate bill.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewBillById(int patientId) {
        String query = "SELECT b.id, p.name AS patient_name, d.name AS doctor_name, " +
                "b.treatment, b.amount, b.payment_status, b.bill_date " +
                "FROM bills b " +
                "JOIN patients p ON b.patient_id = p.id " +
                "JOIN doctors d ON b.doctor_id = d.id " +
                "WHERE b.patient_id = ? ORDER BY b.bill_date DESC";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, patientId);
            try (ResultSet rs = ps.executeQuery()) {
                System.out.println("\n+-----+----------------+----------------+--------------------+----------+---------+------------+");
                System.out.println("| ID  |  Patient Name  |  Doctor Name   |     Treatment      |  Amount  | Status  |    Date    |");
                System.out.println("+-----+----------------+----------------+--------------------+----------+---------+------------+");
                while (rs.next()) {
                    System.out.printf("| %-3d | %-14s | %-14s | %-18s | %-8.2f | %-7s | %-10s |\n",
                            rs.getInt("id"),
                            rs.getString("patient_name"),
                            rs.getString("doctor_name"),
                            rs.getString("treatment"),
                            rs.getDouble("amount"),
                            rs.getString("payment_status"),
                            rs.getString("bill_date"));
                    System.out.println("+-----+----------------+----------------+--------------------+----------+---------+------------+");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePaymentStatus() {
        System.out.print("Enter Bill ID to update: ");
        while (!scanner.hasNextInt()) { scanner.next(); }
        int billId = scanner.nextInt();

        System.out.println("Enter Payment Status:");
        System.out.println("1. PAID");
        System.out.println("2. UNPAID");
        System.out.println("3. PENDING");
        while (!scanner.hasNextInt()) { scanner.next(); }
        int choice = scanner.nextInt();

        String status;
        switch (choice) {
            case 1: status = "PAID";    break;
            case 2: status = "UNPAID";  break;
            case 3: status = "PENDING"; break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        String query = "UPDATE bills SET payment_status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setInt(2, billId);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                System.out.println("Payment status updated to " + status);
            } else {
                System.out.println("No bill found with that ID.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void viewAllUnpaidBills() {
        String query = "SELECT b.id, p.name AS patient_name, d.name AS doctor_name, " +
                "b.treatment, b.amount, b.bill_date " +
                "FROM bills b " +
                "JOIN patients p ON b.patient_id = p.id " +
                "JOIN doctors d ON b.doctor_id = d.id " +
                "WHERE b.payment_status = 'UNPAID' ORDER BY b.bill_date DESC";

        try (PreparedStatement ps = connection.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            System.out.println("\nUnpaid Bills");
            System.out.println("+-----+----------------+----------------+--------------------+----------+------------+");
            System.out.println("| ID  |  Patient Name  |  Doctor Name   |     Treatment      |  Amount  |    Date    |");
            System.out.println("+-----+----------------+----------------+--------------------+----------+------------+");
            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("| %-3d | %-14s | %-14s | %-18s | %-8.2f | %-10s |\n",
                        rs.getInt("id"),
                        rs.getString("patient_name"),
                        rs.getString("doctor_name"),
                        rs.getString("treatment"),
                        rs.getDouble("amount"),
                        rs.getString("bill_date"));
                System.out.println("+-----+----------------+----------------+--------------------+----------+------------+");
            }
            if (!found) System.out.println("No unpaid bills found.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}