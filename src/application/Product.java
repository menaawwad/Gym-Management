package application;

import java.time.LocalDate;

public class Product {

	private int ID;
	private String Name;
	private LocalDate RegisterDate;
	private LocalDate LastUpdate;
	private String Barcode;
	private double CostPrice;
	private double SellPrice;
	private double MinPrice;
	private double Weight;
	private int Stock;
	private int MinOrder;
	
	public Product(int iD, String name, LocalDate registerDate, LocalDate lastUpdate, String barcode, double costPrice,
			double sellPrice, double minPrice, double weight, int stock, int minOrder) {
		ID = iD;
		Name = name;
		RegisterDate = registerDate;
		LastUpdate = lastUpdate;
		Barcode = barcode;
		CostPrice = costPrice;
		SellPrice = sellPrice;
		MinPrice = minPrice;
		Weight = weight;
		Stock = stock;
		MinOrder = minOrder;
	}

	public Product(String name, LocalDate registerDate, LocalDate lastUpdate, String barcode, double costPrice,
			double sellPrice, double minPrice, double weight, int stock, int minOrder) {
		Name = name;
		RegisterDate = registerDate;
		LastUpdate = lastUpdate;
		Barcode = barcode;
		CostPrice = costPrice;
		SellPrice = sellPrice;
		MinPrice = minPrice;
		Weight = weight;
		Stock = stock;
		MinOrder = minOrder;
	}
	public Product(String name,String barcode, double costPrice,
			double sellPrice, double minPrice, double weight, int minOrder) {
		Name = name;
		Barcode = barcode;
		CostPrice = costPrice;
		SellPrice = sellPrice;
		MinPrice = minPrice;
		Weight = weight;
		MinOrder = minOrder;
	}
	
	
	
	public Product(Product other) {
		this.setID(other.getID());
		this.setName(other.getName());
		this.setRegisterDate(other.getRegisterDate());
		this.setLastUpdate(other.getLastUpdate());
		this.setBarcode(other.getBarcode());
		this.setCostPrice(other.getCostPrice());
		this.setSellPrice(other.getSellPrice());
		this.setMinPrice(other.getMinPrice());
		this.setWeight(other.getWeight());
		this.setStock(other.getStock());
		this.setMinOrder(other.getMinOrder());
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

	public LocalDate getRegisterDate() {
		return RegisterDate;
	}

	public void setRegisterDate(LocalDate registerDate) {
		RegisterDate = registerDate;
	}

	public LocalDate getLastUpdate() {
		return LastUpdate;
	}

	public void setLastUpdate(LocalDate lastUpdate) {
		LastUpdate = lastUpdate;
	}

	public String getBarcode() {
		return Barcode;
	}

	public void setBarcode(String barcode) {
		Barcode = barcode;
	}

	public double getCostPrice() {
		return CostPrice;
	}

	public void setCostPrice(double costPrice) {
		CostPrice = costPrice;
	}

	public double getSellPrice() {
		return SellPrice;
	}

	public void setSellPrice(double sellPrice) {
		SellPrice = sellPrice;
	}

	public double getMinPrice() {
		return MinPrice;
	}

	public void setMinPrice(double minPrice) {
		MinPrice = minPrice;
	}

	public double getWeight() {
		return Weight;
	}

	public void setWeight(double weight) {
		Weight = weight;
	}

	public int getStock() {
		return Stock;
	}

	public void setStock(int stock) {
		Stock = stock;
	}

	public int getMinOrder() {
		return MinOrder;
	}

	public void setMinOrder(int minOrder) {
		MinOrder = minOrder;
	}

	@Override
	public String toString() {
		return "ID: " + ID + " Name: " + Name + " Barcode: " + Barcode;
	}	
}
