package application;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

import java.sql.Date;
import java.time.LocalDate;

import javafx.event.ActionEvent;

import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class addNewToDoController {
	@FXML
	private TextArea txtToDo;
	@FXML
	private Button btnCancel;
	@FXML
	private Button btnSave;
	@FXML
	private DatePicker datePicker;

	@FXML
	public void clickCancel(ActionEvent event) {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}

	@FXML
	void onActionDatePicker(ActionEvent event) {
		if (datePicker.getValue() != null) {
			if (datePicker.getValue().isBefore(LocalDate.now())) {
				Function.alert("You can't select date before today");
				datePicker.setValue(null);
			}
		}
	}

	@FXML
	public void clickSave(ActionEvent event) {
		if (!txtToDo.getText().equals("")) {
			if (datePicker.getValue() != null) {
				ToDo todo = new ToDo(0, txtToDo.getText(), Date.valueOf(datePicker.getValue()), "In progress");
				DBConnect.AddNewToDo(todo);
			} else {
				ToDo todo = new ToDo(0, txtToDo.getText(), null, "In progress");
				DBConnect.AddNewToDo(todo);
			}
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.close();
		} else {
			Function.alert("you have to write somthing");
		}
	}
}
