package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class confirmationNewController {

    @FXML
    private Button btnCancel;

    @FXML
    private Button btnConfirm;

    @FXML
    private TextField txtEnd;

    @FXML
    private TextField txtIDNumber;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtStart;

    Subscription subscription;
    public void getData(String name,String IDNum,String daysLift,Subscription sub) {
		txtName.setText(name);
		txtIDNumber.setText(IDNum);
		txtStart.setText(sub.getStartinDate().toString());
		txtEnd.setText(sub.getEndDate().toString());
		subscription = new Subscription(sub);
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
    	DBConnect.AddSubscription(subscription);
		Main.close = true;
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
    }

}
