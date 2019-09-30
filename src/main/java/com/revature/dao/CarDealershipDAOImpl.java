package com.revature.dao;

import static com.revature.util.LoggerUtil.*;

public class CarDealershipDAOImpl implements CarDealershipDAO{

	@Override
	public int addCar(String model, String name, int vin, double p) {
		// TODO Auto-generated method stub
		info("Car Added");
		return 1;
	}

	@Override
	public int putOffer(int i) {
		// TODO Auto-generated method stub
		info("Putting down an Offer");

		return 0;
	}

	@Override
	public int removeCar(int id) {
		// TODO Auto-generated method stub
		info("Removing a car from the lot");

		return 0;
	}

	@Override
	public void saveCustomer(String first, String last, String ssn) {
		// TODO Auto-generated method stub
		debug("Saving customer info");
	}

}
