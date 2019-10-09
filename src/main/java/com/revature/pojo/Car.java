package com.revature.pojo;

public class Car {

	private String name;
	private String model;
	private int year;
	private double price;
	private String vin;
	private double remainingPayment;
	private String owner;
	
	public Car() {
		super();
	}	
	public Car(String name, String model, int year, double price, String vin) {
		super();
		this.name = name.toLowerCase();
		this.model = model.toLowerCase();
		this.year = year;
		this.price = price;
		this.vin = vin.toUpperCase();
		this.remainingPayment = price;
	}

	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
	}

	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public double getRemainingPayment() {
		return remainingPayment;
	}

	public void setRemainingPayment(double remainingPayment) {
		this.remainingPayment = remainingPayment;
	}	
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(remainingPayment);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((vin == null) ? 0 : vin.hashCode());
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Car other = (Car) obj;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (Double.doubleToLongBits(remainingPayment) != Double.doubleToLongBits(other.remainingPayment))
			return false;
		if (vin == null) {
			if (other.vin != null)
				return false;
		} else if (!vin.equals(other.vin))
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Car [name= " + name.toUpperCase() + ", model= " + model.toUpperCase() + ", year= " + year + ", price= " + price + ", vin= " + vin
				+ ", remainingPayment= " + remainingPayment + "]\n";
	}
	

	
}
