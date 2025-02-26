package application;

import java.time.LocalDate;

public class Purchase {
	
	private int ID;
	private LocalDate Date;
	private int Quantity;
	private double Price;
	
	
	public Purchase(int iD, LocalDate date, int quantity, double price) {
		ID = iD;
		Date = date;
		Quantity = quantity;
		Price = price;
	}
	public Purchase(LocalDate date, int quantity, double price) {
		Date = date;
		Quantity = quantity;
		Price = price;
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public LocalDate getDate() {
		return Date;
	}
	public void setDate(LocalDate date) {
		Date = date;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	public double getPrice() {
		return Price;
	}
	public void setPrice(double price) {
		Price = price;
	}
	
	@Override
	public String toString() {
		return "Purchase [ID=" + ID + ", Date=" + Date + ", Quantity=" + Quantity + ", Price=" + Price + "]";
	}
	
	
	
}
