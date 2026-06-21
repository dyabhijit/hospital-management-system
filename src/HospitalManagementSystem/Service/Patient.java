package HospitalManagementSystem.Service;

import HospitalManagementSystem.DAO.PatientDao;
import HospitalManagementSystem.DaoInterfaces.IPatientDao;
import HospitalManagementSystem.ServiceInterfaces.IPatient;
import HospitalManagementSystem.Validation.PatientValidation;

import java.sql.Connection;
import java.util.Scanner;

public class Patient implements IPatient {
    private Connection connection;
    private Scanner scanner;
    private PatientValidation patientValidation;

    public Patient(Connection connection,Scanner scanner){
        this.connection = connection;
        this.scanner = scanner;
        this.patientDao = new PatientDao(connection);
        this.patientValidation = new PatientValidation(scanner);
    }

    private IPatientDao patientDao;

    public void addPatient(){
        scanner.nextLine();
        String name = patientValidation.checkName();
        int age = patientValidation.checkAge();
        String gender = patientValidation.checkGender();


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
