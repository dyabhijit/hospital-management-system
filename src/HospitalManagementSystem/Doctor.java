package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Doctor {
        private Connection connection;

    public Doctor(Connection connection){
        this.connection = connection;
    }
    public void viewDoctors() {
        try {
            String query = "select * from doctors";
            try (PreparedStatement ps = connection.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                System.out.println("\nDoctors");
                System.out.println("+-----------+--------------------+-------------------------+");
                System.out.println("| Doctor ID |        Name        |      Specialization     |");
                System.out.println("+-----------+--------------------+-------------------------+");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    String specialization = rs.getString("specialization");
                    System.out.printf("| %-9d | %-18s | %-23s |\n", id, name, specialization);
                    System.out.println("+-----------+--------------------+-------------------------+");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
        public boolean getDoctorById(int id){
        try{
            String query = "Select * from doctors where id = ?";
            PreparedStatement ps = connection.prepareStatement(query);
            int i = 0;
            ps.setInt(++i, id);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                return true;
            }else{
                return false;
            }
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
