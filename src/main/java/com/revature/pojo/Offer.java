package com.revature.pojo;

public class Offer {
	private int amount;
	private int customerId;
	private int carId;
	private boolean status = false; //false = rejected so far
	
	public Offer() {
		super();
	}
	public Offer(int amount, int customerId, int carId) {
		super();
		this.amount = amount;
		this.customerId = customerId;
		this.carId = carId;
	}
	
	
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getCustomerId() {
		return customerId;
	}
	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}
	public int getCarId() {
		return carId;
	}
	public void setCarId(int carId) {
		this.carId = carId;
	}
	public boolean isStatus() {
		return status;
	}
	public void setStatus(boolean status) {
		this.status = status;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + amount;
		result = prime * result + carId;
		result = prime * result + customerId;
		result = prime * result + (status ? 1231 : 1237);
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
		Offer other = (Offer) obj;
		if (amount != other.amount)
			return false;
		if (carId != other.carId)
			return false;
		if (customerId != other.customerId)
			return false;
		if (status != other.status)
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Offer [amount=" + amount + ", customerId=" + customerId + ", carId=" + carId + ", status=" + status
				+ "]";
	}	
	
}
