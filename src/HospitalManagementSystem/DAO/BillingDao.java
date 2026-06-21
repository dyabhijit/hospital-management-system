package HospitalManagementSystem.DAO;

import HospitalManagementSystem.DaoInterfaces.IBillingDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class BillingDao implements IBillingDao {
    private Connection connection;

    public BillingDao(Connection connection) {
        this.connection = connection;
    }

    public int generateBillDao(int patientId, int doctorId, String treatment, double amount, String billDate) {
        String query = "INSERT INTO bills(patient_id, doctor_id, treatment, amount, payment_status, bill_date) VALUES (?, ?, ?, ?, 'UNPAID', ?)";
        try(PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, patientId);
            ps.setInt(2, doctorId);
            ps.setString(3, treatment);
            ps.setDouble(4, amount);
            ps.setString(5, billDate);
            int rows = ps.executeUpdate();
            return rows;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void viewBillByIdDao(int patientId) {
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
    public int updatePaymentStatusDao(String status,int billId){
        String query = "UPDATE bills SET payment_status = ? WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setString(1, status);
            ps.setInt(2, billId);

            int rows = ps.executeUpdate();
            return rows;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public void viewAllUnpaidBillsDao(){
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
