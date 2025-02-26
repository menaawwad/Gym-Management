package application;

public class PurchaseDetails {

	private int ID;
	private int PurchasID;
	private int ProductID;
	private String productName;
	private double UnitPrice;
	private int Quantity;
	private double MinPrice;
	private double SellPrice;
	private double RowPrice;

	public PurchaseDetails(int iD, int purchasID, int productID, String productName, double unitPrice, int quantity,
			double rowPrice, double minPrice, double sellPrice) {
		ID = iD;
		PurchasID = purchasID;
		ProductID = productID;
		this.productName = productName;
		UnitPrice = unitPrice;
		Quantity = quantity;
		MinPrice = minPrice;
		SellPrice = sellPrice;
		RowPrice = rowPrice;
	}

	public PurchaseDetails(int purchasID, int productID, String productName, double unitPrice, int quantity,
			double rowPrice, double minPrice, double sellPrice) {
		PurchasID = purchasID;
		ProductID = productID;
		this.productName = productName;
		UnitPrice = unitPrice;
		Quantity = quantity;
		MinPrice = minPrice;
		SellPrice = sellPrice;
		RowPrice = rowPrice;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getPurchasID() {
		return PurchasID;
	}

	public void setPurchasID(int purchasID) {
		PurchasID = purchasID;
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

	public double getMinPrice() {
		return MinPrice;
	}

	public void setMinPrice(double minPrice) {
		MinPrice = minPrice;
	}

	public double getSellPrice() {
		return SellPrice;
	}

	public void setSellPrice(double sellPrice) {
		SellPrice = sellPrice;
	}

	@Override
	public String toString() {
		return "PurchaseDetails [ID=" + ID + ", PurchasID=" + PurchasID + ", ProductID=" + ProductID + ", productName="
				+ productName + ", UnitPrice=" + UnitPrice + ", Quantity=" + Quantity + ", RowPrice=" + RowPrice + "]";
	}

}
