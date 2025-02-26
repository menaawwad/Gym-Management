package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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

import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class retrieveCustomerSceneController implements Initializable {
	@FXML
	private TextField textFieldSearch;
	@FXML
	private TableColumn<Customer, String> columnAdressCustomer;

	@FXML
	private TableColumn<Customer, String> columnBirthdayCustomer;

	@FXML
	private TableColumn<Customer, String> columnEmailCustomer;

	@FXML
	private TableColumn<Customer, String> columnGenderCustomer;

	@FXML
	private TableColumn<Customer, String> columnIdCustomer;

	@FXML
	private TableColumn<Customer, String> columnNameCustomer;

	@FXML
	private TableColumn<Customer, String> columnPhoneNumberCustomer;

	@FXML
	private TableColumn<Customer, String> columnStatusCustomer;

	@FXML
	private TableView<Customer> customerTable;

	@FXML
	private Button BtnCancel;

	String query = null;
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Customer customer = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addButtonToTable();
		loadData();
		refresh();
	}

	private void loadData() {
		

		columnNameCustomer.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnIdCustomer.setCellValueFactory(new PropertyValueFactory<>("customerId"));
		columnBirthdayCustomer.setCellValueFactory(new PropertyValueFactory<>("birthday"));
		columnPhoneNumberCustomer.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		columnAdressCustomer.setCellValueFactory(new PropertyValueFactory<>("adress"));
		columnEmailCustomer.setCellValueFactory(new PropertyValueFactory<>("Email"));
		columnGenderCustomer.setCellValueFactory(new PropertyValueFactory<>("Gender"));
		columnStatusCustomer.setCellValueFactory(new PropertyValueFactory<>("Status"));
		customerTable.setItems(observableList);

	}

	ObservableList<Customer> observableList = FXCollections.observableArrayList();

	private void refresh() {
		
		try {
			connection = DBConnect.getConnect();
			observableList.clear();
			query = "SELECT * FROM `tblcustomers` WHERE removed = 1";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				observableList.add(new Customer(resultSet.getInt("id"),resultSet.getString("name"), resultSet.getString("customerId"),
						resultSet.getDate("birthday").toLocalDate(), resultSet.getString("adress"),
						resultSet.getString("phone number"), resultSet.getString("email"),
						resultSet.getString("gender"), resultSet.getString("status")));
				customerTable.setItems(observableList);
				// Initial filtered list
				FilteredList<Customer> filterdData = new FilteredList<>(observableList, b -> true);
				
				textFieldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
					filterdData.setPredicate(Customer -> {
						// If no search value then display all
						if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
							return true;
						}

						String input = newValue.toLowerCase();

						if (Customer.getName().toLowerCase().indexOf(input) > -1) {
							return true;// means we found match in name
						} else if (Customer.getCustomerId().toLowerCase().indexOf(input) > -1) {
							return true;// means we found match in customer ID
						} else if (Customer.getPhoneNumber().toLowerCase().indexOf(input) > -1) {
							return true;// means we found match in customer Phone Number
						} else
							return false;
					});
				});
				SortedList<Customer> sortedData = new SortedList<>(filterdData);

				// Bind sorted result with table view
				sortedData.comparatorProperty().bind(customerTable.comparatorProperty());

				// apply filtered and sorted data to the table view
				customerTable.setItems(sortedData);

			}
			preparedStatement.close();
			connection.close();
			resultSet.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void addButtonToTable() {
		TableColumn<Customer, Void> retrieveColumn = new TableColumn("Retrieve");

		Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>> cellFactory = new Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>>() {
			@Override
			public TableCell<Customer, Void> call(final TableColumn<Customer, Void> param) {
				final TableCell<Customer, Void> cell = new TableCell<Customer, Void>() {

					private final Button btn = new Button("Retrieve");

					{
						btn.setOnAction((ActionEvent event) -> {
							Customer customer = getTableView().getItems().get(getIndex());
							DBConnect.retrieveCustomer(customer.getCustomerId());
							refresh();
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};

		retrieveColumn.setCellFactory(cellFactory);

		customerTable.getColumns().add(retrieveColumn);

	}

	// Event Listener on TextField[#textFieldSearch].onKeyTyped
	@FXML
	public void searchKeyTyped(KeyEvent event) {
	}

	// Event Listener on Button[#BtnCancel].onAction
	@FXML
	public void clickCancel(ActionEvent event) {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}
}
