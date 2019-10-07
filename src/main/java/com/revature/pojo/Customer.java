package com.revature.pojo;

import static com.revature.util.LoggerUtil.debug;
import java.util.regex.Pattern;
import static com.revature.util.LoggerUtil.error;
import static com.revature.util.LoggerUtil.info;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.revature.dao.CarDealershipDAOImpl;
import com.revature.main.CarDealershipDriver;

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
		boolean askVin = true;
		boolean promptOffer = true;
		int result = 0;
		Offer offerMade = null;
		Car singleCar = null;
		String vin = null;
		
		do {
			try {
				askVin = true;
				promptOffer = true;
				while(askVin) {
					info("What is the Car VIN? Press '#' to return to the Customer Menu");
					vin = scanner.nextLine();
					if(vin.contains("#")) {
						askVin = false;
						stay = false;
						continue;
					}else if(Pattern.matches("[a-zA-Z0-9]{17}", vin)) {
						askVin = false;
					}else {
						info("Enter the 17 alphanumeric characters located on the windshield");
					}
				}
				if(!stay) {
					continue;
				}
				vin = vin.toUpperCase();
				singleCar = carDAO.singleCarFromCarsTable(vin);
				if(  singleCar != null ) {
					info( "DETAILS: " + singleCar.toString() );
				}else {
					info("No car found with this VIN. Try again.");
					continue;
				}
				while(promptOffer) {
					info("Would you like to make an offer for this vehicle? Press (Y) Yes or (N) No to search another VIN");
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
					else if(Pattern.matches("[0-9]{1,8}", amount)) {	//Play safe -> 9 digits can overflow INT
						promptOffer = false;
					}else {
						info("Enter up to 8 digits only!");
					}
				}
				if(promptOffer) {	//contained #
					continue;
				}
				offerMade = new Offer();
				offerMade.setVin(vin);
				offerMade.setCustomerId(this.userName);
				offerMade.setAmount(Integer.parseInt(amount));
				info(offerMade.toString()) ;
				result = carDAO.putOffer( offerMade);
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
				info("\tTry Again. Please enter valid the valid data type\n");
			}
			catch(Exception e) {
				debug("Different Error");
				error(e);
			}			

		}while(stay == true);
		return offerMade;
	}
	public void viewAllCars() {
		debug("Pulling all cars (result set -> to arrayList of cars");
		cars = carDAO.getEntireCarList();
		if(cars == null) {
			info("No cars owned");
			return;
		}
		debug("Car list size: " + cars.size());
		for(Car c: cars) {
			info(c.toString());
		}
	}
	public void viewOwnedCars() {
		debug("Pulling owner's cars (result set -> to arrayList of cars");
		cars = carDAO.getOwnersCarList(userName);	
		if(cars == null) {
			info("No cars owned");
			return;
		}
		for(Car c: cars) {
			info(c.toString());
		}
	}
	public void viewRemainingPayments() {
		info("Ask for Car VIN and Pull some info and make calculations based off info");
	}
	//Menu Interface
	public void customerOptions() {		
		debug("Logging in....");
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
