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
        System.out.println("4. EXIT");
        System.out.print("\nEnter your choice (1-4): ");
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
            case 4:
                System.out.println("Exit");
                break;
            default:
                System.out.println("Enter correct choice..!!");
        }
    }

    /*********************************************STUDENT MANAGEMENT SYSTEM************************************************/
    private static void handleStudentManagement() {
        System.out.println("\nWELCOME TO STUDENT MANAGEMENT SYSTEM\n");
        System.out.println("A. NEW ADMISSION");
        System.out.println("B. UPDATE STUDENT DETAILS");
        System.out.println("C. ISSUE TC");
        System.out.println("D. Back to Previous");
        System.out.print("Enter your choice (a-d): ");
        String choice = scanner.next().toLowerCase();

        System.out.println("Initial Details-");
        int result = displayStudents();

        switch (choice) {
            case "a":
                insertStudent();
                System.out.println("\nModified details -");
                displayStudents();
                break;
            case "b":
                if (result==1)
                    break;
                updateStudent();
                System.out.println("\nModified details -");
                displayStudents();
                break;
            case "c":
                deleteStudent();
                System.out.println("\nModified details -");
                displayStudents();
                break;
            case "d":
                System.out.println("Going Back.....");
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

            if(checkDuplicate(admno)){
                System.out.println("Admission no : "+admno+" already exist!!");
                return;
            }

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

    private static int displayStudents() {
        if (!checkEmpty()){
            return 1;
        }
        System.out.println("Details are :");
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management", "root", "kunal@333");
            Statement stmt = conn.createStatement()) {      //to execute sql queries
            String sql = "SELECT * FROM student";
            ResultSet rs = stmt.executeQuery(sql);

            while (rs.next()) {
                //getting values from each attribute present in student table
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
        return 0;
    }

    private static void updateStudent() {

        //check if table is empty
        if (!checkEmpty()){
            System.out.println("Can't Modify! Students are not present");
            return;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management", "root", "kunal@333");
             Statement stmt = conn.createStatement()) {

            System.out.println();
            System.out.print("Enter Admission No: ");
            int tempst = scanner.nextInt();
            if (!checkDuplicate(tempst)){
                System.out.println("Admission no : "+tempst+" does not exist!!");
                return;
            }
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
            if (!checkDuplicate(temp)){
                System.out.println("Admission no : "+temp+" does not exist!!");
                return;
            }
            System.out.print("Are you sure you want to delete the record (y/n): ");
            String ans = scanner.next();

            if (ans.equalsIgnoreCase("y")) {
                String sql = String.format("DELETE FROM student WHERE admno=%d", temp);
                stmt.executeUpdate(sql);
                System.out.println("admission no : "+temp+ " deleted successfully!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /************************************ FEE MANAGEMENT SYSTEM **********************************************/
    private static void handleFeeManagement() {
        System.out.println("WELCOME TO FEE MANAGEMENT SYSTEM");
        System.out.println("A. NEW FEE");
        System.out.println("B. BACK");
        System.out.print("Enter your choice: ");
        String choice = scanner.next().toLowerCase();

        switch (choice) {
            case "a":
                insertFee();
                break;
            case "b":
                System.out.println("Going Back..................");
                break;
            default:
                System.out.println("Enter correct choice...!!");
        }
    }

    private static void insertFee() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management", "root", "kunal@333");
             Statement stmt = conn.createStatement()) {

            System.out.print("Enter adm no: ");
            int admno = scanner.nextInt();
            if (!checkDuplicate(admno)){
                System.out.println("Admission no : "+admno+" does not exist!!");
                return;
            }
            System.out.print("Enter fee amount: ");
            float fee = scanner.nextFloat();
            System.out.print("Enter Month: ");
            String month = scanner.next();

            String sql = String.format("INSERT INTO fee(admno, fee, month) VALUES (%d, %f, '%s')",
                    admno, fee, month);
            stmt.executeUpdate(sql);
            System.out.println("Fee of adm no : "+admno+ " for month : "+month+ " added successfully!!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /***************************************** EXAM MANAGEMENT SYSTEM ***********************************************/
    private static void handleExamManagement() {
        System.out.println("WELCOME TO EXAM MANAGEMENT SYSTEM");
        System.out.println("A. EXAM DETAILS");
        System.out.println("B. DELETE DETAILS");
        System.out.println("C. BACK");
        System.out.print("Enter your choice: ");
        String choice = scanner.next().toLowerCase();

        switch (choice) {
            case "a":
                insertExam();
                break;
            case "b":
                deleteExam();
                break;
            case "c":
                System.out.println("Going Back........................");
                break;
            default:
                System.out.println("Enter correct choice...!!");
        }
    }

    private static void insertExam() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management", "root", "kunal@333");
             Statement stmt = conn.createStatement()) {

            System.out.print("Enter Admission No: ");
            int admno = scanner.nextInt();
            if (!checkDuplicate(admno)){
                System.out.println("Admission no : "+admno+" does not exist!!");
                return;
            }
            System.out.print("Enter Student Name: ");
            String sname = scanner.next();
            System.out.print("Enter percentage: ");
            float per = scanner.nextFloat();
            System.out.print("Enter result(pass/fail) : ");
            String res = scanner.next();

            String sql = String.format("INSERT INTO exam(sname, admno, per, res) VALUES ('%s', %d, %f, '%s')",
                    sname, admno, per, res);
            stmt.executeUpdate(sql);

            System.out.println("Exam Details Inserted Successfully!!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void deleteExam() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management", "root", "kunal@333");
             Statement stmt = conn.createStatement()) {

            System.out.println();
            System.out.print("Enter adm no to be deleted: ");
            int admno = scanner.nextInt();
            if (!checkDuplicate(admno)){
                System.out.println("Admission no : "+admno+" does not exist!!");
                return;
            }
            System.out.print("Are you sure you want to delete the record (y/n): ");
            String ans = scanner.next();

            if (ans.equalsIgnoreCase("y")) {
                String sql = String.format("DELETE FROM exam WHERE admno=%d", admno);
                stmt.executeUpdate(sql);
                System.out.println("Details of Admission no : "+admno+" are deleted!!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /******************************************** OTHER UTILITY METHODS ******************************************/
    //method to check if table is empty
    private static boolean checkEmpty() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management", "root", "kunal@333");
             Statement stmt = conn.createStatement()) {
            //it can throw SQL exception
            String sql = "SELECT COUNT(*) AS count FROM student";       //gives no of rows present in student table
            ResultSet rs = stmt.executeQuery(sql);              //sql query executed using following, stores resultSet object

            if (rs.next()) {
                int count = rs.getInt("count");     //rs.getInt retrieves integer value from current row
                if (count == 0) {
                    System.out.println("Initially No New Admissions!!");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();        //shows the details of an exception
        }
        return true;
    }

    //Method for if adm no is aleady exist or not exist
    private static boolean checkDuplicate(int admno) {

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/student_management", "root", "kunal@333");
             Statement stmt = conn.createStatement()) {
            String insertSql = "SELECT * FROM student";
            ResultSet insertRs = stmt.executeQuery(insertSql);

            while (insertRs.next()) {
                //getting value from admno to check duplicate
                int checkAdmno = insertRs.getInt("admno");
                if(admno == checkAdmno) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}



