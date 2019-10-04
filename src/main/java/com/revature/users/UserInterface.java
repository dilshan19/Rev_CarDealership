package com.revature.users;

import java.io.IOException;
import java.util.InputMismatchException;
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
	private boolean match(String userName, String PIN) {	//TODO method to verify login from database info
		boolean check = true;	//CHANGE
		check = customerDAO.checkForLogin(userName, PIN);
		return check;
	}
	public void login() {
		// TODO Turn this into a while
		info("Please Log in");		
		boolean stay = true;
		boolean validLogin = false;
		String userName = null;
		String PIN = null;
		do {
			try {
				info("\n\t>>Enter Your Username!\n");
				userName = scanner.nextLine();
				customer1.setUserName(userName);
				info("\n\t>>Enter Your PIN!\n");
				PIN = scanner.nextLine();
				customer1.setPin(PIN);
				if(this.match(userName, PIN) ) {
					stay = false;
					validLogin = true;
				}else {
					info("Wrong Combination.");
					info("Press (Y) For Main Menu Or Enter Any Other Key To Retry");
					String returnToMain = scanner.nextLine();
					if(returnToMain.contains("y") || returnToMain.contains("Y")) {
						stay = false;
						break;
					}
				}
			}
			catch(InputMismatchException e) {
				info("Try Again. Please enter valid the valid data type\n");
				error(e);
			}catch(NumberFormatException e){
				error(e);
				info("Try Again. Please enter valid the valid data type\n");
			}
			catch(Exception e) {
				debug("Different Error");
				error(e);
				stay = false;
			}
		}while(stay == true);
		if(validLogin) {
			customer1.setPin(PIN);
			customer1.setUserName(userName);
			customer1.customerOptions();
		}		
	}
	
	private boolean exitRegister(String line) {
		boolean check = false;
		
		
		return check;
	}

	public void register() throws IOException{			
			boolean stay = true;
			boolean promptForUsername = true;
			boolean promptForPIN = true;
			boolean validRegister = false;
			
			do {
				try {
					info("Enter '#' to exit now, or press any other key to continue registering");
					String cont = scanner.nextLine(); 
					if(cont.contains("#")) {
						stay = false;continue;
					}
					info("\n\t>>Enter Your First Name!\n");
					String name = scanner.nextLine();
					if(Pattern.matches("[a-zA-Z]+", name)) {
						info("\n\t>>Enter Your Last Name!\n");
						String lname = scanner.nextLine();
						if(Pattern.matches("[a-zA-Z]+", lname)) {
							while(promptForUsername) {
								info("\n\t>>Enter a Username or Press '#' to Start Over!\n");
								String username = scanner.nextLine();
								if(Pattern.matches("[a-zA-Z0-9]{6,18}", username)) {
									promptForUsername = false;
									info("You've entered: " + username);
									while(promptForPIN) {
										info("\n\t>>Enter your 4 Digit Pin or Press Q to Start Over!\n");
										String pin = scanner.nextLine();
										if(Pattern.matches("^[0-9]{4}$", pin)){	//TODO: change to a loop
											validRegister = true;
											debug("Pin: " + pin);
											promptForPIN = false;
											stay = false;
										}else if(pin.contains("Q") || pin.contains("q") ) {
											info("Starting over");
											break;
										}
										else{
											info("\n\tInvalid Input! Try Again!\n");
										}
									}
								}else if(username.contains("#")){
									info("Starting over");
									break;
								}
								else{
									info("Username must have 6-18 Alphanumeric characters");
								}
							}
						}else {
							info("\n\tInvalid Last Name!\n");
						}
						
					}else {
						info("\n\tInvalid Input!\n");
					}					
				}
				catch(Exception e) {
					debug("Exception caught");
					error(e);
					stay = true;
				}
			}while(stay);
			if(validRegister) {
				info("Account created.");
				login();
			}
		}
	
	
	
	}
