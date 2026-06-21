package HospitalManagementSystem.DAO;

import HospitalManagementSystem.DaoInterfaces.IDoctorDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DoctorDao implements IDoctorDao {
    private Connection connection;

    public DoctorDao(Connection connection){
        this.connection = connection;
    }

    public void viewDoctorsDao(){

        String query = "select * from doctors";
        try(PreparedStatement ps = connection.prepareStatement(query)) {
            try (ResultSet rs = ps.executeQuery()) {
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

    public boolean getDoctorByIdDao(int id){
        String query = "Select * from doctors where id = ?";
        try(PreparedStatement ps = connection.prepareStatement(query)){
            int i = 0;
            ps.setInt(++i, id);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}
