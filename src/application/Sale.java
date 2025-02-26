package application;

import java.time.LocalDate;

public class Sale {

	private int ID;
	private LocalDate Date;
	private int Customer;
	private String CustomerName;
	private String CustomerCardID;
	private int Quantity;
	private double TotalPrice;
	private double SaleProfit;
	private String Note;

	public Sale(int iD, LocalDate date, int customer, String customerName, String customerCardID, int quantity,
			double totalPrice, double saleProfit, String note) {
		ID = iD;
		Date = date;
		Customer = customer;
		CustomerName = customerName;
		CustomerCardID = customerCardID;
		Quantity = quantity;
		TotalPrice = totalPrice;
		SaleProfit = saleProfit;
		Note = note;

	}

	public Sale(LocalDate date, int customer, String customerName, String customerCardID, int quantity,
			double totalPrice, double saleProfit, String note) {
		Date = date;
		Customer = customer;
		CustomerName = customerName;
		CustomerCardID = customerCardID;
		Quantity = quantity;
		TotalPrice = totalPrice;
		SaleProfit = saleProfit;
		Note = note;
	}

	public Sale(Sale other) {
		this.setID(other.getID());
		this.setCustomer(other.getCustomer());
		this.setCustomerName(other.getCustomerName());
		this.setCustomerCardID(other.getCustomerCardID());
		this.setQuantity(other.getQuantity());
		this.setTotalPrice(other.getTotalPrice());
		this.setSaleProfit(other.getSaleProfit());
		this.setNote(other.getNote());
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

	public int getCustomer() {
		return Customer;
	}

	public void setCustomer(int customer) {
		Customer = customer;
	}

	public String getCustomerName() {
		return CustomerName;
	}

	public void setCustomerName(String customerName) {
		CustomerName = customerName;
	}

	public String getCustomerCardID() {
		return CustomerCardID;
	}

	public void setCustomerCardID(String customerCardID) {
		CustomerCardID = customerCardID;
	}

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}

	public double getTotalPrice() {
		return TotalPrice;
	}

	public void setTotalPrice(double totalPrice) {
		TotalPrice = totalPrice;
	}

	public double getSaleProfit() {
		return SaleProfit;
	}

	public void setSaleProfit(double saleProfit) {
		SaleProfit = saleProfit;
	}

	public String getNote() {
		return Note;
	}

	public void setNote(String note) {
		Note = note;
	}

	@Override
	public String toString() {
		return "Sale [ID=" + ID + ", Date=" + Date + ", Customer=" + Customer + ", CustomerName=" + CustomerName
				+ ", CustomerCardID=" + CustomerCardID + ", Quantity=" + Quantity + ", TotalPrice=" + TotalPrice
				+ ", SaleProfit=" + SaleProfit + ", Note=" + Note + "]";
	}

}
