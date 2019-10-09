package com.revature.pojo;

import java.io.IOException;
import java.text.DecimalFormat;
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
	private ArrayList<Offer> offerList = null;
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
				info("\n\t>>You have chosen to add a car.");
				info("\n\t>>Enter Car Make");
				String make = scanner.nextLine();
				car.setName(make.toLowerCase());
				info("\n\t>>Enter Car Model");
				String model = scanner.nextLine();
				car.setModel(model.toLowerCase());
				info("\n\t>>Enter model year");
				String year = scanner.nextLine();
				if(!Pattern.matches("[0-9]{4}", year)) {
					info("\n\t>>Enter 4 digits only please");
					continue;
				}
				car.setYear(Integer.parseInt(year));
				info("\n\t>>Enter Car VIN");
				String vin = scanner.nextLine();
				if(!Pattern.matches("[0-9a-zA-Z]{17}", vin)) {
					info("\n\t>>Enter 17 alphanumerical characters only please");
					continue;
				}
				car.setVin(vin.toUpperCase());
				info("\n\t>>Enter Car Price");
				String price = scanner.nextLine();
				if(!Pattern.matches("[0-9.]{1,10}", price)) {
					info("\n\t>>Enter up to 10 digits (decimal included) only please");
					continue;
				}
				car.setPrice(Double.parseDouble(price));
				car.setRemainingPayment(Double.parseDouble(price));
				result = carDAO.addCar(car,tableOpt.CAR.levelCode);
				switch(result) {
				case 0:
					info("\n\t>>This VIN has already been registered");break;
				case 1:
					stay = false;
					info(car.toString());
					info("\n\t>>The car was successfully added to the Lot:");
					stay = false;break;
				case 2:
					info("\n\t>>Database error. Please contact system admin.");break;
				default: 
					info("\n\t>>Unhandled database error. Please contact system admin.");
				}
			}catch(InputMismatchException e) {
				info("\n\t>>Try Again. Please enter valid the valid data type");
				error(e);
			}catch(NumberFormatException e) {
				info("\n\t>>Try Again. Please enter valid the valid data type");
				error(e);
			}			
			catch(Exception e) {
				debug("Different Error");
				error(e);
			}
		}while(stay);
	}
	
	public boolean viewOfferTable() {
		offerList = carDAO.getAllOffers();
		if(offerList == null) {
			info("\n\t>>No offers posted");
			return false;
		}
		debug("Offer list size: " + offerList.size());
		int count = 0;
		for(Offer c: offerList) {
			info(count +  ". " + c.toString());
			count++;
		}
		return true;
	}
	//TODO: ask for VIN and query DB using CarDealershipDAO
	public int reviewOffer() {
		boolean stay = true;
		int result = 0;
		boolean promptForOptionCounter = true;
		boolean promptForReview = true;

		do {	//TODO: fix structure, fix SCANNERS
			try {
				promptForOptionCounter = true;
				promptForReview = true;
				if(!viewOfferTable()) {	//if table is empty
					break;
				}
				String choice = null;
				while(promptForOptionCounter) {
					info("\n\t>>Enter the option number you'd like to review, or press (#) to quit");
					choice = scanner.nextLine();
					if(choice.contains("#")) {
						break;
					}else if( Integer.parseInt(choice) > -1 && Integer.parseInt(choice) < offerList.size()) {
						promptForOptionCounter = false;
					}else {
						info("\n\t>>Invalid input. Pick a digit in the range of options");
					}
				}
				if(promptForOptionCounter) {	//Retrying VIN option
					break;
				}
				int offerChoice = Integer.parseInt(choice);
				String currentVIN = offerList.get(offerChoice).getVin();
				String currentUsername = offerList.get(offerChoice).getCustomerId();
				double currentAmount = offerList.get(offerChoice).getAmount();
				while(promptForReview) {
					info(offerList.get(offerChoice).toString());
					info("\n\t>>Would you like to (A) Accept or (R) Reject this offer? Press (#) to quit");
					choice = scanner.nextLine();
					if(choice.contains("A") || choice.contains("a")) {
						carDAO.insertPaymentOnAcceptedOffer_SP(currentUsername, currentVIN);//grab data from offers -> payments. 
						carDAO.removeOffers(currentVIN, null);	//Delete offers with same VIN
						Car tempCar = carDAO.singleCarFromCarsTable(currentVIN);
						tempCar.setOwner(currentUsername);
						tempCar.setRemainingPayment(currentAmount);
						info(tempCar.toString());
						carDAO.addCar(tempCar, tableOpt.OWNERS.levelCode);	//addCar to owned table
						carDAO.removeCar(currentVIN);
						info("\n\t>>The offer was accepted. The vehicle will moved from the lot to the customer's inventory\n");
						promptForReview = false;
					}else if( choice.contains("R") || choice.contains("r")) {
						carDAO.removeOffers(currentVIN, currentUsername);	//deletes a SINGLE offer
						info("\n\t>>The offer was rejected and removed from our records");						
						promptForReview = false;
					}else if(choice.contains("#")){
						stay = false;
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
					info("\n\t>>Enter the Car VIN or (#) to quit");
					String vin = scanner.nextLine();
					if(vin.contains("#")) {
						return;
					}else if(!Pattern.matches("[a-zA-Z0-9]{17}", vin)) {
						info("\n\t>>Enter a 17 digit alphanumeric number please");
						continue;
					}
					result = carDAO.removeCar(vin); 
					if(result) {
						info("\n\t>>The car has been removed from the lot");
						promptForVIN = false;
					}else {
						info("\n\t>>The car does not exist in our records. Try a different VIN");
					}
				}
	
			}catch(InputMismatchException e) {
				info("\n\t>>Try Again. Please enter valid the valid data type\n");
				stay = false;
				error(e);
			}catch(NumberFormatException e){
				error(e);
				info("\n\t>>Try Again. Please enter valid the valid data type\n");
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
	
	public double calcMonthlyPayments(double principal, int years) {
		double result = 0.0;
		final double SALESMULTIPLIER = 1.06; //FLORIDA
		final double APR = 0.05;
		final double monthly_rate = APR / 12.0;
		double months = years * 12;
		//FROM HERE WE ADD/DEDUCT NOTHING (ie trade in, rebates, etc.)
		//loan amortization formula:
		principal = SALESMULTIPLIER * principal;

		double common = Math.pow((1.0+monthly_rate), months);
		double numerator = (monthly_rate * common);

		double denominator = common - 1.0;

		result = principal * numerator / denominator;
		return result;
	}
	
	public boolean viewPaymentTable() {
		offerList = carDAO.viewAllPayments();
		if(offerList == null) {
			info("No payments posted");
			return false;
		}
		debug("Payment list size: " + offerList.size());
		int count = 0;
		for(Offer c: offerList) {
			info(count +  ". " + c.toString());
			count++;
		}
		return true;
	}
	public void viewPayments() {
		boolean stay = true;
		int result = 0;
		boolean confirmOffer = true;
		boolean promptForOptionCounter = true;
		boolean promptForMonths = true;
		final int MAXYEARS = 10;
		do {
			try {
				
				promptForOptionCounter = true;
				promptForMonths = true;
				if(!viewPaymentTable()) {	//if table is empty
					break;
				}
				String choice = null;
				String yearCount = null;
				while(promptForOptionCounter) {
					info("\n\t>>Enter the option number you'd like to estimate monthly payments for, or press (#) to quit");
					choice = scanner.nextLine();
					if(choice.contains("#")) {
						break;
					}else if( Integer.parseInt(choice) > -1 && Integer.parseInt(choice) < offerList.size()) {
						promptForOptionCounter = false;
					}else {
						info("\n\t>>Invalid input. Pick a digit in the range of options");
					}
				}
				if(promptForOptionCounter) {	//Retrying VIN option
					break;
				}
				int choice_int = Integer.parseInt(choice);
				while(promptForMonths) {
					info("\n\t>>Enter the number of years for the loan, or press (#) to quit");
					yearCount = scanner.nextLine();
					if(yearCount.contains("#")) {
						break;
					}else if( Integer.parseInt(yearCount) > -1 && Integer.parseInt(yearCount) <= MAXYEARS) {
						promptForMonths = false;
					}else {
						info("\n\t>>Invalid input. Pick a digit in the 0 to 10");
					}
				}
				if(promptForMonths) {	//Retrying VIN option
					break;
				}
				double monthly =calcMonthlyPayments(offerList.get(choice_int).getAmount(), Integer.parseInt(yearCount));
				DecimalFormat dec = new DecimalFormat("#0.00");
				debug("Assumptions: 6% sales tax, 5% APR");
				info("\t>>Monthly estimate for your car loan: " + yearCount + " year term: " + dec.format(monthly)+"\n");
			}catch(InputMismatchException e) {
				info("Try Again. Please enter valid the valid data type\n");
				error(e);
			}catch(NumberFormatException e){
				//e.printStackTrace();
				stay = false;
				error(e);
				info("\n\t>>Try Again. Please enter valid the valid data type\n");
			}
			catch(Exception e) {
				debug("Different Error");
				error(e);
				stay = false;
			}
		}while(stay);
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
			info("\tWelcome to the Employee Menu, " + getUsername());
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
