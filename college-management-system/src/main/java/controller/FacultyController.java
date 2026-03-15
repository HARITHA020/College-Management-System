package controller;

import java.util.Scanner;

import service.AdminService;
import service.FacultyService;
import service.StudentService;

public class FacultyController {

	private FacultyService facultyService = new FacultyService();
	private AdminService adminService= new AdminService();
	private StudentService studentService= new StudentService();
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
				studentService.viewStudents();
				break;

			case 2:
				facultyService.viewAttendance();
				break;

			case 3:
				facultyService.viewMarks();
				break;

			case 4:
				adminService.viewTimetable();
				break;

			case 5:
				return;

			default:
				System.out.println("Invalid Choice");
			}
		}
	}
}