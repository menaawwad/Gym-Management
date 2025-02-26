package application;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class confirmationPauseController {
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtIDNumber;
	@FXML
	private TextField txtDays;
	@FXML
	private Button btnCancel;
	@FXML
	private Button btnConfirm;
	
	Subscription subscription;
	
	public void getData(String name,String IDNum,String daysLift,Subscription sub) {
		txtName.setText(name);
		txtIDNumber.setText(IDNum);
		txtDays.setText(daysLift);
		subscription = new Subscription(sub);
	}
	
	@FXML
	public void clickCancel(ActionEvent event) {
		Main.close = false;
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}
	
	@FXML
	public void clickConfirm(ActionEvent event) {
		DBConnect.FreezeSubscription(subscription);
		Main.close = true;
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}
}
