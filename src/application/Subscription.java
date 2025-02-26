package application;

import java.sql.Date;

public class Subscription {
	private int Id;
	private int customerNumber;
	private int subscriptionType;
	private String strsubscriptionType;
	private double price;
	private Date registrationdate;
	private Date StartinDate;
	private Date EndDate;
	private Date lastUpdate;
	private Date PauseDate;
	private String status;

	public Subscription(int id, int customerNumber, int subscriptionType, String strsubscriptionType, double price,
			Date registrationdate, Date startinDate, Date endDate, Date lastUpdate, Date pauseDate, String status) {
		this.Id = id;
		this.customerNumber = customerNumber;
		this.subscriptionType = subscriptionType;
		this.strsubscriptionType = strsubscriptionType;
		this.price = price;
		this.registrationdate = registrationdate;
		this.StartinDate = startinDate;
		this.EndDate = endDate;
		this.lastUpdate = lastUpdate;
		this.PauseDate = pauseDate;
		this.status = status;
	}

	public Subscription(int customerNumber, int subscriptionType, String strsubscriptionType, double price,
			Date registrationdate, Date startinDate, Date endDate, Date lastUpdate, Date pauseDate, String status) {
		this.customerNumber = customerNumber;
		this.subscriptionType = subscriptionType;
		this.strsubscriptionType = strsubscriptionType;
		this.price = price;
		this.registrationdate = registrationdate;
		this.StartinDate = startinDate;
		this.EndDate = endDate;
		this.lastUpdate = lastUpdate;
		this.PauseDate = pauseDate;
		this.status = status;
	}

	public Subscription(Subscription other) {
		this.setId(other.getId());
		this.setCustomerNumber(other.getCustomerNumber());
		this.setSubscriptionType(other.getSubscriptionType());
		this.setStrsubscriptionType(other.getStrsubscriptionType());
		this.setPrice(other.getPrice());
		this.setRegistrationdate(other.getRegistrationdate());
		this.setStartinDate(other.getStartinDate());
		this.setEndDate(other.getEndDate());
		this.setLastUpdate(other.getLastUpdate());
		this.setPauseDate(other.getPauseDate());
		this.setStatus(other.getStatus());
	}

	public String getStrsubscriptionType() {
		return strsubscriptionType;
	}

	public void setStrsubscriptionType(String strsubscriptionType) {
		this.strsubscriptionType = strsubscriptionType;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(int customerNumber) {
		this.customerNumber = customerNumber;
	}

	public int getSubscriptionType() {
		return subscriptionType;
	}

	public void setSubscriptionType(int subscriptionType) {
		this.subscriptionType = subscriptionType;
	}

	public double getPrice() {
		return this.price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Date getRegistrationdate() {
		return registrationdate;
	}

	public void setRegistrationdate(Date registrationdate) {
		this.registrationdate = registrationdate;
	}

	public Date getStartinDate() {
		return StartinDate;
	}

	public void setStartinDate(Date startinDate) {
		StartinDate = startinDate;
	}

	public Date getEndDate() {
		return EndDate;
	}

	public void setEndDate(Date endDate) {
		EndDate = endDate;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}

	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

	public Date getPauseDate() {
		return PauseDate;
	}

	public void setPauseDate(Date PauseDate) {
		this.PauseDate = PauseDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Subscription [Id=" + Id + ", customerNumber=" + customerNumber + ", subscriptionType="
				+ subscriptionType + ", registrationdate=" + registrationdate + ", StartinDate=" + StartinDate
				+ ", EndDate=" + EndDate + ", lastUpdate=" + lastUpdate + ", status=" + status + "]";
	}

}
