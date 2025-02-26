package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;

import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class expensesMainSceneController implements Initializable {
	int sum;
	@FXML
	private Button btnAddNewExpenses;
	@FXML
	private TextField txtSearch;
	@FXML
	private Button btnRefrech;
	@FXML
	private DatePicker datFrom;
	@FXML
	private DatePicker datTo;
	@FXML
	private Button btnSearchByDate;
	@FXML
	private TableView<Expenses> tblExpenses;
	@FXML
	private TableColumn<Expenses, String> columnID;
	@FXML
	private TableColumn<Expenses, String> columnName;
	@FXML
	private TableColumn<Expenses, String> columnType;
	@FXML
	private TableColumn<Expenses, String> columnDate;
	@FXML
	private TableColumn<Expenses, String> columnCost;
	@FXML
	private TableColumn<Expenses, String> columnNote;
	@FXML
	private TableColumn<Expenses, Void> columnAction;

	@FXML
	private TextField txtSum;

	@FXML
	private ComboBox<String> cboFilter;

	private String[] Type = { "All", "supplies", "equipment", "variable Expenses", "rent", "markting", "salaries",
			"comunication", "other" };

	String query = "SELECT * FROM `tblexpenses` WHERE ID LIKE '%%' OR Name LIKE '%%' ORDER BY `tblexpenses`.`ID` DESC";
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		cboFilter.getItems().addAll(Type);
		loadData();
		try {
			
			refresh();
		} catch (IOException e) {
			e.printStackTrace();
		}
		txtSearch.setOnKeyTyped(event->{
			try {
				refresh();
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
		});
		cboFilter.setOnHidden(event ->{
			try {
				refresh();
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
		});
		datechecker();
	}

	private void loadData() {

		columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		columnDate.setCellValueFactory(new PropertyValueFactory<>("expensesDate"));
		columnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		columnType.setCellValueFactory(new PropertyValueFactory<>("Type"));
		columnCost.setCellValueFactory(new PropertyValueFactory<>("Cost"));
		columnNote.setCellValueFactory(new PropertyValueFactory<>("Note"));
		tblExpenses.setItems(observableList);
	}

	ObservableList<Expenses> observableList = FXCollections.observableArrayList();

	private void refresh() throws NumberFormatException, IOException {
		filter();
		sum = 0;
		try {
			connection = DBConnect.getConnect();
			observableList.clear();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				observableList.add(new Expenses(resultSet.getInt("ID"), resultSet.getString("Name"),
						resultSet.getDate("Date").toLocalDate(), resultSet.getDouble("Cost"),
						resultSet.getString("Type"), resultSet.getString("Note")));

				sum += resultSet.getDouble("Cost");
				tblExpenses.setItems(observableList);
//				// Initial filtered list
//				FilteredList<Expenses> filterdData = new FilteredList<>(observableList, b -> true);
//
//				txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
//					filterdData.setPredicate(Expenses -> {
//						// If no search value then display all
//						if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
//							return true;
//						}
//
//						String input = newValue.toLowerCase();
//
//						if (String.valueOf(Expenses.getID()).toLowerCase().indexOf(input) > -1) {
//							return true;// means we found match in name
//						} else if (Expenses.getExpensesDate().toString().toLowerCase().indexOf(input) > -1) {
//							return true;// means we found match in Expenses ID
//
//						} else if (Expenses.getName().toString().toLowerCase().indexOf(input) > -1) {
//							return true;
//						} else
//							return false;
//					});
//				});
//				SortedList<Expenses> sortedData = new SortedList<>(filterdData);
//
//				// Bind sorted result with table view
//				sortedData.comparatorProperty().bind(tblExpenses.comparatorProperty());
//
//				// apply filtered and sorted data to the table view
//				tblExpenses.setItems(sortedData);

			}
			preparedStatement.close();
			connection.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		txtSum.setText(String.valueOf(sum));
		addButtonToTable();
	}

	private void addButtonToTable() throws IOException {
		Callback<TableColumn<Expenses, Void>, TableCell<Expenses, Void>> cellFactory = new Callback<TableColumn<Expenses, Void>, TableCell<Expenses, Void>>() {
			@Override
			public TableCell<Expenses, Void> call(final TableColumn<Expenses, Void> param) {
				final TableCell<Expenses, Void> cell = new TableCell<Expenses, Void>() {

					private final Button btnEdit = new Button("Edit");

					{
						btnEdit.setOnAction((ActionEvent event) -> {
							Expenses Expenses = getTableView().getItems().get(getIndex());
							try {
								Main.editExpensesScene(Expenses);
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
					}
					private final Button btnDelete = new Button("Delete");

					{
						btnDelete.setOnAction((ActionEvent event) -> {
							DBConnect.deleteExpenses(getTableView().getItems().get(getIndex()));
							try {
								refresh();
							} catch (NumberFormatException | IOException e) {
								e.printStackTrace();
							}
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							HBox managebtn = new HBox(btnEdit, btnDelete);
							managebtn.setStyle("-fx-alignment:center");
							HBox.setMargin(btnEdit, new Insets(2, 2, 0, 3));
							HBox.setMargin(btnDelete, new Insets(2, 3, 0, 2));

							setGraphic(managebtn);

							setText(null);
						}
					}
				};

				return cell;
			}
		};

		columnAction.setCellFactory(cellFactory);
	}

	@FXML
	public void clickAddNewExpenses(ActionEvent event) throws IOException {
		Main.addExpensesScene();
		refresh();
	}

	@FXML
	public void clickRefrech(ActionEvent event) throws NumberFormatException, IOException {
		txtSearch.setText("");
		query = "SELECT * FROM `tblexpenses` WHERE ID LIKE '%%' OR Name LIKE '%%' ORDER BY `tblexpenses`.`ID` DESC";
		datFrom.setValue(null);
		datTo.setValue(null);
		cboFilter.setValue("All");
		refresh();
	}

	@FXML
	public void clickSearchByDate(ActionEvent event) throws NumberFormatException, IOException {
		query = "SELECT * FROM `tblexpenses`";
		if (datFrom.getValue() != null && datTo.getValue() == null) {
			query += " WHERE Date >= '" + datFrom.getValue().toString() + "'";
		}
		if (datFrom.getValue() == null && datTo.getValue() != null) {
			query += " WHERE Date <= '" + datTo.getValue().toString() + "'";
		}
		if (datFrom.getValue() != null && datTo.getValue() != null) {
			query += " WHERE Date >= '" + datFrom.getValue().toString() + "' AND Date <= '"
					+ datTo.getValue().toString() + "'";
		}
		if (cboFilter.getValue() != null) {
			if (!cboFilter.getValue().equals("All")) {
				query += " AND Type = '" + cboFilter.getValue() + "'";
			}
		}
		refresh();
	}

	@FXML
	public void clickSearch(ActionEvent event) throws NumberFormatException, IOException {
		filter();
		refresh();
	}

	private void filter() {
		query = "SELECT * FROM `tblexpenses` WHERE ( ID LIKE '%" + txtSearch.getText() + "%' OR Name LIKE '%"
				+ txtSearch.getText() + "%' ) ";
		if (datFrom.getValue() != null) {
			query += " AND Date >= '" + datFrom.getValue().toString() + "' ";
		}
		if (datTo.getValue() != null) {
			query += " AND Date <= '" + datTo.getValue().toString() + "' ";
		}
		if (cboFilter.getValue() != null) {
			if (!cboFilter.getValue().equals("All")) {
				query += " AND Type = '" + cboFilter.getValue() + "' ";
			}
		}
		query += " ORDER BY `tblexpenses`.`ID` DESC";
	}

	@FXML
	void onActionFilter(ActionEvent event) throws NumberFormatException, IOException {
//		filter();
//		refresh();
	}
	private void datechecker() {
		datTo.setOnHidden(event -> {
			if (datTo.getValue() != null && datFrom.getValue() != null) {
				if (datFrom.getValue().isAfter(datTo.getValue())) {
					datFrom.setValue(null);
				}
			}
		});
		datFrom.setOnHidden(event -> {
			if (datTo.getValue() != null && datFrom.getValue() != null) {
				if (datFrom.getValue().isAfter(datTo.getValue())) {
					datTo.setValue(null);
				}
			}
		});
	}
}
