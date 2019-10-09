package com.revature.pojo;

import static com.revature.util.LoggerUtil.debug;
import java.util.regex.Pattern;
import static com.revature.util.LoggerUtil.error;
import static com.revature.util.LoggerUtil.info;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.revature.dao.CarDealershipDAOImpl;
import com.revature.main.CarDealershipDriver;
import com.revature.users.UserInterface.tableOpt;

public class Customer {
	
	private String firstName;
	private String lastName;
	private String userName;
	private String pin;
	private Offer offer;
	private ArrayList<Car> cars;
	private CarDealershipDAOImpl carDAO = new CarDealershipDAOImpl();
	private Scanner scanner = new Scanner(System.in);

	public Customer() {
		super();
	}
	public Customer(String firstName, String lastName, String userName, String pin) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
		this.pin = pin;
	}
	//Getter and Setters
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public String getPin() {
		return pin;
	}
	public Offer getOffer() {
		return offer;
	}
	public void setOffer(Offer offer) {
		this.offer = offer;
	}
	
	//Menu Options
	public Offer makeOffer() {
		boolean stay = true;
		boolean promptForOptionCounter = true;
		boolean promptOffer = true;
		Offer offerMade = null;
		Car singleCar = null;
		
		do {
			try {
				promptOffer = true;
				promptForOptionCounter = true;
				if(!viewAllCars()) {
					break;
				}
				String choice = null;
				while(promptForOptionCounter) {
					info("Enter the option number you'd like to review, or press (#) to quit");
					choice = scanner.nextLine();
					if(choice.contains("#")) {
						break;
					}else if( Integer.parseInt(choice) > -1 && Integer.parseInt(choice) < cars.size()) {
						promptForOptionCounter = false;
					}else {
						info("Invalid input. Pick a digit in the range of options");
					}
				}
				if(promptForOptionCounter) {	//Retrying VIN option
					break;
				}
				int offerChoice = Integer.parseInt(choice);
				singleCar = carDAO.singleCarFromCarsTable(cars.get(offerChoice).getVin());
				if(  singleCar != null ) {
					info( "DETAILS: " + singleCar.toString() );
				}else {
					info("No car details found in database");
					continue;
				}
				while(promptOffer) {
					info("Would you like to make an offer for this vehicle? Press (Y) Yes or (N) No to search another vehicle");
					String resp = scanner.nextLine();
					if(resp.contains("Y") || resp.contains("y") ) {
						promptOffer = false;
						continue;
					}else if( resp.contains("N") || resp.contains("n") ) {
						break;
					}else {
						info("Enter one of the two options!");
					}
				}
				if(promptOffer) {	//retry lookup (Pressed N)
					continue;
				}
				promptOffer = true;//reuse
				String amount = null;
				while(promptOffer) {
					info("How much would you like to offer? Press (#) to start over");
					amount = scanner.nextLine();
					if(amount.contains("#")) {
						break;
					}
					else if(Pattern.matches("[0-9.]{1,10}", amount) && Double.parseDouble(amount) > 0.0) {	//Play safe -> 9 digits can overflow INT
						promptOffer = false;
					}else {
						info("Enter a positive 10 digit number only!");
					}
				}
				if(promptOffer) {	//contained #
					continue;
				}
				String vin = cars.get(offerChoice).getVin();
				offerMade = new Offer();
				offerMade.setVin(vin);
				offerMade.setCustomerId(this.userName);
				offerMade.setAmount(Double.parseDouble(amount));

				info(offerMade.toString()) ;
				int result = carDAO.putOffer( offerMade);
				switch (result) {
				case 0:
					info("There was an unexpected error in our system. Please try again.");
					stay = false;
					break;
				case 1:
					info("You've updated your offer for VIN: " + vin);
					stay = false;
					break;
				default:
					info("\tThere was an unexpected error in our system. Please try again.");
					break;
				}
			}catch(InputMismatchException e) {
				info("\tTry Again. Please enter valid the valid data type\n");
				error(e);
			}catch(NumberFormatException e){
				error(e);
				e.printStackTrace();
				info("\tTry Again. Please enter valid the valid data type\n");
			}
			catch(Exception e) {
				debug("Different Error");
				error(e);
			}			

		}while(stay == true);
		return offerMade;
	}
	public boolean viewAllCars() {
		cars = carDAO.getEntireCarList();
		if(cars == null) {
			info("No cars in the lot");
			return false;
		}
		debug("Car list size: " + cars.size());
		int count = 0;
		for(Car c: cars) {
			info(count +  ". " + c.toString());
			count++;
		}
		return true;
	}
	public boolean viewOwnedCars() {
		cars = carDAO.getOwnersCarList(userName);	
		if(cars == null) {
			info("No cars owned");
			return false;
		}
		int count = 0;
		for(Car c: cars) {
			info(count +  ". " + c.toString());
			count++;
		}
		return true;
	}
	public void viewRemainingPayments() {
		boolean stay = true;
		boolean promptForOptionCounter = true;
		double remaining = 0.0;
		do {
			try {
				promptForOptionCounter = true;
				if(!viewOwnedCars()) {
					break;
				}
				String choice = null;
				while(promptForOptionCounter) {
					info("Enter the option number you'd like to examine, or press (#) to quit");
					choice = scanner.nextLine();
					if(choice.contains("#")) {
						break;
					}else if( Integer.parseInt(choice) > -1 && Integer.parseInt(choice) < cars.size()) {
						promptForOptionCounter = false;
					}else {
						info("Invalid input. Pick a digit in the range of options");
					}
				}
				if(promptForOptionCounter) {	//Retrying VIN option
					break;
				}
				int offerChoice = Integer.parseInt(choice);
				remaining = cars.get(offerChoice).getRemainingPayment();
				String carName = cars.get(offerChoice).getName();
				String carModel = cars.get(offerChoice).getModel();
				DecimalFormat dec = new DecimalFormat("#0.00");
				info("You owe: " + dec.format(remaining) + " on the " + carName + " " + carModel);
			}catch(InputMismatchException e) {
				info("Try Again. Please enter valid the valid data type\n");
				stay = false;
				error(e);
			}catch(NumberFormatException e){
				e.printStackTrace();
				error(e);
				info("Try Again. Please enter valid the valid data type\n");
				stay = false;
			}
			catch(Exception e) {
				debug("Different Error");
				error(e);
				stay = false;
			}			

		}while(stay == true);
	
		
	}
	//Menu Interface
	public void customerOptions() {		
		boolean stay = true;
		do {
			try {
				info("\tWelcome to Customer Menu "+ this.getUserName());
				info("\t>>Enter '1' View Cars");
				info("\t>>Enter '2' Make An Offer!");
				info("\t>>Enter '3' View Owned Cars!");
				info("\t>>Enter '4' View Remaining Payments!");
				info("\t>>Enter 'Q' To Exit Back to Main Menu");
				String choice = scanner.nextLine();
				debug("User input was: " + choice);
				switch (choice) {
				case "1":
					this.viewAllCars();
					break;
				case "2":
					this.makeOffer();
					break;
				case "3":
					this.viewOwnedCars();
					break;
				case "4":
					this.viewRemainingPayments();
					break;
				case "Q":
					stay = false;
					break;
				case "q":
					stay = false;
					break;
				default:
					info("\tInvalid Input!");
					break;
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

		}while(stay == true);
	
	}
	
}
