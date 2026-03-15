package controller;

import java.util.Scanner;
import service.FacultyService;

public class FacultyController {

	private FacultyService facultyService = new FacultyService();
	private Scanner scanner = new Scanner(System.in);

	public void showMenu() {

		while (true) {

			System.out.println("1. View Students");
			System.out.println("2. View Attendance");
			System.out.println("3. View Marks");
			System.out.println("4. View Timetable");
			System.out.println("5. Logout");

			int choice = scanner.nextInt();

			switch (choice) {

			case 1:
				facultyService.viewStudents();
				break;

			case 2:
				facultyService.viewAttendance();
				break;

			case 3:
				facultyService.viewMarks();
				break;

			case 4:
				facultyService.viewTimeTable();
				break;

			case 5:
				return;

			default:
				System.out.println("Invalid Choice");
			}
		}
	}
}