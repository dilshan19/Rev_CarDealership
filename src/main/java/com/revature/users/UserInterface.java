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
	public enum tableOpt {
	    USER (0),
	    CAR (1),
		EMP (2),
		OWNERS(4);
	    public final int levelCode;

	    private tableOpt(int levelCode) {
	        this.levelCode = levelCode;
	    }
	}
	private Car car1 = new Car();
	private Customer customer1 = new Customer();
	private Employee employee = new Employee();
	private CarDealershipDAOImpl UI_carDAO = new CarDealershipDAOImpl();
	private Scanner scanner = new Scanner(System.in);
	
	public void login() {
		// TODO Turn this into a while
		info("Please Log in");		
		boolean stay = true;
		boolean validLogin = false;
		boolean promptForUsername;
		boolean isEmployee = false;
		
		String userName = null;
		String PIN = null;
		do {
			try {
				promptForUsername = true;
				while(promptForUsername) {
					info("\n\t>>Enter your username or (#) to quit!\n");
					userName = scanner.nextLine();
					if(userName.contains("#")){
						break;
					}else if(!UI_carDAO.isDuplicateString(userName, tableOpt.USER.levelCode)){	//if not in database
						info("That username is not in our database! Try again.");
					}else {
						promptForUsername = false;
					}
				}	
				if(promptForUsername)return;	//pressed quit (#)
				customer1.setUserName(userName);
				info("\n\t>>Enter Your PIN!\n");
				PIN = scanner.nextLine();
				customer1.setPin(PIN);
				if(UI_carDAO.checkForTwoMatchingStrings(userName, PIN, tableOpt.USER.levelCode) ) {
					isEmployee = UI_carDAO.isDuplicateString(userName, 2);//check if user is an employee
					stay = false;
					validLogin = true;
				}else {
					info("Wrong Combination.");
					info("Press (#) For Main Menu Or Enter Any Other Key To Retry");
					String returnToMain = scanner.nextLine();
					if(returnToMain.contains("#") ) {
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
			if(isEmployee) {
				employee.setPin(PIN);
				employee.setUsername(userName);
				employee.employeeMenuOptions();
			}else {
				customer1.setPin(PIN);
				customer1.setUserName(userName);
				customer1.customerOptions();
			}
		}		
	}
	

	public void register() throws IOException{			
			boolean stay = true;
			boolean promptForUsername;
			boolean promptForPIN;
			boolean validRegister;
			
			do {
				promptForUsername = true;
				promptForPIN = true;
				validRegister = false;
				try {
					info("Enter '#' to exit now, or press any other key to continue registering");
					String cont = scanner.nextLine(); 
					if(cont.contains("#")) {
						stay = false;continue;
					}
					info("\n\t>>Enter Your First Name!\n");
					String name = scanner.nextLine();
					if(Pattern.matches("[a-zA-Z0-9]{1,16}", name)) {
						info("\n\t>>Enter Your Last Name!\n");
						String lname = scanner.nextLine();
						if(Pattern.matches("[a-zA-Z0-9]{1,16}", lname)) {
							while(promptForUsername) {
								info("\n\t>>Enter a Username or Press '#' to Start Over!\n");
								String username = scanner.nextLine();
								if(Pattern.matches("[a-zA-Z0-9]{6,18}", username)) {
									info("You've entered: " + username);
									if(!UI_carDAO.isDuplicateString(username, tableOpt.USER.levelCode)) {
										promptForUsername = false;
									}else {
										info("This username has been taken. Try another!");
										continue;
									}
									while(promptForPIN) {
										info("\n\t>>Enter a 4 Digit Pin or Press (Q) to Start Over!\n");
										String pin = scanner.nextLine();
										if(Pattern.matches("^[0-9]{4}$", pin)){	//TODO: change to a loop
											customer1.setFirstName(name);
											customer1.setLastName(lname);
											customer1.setUserName(username);
											customer1.setPin(pin);
											if(!UI_carDAO.saveCustomer(customer1)) {
												info("Database could not process your information. Please try again.");
												return;
											}
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
					debug("Exception caught in UI");
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
