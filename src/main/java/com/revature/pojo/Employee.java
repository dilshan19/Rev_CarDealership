package com.revature.pojo;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.revature.dao.CarDealershipDAOImpl;
import com.revature.main.CarDealershipDriver;

import static com.revature.util.LoggerUtil.*;

public class Employee {
	private String userName;
	private String PIN;
	private CarDealershipDAOImpl carDAO = new CarDealershipDAOImpl();
	private Car car = new Car();
	private Scanner scanner = new Scanner(System.in);

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
				car.setName(make);
				info("\tEnter Car Model");
				String model = scanner.nextLine();
				car.setModel(model);
				info("\tEnter model year");
				String year = scanner.nextLine();
				car.setYear(Integer.parseInt(year));
				info("\tEnter Car VIN");
				String vin = scanner.nextLine();
				car.setVin(vin);
				info("\tEnter Car Price");
				String price = scanner.nextLine();
				car.setPrice(Double.parseDouble(price));
				result = carDAO.addCar(car);
				if(result == 1) {
					stay = false;
					info(car.toString());
					info("\tThe car was successfully added to the Lot:");
					stay = false;
				}else if(result == 0){	//consider case where car with the same VIN is stored
					info("This information is invalid");
				}else if(result == 2) {
					info("This VIN has already been registered");
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
		debug("Pulling data -> truning into Offer obj -> make a decision here");
		boolean stay = true;
		Offer offer = new Offer();
		int result = 0;
		boolean confirmOffer = true;

		do {	//TODO: fix structure, fix SCANNERS
			try {
				info("\tEnter Car VIN or Quit (#) to Employee Menu");
				String vin = scanner.nextLine();
				debug("vin: " + vin);
				offer = carDAO.getOffer( vin );
				if(vin.contains("Q")) {
					debug("Q detected");
				}

				if (offer != null && ( offer.getVin() == vin) ) {
					while (confirmOffer) {
						info("\tCustomer ID: " + offer.getCustomerId() + " has offered: " + offer.getAmount());
						info("\tAccept (Y) or Reject (N)?");
						String choice = scanner.nextLine();
						debug("User input was: " + choice);
						switch (choice) {// TODO: add methods to dealershipDAO to update DB with below results
						case "y":
							info("Offer Accepted");
							stay = false;confirmOffer = false;offer.setStatus(true);
							result = 1;
							break;
						case "Y":
							info("Offer Accepted");
							stay = false;confirmOffer = false;offer.setStatus(true);
							result = 1;
							break;
						case "n":
							info("Offer Rejected");
							stay = false;confirmOffer = false;offer.setStatus(false);
							result = 2;
							break;
						case "N":
							info("Offer Rejected");
							confirmOffer = false;confirmOffer = false;offer.setStatus(false);
							result = 2;
							break;
						default:
							info("Please Enter A Valid Option");
							break;
						}
					}
				} else if(vin.contains("#")) {
					stay = false;
				}				
				else { // consider case where car with the same VIN is stored
					info("VIN not in our system, try again.");
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
	public int removeCar_helper() {
		int result = 0;
		info("\tEnter The Car Id!");
		String vin = scanner.nextLine();
		if(carDAO.checkIfCarInLot(vin)) {
			result = carDAO.removeCar(vin);
		}
		return result;
	}
	public int removeCar() {
		boolean stay = true;		
		int result = 0;
		do {
			try {
				info("\tEnter (R) to remove a car, or (Q) to return to Employee Menu");
				String removeCar = scanner.nextLine();
				switch(removeCar) {
				case "R":
					result = removeCar_helper();stay = false;
					break;			
				case "r":
					result = removeCar_helper();stay = false;
					break;
				case "Q":
					stay = false;
					break;
				case "q":
					stay = false;
					break;
				default:
					info("\tInvalid Input!");
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
		switch(result) {
		case 0:
			info("\tVIN not found");
			break;
		case 1:
			info("\tCar successfully removed");
			break;
		case 2:
			info("\tDatabase error");
			break;
		}
        return result;
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
	private boolean match(String userName, String PIN) {	//TODO method to verify login from database info
		boolean check = true;	//CHANGE
		check = carDAO.checkForLogin(userName, PIN);
		return check;
	}
	public void employeeMenuOptions() {
		boolean stay = true;
		boolean hasNotLoggedIn = true;
		final String samplePin = "1234";

		do {
			if(hasNotLoggedIn) {
				info("\t>>Enter Enter your Username!");
				String emp_userName = scanner.nextLine();
				info("\t>>Enter Enter Your 4 Digit PIN!");
				String pin = scanner.nextLine();
				if(match(emp_userName, PIN)) {
					hasNotLoggedIn = false;
				}
				else {
					info("Incorrect Pin. Please Try Again!");
					stay = false;
				}	
			}
				
			info("\tWelcome to the Employee Menu");
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
