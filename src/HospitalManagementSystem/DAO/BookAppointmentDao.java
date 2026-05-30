package HospitalManagementSystem.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BookAppointmentDao {

    private Connection connection;

    public BookAppointmentDao(Connection connection){
        this.connection = connection;
    }

    public int bookAppointmentDao(int patient_id,int doctor_id,String appointmentDate) {
            String appointmentQuery = "insert into appointments(patient_id,doctor_id,appointment) values (?,?,?)";
            try {
                PreparedStatement ps = connection.prepareStatement(appointmentQuery);
                int i = 0;
                ps.setInt(1, patient_id);
                ps.setInt(2, doctor_id);
                ps.setString(3, appointmentDate);
                int affectedRow = ps.executeUpdate();
                return affectedRow;

            }catch(SQLException e){
                e.printStackTrace();
            }
            return 0;
        }

        public boolean checkDoctorAvailabilityDao(int doctor_id,String appointmentDate){
            String query = "select count(*) from appointments where doctor_id = ? and appointment = ?";
            try{
                PreparedStatement ps = connection.prepareStatement(query);
                int i = 0;
                ps.setInt(1, doctor_id);
                ps.setString(2, appointmentDate);
                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    int count = rs.getInt(1);
                    if(count==0){
                        return true;
                    }else{
                        return false;
                    }
                }

            }catch (SQLException e){
                e.printStackTrace();
            }
            return false;
        }

}
