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

public class editToDoController {
	@FXML
	private TextArea txtToDo;
	@FXML
	private Button btnCancel;
	@FXML
	private Button btnSave;
	@FXML
	private DatePicker datePicker;

	ToDo newtodo;

	public void getData(ToDo todo) {
		txtToDo.setText(todo.getTodo());
		if (todo.getDate() != null)
			datePicker.setValue(todo.getDate().toLocalDate());
		newtodo = todo;
	}

	@FXML
	void onActionDatePicker(ActionEvent event) {
		if (datePicker.getValue() != null) {
			if (datePicker.getValue().isBefore(LocalDate.now())) {
				Function.alert("You can't select date before today");
				if (newtodo.getDate() != null)
					datePicker.setValue(newtodo.getDate().toLocalDate());
				else {
					datePicker.setValue(null);
				}
			}
		}
	}

	@FXML
	public void clickCancel(ActionEvent event) {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}

	@FXML
	public void clickSave(ActionEvent event) {
		if (!txtToDo.getText().equals("")) {
			newtodo.setTodo(txtToDo.getText());
			if (datePicker.getValue() != null) {
				newtodo.setDate(Date.valueOf(datePicker.getValue()));
				DBConnect.updateToDo(newtodo);
			} else {
				newtodo.setDate(null);
				DBConnect.updateToDo(newtodo);
			}
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.close();
		} else {
			Function.alert("you have to write somthing");
		}
	}
}
