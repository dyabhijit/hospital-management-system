package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BookAppointment {
    public static void bookAppointment(Patient patient,Doctor doctor,Connection connection, Scanner scanner){
        System.out.println("Enter Patient Id");
        while (!scanner.hasNextInt()) { scanner.next(); }
        int patient_id = scanner.nextInt();

        System.out.println("Enter Doctor Id");
        while (!scanner.hasNextInt()) { scanner.next(); }
        int doctor_id = scanner.nextInt();

        System.out.println("Enter Appointment date (YYYY-MM-DD)");
        String appointmentDate  = scanner.next();
        if(patient.getPatientById(patient_id) && doctor.getDoctorById(doctor_id)){
            if(checkDoctorAvailability(doctor_id, appointmentDate, connection)){
                String appointmentQuery = "insert into appointments(patient_id,doctor_id,appointment) values (?,?,?)";
                try {
                    PreparedStatement ps = connection.prepareStatement(appointmentQuery);
                    int i = 0;
                    ps.setInt(1, patient_id);
                    ps.setInt(2, doctor_id);
                    ps.setString(3, appointmentDate);
                    int affectedRow = ps.executeUpdate();
                    if(affectedRow>0){
                        System.out.println("Appointment Booked");
                    }else{
                        System.out.println("Failed to Book Appointment");
                    }
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }else{
                System.out.println("Doctor not available on this date!!");
            }

        }else{
            System.out.println("Either doctor and patient doesn't exit");
        }
    }

    public static boolean checkDoctorAvailability(int doctor_id,String appointmentDate, Connection connection){
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
