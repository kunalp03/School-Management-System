# School-Management-System
The "School Management System" is a project developed in Java that provides management functionalities for a school. The project consists of multiple classes, namely Main.java and Operations.java, which together create a command-line interface for managing student information, fee management, and exam management.

The Main class serves as the entry point for the application. It displays a menu-driven interface using System.out.println statements to prompt the user for their choice. The Operations class contains methods for handling the different functionalities of the system.

The Operations class is responsible for handling the various management tasks. It includes the following functionalities:

Student Management: This feature allows users to perform operations related to student management. It provides options to perform new admissions, update student details, issue transfer certificates (TC), and display existing student details. The Operations class interacts with a MySQL database using JDBC to perform CRUD operations on the student table.

Fee Management: This feature enables users to manage student fees. It allows the user to add new fee records for students by providing the admission number, fee amount, and month. The Operations class interacts with the database to insert fee records into the fee table.

Exam Management: This feature provides functionalities for managing exam details. Users can enter exam information such as student name, admission number, percentage, and result (pass/fail). The Operations class interacts with the database to insert and delete exam records in the exam table.

The code handles user input validation using exception handling mechanisms like try-catch blocks to ensure correct inputs are provided. It also includes utility methods to check for duplicate admission numbers and to check if tables are empty in the database.

The project utilizes JDBC to establish a connection with a MySQL database and perform database operations such as inserting, updating, deleting, and retrieving data.

Overall, the School Management System project in Java provides a command-line interface for managing student information, fee records, and exam details within a school.
