package com.revature.pojo;

import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Pattern;

import com.revature.dao.CarDealershipDAOImpl;
import com.revature.main.CarDealershipDriver;

import static com.revature.util.LoggerUtil.*;

public class Employee {
	
	CarDealershipDAOImpl cardealerdao = new CarDealershipDAOImpl();
	Car car = new Car();	
	Scanner scanner = new Scanner(System.in);
	Scanner scannerint = new Scanner(System.in);
	Scanner scannerd = new Scanner(System.in);


	public void addCar() {
		int x = 1;
		do {
			try {
				System.out.println("\tAdd Car To The Lot");
				System.out.println("\tEnter Car Make");
				String make = scanner.nextLine();
				System.out.println("\tEnter Car Model");
				String model = scanner.nextLine();
				System.out.println("\tEnter Car VIN");
				int vin = scannerint.nextInt();
				System.out.println("\tEnter Car Price");
				double price = scannerd.nextDouble();
				cardealerdao.addCar(make, model, vin, price);
				if(cardealerdao.addCar(make, model, vin, price) > 0) {
					x = 2;
					System.out.println("\tThe Car Was Successfully Added\n");
					debug(" ");
				}else {
					System.out.println("\tEnter 'R' to To Remove A Cars From The Lot !");
					System.out.println("\tEnter 'S' to To Show Cars From The Lot !");
					System.out.println("\tPress Any key to Exit !");
					String addMoreExit = scanner.nextLine();
					if(addMoreExit.contains("R")) {
						x = 2; this.removeCar();
					  
					} else if(addMoreExit.contains("S")) {
						x = 2; this.showCars();
				    } else{ 
				        x = 2;
					    debug(" "); 
					}
				}
				 	
			}catch(Exception e) {
				e.getStackTrace();
				//error(e);
			}
		}while(x == 1);
	}
	public boolean viewOffer() {
		
		return false;
	}
	
	public boolean removeCar() {
		System.out.println("\tEnter 'R' To Remove A Car From The Car Lot!");
		String removeCar = scanner.nextLine();
		if(removeCar.contains("R")) {
			System.out.println("\tEnter The Car Id!");
			String carId = scanner.nextLine();
			if(Pattern.matches("[0-9]+", carId)) {
				int id = Integer.valueOf(carId);
				cardealerdao.removeCar(id);
				if(cardealerdao.removeCar(id) > 0) {
					System.out.println("The Car Was Successfully Deleted");
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
		System.out.println(v);
		do {
			System.out.println("\tEnter 'E' to Exit");
			String exit = scanner.nextLine();
			if(exit.contains("E")) {
				CarDealershipDriver.main(null);
				x = 2;
			}else {
				System.out.println("\tInvalid Input!");
				continue;
			}
		}while(x ==1);
	}
	*/
	public void employeeMenuOptions() {

		System.out.println("\t>>Enter Enter Your 4 Digit PIN!");
		String pin = scanner.nextLine();
		String samplePin = "1234";
		if(pin.contains(samplePin)) {	
			info("Employee Menu executions starts");		
				System.out.println("\tWelcome to Employee Menu ");
				System.out.println("\t>>Enter 'A' Add A Car to The Lot!");
				System.out.println("\t>>Enter 'D' Accept or reject an offer!");
				System.out.println("\t>>Enter 'S' View Payments!");
				System.out.println("\t>>Enter 'W' Remove A Car From The Lot!");
				System.out.println("\t>>Enter 'Q' To Exit Back to Main Menu");
	
				String choice = scanner.nextLine();
				info("User input was: " + choice);
				switch(choice) {
					case "A":   this.addCar();
					break;
					case "D":   this.viewOffer();
					break;
					case "S":   this.showCars();
					break;
					case "W":   this.removeCar();
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
