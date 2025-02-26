package application;

import java.sql.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class confirmationExtendController {

	@FXML
	private Button btnCancel;

	@FXML
	private Button btnConfirm;

	@FXML
	private TextField txtEnd;

	@FXML
	private TextField txtExtendedDays;

	@FXML
	private TextField txtIDNumber;

	@FXML
	private TextField txtName;

	@FXML
	private TextField txtStart;

	@FXML
	private TextField txtTotalDays;

	Subscription subscription;

	public void getData(String name, String IDNum, String daysLift, Subscription sub, Date oldDate) {
		txtName.setText(name);
		txtIDNumber.setText(IDNum);
		txtTotalDays.setText(daysLift);
		txtStart.setText(sub.getStartinDate().toString());
		txtEnd.setText(sub.getEndDate().toString());
		subscription = new Subscription(sub);
		txtExtendedDays.setText(Function.daysBetween(oldDate.toLocalDate(), sub.getEndDate().toLocalDate()));
	}

	@FXML
	void clickCancel(ActionEvent event) {
		Main.close = false;
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}

	@FXML
	void clickConfirm(ActionEvent event) {

		if (subscription.getStatus().equals("Freeze"))
			DBConnect.FreezeSubscription(subscription);
		else
			DBConnect.AddSubscription(subscription);
		Main.close = true;
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}

}
