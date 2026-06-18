package HospitalManagementSystem.Service;

import HospitalManagementSystem.DAO.BillingDao;
import HospitalManagementSystem.DaoInterfaces.IBillingDao;
import HospitalManagementSystem.ServiceInterfaces.IBilling;

import java.sql.*;
import java.util.Scanner;

public class Billing implements IBilling {
    private Connection connection;
    private Scanner scanner;


    public Billing(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
        this.billingDao = new BillingDao(connection);
    }

    private IBillingDao billingDao;

    public void generateBill() {
        System.out.print("Enter Patient ID: ");
        while (!scanner.hasNextInt()) { scanner.next(); }
        int patientId = scanner.nextInt();

        System.out.print("Enter Doctor ID: ");
        while (!scanner.hasNextInt()) { scanner.next(); }
        int doctorId = scanner.nextInt();

        scanner.nextLine();

        System.out.print("Enter Treatment Description: ");
        String treatment = scanner.nextLine();

        System.out.print("Enter Amount: ");
        while (!scanner.hasNextDouble()) { scanner.next(); }
        double amount = scanner.nextDouble();

        System.out.print("Enter Bill Date (YYYY-MM-DD): ");
        String billDate = scanner.next();

        int rows = billingDao.generateBillDao(patientId,doctorId,treatment,amount,billDate);
            if (rows > 0) {
                System.out.println("Bill generated successfully.");
                viewBillById(patientId);
            } else {
                System.out.println("Failed to generate bill.");
            }
    }

    public void viewBillById(int patientId){
        billingDao.viewBillByIdDao(patientId);
    }

    public void updatePaymentStatus() {
        System.out.print("Enter Bill ID to update: ");
        while (!scanner.hasNextInt()) { scanner.next(); }
        int billId = scanner.nextInt();

        System.out.println("Enter Payment Status:");
        System.out.println("1. PAID");
        System.out.println("2. UNPAID");
        System.out.println("3. PENDING");
        while (!scanner.hasNextInt()) { scanner.next(); }
        int choice = scanner.nextInt();

        String status;
        switch (choice) {
            case 1: status = "PAID";    break;
            case 2: status = "UNPAID";  break;
            case 3: status = "PENDING"; break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        int rows = billingDao.updatePaymentStatusDao(status,billId);
        if (rows > 0) {
            System.out.println("Payment status updated to " + status);
        } else {
            System.out.println("No bill found with that ID.");
        }
    }

    public void viewAllUnpaidBills() {
        billingDao.viewAllUnpaidBillsDao();
    }
}