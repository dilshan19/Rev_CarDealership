package com.revature.test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.revature.dao.CarDealershipDAOImpl;
import com.revature.pojo.Employee;

public class CarDealershipDAOTest {
	CarDealershipDAOImpl carDao = new CarDealershipDAOImpl();
	Employee employee = new Employee();
	

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	@Test
	public void addCarTest() {
		  //assertEquals(1, carDao.addCar("Ford", "Mustang", 1234, 23089)); 
	}

	@Test
	public void testStateReturnForEmployeeOfferReview() {
		assertEquals(0, employee.reviewOffer() );
	}
}
