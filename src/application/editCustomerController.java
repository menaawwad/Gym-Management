package application;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import java.time.LocalDate;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class editCustomerController {
	@FXML
	private TextField nameTextField;
	@FXML
	private TextField idTextField;
	@FXML
	private TextField addressTextField;
	@FXML
	private DatePicker birthdayDatePicker;
	@FXML
	private TextField emailTextField;
	@FXML
	private TextField phoneNumberTextField;
	@FXML
	private Label labelCusomerNumber;
	@FXML
	private Button updateButton;
	@FXML
	private Button cancelButton;

	private LocalDate birthday;
	private Customer newCustomer;

	public void getCustomer(Customer customer) {
		labelCusomerNumber.setText("Customer Number : " + customer.getID());
		nameTextField.setText(customer.getName());
		idTextField.setText(customer.getCustomerId());
		addressTextField.setText(customer.getAdress());
		emailTextField.setText(customer.getEmail());
		phoneNumberTextField.setText(customer.getPhoneNumber());
		birthdayDatePicker.setValue(customer.getBirthday());

		birthday = customer.getBirthday();
		newCustomer = new Customer(customer);
		oregnal();
	}

	@FXML
	public void updateClick(ActionEvent event) {
		oregnal();
		boolean flag = true;
		if (nameTextField.getText().isEmpty()) {
			Function.alert("please enter name");
			nameTextField.setStyle("-fx-border-color: red;");
			flag = false;
		}
		if (birthdayDatePicker.getValue() == null) {
			Function.alert("please enter birthday");
			birthdayDatePicker.setStyle("-fx-border-color: red; -fx-font-size: 18;");
			flag = false;
		}
		if (phoneNumberTextField.getText().isEmpty()) {
			Function.alert("please enter phone number");
			phoneNumberTextField.setStyle("-fx-border-color: red;");
			flag = false;
		}
		if (!Function.allNumbers(phoneNumberTextField.getText()) || phoneNumberTextField.getText().length() != 10) {
			Function.alert("phone number must be 10 numbers");
			phoneNumberTextField.setStyle("-fx-border-color: red;");
			flag = false;
		}
		if (birthdayDatePicker.getValue() == null) {
			Function.alert("please enter birthday");
			birthdayDatePicker.setStyle("-fx-border-color: red; -fx-font-size: 18;");
			flag = false;
		}
		if (flag) {
			newCustomer.setName(nameTextField.getText());
			if (birthdayDatePicker.getValue() == null)
				newCustomer.setBirthday(birthday);
			else
				newCustomer.setBirthday(birthdayDatePicker.getValue());
			newCustomer.setAdress(addressTextField.getText());
			newCustomer.setEmail(emailTextField.getText());
			newCustomer.setPhoneNumber(phoneNumberTextField.getText());
			DBConnect.updateCustomer(newCustomer);
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.close();
		}
	}

	@FXML
	public void cancelClick(ActionEvent event) {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}

	private void oregnal() {
		nameTextField.setStyle("");
		birthdayDatePicker.setStyle("-fx-font-size: 18;");
		phoneNumberTextField.setStyle("");
	}
}
