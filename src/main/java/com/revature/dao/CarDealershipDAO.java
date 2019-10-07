package com.revature.dao;

import java.util.ArrayList;

import com.revature.pojo.Car;
import com.revature.pojo.Customer;
import com.revature.pojo.Offer;

public interface CarDealershipDAO {
	
	public boolean saveCustomer(Customer c);
	public int addCar(Car car, int field);
	public  ArrayList<Offer> getOffers(String vin);
	public int putOffer(Offer offer);
	public boolean removeCar(String id);
	public boolean removeOffer(String vin, String username);

	public int updateOffer(Offer offer);
	public boolean checkIfCarInLot(String vin);
	public boolean checkForTwoMatchingStrings(String username, String password, int field);
	public ArrayList<Car> getOwnersCarList(String username);
	public ArrayList<Car> getEntireCarList();
	public Car singleCarFromCarsTable(String VIN);
	public boolean updateRemainingPayment(String VIN, double amount);
	public boolean isDuplicateString(String string, int field);
	
}
