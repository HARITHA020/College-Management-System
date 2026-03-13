package controller;

import java.util.Scanner;
import service.StudentService;

public class StudentController {
	
	private StudentService studentService = new StudentService();
	private Scanner scanner = new Scanner(System.in);

	public void showMenu() {
		while (true) {
			System.out.println("1. Add Student");
			System.out.println("2. View Students");
			System.out.println("3. Exit");
			int choice = scanner.nextInt();
			
			switch (choice) {
			
			case 1:
				System.out.print("Enter ID: ");
				int id = scanner.nextInt();
				System.out.print("Enter Name: ");
				String name = scanner.next();
				System.out.print("Enter Department: ");
				String dept = scanner.next();
				studentService.addStudent(id, name, dept);
				break;
				
			case 2:
				studentService.viewStudents();
				break;
				
			case 3:
				return;
			}
		}
	}
}
