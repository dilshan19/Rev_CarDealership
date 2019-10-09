package com.revature.test;

import static com.revature.util.LoggerUtil.debug;
import static org.junit.Assert.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.postgresql.core.BaseStatement;

import com.revature.dao.CarDealershipDAOImpl;
import com.revature.jdbc.util.ConnectionFactory;
import com.revature.pojo.Car;
import com.revature.pojo.Customer;
import com.revature.pojo.Employee;

@RunWith(MockitoJUnitRunner.class)
public class CarDealershipDAOTest {
	private CarDealershipDAOImpl carDAO = new CarDealershipDAOImpl();
	Employee employee = new Employee();
	private Car testCar = new Car();
	private Customer testCust;
	
	public CarDealershipDAOTest() throws SQLException {
		super();
	}
	
	@Mock
	private Connection conn;
	
	@Mock
    private PreparedStatement mockPreparedStmnt;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		
	}

	@Before
	public void setUp() throws Exception {
		
		
		
		/*
		 * when(conn.prepareStatement(Matchers.anyString())).thenReturn(
		 * mockPreparedStmnt); testCar = new Car(); testCust = new Customer();
		 * carDAO.setConn(conn);
		 */
		 
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}	
	
	@Test
	public void testStateReturnForEmployeeOfferReview() {
		//assertEquals(0, employee.reviewOffer() );
	}

	@Test
	public void testDuplicateStringMethod() {	// must turn off the setup functions for this to pass...
		assertEquals(true, carDAO.isDuplicateString("JYAVG04E38A012345",1));
		assertEquals(false, carDAO.isDuplicateString("JYAVG04E38A012346",1));
	}
	
	@Test
	public void testAddCarFuncOutput() {
		Car temp_car = new Car( "Mercedez", "SLK350", 2010, 19500.89, "1FMCU92104DA46983");
		assertEquals(1, carDAO.addCar(temp_car,1));
	}
	
	@Test
	public void testThatCarIsCreated() {	//test that the right car obj is created
		testCar.setModel("Chevy");
		testCar.setName("Avalanche");
		testCar.setYear(2006);
		testCar.setVin("JYAVG04E38A012347");
		testCar.setPrice(10000.50);
		testCar.setRemainingPayment(10000.50);
		
		carDAO.addCar(testCar,1);
		System.out.println("Mock car created");	

		String sql = "insert into cars (vin, make, model, model_year, price, remaining) "
				+ "values ('"+testCar.getVin()+"','"+testCar.getName()+"','"+testCar.getModel()+"',"
		+testCar.getYear()+","+testCar.getPrice()+","+testCar.getRemainingPayment()+")";
		try {
			Mockito.verify(mockPreparedStmnt, times(1)).executeUpdate();
			System.out.println("Verify complete");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testNoDuplicateUsernamesInUserTable() {
		assertEquals(true, carDAO.isDuplicateString("GReyes1", 0));
		assertEquals(true, carDAO.isDuplicateString("GReyes2", 0));
		assertEquals(false, carDAO.isDuplicateString("GReyes3", 0));
		assertEquals(true, carDAO.isDuplicateString("JYAVG04E38A012345", 1));

	}
	
	@Test
	public void testSavingCustomerFunc() {
		testCust.setUserName("Gerardo");
		testCust.setFirstName("Reyes");
		testCust.setLastName("GReyes1");
		testCust.setPin("1111");

		assertEquals(true, carDAO.saveCustomer(testCust)); //manually check DB...
		try {
			Mockito.verify(mockPreparedStmnt, times(1)).executeUpdate();
			System.out.println("Verify complete");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Test	
	public void testTwoStringMatchingFunc() {
		assertEquals(true, carDAO.checkForTwoMatchingStrings("GReyes1", "1111", 2)); //check that these strings are in the table 2 (Users -> check enum)
		assertEquals(false, carDAO.checkForTwoMatchingStrings("GReyes2", "1111", 2)); 		
		assertEquals(false, carDAO.checkForTwoMatchingStrings("GReyes1", "11112", 2)); 		
	}
	
	@Test	
	public void testViewingAllCars() {
		ArrayList<Car> tempList = new ArrayList<Car>();
		//assertEquals(true, carDAO.getEntireCarList()); 
	
	}
	
	@Test	
	public void testCalculateMonthlyPayment() {
		
		double principal = 37999.0;
		int years = 6;
		
		Double result = employee.calcMonthlyPayments(principal, years);
		assertEquals( true , Math.round(result) == 649);
		assertEquals( false , Math.round(result) == 650);

	}
	

}
