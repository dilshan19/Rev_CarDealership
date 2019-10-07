package com.revature.pojo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.revature.dao.CarDealershipDAOImpl;
import com.revature.main.CarDealershipDriver;
import com.revature.users.UserInterface.tableOpt;

import static com.revature.util.LoggerUtil.*;

public class Employee {

	private String username;
	private String pin;
	private CarDealershipDAOImpl carDAO = new CarDealershipDAOImpl();
	private Car car = new Car();
	private Scanner scanner = new Scanner(System.in);

	
	public Employee() {
		super();
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String userName) {
		this.username = userName;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pIN) {
		pin = pin;
	}
	
	
	//TODO: ask for Car info and return an int, code for DB's response
	public void addCar() {
		boolean stay = true;
		Car car = new Car();
		int result = 0;
		do {	//TODO: fix structure, fix SCANNERS
			try {
				info("\tYou have chose to add a car.");
				info("\tEnter Car Make");
				String make = scanner.nextLine();
				car.setName(make.toLowerCase());
				info("\tEnter Car Model");
				String model = scanner.nextLine();
				car.setModel(model.toLowerCase());
				info("\tEnter model year");
				String year = scanner.nextLine();
				car.setYear(Integer.parseInt(year));
				info("\tEnter Car VIN");
				String vin = scanner.nextLine();
				car.setVin(vin.toUpperCase());
				info("\tEnter Car Price");
				String price = scanner.nextLine();
				car.setPrice(Double.parseDouble(price));
				car.setRemainingPayment(Double.parseDouble(price));
				result = carDAO.addCar(car,tableOpt.CAR.levelCode);
				switch(result) {
				case 0:
					info("This VIN has already been registered");break;
				case 1:
					stay = false;
					info(car.toString());
					info("\tThe car was successfully added to the Lot:");
					stay = false;break;
				case 2:
					info("Database error. Please contact system admin.");break;
				default: 
					info("Unhandled database error. Please contact system admin.");
				}
			}catch(InputMismatchException e) {
				info("Try Again. Please enter valid the valid data type");
				error(e);
			}catch(NumberFormatException e) {
				info("Try Again. Please enter valid the valid data type");
				error(e);
			}			
			catch(Exception e) {
				debug("Different Error");
				error(e);
			}
		}while(stay);
	}
	
	//TODO: ask for VIN and query DB using CarDealershipDAO
	public int reviewOffer() {
		boolean stay = true;
		int result = 0;
		boolean confirmOffer = true;
		boolean promptForOptionCounter = true;
		boolean promptForReview = true;
		

		do {	//TODO: fix structure, fix SCANNERS
			try {
				info("\tEnter Car VIN or (#) Quit to Employee Menu");
				String vin = scanner.nextLine();
				if(vin.contains("#")) {
					debug("# detected");
					stay = false;
					continue;
				}
				vin = vin.toUpperCase();				
				debug("vin: " + vin);
				ArrayList<Offer> offerList = carDAO.getOffers( vin );
				if (offerList == null ) {
					info("VIN not in our system, try again.");
					continue;
				}
				int optionCounter = 0;
				for(Offer o: offerList) {
					info(optionCounter + ". " + o.toString());
					optionCounter++;
				}
				String choice = null;
				while(promptForOptionCounter) {
					info("Enter the option number you'd like to review, or press (#) to try another VIN");
					choice = scanner.nextLine();
					if(choice.contains("#")) {
						break;
					}else if( Integer.parseInt(choice) > -1 && Integer.parseInt(choice) < offerList.size()) {
						promptForOptionCounter = false;
					}else {
						info("Invalid input. Pick a digit in the range of options");
					}
				}
				if(promptForOptionCounter) {	//Retrying VIN option
					continue;
				}
				int offerChoice = Integer.parseInt(choice);
				String currentVIN = offerList.get(offerChoice).getVin();
				String currentUsername = offerList.get(offerChoice).getCustomerId();
				double currentAmount = offerList.get(offerChoice).getAmount();

				while(promptForReview) {
					info(offerList.get(offerChoice).toString());
					info("Would you like to (A) Accept or (R) Reject this offer? Press (#) to quit");
					choice = scanner.nextLine();
					if(choice.contains("A") || choice.contains("a")) {
						carDAO.removeOffer(currentVIN, currentUsername);
						Car tempCar = carDAO.singleCarFromCarsTable(currentVIN);
						tempCar.setOwner(currentUsername);
						tempCar.setRemainingPayment(currentAmount);
						info(tempCar.toString());
						carDAO.addCar(tempCar, tableOpt.OWNERS.levelCode);	//addCar to owned table
						carDAO.removeCar(currentVIN);
						info("The offer was accepted. The vehicle will moved from the lot to the customer's inventory");
						promptForReview = false;
					}else if( choice.contains("R") || choice.contains("r")) {
						carDAO.removeOffer(currentVIN, currentUsername);
						info("The offer was rejected and removed from our records");						
						promptForReview = false;
					}else if(choice.contains("#")){
						break;
					}else {
						info("Invalid input. Pick a digit in the range of options");
					}					
				}
				if(promptForReview){ //Quit the accept/reject stage
					break;
				}		
							
			}catch(InputMismatchException e) {
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
		}while(stay);
		
		return result;
	}
	
	//TODO: ask for VIN and use int to return DB's response

	public void removeCar() {
		boolean stay = true;	
		boolean promptForVIN = true;
		boolean promptForOption1 = true;

		boolean result = false;
		do {
			try {
				promptForOption1 = true;
				promptForVIN = true;
				while(promptForOption1) {
					info("\tEnter (R) to remove a car, or (Q) to return to Employee Menu");
					String choice = scanner.nextLine();
					if(choice.contains("Q") || choice.contains("q")) {
						stay = false;break;
					}else if( choice.contains("r") || choice.contains("R")) {
						promptForOption1 = false;
						break;
					}else {
						info("Enter one of the options!");
					}
				}
				if(promptForOption1) {break;}//quit to main
				
				while(promptForVIN) {
					info("\tEnter the Car VIN or (#) to quit");
					String vin = scanner.nextLine();
					if(vin.contains("#")) {
						return;
					}else if(!Pattern.matches("[a-zA-Z0-9]{17}", vin)) {
						info("Enter a 17 digit alphanumeric number please");
						continue;
					}
					result = carDAO.removeCar(vin); 
					if(result) {
						info("The car has been removed from the lot");
						promptForVIN = false;
					}else {
						info("The car does not exist in our records. Try again");
					}
				}
	
			}catch(InputMismatchException e) {
				info("Try Again. Please enter valid the valid data type\n");
				stay = false;
				error(e);
			}catch(NumberFormatException e){
				error(e);
				info("Try Again. Please enter valid the valid data type\n");
				stay = false;
			}
			catch(Exception e) {
				debug("Different Error");
				error(e);
				stay = false;
			}			
		}while(stay);
		//TODO: switch case for result
	}
	
	public void showCars() {
		info("Showing Cars");
	}
	
	public void viewPayments() {
		info("Viewing Cars");

	}
	/*
	public void exit(String v) {
		int x = 1;
		info(v);
		do {
			info("\tEnter 'E' to Exit");
			String exit = scanner.nextLine();
			if(exit.contains("E")) {
				CarDealershipDriver.main(null);
				x = 2;
			}else {
				info("\tInvalid Input!");
				continue;
			}
		}while(x ==1);
	}
	*/

	public void employeeMenuOptions() {
		boolean stay = true;
		final String samplePin = "1234";

		do {				
			info("\tWelcome to the Employee Menu " + getUsername());
			info("\t>>Enter '1' Add A Car to The Lot!");
			info("\t>>Enter '2' Accept or reject an offer!");
			info("\t>>Enter '3' View Payments!");
			info("\t>>Enter '4' Remove A Car From The Lot!");
			info("\t>>Enter 'Q' To Exit Back to Main Menu");

			String choice = scanner.nextLine();
			debug("User input was: " + choice);
			switch (choice) {
			case "1":
				this.addCar();
				break;
			case "2":
				this.reviewOffer();
				break;
			case "3":
				this.viewPayments();
				break;
			case "4":
				this.removeCar();
				break;
			case "q":
				stay = false;
			case "Q":
				stay = false;
			default:
				info("\tInvalid Input!");
				break;
			}

		}while(stay);
	}
	
	
}
