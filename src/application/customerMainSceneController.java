package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class customerMainSceneController implements Initializable {
	SimpleDateFormat DateFormat = new SimpleDateFormat("MM/dd/yyyy");

	@FXML
	private Button addNewCustomerBtn;

	@FXML
	private Button btnClear;

	@FXML
	private Button deleteBtn;

	@FXML
	private TextField textFieldSearchCustomer;

	@FXML
	private ChoiceBox<String> chbFlter;

	private String[] filter = { "All", "Active", "Inactive", "Freeze", "Expired", "No Status" };

	@FXML
	private TableColumn<Customer, String> columnAdressCustomer;

	@FXML
	private TableColumn<Customer, Date> columnBirthdayCustomer;

	@FXML
	private TableColumn<Customer, String> columnEmailCustomer;

	@FXML
	private TableColumn<Customer, String> columnGenderCustomer;

	@FXML
	private TableColumn<Customer, String> columnIdCustomer;

	@FXML
	private TableColumn<Customer, String> columnNameCustomer;

	@FXML
	private TableColumn<Customer, String> columnCustomerNumber;

	@FXML
	private TableColumn<Customer, String> columnPhoneNumberCustomer;

	@FXML
	private TableColumn<Customer, Void> columnStatusCustomer;
	@FXML
	private TableColumn<Customer, Void> columnAction;

	@FXML
	private TableView<Customer> customerTable;

	@FXML
	private Button btnRetrieve;

	String query = "";
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		DBConnect.updateAllCustomersStatus();
		textFieldSearchCustomer.setTooltip(new Tooltip("Search by customer Name  or Customer ID or customer Card ID"));
		chbFlter.getItems().addAll(filter);
		chbFlter.setValue("Search by status");
		textFieldSearchCustomer.setOnKeyTyped(event->{
			try {
				refresh();
			} catch (NumberFormatException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		chbFlter.setOnAction(arg01 -> {
			try {
				filter(arg01);
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
		});
		loadData();
		try {
			refresh();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void filter() {
		query = "SELECT * FROM `tblcustomers` WHERE removed = 0 AND (name LIKE '%" + textFieldSearchCustomer.getText()
				+ "%' OR ID LIKE '%" + textFieldSearchCustomer.getText() + "%' OR `tblcustomers`.`phone number` LIKE '%"
				+ textFieldSearchCustomer.getText() + "%' OR customerId LIKE '%" + textFieldSearchCustomer.getText()
				+ "%')";
		if (chbFlter.getValue() != null) {
			if (chbFlter.getSelectionModel().getSelectedItem().equals("Active"))
				query += " AND status = 'Active'";
			if (chbFlter.getSelectionModel().getSelectedItem().equals("Inactive"))
				query += " AND status = 'Inactive'";
			if (chbFlter.getSelectionModel().getSelectedItem().equals("Freeze"))
				query += " AND status = 'Freeze'";
			if (chbFlter.getSelectionModel().getSelectedItem().equals("Expired"))
				query += " AND status = 'Expired'";
			if (chbFlter.getSelectionModel().getSelectedItem().equals("No Status"))
				query += " AND status = ''";
		}
		query += "ORDER BY `tblcustomers`.`ID` DESC";
	}

	public void filter(ActionEvent event) throws NumberFormatException, IOException {
		refresh();
	}

	private void loadData() {

		columnCustomerNumber.setCellValueFactory(new PropertyValueFactory<>("ID"));
		columnNameCustomer.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnIdCustomer.setCellValueFactory(new PropertyValueFactory<>("customerId"));
		columnBirthdayCustomer.setCellValueFactory(new PropertyValueFactory<>("birthday"));
		columnPhoneNumberCustomer.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		columnAdressCustomer.setCellValueFactory(new PropertyValueFactory<>("adress"));
		columnEmailCustomer.setCellValueFactory(new PropertyValueFactory<>("Email"));
		columnGenderCustomer.setCellValueFactory(new PropertyValueFactory<>("Gender"));
//		columnStatusCustomer.setCellValueFactory(new PropertyValueFactory<>("Status"));
		customerTable.setItems(observableList);

	}

	ObservableList<Customer> observableList = FXCollections.observableArrayList();

	private void refresh() throws NumberFormatException, IOException {
		filter();
		try {
			connection = DBConnect.getConnect();
			observableList.clear();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				observableList.add(new Customer(Integer.parseInt(resultSet.getString("ID")),
						resultSet.getString("name"), resultSet.getString("customerId"),
						resultSet.getDate("birthday").toLocalDate(), resultSet.getString("adress"),
						resultSet.getString("phone number"), resultSet.getString("email"),
						resultSet.getString("gender"), resultSet.getString("status")));
				customerTable.setItems(observableList);

			}
			preparedStatement.close();
			connection.close();
			resultSet.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		addButtonToTable();
		customerStatus();
	}

	private void addButtonToTable() throws IOException {
		Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>> cellFactory = new Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>>() {
			@Override
			public TableCell<Customer, Void> call(final TableColumn<Customer, Void> param) {
				final TableCell<Customer, Void> cell = new TableCell<Customer, Void>() {

					private final Button btnEdit = new Button("Edit");

					{
						btnEdit.setOnAction((ActionEvent event) -> {
							Customer customer = getTableView().getItems().get(getIndex());
							try {
								Main.editCustomerScene(customer);
								DBConnect.updateAllCustomersStatus();
								refresh();
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
					}

					private final Button btnSubscription = new Button("Subscriprion");

					{
						btnSubscription.setOnAction((ActionEvent event) -> {
							Customer customer = getTableView().getItems().get(getIndex());
							try {
								Main.manageSubscriptionScene(customer);
								DBConnect.updateAllCustomersStatus();
								refresh();
							} catch (IOException e) {
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
							HBox managebtn = new HBox(btnEdit, btnSubscription);
							managebtn.setStyle("-fx-alignment:center");
							HBox.setMargin(btnEdit, new Insets(2, 2, 0, 3));
							HBox.setMargin(btnSubscription, new Insets(2, 3, 0, 2));

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

	private void customerStatus() throws IOException {
		Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>> cellFactory = new Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>>() {
			@Override
			public TableCell<Customer, Void> call(final TableColumn<Customer, Void> param) {
				final TableCell<Customer, Void> cell = new TableCell<Customer, Void>() {

					private final Label status = new Label();

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							status.setText(observableList.get(getIndex()).getStatus());
							HBox managebtn = new HBox(status);
							if (status.getText().equals("Expired")) {
								status.setStyle(" -fx-text-fill:white;");
								managebtn.setStyle("-fx-alignment:center; -fx-background-color:red;");
							}
							if (status.getText().equals("Active")) {
								status.setStyle(" -fx-text-fill:white;");
								managebtn.setStyle("-fx-alignment:center; -fx-background-color:green;");
							}
							if (status.getText().equals("Inactive")) {
								status.setStyle(" -fx-text-fill:black;");
								managebtn.setStyle("-fx-alignment:center; -fx-background-color:yellow;");
							}
							if (status.getText().equals("Freeze")) {
								status.setStyle(" -fx-text-fill:white;");
								managebtn.setStyle("-fx-alignment:center; -fx-background-color:blue;");
							}
							setGraphic(managebtn);
							setText(null);
						}
					}
				};
				return cell;
			}
		};
		columnStatusCustomer.setCellFactory(cellFactory);
	}

	@FXML
	void addNewCustomerClick(ActionEvent event) throws IOException {
		Main.addCustomerScene();
		DBConnect.updateAllCustomersStatus();
		refresh();
	}

	@FXML
	void clickClear(ActionEvent event) throws NumberFormatException, IOException {
		textFieldSearchCustomer.setText("");
		refresh();
	}

	@FXML
	void deleteClick(ActionEvent event) throws NumberFormatException, IOException {
		if (!customerTable.getSelectionModel().isEmpty()) {
			String name = customerTable.getSelectionModel().getSelectedItem().getName();
			String ID = customerTable.getSelectionModel().getSelectedItem().getCustomerId();
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("remove customer");
			alert.setHeaderText("remove customer");
			alert.setContentText("Are you sure want to remove " + name + " ID:" + ID + " ?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				DBConnect.deldeteCustomer(ID);
				refresh();
			}
		} else {
			Function.alert("you have to select one of the customers");
		}
	}

	@FXML
	void clickRetrieve(ActionEvent event) throws IOException {
		Main.retrieveCustomerScene();
		DBConnect.updateAllCustomersStatus();
		refresh();
	}

}
