package HospitalManagementSystem.Service;

import HospitalManagementSystem.DAO.PatientDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {
    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection,Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
        this.patientDao = new PatientDao(connection);
    }

    private PatientDao patientDao;

    public void addPatient(){
        System.out.print("Enter Patient Name: ");
        String name = scanner.next();
        System.out.print("Enter Patient Age: ");

        int age;
        do {
            while (!scanner.hasNextInt()) {
                System.out.print("Please enter a valid number: ");
                scanner.next();
            }
            age = scanner.nextInt();
            if (age <= 0 || age > 150) System.out.println("Please enter a realistic age.");
        } while (age <= 0 || age > 150);

        System.out.print("Enter Patient Gender: ");
        String gender = scanner.next();

        int affectedRows = patientDao.addPatientDao(name,age,gender);

        if(affectedRows > 0){
            System.out.println("Patient added successfully");
        }else{
            System.out.println("Failed to add patient");
        }
    }


    public void viewPatients() {
        patientDao.viewPatientsDao();
    }

    public boolean getPatientById(int id){
        return patientDao.getPatientByIdDao(id);
    }
 }
