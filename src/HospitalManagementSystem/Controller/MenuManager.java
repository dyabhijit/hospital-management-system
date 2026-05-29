package HospitalManagementSystem.Controller;

import HospitalManagementSystem.Service.*;
import java.sql.Connection;
import java.util.Scanner;

public class MenuManager {
    private String role;
    private Patient patient;
    private Doctor doctor;
    private BookAppointment bookAppointment;
    private MedicalRecord medicalRecord;
    private Billing billing;
    private Scanner scanner;
    private Connection connection;

    public MenuManager(String role, Connection connection, Scanner scanner) {
        this.role = role;
        this.connection = connection;
        this.scanner = scanner;
        this.patient = new Patient(connection, scanner);
        this.doctor = new Doctor(connection);
        this.medicalRecord = new MedicalRecord(connection, scanner);
        this.billing = new Billing(connection, scanner);
    }

    public void showMenu() {
        while (true) {
            System.out.println("\n=============================");
            System.out.println(" Logged in as: " + role);
            System.out.println("=============================");

            if (role.equals("ADMIN")) {
                showAdminMenu();
            } else if (role.equals("DOCTOR")) {
                showDoctorMenu();
            } else if (role.equals("RECEPTIONIST")) {
                showReceptionistMenu();
            }
        }
    }

    private void showAdminMenu() {
        System.out.println("1.  Add Patient");
        System.out.println("2.  View Patients");
        System.out.println("3.  View Doctors");
        System.out.println("4.  Book Appointment");
        System.out.println("5.  Add Medical Record");
        System.out.println("6.  View Medical Records");
        System.out.println("7.  Delete Medical Record");
        System.out.println("8.  Generate Bill");
        System.out.println("9.  View Patient Bills");
        System.out.println("10. Update Payment Status");
        System.out.println("11. View All Unpaid Bills");
        System.out.println("12. Logout");
        System.out.print("Enter Your Choice: ");

        while (!scanner.hasNextInt()) { scanner.next(); }
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:  patient.addPatient();                                    break;
            case 2:  patient.viewPatients();                                  break;
            case 3:  doctor.viewDoctors();                                    break;
            case 4:  BookAppointment.bookAppointment(patient, doctor, connection, scanner); break;
            case 5:  medicalRecord.addMedicalRecord();                        break;
            case 6:  medicalRecord.viewRecordsByPatient();                    break;
            case 7:  medicalRecord.deleteRecord();                            break;
            case 8:  billing.generateBill();                                  break;
            case 9:
                System.out.print("Enter Patient ID: ");
                while (!scanner.hasNextInt()) { scanner.next(); }
                billing.viewBillById(scanner.nextInt());
                break;
            case 10: billing.updatePaymentStatus();                           break;
            case 11: billing.viewAllUnpaidBills();                            break;
            case 12:
                System.out.println("Logged out successfully.");
                System.exit(0);
            default: System.out.println("Invalid choice.");
        }
    }

    private void showDoctorMenu() {
        System.out.println("1. View Patients");
        System.out.println("2. View Doctors");
        System.out.println("3. Add Medical Record");
        System.out.println("4. View Medical Records");
        System.out.println("5. Logout");
        System.out.print("Enter Your Choice: ");

        while (!scanner.hasNextInt()) { scanner.next(); }
        int choice = scanner.nextInt();

        switch (choice) {
            case 1: patient.viewPatients();               break;
            case 2: doctor.viewDoctors();                 break;
            case 3: medicalRecord.addMedicalRecord();     break;
            case 4: medicalRecord.viewRecordsByPatient(); break;
            case 5:
                System.out.println("Logged out successfully.");
                System.exit(0);
            default: System.out.println("Invalid choice.");
        }
    }

    private void showReceptionistMenu() {
        System.out.println("1. Add Patient");
        System.out.println("2. View Patients");
        System.out.println("3. View Doctors");
        System.out.println("4. Book Appointment");
        System.out.println("5. Generate Bill");
        System.out.println("6. View Patient Bills");
        System.out.println("7. Logout");
        System.out.print("Enter Your Choice: ");

        while (!scanner.hasNextInt()) { scanner.next(); }
        int choice = scanner.nextInt();

        switch (choice) {
            case 1: patient.addPatient();   break;
            case 2: patient.viewPatients(); break;
            case 3: doctor.viewDoctors();   break;
            case 4: BookAppointment.bookAppointment(patient, doctor, connection, scanner); break;
            case 5: billing.generateBill(); break;
            case 6:
                System.out.print("Enter Patient ID: ");
                while (!scanner.hasNextInt()) { scanner.next(); }
                billing.viewBillById(scanner.nextInt());
                break;
            case 7:
                System.out.println("Logged out successfully.");
                System.exit(0);
            default: System.out.println("Invalid choice.");
        }
    }
}