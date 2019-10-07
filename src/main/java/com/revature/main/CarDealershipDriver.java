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
		int count = 1;
		info("\n" + count++);

		try {

			boolean keepPrompting = true;

			while(keepPrompting) {
				info("\t\t\t\t /////////////////////////////////// ");
				info("\t\t\t\t/                                   /");
				info("\t\t\t\t/          Welcome To RevCar        /");
				info("\t\t\t\t/                                   /");
				info("\t\t\t\t ///////////////////////////////////");
				info("\tCustomer: Register (R)\t: Login (L)\tTerminate Program: (T) ");
				String choice = scanner.nextLine();
				debug("User input was: " + choice);
				if(choice.contains("R") || choice.contains("r")) {
					userInterface.register();
				}
				else if(choice.contains("L") || choice.contains("l")) {
					userInterface.login();
				}
				else if(choice.contains("T") || choice.contains("t")){
					break;
				}
				else{info("Invalid Input!");
				}
			}
			
			scanner.close();
			debug("Exiting menu now");
		}catch (IOException e) {
			error(e);
		}
		
		
		
	}

}
