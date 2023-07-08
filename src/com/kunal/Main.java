package com.kunal;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {

        System.out.println(":-:-:-:-:-:SCHOOL MANAGEMENT SYSTEM:-:-:-:-:-:");
        System.out.println("----------------------------------------------");

        Operations op = new Operations();
        int choice = op.displayMenu();
        op.handleChoice(choice);

        System.out.println("\n*************************\nHave a great day ahead!!!\n*************************");
        System.out.println("-----------------------------------------------------");
        System.out.println("School Management System.\nVersion: 1.1.3\nDeveloped by: KUNAL PATIL");
        System.out.println("-----------------------------------------------------");

        System.out.print("Do you want to restart the program? (y/n): ");
        String restart = scanner.next().toLowerCase();
        if (restart.equals("y")) {
            main(args);
        } else {
            System.exit(0);
        }
    }
}