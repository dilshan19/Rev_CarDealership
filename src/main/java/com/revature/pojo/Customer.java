package com.revature.pojo;

import static com.revature.util.LoggerUtil.debug;
import static com.revature.util.LoggerUtil.error;
import static com.revature.util.LoggerUtil.info;

import java.util.ArrayList;
import java.util.Scanner;

import com.revature.main.CarDealershipDriver;

public class Customer {
	
	private String firstName;
	private String lastName;
	private String userName;
	private String pin;
	Offer offer;
	ArrayList<Car> cars;
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
	public void viewCars() {
		info("Pulling cars from a database?");
	}
	public Offer makeOffer() {
		info("Prompting customer for type of offer");
		return null;
	}
	public void viewOwnedCars() {
		info("Pulling some table of cars");
	}
	public void viewRemainingPayments() {
		info("Ask for Car VIN and Pull some info and make calculations based off info");
	}
	//Menu Interface
	public void customerOptions() {		
		debug("Logging in....");		
		boolean quitToMainMenu = false;
		do {
			info("Customer Menu executions starts");
			info("\tWelcome to Customer Menu "+ this.getUserName());
			info("\t>>Enter '1' View Cars");
			info("\t>>Enter '2' Make An Offer!");
			info("\t>>Enter '3' View Owned Cars!");
			info("\t>>Enter '4' View Remaining Payments!");
			info("\t>>Enter 'Q' To Exit Back to Main Menu");
			Scanner scanner = new Scanner(System.in);
			String choice = scanner.nextLine();
			debug("User input was: " + choice);
			switch (choice) {
			case "1":
				this.viewCars();
				quitToMainMenu = true;
				break;
			case "2":
				this.makeOffer();
				quitToMainMenu = true;
				break;
			case "3":
				this.viewOwnedCars();
				quitToMainMenu = true;
				break;
			case "4":
				this.viewRemainingPayments();
				quitToMainMenu = true;
				break;
			case "Q":
				quitToMainMenu = true;
				break;
			case "q":
				quitToMainMenu = true;
				break;
			default:
				info("\tInvalid Input!");
				break;
			}

		}while(quitToMainMenu == false);
	
	}
	
}
