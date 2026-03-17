package main;

import java.util.Scanner;
import controller.LoginController;

public class MainApp {

    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        LoginController logincontroller = new LoginController();

        while (true) { // 🔁 LOOP FOREVER

            System.out.println("\n===== LOGIN PAGE =====");

            System.out.print("Enter Email Id: ");
            String email = input.nextLine();

            System.out.print("Enter Password: ");
            String password = input.nextLine();

            logincontroller.login(email, password);

            // After logout → comes back here again automatically
        }
    }
}