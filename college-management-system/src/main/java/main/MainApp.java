package main;

import java.util.Scanner;

import controller.LoginController;

public class MainApp {

	public static void main(String[] args) {
		Scanner input=new Scanner(System.in);
		System.out.println("Enter the Email Id:");
		String email=input.next();
		
		System.out.println("Enter the password:");
		String password=input.next();
		
		LoginController logincontroller=new LoginController();
		logincontroller.login(email, password);
		

	}
}
