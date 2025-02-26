package application;
public class SaleDetails {

	private int ID;
	private int SaleID;
	private int ProductID;
	private String productName;
	private double UnitPrice;
	private int Quantity;
	private double RowPrice;
	private double RowProfit;
		
	public SaleDetails(int iD, int saleID, int productID, String productName, double unitPrice, int quantity,
			double rowPrice, double rowProfit) {
		ID = iD;
		SaleID = saleID;
		ProductID = productID;
		this.productName = productName;
		UnitPrice = unitPrice;
		Quantity = quantity;
		RowPrice = rowPrice;
		RowProfit = rowProfit;
	}
	
	public SaleDetails(int saleID, int productID, String productName, double unitPrice, int quantity, double rowPrice,
			double rowProfit) {
		SaleID = saleID;
		ProductID = productID;
		this.productName = productName;
		UnitPrice = unitPrice;
		Quantity = quantity;
		RowPrice = rowPrice;
		RowProfit = rowProfit;
	}

	public SaleDetails(SaleDetails other){
		this.setID(other.getID());
		this.setSaleID(other.getSaleID());
		this.setProductID(other.getProductID());
		this.setProductName(other.getProductName());
		this.setUnitPrice(other.getUnitPrice());
		this.setQuantity(other.getQuantity());
		this.setRowPrice(other.getRowPrice());
		this.setRowProfit(other.getRowProfit());
	}

	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public int getSaleID() {
		return SaleID;
	}
	public void setSaleID(int saleID) {
		SaleID = saleID;
	}
	public int getProductID() {
		return ProductID;
	}
	public void setProductID(int productID) {
		ProductID = productID;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getUnitPrice() {
		return UnitPrice;
	}
	public void setUnitPrice(double unitPrice) {
		UnitPrice = unitPrice;
	}
	public int getQuantity() {
		return Quantity;
	}
	public void setQuantity(int quantity) {
		Quantity = quantity;
	}
	public double getRowPrice() {
		return RowPrice;
	}
	public void setRowPrice(double rowPrice) {
		RowPrice = rowPrice;
	}
	public double getRowProfit() {
		return RowProfit;
	}
	public void setRowProfit(double rowProfit) {
		RowProfit = rowProfit;
	}
	
	
	
}
