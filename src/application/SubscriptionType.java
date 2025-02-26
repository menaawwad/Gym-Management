package application;

public class SubscriptionType {

	private int ID;
	private String type;
	private double price;
	
	public SubscriptionType(int iD, String type, double price) {
		ID = iD;
		this.type = type;
		this.price = price;
	}
	public SubscriptionType( String type, double price) {
		this.type = type;
		this.price = price;
	}
	
	public SubscriptionType(SubscriptionType other) {
		this.setID(other.getID());
		this.setType(other.getType());
		this.setPrice(other.getPrice());
	}
	
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	
}
