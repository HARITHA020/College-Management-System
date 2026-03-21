package main;

import java.util.Scanner;
import controller.LoginController;

public class MainApp {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        LoginController loginController = new LoginController();

        while (true) {
            System.out.println("\n===== LOGIN PAGE =====");

            System.out.print("Enter Email Id: ");
            String email = input.nextLine();

            System.out.print("Enter Password: ");
            String password = input.nextLine();

            boolean success = loginController.login(email, password);

            if (!success) {
                System.out.println("Login failed. Try again.");
                continue;
            }

            System.out.println("\nYou have logged out. Returning to LOGIN PAGE...");
        }
    }
}