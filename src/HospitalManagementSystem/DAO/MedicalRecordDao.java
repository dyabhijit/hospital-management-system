package HospitalManagementSystem.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MedicalRecordDao {
    private Connection connection;
    public MedicalRecordDao(Connection connection){
        this.connection = connection;
    }

    public void addMedicalRecordDao(int patientId,int doctorId,String diagnosis,String prescription,String recordDate){
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

    public void viewRecordsByPatientDao(int patientId){
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

    public int deleteRecordDao(int recordId){
        String query = "DELETE FROM medical_records WHERE id = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, recordId);
            int rows = ps.executeUpdate();
            return rows;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
