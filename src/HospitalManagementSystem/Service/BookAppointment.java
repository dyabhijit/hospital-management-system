package HospitalManagementSystem.Service;

import HospitalManagementSystem.DAO.BookAppointmentDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BookAppointment {

    private Connection connection;
    private Scanner scanner;

    public BookAppointment(Connection connection,Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
        this.bookAppointmentDao = new BookAppointmentDao(connection);
    }
    private BookAppointmentDao bookAppointmentDao;

    public void bookAppointment(Patient patient, Doctor doctor){
        System.out.println("Enter Patient Id");
        while (!scanner.hasNextInt()) { scanner.next(); }
        int patient_id = scanner.nextInt();

        System.out.println("Enter Doctor Id");
        while (!scanner.hasNextInt()) { scanner.next(); }
        int doctor_id = scanner.nextInt();

        System.out.println("Enter Appointment date (YYYY-MM-DD)");
        String appointmentDate  = scanner.next();
        if(patient.getPatientById(patient_id) && doctor.getDoctorById(doctor_id)){
            if(checkDoctorAvailability(doctor_id, appointmentDate)){
                int affectedRow = bookAppointmentDao.bookAppointmentDao(patient_id,doctor_id,appointmentDate);
                if(affectedRow>0){
                    System.out.println("Appointment Booked");
                }else{
                    System.out.println("Failed to Book Appointment");
                }
            }else{
                System.out.println("Doctor not available on this date!!");
            }

        }else{
            System.out.println("Either doctor and patient doesn't exit");
        }
    }

    public boolean checkDoctorAvailability(int doctor_id,String appointmentDate){
        return bookAppointmentDao.checkDoctorAvailabilityDao(doctor_id,appointmentDate);
    }
}
