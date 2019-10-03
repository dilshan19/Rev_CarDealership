package com.revature.dao;

import java.util.ArrayList;

import com.revature.pojo.Car;
import com.revature.pojo.Customer;
import com.revature.pojo.Offer;

public interface CarDealershipDAO {
	
	public void saveCustomer(String first, String last, String ssn);
	public int addCar(Car car);
	public Offer getOffer(String vin);
	public int putOffer(Offer offer);
	public int removeCar(String id);
	public int updateOffer(Offer offer);
	public boolean checkIfCarInLot(String vin);
	public boolean checkForLogin(String username, String password);
	public boolean checkForRegister(Customer c);
	public ArrayList<Car> getOwnersCarList(String username);
	public ArrayList<Car> getEntireCarList(String username);
	public Car pullIndividualCar(String VIN);

	
}
