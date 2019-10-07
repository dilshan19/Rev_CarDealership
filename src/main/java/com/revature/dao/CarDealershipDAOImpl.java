package com.revature.dao;

import static com.revature.util.LoggerUtil.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.revature.jdbc.util.ConnectionFactory;
import com.revature.pojo.Car;
import com.revature.pojo.Customer;
import com.revature.pojo.Offer;

public class CarDealershipDAOImpl implements CarDealershipDAO{

	private Connection conn = ConnectionFactory.getConnection();
	private Offer offer = new Offer();
	private Car car = new Car();
	
	public void setConn(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public int addCar(Car car, int field) {
		int result = 0;
		try {
			String sql;
			PreparedStatement stmt;
			int count = 1;
			switch(field) {
			case 1:
				sql = "insert into cars (vin, make, model, model_year, price, remaining) values (?,?,?,?,?,?)";
				stmt = conn.prepareStatement(sql);
				stmt.setString(count++, car.getVin());
				stmt.setString(count++, car.getName());
				stmt.setString(count++, car.getModel());
				stmt.setInt(count++, car.getYear());
				stmt.setDouble(count++, car.getPrice());
				stmt.setDouble(count++, car.getRemainingPayment());
				debug(stmt.toString());
				result = stmt.executeUpdate();
				break;
			case 4:
				sql = "insert into owners(VIN, username, make,model,model_year, remaining ) values (?,?,?,?,?,?)";
				stmt = conn.prepareStatement(sql);
				count = 1;
				stmt.setString(count++, car.getVin());
				stmt.setString(count++, car.getOwner());
				stmt.setString(count++, car.getName());
				stmt.setString(count++, car.getModel());
				stmt.setInt(count++, car.getYear());
				stmt.setDouble(count++, car.getRemainingPayment());
				debug(stmt.toString());
				result = stmt.executeUpdate();
				break;
			default:
				debug("Use an apprpriate 2nd arg to addCar()");	
					
			}
			

		} catch (SQLException e) {
			int errorCode = Integer.parseInt(e.getSQLState());
			debug("State: " + errorCode);
			e.printStackTrace();
			switch(result) {
			case 23505://vin taken
				result = 0;
				break;
			default:
				result = 2;			
			}
		}	
		return result;
	}

	@Override
	public int putOffer(Offer offer) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			String sql = "INSERT INTO offers (vin, username, offeramount)\r\n" + 
					"VALUES (?,?,?)\r\n" + 
					"ON CONFLICT (vin, username) DO UPDATE \r\n" + 
					"  SET vin = excluded.vin,\r\n" + 
					"  	username = excluded.username,\r\n" + 
					" 	offeramount = excluded.offeramount"; 
			PreparedStatement stmt = conn.prepareStatement(sql);
			int count = 1;
			stmt.setString(count++, offer.getVin());
			stmt.setString(count++, offer.getCustomerId());
			stmt.setDouble(count++, offer.getAmount());
			result = stmt.executeUpdate();	//should be 1 row updated
		} catch (SQLException e) {
			int errorCode = Integer.parseInt(e.getSQLState());
			debug("State: " + errorCode);
			e.printStackTrace();
			switch(result) {
			case 23505://PK taken
				result = 0;
				break;
			default:
				result = 2;			
			}
		}	
		return result;
	}

	@Override
	public boolean removeCar(String id) {
		// TODO Auto-generated method stub
		int result = 0;
		try {
			String sql = "delete from cars where vin = ?";
			debug(sql);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1,id);
			result = stmt.executeUpdate();
		} catch (SQLException e) {
			int errorCode = Integer.parseInt(e.getSQLState());
			debug("State: " + errorCode);
			e.printStackTrace();
		}	
		return result==1;
	}

	@Override
	public boolean saveCustomer(Customer c) {
		// TODO Auto-generated method stub
		int result = 0;
		boolean success = false;
		try {
			String sql = "insert into users (username, first_name, last_name, pin) values (?,?,?,?)";
			PreparedStatement stmt = conn.prepareStatement(sql);
			int count = 1;
			stmt.setString(count++, c.getUserName());
			stmt.setString(count++, c.getFirstName());
			stmt.setString(count++, c.getLastName());
			stmt.setString(count++, c.getPin());
			debug(stmt.toString());
			result = stmt.executeUpdate();
			success = true;
		} catch (SQLException e) {
			int errorCode = Integer.parseInt(e.getSQLState());
			debug("State: " + errorCode);
			e.printStackTrace();
			switch(result) {
			case 23505://username taken
				error("Username taken in users table");
				result = 0;
				break;
			default:
				result = 2;			
			}
		}	
		return success;
	}

	@Override
	public ArrayList<Offer> getOffers(String vin) {
		// TODO Auto-generated method stub

		ArrayList<Offer> offerList = new ArrayList<Offer>();
		String sql = "Select * from offers where vin = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, vin);
			ResultSet r = stmt.executeQuery();
			Offer tempOffer = new Offer();
			while(r.next() != false) {
				tempOffer = new Offer();
				tempOffer.setVin(r.getString(1));
				tempOffer.setCustomerId(r.getString(2));
				tempOffer.setAmount(r.getInt(3));
				offerList.add(tempOffer);
			}
			r.close();
		}catch(SQLException e) {
			debug("State: " + e.getSQLState());
			e.printStackTrace();
		}
		
		return ( offerList.size() != 0 ) ? offerList : null;		
		
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
	public boolean checkForTwoMatchingStrings(String username, String password, int field) {
		// TODO Query to Check that strings match
		boolean isMatch = true;
		String sql;
		int count = 0;
		int counter = 1;

		ResultSet r;
		try {
			PreparedStatement stmt;
			switch(field) {
			case 0:	//users
				sql = "SELECT COUNT(*) AS rowcount FROM users WHERE (username = ? AND pin = ?)";
				stmt = conn.prepareStatement(sql);
				stmt.setString(counter++, username);
				stmt.setString(counter++, password);
				r = stmt.executeQuery();
				r.next();
				count = r.getInt("rowcount");
				r.close();
				break;
			case 1: //cars
				sql = "SELECT COUNT(*) AS rowcount FROM users WHERE (username = ? AND pin = ?)";
				stmt = conn.prepareStatement(sql);
				stmt.setString(counter++, username);
				stmt.setString(counter++, password);
				r = stmt.executeQuery();
				r.next();
				count = r.getInt("rowcount");
				r.close();
				break;
			case 2:
				sql = "SELECT COUNT(*) AS rowcount FROM users WHERE (username = ? AND pin = ?)";
				stmt = conn.prepareStatement(sql);
				stmt.setString(counter++, username);
				stmt.setString(counter++, password);
				debug(stmt.toString());
				r = stmt.executeQuery();
				r.next();
				count = r.getInt("rowcount");
				r.close();
				break;
				
				default: 
					error("Choose a valid table");
			}

			isMatch = (count == 1) ? true : false;
			debug("Table " + field + " has " + count + " row(s).");
		} catch (SQLException e) {
			debug("State: " + e.getSQLState());
			e.printStackTrace();
		}	
		return isMatch;
	}

	@Override
	public ArrayList<Car> getOwnersCarList(String username) {
		// TODO Auto-generated method stub
		ArrayList<Car> tempList = new ArrayList<Car>();
		try {
			String sql = "Select * from owners where username = ?";
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, username);
			ResultSet r = stmt.executeQuery();
			Car tempCar;
			while(r.next() != false) {
				tempCar = new Car();
				tempCar.setVin(r.getString(1));
				tempCar.setOwner(r.getString(2));				
				tempCar.setName(r.getString(3));
				tempCar.setModel(r.getString(4));
				tempCar.setYear(r.getInt(5));
				tempCar.setRemainingPayment(r.getDouble(6));
				tempCar.toString();
				tempList.add(tempCar);
			}
			r.close();
		}catch(SQLException e) {
			debug("State: " + e.getSQLState());
			e.printStackTrace();
		}		
		return ( tempList.size() != 0 ) ? tempList : null;
	}

	@Override
	public ArrayList<Car> getEntireCarList() {
		// TODO Auto-generated method stub
		ArrayList<Car> tempList = new ArrayList<Car>();
		String sql = "Select * from cars";
		try {
			Statement stmt = conn.createStatement();
			ResultSet r = stmt.executeQuery(sql);
			Car tempCar = new Car();
			while(r.next() != false) {
				tempCar = new Car();
				tempCar.setVin(r.getString(1));
				tempCar.setModel(r.getString(2));				
				tempCar.setName(r.getString(3));
				tempCar.setYear(r.getInt(4));
				tempCar.setPrice(r.getDouble(5));
				tempCar.setRemainingPayment(r.getDouble(6));
				tempCar.toString();
				tempList.add(tempCar);
			}
			r.close();
		}catch(SQLException e) {
			debug("State: " + e.getSQLState());
			e.printStackTrace();
		}		
		return ( tempList.size() != 0 ) ? tempList : null;
	}

	@Override
	public Car singleCarFromCarsTable(String VIN) {
		// TODO Auto-generated method stub
		//Car oneCar = new Car("Ford", "mustang", 2019, 36000, VIN);
		Car singleCar = null;
		String sql = "Select * from cars where VIN = ?";
		try {
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1, VIN);
			ResultSet r = stmt.executeQuery();
			while(r.next() != false) {	//this SHOULD happen once bc quering on PK
				singleCar = new Car();
				singleCar.setVin(r.getString(1));
				singleCar.setModel(r.getString(2));				
				singleCar.setName(r.getString(3));
				singleCar.setYear(r.getInt(4));
				singleCar.setPrice(r.getDouble(5));
				singleCar.setRemainingPayment(r.getDouble(6));
				singleCar.toString();
			}
			r.close();
		}catch(SQLException e) {
			debug("State: " + e.getSQLState());
			e.printStackTrace();
		}		
		return singleCar;
	}

	@Override
	public boolean updateRemainingPayment(String VIN, double amount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isDuplicateString(String string, int field) {
		// TODO Auto-generated method stub
		boolean duplicate = true;
		String sql;
		int count = 0;
		ResultSet r;
		try {
			PreparedStatement stmt;
			switch(field) {
			case 0:	//users
				sql = "SELECT COUNT(*) AS rowcount FROM users where username = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, string);
				r = stmt.executeQuery();
				r.next();
				count = r.getInt("rowcount");
				r.close();
				break;
			case 1: //cars
				sql = "SELECT COUNT(*) AS rowcount FROM cars where vin = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, string);
				r= stmt.executeQuery();
				r.next();
				count = r.getInt("rowcount");
				r.close();
				break;
			case 2: //check isEmp in users
				sql = "SELECT COUNT(*) AS rowcount FROM users where (username = ? and isEmp = true ) ";
				stmt = conn.prepareStatement(sql);
				stmt.setString(1, string);
				r= stmt.executeQuery();
				r.next();
				count = r.getInt("rowcount");
				r.close();
				break;
				default: 
					debug("Choose a valid table");

			}

			duplicate = (count == 1) ? true : false;
			debug("MyTable has " + count + " row(s).");
		} catch (SQLException e) {
			debug("State: " + e.getSQLState());
			e.printStackTrace();
		}	
		return duplicate;
	}

	@Override
	public boolean removeOffer(String vin, String username) {
		
		int result = 0;
		try {
			String sql = "delete from offers where (vin = ? and username = ?)";
			debug(sql);
			PreparedStatement stmt = conn.prepareStatement(sql);
			stmt.setString(1,vin);
			stmt.setString(2,username);
			result = stmt.executeUpdate();
		} catch (SQLException e) {
			int errorCode = Integer.parseInt(e.getSQLState());
			debug("State: " + errorCode);
			e.printStackTrace();
		}	
		return result==1;
	}
	
	

}
