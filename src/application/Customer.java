package application;

import java.time.LocalDate;

public class Customer {

	private int ID;
	private String name;
    private String customerId;
    private LocalDate birthday;
    private String adress;
    private String phoneNumber;
    private String Email;
    private String Gender;
    private String Status;
    
	public Customer(int id,String name, String customerId, LocalDate birthday, String adress, String phoneNumber, String email,
			String gender, String status) {
		this.ID = id;
		this.name = name;
		this.customerId = customerId;
		this.birthday = birthday;
		this.adress = adress;
		this.phoneNumber = phoneNumber;
		this.Email = email;
		this.Gender = gender;
		this.Status = status;
	}
	public Customer(String name, String customerId, LocalDate birthday, String adress, String phoneNumber, String email,
			String gender, String status) {
		this.name = name;
		this.customerId = customerId;
		this.birthday = birthday;
		this.adress = adress;
		this.phoneNumber = phoneNumber;
		this.Email = email;
		this.Gender = gender;
		this.Status = status;
	}
	
	public Customer() {		
	}
	
	public Customer(Customer other) {
		this.ID = other.getID();
		this.name = other.getName();
		this.customerId = other.getCustomerId();
		this.birthday = other.getBirthday();
		this.adress = other.getAdress();
		this.phoneNumber = other.getPhoneNumber();
		this.Email = other.getEmail();
		this.Gender = other.getGender();
		this.Status = other.getStatus();
	}
	
	
	
	public Customer(int iD, String name, String customerId) {
		ID = iD;
		this.name = name;
		this.customerId = customerId;
	}
	
	public int getID() {
		return this.ID;
	}
	
	public void setID(int id) {
		this.ID = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getGender() {
		return Gender;
	}

	public void setGender(String gender) {
		Gender = gender;
	}

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		this.Status = status;
	}

	@Override
	public String toString() {
		return "name: " + name + "  customerId: " + customerId + "  ID: " + ID;
	}
    
    
}
