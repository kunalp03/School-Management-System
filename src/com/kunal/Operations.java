package com.kunal;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.sql.*;

public class Operations {

    private static final Scanner scanner = new Scanner(System.in);

    int displayMenu() {
        System.out.println("1. STUDENT MANAGEMENT");
        System.out.println("2. FEE MANAGEMENT");
        System.out.println("3. EXAM MANAGEMENT");
        System.out.print("\nEnter your choice (1-3): ");
        int choice;
        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Clear the invalid input from the scanner
            choice = -1; // Assign a default value or an indicator for an invalid choice
        }

        return choice;
    }

    void handleChoice(int choice) {
        switch (choice) {
            case 1:
                handleStudentManagement();
                break;
            case 2:
                handleFeeManagement();
                break;
            case 3:
                handleExamManagement();
                break;
            default:
                System.out.println("Enter correct choice..!!");
        }
    }

    private static void handleStudentManagement() {
        System.out.println("\nWELCOME TO STUDENT MANAGEMENT SYSTEM\n");
        System.out.println("A. NEW ADMISSION");
        System.out.println("B. UPDATE STUDENT DETAILS");
        System.out.println("C. ISSUE TC");
        System.out.print("Enter your choice (a-c): ");
        String choice = scanner.next();

        System.out.println("\nInitially the details are..\n");
        displayStudents();

        switch (choice) {
            case "a":
                insertStudent();
                System.out.println("\nModified details are..\n");
                displayStudents();
                break;
            case "b":
                updateStudent();
                System.out.println("\nModified details are..\n");
                displayStudents();
                break;
            case "c":
                deleteStudent();
                System.out.println("\nModified details are..\n");
                displayStudents();
                break;
            default:
                System.out.println("Enter correct choice...!!");
        }
    }

    private static void handleFeeManagement() {
        System.out.println("WELCOME TO FEE MANAGEMENT SYSTEM");
        System.out.println("A. NEW FEE");
        System.out.print("Enter your choice: ");
        String choice = scanner.next();

        switch (choice) {
            case "a":
                insertFee();
                break;
            default:
                System.out.println("Enter correct choice...!!");
        }
    }

    private static void handleExamManagement() {
        System.out.println("WELCOME TO EXAM MANAGEMENT SYSTEM");
        System.out.println("A. EXAM DETAILS");
        System.out.println("B. DELETE DETAILS");
        System.out.print("Enter your choice: ");
        String choice = scanner.next();

        switch (choice) {
            case "a":
                insertExam();
                break;
            case "b":
                deleteExam();
                break;
            default:
                System.out.println("Enter correct choice...!!");
        }
    }

    private static void insertStudent() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management", "root", "kunal@333");
             Statement stmt = conn.createStatement()) {

            System.out.print("Enter Student's Name: ");
            String sname = scanner.next();
            System.out.print("Enter Admission No: ");
            int admno = scanner.nextInt();
            System.out.print("Enter Date of Birth (yyyy-mm-dd): ");
            String dob = scanner.next();
            System.out.print("Enter class for admission: ");
            String cls = scanner.next();
            System.out.print("Enter City: ");
            String cty = scanner.next();

            String sql = String.format("INSERT INTO student(sname, admno, dob, cls, cty) VALUES ('%s', %d, '%s', '%s', '%s')",
                    sname, admno, dob, cls, cty);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void displayStudents() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management", "root", "kunal@333");
             Statement stmt = conn.createStatement()) {

            String sql = "SELECT * FROM student";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                String sname = rs.getString("sname");
                int admno = rs.getInt("admno");
                String dob = rs.getString("dob");
                String cls = rs.getString("cls");
                String cty = rs.getString("cty");

                System.out.printf("(Name=%s, Admission no.=%d, Date of Birth=%s, class=%s, city=%s)%n", sname, admno, dob, cls, cty);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void updateStudent() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management", "root", "kunal@333");
             Statement stmt = conn.createStatement()) {

            System.out.println();
            System.out.print("Enter Admission No: ");
            int tempst = scanner.nextInt();
            System.out.print("Enter new class: ");
            String temp = scanner.next();

            String sql = String.format("UPDATE student SET cls='%s' WHERE admno=%d", temp, tempst);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteStudent() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management", "root", "kunal@333");
             Statement stmt = conn.createStatement()) {

            System.out.println();
            System.out.print("Enter admission no to be deleted: ");
            int temp = scanner.nextInt();
            System.out.print("Are you sure you want to delete the record (y/n): ");
            String ans = scanner.next();

            if (ans.equalsIgnoreCase("y")) {
                String sql = String.format("DELETE FROM student WHERE admno=%d", temp);
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertFee() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management", "root", "kunal@333");
             Statement stmt = conn.createStatement()) {

            System.out.print("Enter adm no: ");
            int admno = scanner.nextInt();
            System.out.print("Enter fee amount: ");
            float fee = scanner.nextFloat();
            System.out.print("Enter Month: ");
            String month = scanner.next();

            String sql = String.format("INSERT INTO fee(admno, fee, month) VALUES (%d, %f, '%s')",
                    admno, fee, month);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void insertExam() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management", "root", "kunal@333");
             Statement stmt = conn.createStatement()) {

            System.out.print("Enter Student Name: ");
            String sname = scanner.next();
            System.out.print("Enter Admission No: ");
            int admno = scanner.nextInt();
            System.out.print("Enter percentage: ");
            float per = scanner.nextFloat();
            System.out.print("Enter result: ");
            String res = scanner.next();

            String sql = String.format("INSERT INTO exam(sname, admno, per, res) VALUES ('%s', %d, %f, '%s')",
                    sname, admno, per, res);
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteExam() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management", "root", "kunal@333");
             Statement stmt = conn.createStatement()) {

            System.out.println();
            System.out.print("Enter adm no to be deleted: ");
            int temp = scanner.nextInt();
            System.out.print("Are you sure you want to delete the record (y/n): ");
            String ans = scanner.next();

            if (ans.equalsIgnoreCase("y")) {
                String sql = String.format("DELETE FROM exam WHERE admno=%d", temp);
                stmt.executeUpdate(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
