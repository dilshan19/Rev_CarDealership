package com.revature.pojo;

import static com.revature.util.LoggerUtil.debug;
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
	private CarDealershipDAOImpl carDAO= new CarDealershipDAOImpl();
	private Scanner scanner = new Scanner(System.in);

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
	public Offer getOffer() {
		return offer;
	}
	public void setOffer(Offer offer) {
		this.offer = offer;
	}
	
	//Menu Options
	public Car makeOffer_helper(String VIN){
		Car temp = carDAO.pullIndividualCar(VIN);
		return temp;
	}
	public Offer makeOffer() {
		debug("Making an offerMade..");		
		boolean stay = true;
		int result = 0;
		Offer offerMade = null;
		Car temp = null;
		do {
			try {
				info("What is the Car VIN? Press '#' to return to the Customer Menu");
				String vin = scanner.nextLine();
				if(vin.contains("#")) {
					debug("User input was: " + vin);
					stay = false;continue;
				}
				temp = makeOffer_helper(vin);
				if( temp != null ) {
					info( temp.toString() );
				}else {
					info("No car found with this VIN. Try again.");
					continue;
				}
				offerMade = new Offer();
				offerMade.setVin(vin);
				offerMade.setCustomerId(userName);
				info("How much are you offering for the " + temp.getYear() + " " + temp.getName() + " " + temp.getModel() + "?");
				int amount = scanner.nextInt();
				offerMade.setAmount(amount);
				result = carDAO.putOffer( offerMade);
				switch (result) {
				case 0:
					info("Your offer could not be processed!");
					stay = false;
					break;
				case 1:
					info("We have saved your offer");
					stay = false;
					break;
				default:
					info("\tInvalid Input!");
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
			}			

		}while(stay == true);
		return offerMade;
	}
	public void viewAllCars() {
		info("Pulling all cars (result set -> to arrayList of cars");
		cars = carDAO.getEntireCarList(userName);
		if(cars == null) {
			info("No cars to show");
			return;
		}
		for(Car c: cars) {
			c.toString();
		}
	}
	public void viewOwnedCars() {
		info("Pulling owner's cars (result set -> to arrayList of cars");
		cars = carDAO.getOwnersCarList(userName);	
		if(cars == null) {
			info("No cars owned");
			return;
		}
		for(Car c: cars) {
			c.toString();
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
