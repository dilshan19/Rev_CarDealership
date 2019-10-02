package com.revature.dao;

import com.revature.pojo.Offer;

public interface CarDealershipDAO {
	
	public void saveCustomer(String first, String last, String ssn);
	public int addCar(String model, String name, int vin, double p);
	public Offer getOffer(int vin);
	public int putOffer(int i);
	public int removeCar(int id);
	
}
