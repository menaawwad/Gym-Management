package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;

import javafx.scene.control.TextArea;

import javafx.scene.control.DatePicker;

public class addNewExpensesController implements Initializable{
	boolean flag;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtCost;
	@FXML
	private DatePicker datDate;
	@FXML
	private TextArea txtNote;
	@FXML
	private Button btnCancel;
	@FXML
	private Button btnSave;
	@FXML
	private ComboBox<String> cboType;
	
	private String[] Type = { "supplies", "equipment", "variable Expenses", "rent", "markting", "salaries","comunication", "other" };

	@FXML
	public void clickCancel(ActionEvent event) {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void clickSave(ActionEvent event) {
		flag = true;
		if (txtName.getText().equals("")) {
			flag = false;
			Function.alert("please enter Name");
		}
		if (datDate.getValue() == null) {
			flag = false;
			Function.alert("please enter Date");
		}
		if (txtCost.getText().equals("")) {
			flag = false;
			Function.alert("please enter Cost");
		}
		if (!Function.doubleNumbers(txtCost.getText())) {
			flag = false;
			Function.alert("Cost must be all numbers");
		}
		if(cboType.getValue() == null) {
			flag = false;
			Function.alert("you have to select type");
		}
		if (flag) {
			Expenses expenses = new Expenses(0, txtName.getText(), datDate.getValue(),
					Double.valueOf(txtCost.getText()),cboType.getValue(),txtNote.getText());
			DBConnect.AddNewExpenses(expenses);
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.close();
		}

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cboType.getItems().addAll(Type);		
	}
}
