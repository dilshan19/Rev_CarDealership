package com.revature.main;

import static com.revature.util.LoggerUtil.*;

import java.io.IOException;
import java.util.Scanner;

import com.revature.pojo.Employee;
import com.revature.users.UserInterface;

import org.apache.log4j.Logger;

public class CarDealershipDriver {


	public static void main(String[] args) {
		// TODO Auto-generated method stub
				
		UserInterface userInterface = new UserInterface();
		Employee employee = new Employee();
		Scanner scanner = new Scanner(System.in);
		System.out.println("\n\n");

		try {
			System.out.println("\t\t\t\t ----------------------------------- ");
			System.out.println("\t\t\t\t/                                   /");
			System.out.println("\t\t\t\t/          Welcome To RevCar        /");
			System.out.println("\t\t\t\t/                                   /");
			System.out.println("\t\t\t\t -----------------------------------\n\n\n");
			info("Application executions starts");
			boolean keepPrompting = true;

			while(keepPrompting) {
				System.out.println("\tCustomr: Enter (C)\tEmployee: Enter (E)\tTerminate Program: (T) ");
				String choice = scanner.nextLine();
				info("User input was: " + choice);
				if(choice.contains("C") || choice.contains("c")) {
					System.out.println("\t>>Enter 'R' To Register");
					System.out.println("\t>>Enter 'L' To Login!");

					String choiceCust = scanner.nextLine();
					info("Customer input was: " + choiceCust);
					switch(choiceCust) {
						case "R": 	userInterface.register();
						break;
						case "r": 	userInterface.register();
						break;
						case "L":   userInterface.login();
						break;
						case "l": 	userInterface.login();
						break;
						case "T":   keepPrompting = false;
						break;
						case "t": 	keepPrompting = false;
						break;
						default: System.out.println("\tInvalid Input!");
						break;
				}
				}
				else if(choice.contains("E") || choice.contains("e")) {
	                 employee.employeeMenuOptions();
				}
				else {
					System.out.println("Invalid Input!");
				}
			}
			
			scanner.close();
			debug("Exiting menu now");
		}catch (IOException e) {
			error(e);
		}
		
		
		
	}

}
