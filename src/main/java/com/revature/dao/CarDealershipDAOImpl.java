package com.revature.dao;

import static com.revature.util.LoggerUtil.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.revature.jdbc.util.ConnectionFactory;
import com.revature.pojo.Car;
import com.revature.pojo.Customer;
import com.revature.pojo.Offer;

public class CarDealershipDAOImpl implements CarDealershipDAO{
	private Connection conn = ConnectionFactory.getConnection();
	Offer offer = new Offer();
	Car car = new Car();
	@Override
	public int addCar(Car car) {
		// TODO Auto-generated method stub
		int result = 1;
		try {
			String sql2 = "insert into cars values("+car.getVin()+","+car.getName()+","+car.getModel()+","
			+car.getYear()+","+car.getPrice()+","+car.getRemainingPayment()+")";
			String sql3 = "select * from cars";

			Statement stmt = conn.createStatement();

			stmt.executeUpdate(sql2);
			debug("Car with VIN: " + car.getVin() + " inserted");

			ResultSet resultSet = stmt.executeQuery(sql3);

			debug("Select query");

			while (resultSet.next()) {
				info("VIN " + resultSet.getInt(1) + " Make: " + resultSet.getString(2) + " Model: " + resultSet.getString(3));
				info("Year: " + resultSet.getInt(4) + " Price: " + resultSet.getString(5)  + " Remaining: " + resultSet.getString(6));			
			}
			debug("SQL transaction complete");

		} catch (SQLException e) {
			e.printStackTrace();
		}	
		info("Car Added to DB");
		return result;
	}

	@Override
	public int putOffer(Offer offer) {
		// TODO Auto-generated method stub
		debug("Updating offers table in DB");

		return 1;
	}

	@Override
	public int removeCar(String id) {
		// TODO Auto-generated method stub
		info("Removing a car from the DB's lot");

		return 2;
	}

	@Override
	public void saveCustomer(Customer c) {
		// TODO Auto-generated method stub
		debug("Saving customer info into DB");
	}

	@Override
	public Offer getOffer(String vin) {
		// TODO Auto-generated method stub
		offer.setVin("123M");
		offer.setCustomerId("480P");
		offer.setAmount(1000);
		
		
		return offer;
	}

	@Override
	public int updateOffer(Offer offer) {
		// TODO Auto-generated method stub
		//Push change given input argument
		return 0;
	}

	@Override
	public boolean checkIfCarInLot(String vin) {
		// TODO Auto-generated method stub
		boolean check = true;
		
		return check;
	}

	@Override
	public boolean checkForLogin(String username, String password) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean checkForRegister(Customer c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<Car> getOwnersCarList(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ArrayList<Car> getEntireCarList(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Car pullIndividualCar(String VIN) {
		// TODO Auto-generated method stub
		Car oneCar = new Car("Ford", "mustang", 2019, 36000, VIN);
		return oneCar;
	}

	@Override
	public boolean updateRemainingPayment(String VIN, double amount) {
		// TODO Auto-generated method stub
		return false;
	}
	
	

}
