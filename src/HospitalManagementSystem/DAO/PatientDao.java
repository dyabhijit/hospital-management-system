package HospitalManagementSystem.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDao {
    private Connection connection;

    public PatientDao(Connection connection){
        this.connection = connection;
    }

    public int addPatientDao(String name,int age,String gender){
        String query = "insert into patients(name, age, gender) values (?,?,?)";
        try{
            PreparedStatement ps = connection.prepareStatement(query);
            int i=0;
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            int affectedRows = ps.executeUpdate();

            return affectedRows;



        }catch (SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
    public void viewPatientsDao(){
        try {
            String query = "select * from patients";
            try (PreparedStatement ps = connection.prepareStatement(query);
                 ResultSet rs = ps.executeQuery()) {
                System.out.println("\nPatients");
                System.out.println("+------------+--------------------+-----+---------+");
                System.out.println("| Patient ID |        Name        | Age |  Gender |");
                System.out.println("+------------+--------------------+-----+---------+");
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    int age = rs.getInt("age");
                    String gender = rs.getString("gender");
                    System.out.printf("| %-10d | %-18s | %-3d | %-7s |\n", id, name, age, gender);
                    System.out.println("+------------+--------------------+-----+---------+");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getPatientByIdDao(int id){
        String query = "Select * from patients where id = ?";
        try{
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
