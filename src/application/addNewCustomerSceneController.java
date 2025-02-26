package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ChoiceBox;

public class addNewCustomerSceneController implements Initializable {

	@FXML
	private TextField nameTextFieldANC;
	@FXML
	private TextField idTextFieldANC;
	@FXML
	private TextField addressTextFieldANC;
	@FXML
	private DatePicker birthdayDatePicker;
	@FXML
	private TextField emailTextFieldANC;
	@FXML
	private TextField phoneNumberTextFieldANC;
	@FXML
	private ChoiceBox<String> genderChoiceBox;

	private String[] gender = { "Male", "Female" };

	@FXML
	private Button saveButton;
	@FXML
	private Button cancelButton;

	@FXML
	public void saveClick(ActionEvent event) {
		oregnal();
		boolean flag = true;
		if (nameTextFieldANC.getText().isEmpty()) {
			Function.alert("please enter name");
			nameTextFieldANC.setStyle("-fx-border-color: red;");
			flag = false;
		}
		if (idTextFieldANC.getText().isEmpty()) {
			Function.alert("please enter cusomer ID");
			idTextFieldANC.setStyle("-fx-border-color: red;");
			flag = false;
		}
		if (!Function.allNumbers(idTextFieldANC.getText()) || idTextFieldANC.getText().length() != 9) {
			Function.alert("customer ID must be 9 numbers");
			idTextFieldANC.setStyle("-fx-border-color: red;");
			flag = false;
		}
		if (!DBConnect.ifCustomerExist(idTextFieldANC.getText())) {
			Function.alert("customer all ready exist");
			flag = false;
		}
		if (birthdayDatePicker.getValue() == null) {
			Function.alert("please enter birthday");
			birthdayDatePicker.setStyle("-fx-border-color: red; -fx-font-size: 18;");
			flag = false;
		}
		if (phoneNumberTextFieldANC.getText().isEmpty()) {
			Function.alert("please enter phone number");
			phoneNumberTextFieldANC.setStyle("-fx-border-color: red;");
			flag = false;
		}
		if (!Function.allNumbers(phoneNumberTextFieldANC.getText())
				|| phoneNumberTextFieldANC.getText().length() != 10) {
			Function.alert("phone number must be 10 numbers");
			phoneNumberTextFieldANC.setStyle("-fx-border-color: red;");
			flag = false;
		}
		if (genderChoiceBox.getValue() == null) {
			Function.alert("please select gender");
			genderChoiceBox.setStyle("-fx-border-color: red; -fx-font-size: 18;");
			flag = false;
		}
		if (flag) {
			Customer customer = new Customer(nameTextFieldANC.getText(), idTextFieldANC.getText(),
					birthdayDatePicker.getValue(), addressTextFieldANC.getText(), phoneNumberTextFieldANC.getText(),
					emailTextFieldANC.getText(), genderChoiceBox.getValue(), "");
			DBConnect.AddNewCustomer(customer);
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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		genderChoiceBox.getItems().addAll(gender);
		oregnal();
	}

	private void oregnal() {
		nameTextFieldANC.setStyle("");
		idTextFieldANC.setStyle("");
		birthdayDatePicker.setStyle("-fx-font-size: 18;");
		phoneNumberTextFieldANC.setStyle("");
		genderChoiceBox.setStyle("-fx-font-size: 18;");
	}

}
