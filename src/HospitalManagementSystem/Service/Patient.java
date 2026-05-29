package HospitalManagementSystem.Service;

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
    }

    public void addPatient(){
        System.out.print("Enter Patient Name: ");
        String name = scanner.next();
        System.out.print("Enter Patient Age: ");

        int age;
        do {
            System.out.print("Enter Patient Age: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                scanner.next();
            }
            age = scanner.nextInt();
            if (age <= 0 || age > 150) System.out.println("Please enter a realistic age.");
        } while (age <= 0 || age > 150);

        String gender = scanner.next();

        String query = "insert into patients(name, age, gender) values (?,?,?)";
        try{
            PreparedStatement ps = connection.prepareStatement(query);
            int i=0;
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            int affectedRows = ps.executeUpdate();

            if(affectedRows > 0){
                System.out.println("Patient added successfully");
            }else{
                System.out.println("Failed to add patient");
            }


        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public void viewPatients() {
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
    public boolean getPatientById(int id){
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
