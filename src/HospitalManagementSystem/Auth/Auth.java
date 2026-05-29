package HospitalManagementSystem.Auth;

import java.sql.*;
import java.util.Scanner;

public class Auth {
    private Connection connection;
    private Scanner scanner;

    public Auth(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public String[] login() {
        System.out.println("=============================");
        System.out.println("   Hospital Management System");
        System.out.println("=============================");

        int attempts = 3;
        while (attempts > 0) {
            System.out.print("Username: ");
            String username = scanner.next();
            System.out.print("Password: ");
            String password = scanner.next();

            String query = "SELECT * FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, username);
                ps.setString(2, password);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        String role = rs.getString("role");
                        System.out.println("\nLogin successful! Welcome " + username + " (" + role + ")\n");
                        return new String[]{username, role};
                    } else {
                        attempts--;
                        System.out.println("Invalid credentials. " + attempts + " attempts remaining.\n");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Too many failed attempts. Exiting.");
        return null;
    }
}