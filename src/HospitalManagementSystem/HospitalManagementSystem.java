package HospitalManagementSystem;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

public class HospitalManagementSystem {

    private static final Properties config = loadConfig();

    private static Properties loadConfig() {
        Properties props = new Properties();
        try (InputStream input = HospitalManagementSystem.class
                .getClassLoader().getResourceAsStream("config.properties")) {
            if (input == null) throw new RuntimeException("config.properties not found");
            props.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config", e);
        }
        return props;
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Scanner scanner = new Scanner(System.in);

        try (Connection connection = DriverManager.getConnection(
                config.getProperty("db.url"),
                config.getProperty("db.username"),
                config.getProperty("db.password")
        )) {
            Patient patient = new Patient(connection, scanner);
            Doctor doctor = new Doctor(connection);
            MedicalRecord medicalRecord = new MedicalRecord(connection, scanner);

            while (true) {
                System.out.println("Hospital Management System");
                System.out.println("1. Add Patients");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointments");
                System.out.println("5. Exit");
                System.out.println("6. Add Medical Record");
                System.out.println("7. View Patient Medical History");
                System.out.println("8. Delete Medical Record");
                System.out.print("Enter Your Choice: ");
                int choice = getValidIntInput(scanner);

                switch (choice) {
                    case 1: patient.addPatient();   System.out.println(); break;
                    case 2: patient.viewPatients(); System.out.println(); break;
                    case 3: doctor.viewDoctors();   System.out.println(); break;
                    case 4: BookAppointment.bookAppointment(patient, doctor, connection, scanner);
                        System.out.println(); break;
                    case 5:
                        System.out.println("THANK YOU FOR USING HOSPITAL MANAGEMENT SYSTEM");
                        return;
                    case 6:
                        medicalRecord.addMedicalRecord();
                        System.out.println();
                        break;
                    case 7:
                        medicalRecord.viewRecordsByPatient();
                        System.out.println();
                        break;
                    case 8:
                        medicalRecord.deleteRecord();
                        System.out.println();
                        break;
                    default:
                        System.out.println("Enter Valid Choice");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private static int getValidIntInput(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.next(); // discard the bad input
        }
        return scanner.nextInt();
    }
}