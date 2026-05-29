package HospitalManagementSystem.DAO;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BillingDao {
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
}
