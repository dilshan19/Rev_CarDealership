package com.revature.pojo;

public class Offer {
	private double amount;
	private String customerId;
	private String vin;
	private boolean status = false; //false = rejected so far
	
	public Offer() {
		super();
	}
	public Offer(int amount, String customerId, String vin, boolean status) {
		super();
		this.amount = amount;
		this.customerId = customerId;
		this.vin = vin;
		this.status = status;
	}

	public double getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getVin() {
		return vin;
	}
	public void setVin(String vin) {
		this.vin = vin;
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
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((customerId == null) ? 0 : customerId.hashCode());
		result = prime * result + (status ? 1231 : 1237);
		result = prime * result + ((vin == null) ? 0 : vin.hashCode());
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
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (customerId == null) {
			if (other.customerId != null)
				return false;
		} else if (!customerId.equals(other.customerId))
			return false;
		if (status != other.status)
			return false;
		if (vin == null) {
			if (other.vin != null)
				return false;
		} else if (!vin.equals(other.vin))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Offer [amount=" + amount + ", customerId=" + customerId + ", vin=" + vin + ", status=" + status + "]";
	}	
	
}
