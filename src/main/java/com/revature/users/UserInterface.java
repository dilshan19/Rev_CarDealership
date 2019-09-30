package com.revature.users;

import java.io.IOException;
import java.util.Scanner;

import com.revature.pojo.Employee;
import com.revature.users.UserInterface;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.revature.dao.CarDealershipDAOImpl;
import com.revature.main.CarDealershipDriver;
import com.revature.pojo.Car;
import com.revature.pojo.Customer;
import com.revature.pojo.Employee;

import static com.revature.util.LoggerUtil.*;

public class UserInterface implements UserOpts{

	Car car1 = new Car();
	Customer customer1 = new Customer();
	Employee employee = new Employee();
	CarDealershipDAOImpl customerDAO = new CarDealershipDAOImpl();
	
	Scanner scanner = new Scanner(System.in);
	private static boolean match(String userName, String PIN) {
		boolean check = false;
		return check;
	}
	public void login() {
		// TODO Auto-generated method stub
		info("Logging in....");		
		int x = 1;
		boolean validLogin = false;
		do {
			try {
				System.out.println("\n\t>>Enter Your Username!\n");
				String userName = scanner.nextLine();				
				System.out.println("\n\t>>Enter Your PIN!\n");
				String PIN = scanner.nextLine();				
				if(this.match(userName, PIN) ) {
					x=2;
					validLogin = true;
				}else {
					System.out.println("Wrong Combination.");
					System.out.println("Press (Y) For Main Menu Or Enter Any Other Key To Retry");
					String returnToMain = scanner.nextLine();
					if(returnToMain.contains("y") || returnToMain.contains("Y")) {
						x=2;
						CarDealershipDriver.main(null);
						break;
					}
				}
			}
			catch(Exception e) {
				error(e);
				x = 2;
			}
		}while(x == 1);
		if(validLogin) {
			customer1.customerOptions();
		}		
	}

	public void register() throws IOException{			
			int x = 1;
			boolean promptForUsername = true;
			boolean promptForPIN = true;
			boolean validRegister = false;
			
			do {
				try {
					System.out.println("\n\t>>Enter Your First Name!\n");
					String name = scanner.nextLine();
					if(Pattern.matches("[a-zA-Z]+", name)) {
						System.out.println("\n\t>>Enter Your Last Name!\n");
						String lname = scanner.nextLine();
						if(Pattern.matches("[a-zA-Z]+", lname)) {
							while(promptForUsername) {
								System.out.println("\n\t>>Enter a Username or Press '#' to Start Over!\n");
								String username = scanner.nextLine();
								if(Pattern.matches("[a-zA-Z0-9]{6,18}", username)) {
									promptForUsername = false;
									System.out.println("You've entered: " + username);
									while(promptForPIN) {
										System.out.println("\n\t>>Enter your 4 Digit Pin or Press Q to Start Over!\n");
										String pin = scanner.nextLine();
										if(Pattern.matches("^[0-9]{4}$", pin)){	//TODO: change to a loop
											validRegister = true;
											debug("Pin: " + pin);
											promptForPIN = false;
											x = 2;
										}else if(pin.contains("Q") || pin.contains("q") ) {
											System.out.println("Starting over");
											break;
										}
										else{
											System.out.println("\n\tInvalid Input! Try Again!\n");
										}
									}
								}else if(username.contains("#")){
									System.out.println("Starting over");
									break;
								}
								else{
									System.out.println("Username must have 6-18 Alphanumeric characters");
								}
							}
						}else {
							System.out.println("\n\tInvalid Last Name!\n");
						}
						
					}else {
						System.out.println("\n\tInvalid Input!\n");
					}					
				}
				catch(Exception e) {
					error(e);
					x = 2;
				}
			}while(x == 1);
			if(validRegister) {
				login();
			}
		}
	
	}
