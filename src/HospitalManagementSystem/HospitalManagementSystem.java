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
            Auth auth = new Auth(connection, scanner);
            String[] userInfo = auth.login();

            if (userInfo == null) {
                System.out.println("Exiting system.");
                return;
            }

            String role = userInfo[1];
            MenuManager menuManager = new MenuManager(role, connection, scanner);
            menuManager.showMenu();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}