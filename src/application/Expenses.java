package application;

import java.time.LocalDate;

public class Expenses {

	private int ID;
	private String Name;
	private LocalDate expensesDate;
	private double Cost;
	private String Type;
	private String Note;

	public Expenses(int iD, String name, LocalDate expensesDate, double cost, String type, String note) {
		ID = iD;
		Name = name;
		this.expensesDate = expensesDate;
		Cost = cost;
		Type = type;
		Note = note;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public LocalDate getExpensesDate() {
		return expensesDate;
	}

	public void setExpensesDate(LocalDate expensesDate) {
		this.expensesDate = expensesDate;
	}

	public double getCost() {
		return Cost;
	}

	public void setCost(double price) {
		Cost = price;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	public String getNote() {
		return Note;
	}

	public void setNote(String note) {
		Note = note;
	}

	@Override
	public String toString() {
		return "Expenses [ID=" + ID + ", Name=" + Name + ", expensesDate=" + expensesDate + ", Cost=" + Cost + ", Type="
				+ Type + ", Note=" + Note + "]";
	}

}
