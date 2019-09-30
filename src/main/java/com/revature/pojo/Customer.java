package com.revature.pojo;

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
		Scanner scanner = new Scanner(System.in);
		System.out.println("\t>>Welcome " + this.getUserName());
		String pin = scanner.nextLine();
		String samplePin = "1234";
		if(pin.contains(samplePin)) {	
			info("Customer Menu executions starts");		
				System.out.println("\tWelcome to Customer Menu ");
				System.out.println("\t>>Enter '1' View Cars");
				System.out.println("\t>>Enter '2' Make An Offer!");
				System.out.println("\t>>Enter '3' View Owned Cars!");
				System.out.println("\t>>Enter '4' View Remaining Payments!");
				System.out.println("\t>>Enter 'Q' To Exit Back to Main Menu");
	
				String choice = scanner.nextLine();
				info("User input was: " + choice);
				switch(choice) {
					case "1":   this.viewCars();
					break;
					case "2":   this.makeOffer();
					break;
					case "3":   this.viewOwnedCars();
					break;
					case "4":   this.viewRemainingPayments();
					break;
					case "5":   CarDealershipDriver.main(null);
					break;
					default: info("\tInvalid Input!");
					break;
				}
				scanner.close();			

		}else {
			info("Incorrect Pin. Please Try Again!");
			CarDealershipDriver.main(null);
		}	
	}
	
}
