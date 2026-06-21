package HospitalManagementSystem.Validation;

import java.util.Scanner;

public class PatientValidation {
    private Scanner scanner;

    public PatientValidation(Scanner scanner){
        this.scanner = scanner;
    }

    public String checkName(){
        String name;
        do {
            System.out.print("Enter Patient Name (First Name and Surname): ");
            name = scanner.nextLine().trim();

            if (!name.matches("[A-Za-z]+\\s+[A-Za-z]+.*")) {
                System.out.println("Please enter a valid full name with surname.");
            }

        } while (!name.matches("[A-Za-z]+\\s+[A-Za-z]+.*"));
        return name;
    }

    public int checkAge(){
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
        return age;
    }

    public String checkGender(){
        String gender;
        boolean validGender;

        do {
            System.out.print("Enter Patient Gender (Male/Female/Other): ");
            gender = scanner.next();

            validGender = gender.equalsIgnoreCase("Male")
                    || gender.equalsIgnoreCase("Female")
                    || gender.equalsIgnoreCase("Other");

            if (!validGender) {
                System.out.println("Please enter Male, Female or Other.");
            }

        } while (!validGender);

        return gender;
    }
}
