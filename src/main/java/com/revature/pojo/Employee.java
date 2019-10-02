package com.revature.pojo;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.revature.dao.CarDealershipDAOImpl;
import com.revature.main.CarDealershipDriver;

import static com.revature.util.LoggerUtil.*;

public class Employee {
	
	CarDealershipDAOImpl cardealerdao = new CarDealershipDAOImpl();
	Car car = new Car();
	Scanner scanner = new Scanner(System.in);
	//Scanner scannerint = new Scanner(System.in);
	//Scanner scannerd = new Scanner(System.in);


	public void addCar() {
		boolean stay = true;
		do {	//TODO: fix structure, fix SCANNERS
			try {
				info("\tAdd A Car To The Lot");
				info("\tEnter Car Make");
				String make = scanner.nextLine();
				car.setName(make);
				info("\tEnter Car Model");
				String model = scanner.nextLine();
				car.setModel(model);
				info("\tEnter Car VIN");
				int vin = scanner.nextInt();
				car.setId(vin);
				info("\tEnter Car Price");
				double price = scanner.nextDouble();
				car.setPrice(price);
				cardealerdao.addCar(make, model, vin, price);
				if(cardealerdao.addCar(make, model, vin, price) > 0) {
					stay = false;;
					info("\tThe Car Was Successfully Added:");
					info(car.toString());
					debug("Car added");
					stay = false;
				}else {	//consider case where car with the same VIN is stored
					info("This information is invalid");
				}
				 	
			}catch(InputMismatchException e) {
				info("Try Again. Please enter valid the valid data type");
				stay = false;
				error(e);
			}catch(Exception e) {
				debug("Different Error");
				stay = false;
				error(e);
			}
		}while(stay);
	}
	public int reviewOffer() {
		info("Pulling data -> truning into Offer obj -> make a decision here");
		//TODO: ask for VIN and query DB using CarDealershipDAO
		boolean stay = true;
		Offer offer = new Offer();
		int result = 0;
		do {	//TODO: fix structure, fix SCANNERS
			try {
				info("\tEnter Car VIN");
				int vin = scanner.nextInt();
				offer = cardealerdao.getOffer(vin);
				if(offer != null) {
					boolean confirmOffer = true;
					stay = false;
					while(confirmOffer) {
						info("\tCustomer ID: " + offer.getCustomerId() + " has offered: " + offer.getAmount() );
						info("\tAccept (Y) or Reject (N)?");
						String choice = scanner.nextLine();
						debug("User input was: " + choice);
						switch(choice) {//TODO: add methods to dealershipDAO to update DB with below results
							case "y":   info("Offer Accepted");stay = false;confirmOffer=false;result = 1;
							break;
							case "Y":   info("Offer Accepted");stay = false;confirmOffer=false;result = 1;
							break;
							case "n":   info("Offer Rejected");stay = false;confirmOffer=false;result = 2;
							break;
							case "N":   info("Offer Rejected");stay = false;confirmOffer=false;result = 2;
							break;
							default:	info("Please Enter A Valid Option");
							break;
						}
					}
				}else {	//consider case where car with the same VIN is stored
					info("This VIN is not in our system");
					stay = false;
				}
				 	
			}catch(InputMismatchException e) {
				info("Try Again. Please enter valid the valid data type");
				stay = false;
				error(e);
			}catch(Exception e) {
				debug("Different Error");
				stay = false;
				error(e);
			}
		}while(stay);
		
		return result;
	}
	
	public boolean removeCar() {
		info("\tEnter 'R' To Remove A Car From The Car Lot!");
		String removeCar = scanner.nextLine();
		if(removeCar.contains("R")) {
			info("\tEnter The Car Id!");
			String carId = scanner.nextLine();
			if(Pattern.matches("[0-9]+", carId)) {
				int id = Integer.valueOf(carId);
				cardealerdao.removeCar(id);
				if(cardealerdao.removeCar(id) > 0) {
					info("The Car Was Successfully Deleted");
                    debug(" ");
                }else {
                    debug("\tCar Not Found!\n");
                }
            }else {
                debug("\tInvalid Input!");
            }
            
        }else {
            debug("\tInvalid Input!");
        }
        return false;
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
		do {
			info("\t>>Enter Enter Your 4 Digit PIN!");
			String pin = scanner.nextLine();
			String samplePin = "1234";
			if(pin.contains(samplePin)) {	
				info("Employee Menu executions starts");		
					info("\tWelcome to Employee Menu ");
					info("\t>>Enter '1' Add A Car to The Lot!");
					info("\t>>Enter '2' Accept or reject an offer!");
					info("\t>>Enter '3' View Payments!");
					info("\t>>Enter '4' Remove A Car From The Lot!");
					info("\t>>Enter 'Q' To Exit Back to Main Menu");
		
					String choice = scanner.nextLine();
					debug("User input was: " + choice);
					switch(choice) {
						case "1":   this.addCar();stay = false;
						break;
						case "2":   this.addCar();stay = false;
						break;
						case "3":   this.reviewOffer();stay = false;
						break;
						case "4":   this.reviewOffer();stay = false;
						break;
						default: info("\tInvalid Input!");
						break;
					}
			}else {
				info("Incorrect Pin. Please Try Again!");
				stay = false;
			}	
		}while(stay);
	}
	
	
}
