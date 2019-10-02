package com.revature.dao;

import static com.revature.util.LoggerUtil.*;

import com.revature.pojo.Offer;

public class CarDealershipDAOImpl implements CarDealershipDAO{

	@Override
	public int addCar(String model, String name, int vin, double p) {
		// TODO Auto-generated method stub
		info("Car Added to DB");
		return 1;
	}

	@Override
	public int putOffer(int i) {
		// TODO Auto-generated method stub
		info("Putting down an Offer in DB");

		return 0;
	}

	@Override
	public int removeCar(int id) {
		// TODO Auto-generated method stub
		info("Removing a car from the DB's lot");

		return 0;
	}

	@Override
	public void saveCustomer(String first, String last, String ssn) {
		// TODO Auto-generated method stub
		debug("Saving customer info into DB");
	}

	@Override
	public Offer getOffer(int vin) {
		// TODO Auto-generated method stub
		return null;
	}

}
