package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;

public class homeMainSceneController implements Initializable {

	@FXML
	private TableView<Customer> tblCustomerTracking;
	@FXML
	private TableColumn<Customer, Void> columnCTNum;
	@FXML
	private TableColumn<Customer, Void> columnCTAction;
	@FXML
	private TableColumn<Customer, String> columnCTID;
	@FXML
	private TableColumn<Customer, String> columnCTName;
	@FXML
	private TableColumn<Customer, String> columnCTPhoneNum;
	@FXML
	private TableColumn<Customer, Void> columnCTStatus;

	@FXML
	private TableView<Product> tblStockOrder;
	@FXML
	private TableColumn<Product, Void> columnSOnum;
	@FXML
	private TableColumn<Product, String> columnSOBarCode;
	@FXML
	private TableColumn<Product, String> columnSOID;
	@FXML
	private TableColumn<Product, String> columnSOName;
	@FXML
	private TableColumn<Product, String> columnSOCostPrice;
	@FXML
	private TableColumn<Product, String> columnSOMinOrder;
	@FXML
	private TableColumn<Product, String> columnSOStock;

	@FXML
	private TableView<ToDo> tblListToDo;
	@FXML
	private TableColumn<ToDo, Void> columnLTDAction;
	@FXML
	private TableColumn<ToDo, Void> columnLTDNum;
	@FXML
	private TableColumn<ToDo, String> columnLTDToDate;
	@FXML
	private TableColumn<ToDo, String> columnLTDToDo;

	@FXML
	private TextArea txtWhiteBoard;

	@FXML
	private Button btnAddToDo;
	@FXML
	private ComboBox<String> cboCustomerStatus;
	@FXML
	private ComboBox<String> cboStatusToDo;

	private String[] filter = { "All", "Active", "Inactive", "Freeze", "Expired", "No Status" };

	private String[] statuss = { "Finished", "Faild", "In progress" };

	String query = null;
	String customerQuery = null;
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	@FXML
	void onActionCustomerStatus(ActionEvent event) throws NumberFormatException, IOException {
		if (cboCustomerStatus.getSelectionModel().getSelectedItem().equals("All")) {
			refreshCustomer();
		}
		if (cboCustomerStatus.getSelectionModel().getSelectedItem().equals("Active")) {
			customerQuery = null;
			refreshCustomer();
			addButtonToTableCustomer();
			addCountToTableCustomer();
		}
		if (cboCustomerStatus.getSelectionModel().getSelectedItem().equals("Inactive")) {
			customerQuery = "SELECT * FROM `tblcustomers` WHERE status = 'Inactive' AND removed = 0";
			refreshCustomer();
			addButtonToTableCustomer();
			addCountToTableCustomer();
		}
		if (cboCustomerStatus.getSelectionModel().getSelectedItem().equals("Freeze")) {
			customerQuery = "SELECT * FROM `tblcustomers` WHERE status = 'Freeze' AND removed = 0";
			refreshCustomer();
			addButtonToTableCustomer();
			addCountToTableCustomer();
		}
		if (cboCustomerStatus.getSelectionModel().getSelectedItem().equals("Expired")) {
			customerQuery = "SELECT * FROM `tblcustomers` WHERE status = 'Expired' AND removed = 0";
			refreshCustomer();
			addButtonToTableCustomer();
			addCountToTableCustomer();
		}
		if (cboCustomerStatus.getSelectionModel().getSelectedItem().equals("No Status")) {
			customerQuery = "SELECT * FROM `tblcustomers` WHERE status = '' AND removed = 0";
			refreshCustomer();
			addButtonToTableCustomer();
			addCountToTableCustomer();
		}
	}

	@FXML
	void onActionToDoStatus(ActionEvent event) throws NumberFormatException, IOException {
		refreshToDo();
	}

	@FXML
	void onKeyWhiteBoard(KeyEvent event) {
		Main.whiteBoard = txtWhiteBoard.getText();
	}

	ObservableList<Customer> observableListCustomer = FXCollections.observableArrayList();
	ObservableList<Product> observableListProduct = FXCollections.observableArrayList();
	ObservableList<ToDo> observableListToDo = FXCollections.observableArrayList();

	private void loadData() {

		columnCTID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		columnCTName.setCellValueFactory(new PropertyValueFactory<>("name"));
		columnCTPhoneNum.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		tblCustomerTracking.setItems(observableListCustomer);

		columnSOID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		columnSOName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		columnSOBarCode.setCellValueFactory(new PropertyValueFactory<>("Barcode"));
		columnSOCostPrice.setCellValueFactory(new PropertyValueFactory<>("CostPrice"));
		columnSOMinOrder.setCellValueFactory(new PropertyValueFactory<>("MinOrder"));
		columnSOStock.setCellValueFactory(new PropertyValueFactory<>("Stock"));
		tblStockOrder.setItems(observableListProduct);

		columnLTDToDate.setCellValueFactory(new PropertyValueFactory<>("date"));
		columnLTDToDo.setCellValueFactory(new PropertyValueFactory<>("Todo"));
		tblListToDo.setItems(observableListToDo);

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		cboCustomerStatus.getItems().addAll(filter);
		cboStatusToDo.getItems().addAll(statuss);
		cboStatusToDo.setValue("In progress");
		txtWhiteBoard.setText(Main.whiteBoard);

		try {
			loadData();
			refreshProduct();
			refreshToDo();
			refreshCustomer();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}

	}

	private void refreshProduct() throws NumberFormatException, IOException {
		tblStockOrder.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				if (event.getClickCount() > 1) {
					try {
						Main.addPurchaseScene(tblStockOrder.getSelectionModel().getSelectedItem());
						refreshProduct();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		try {
			connection = DBConnect.getConnect();
			observableListProduct.clear();
			query = "SELECT * FROM `tblproducts` WHERE `stock` < `minOrder` AND `active` = 1;";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				observableListProduct.add(new Product(resultSet.getInt(1), resultSet.getString(2),
						resultSet.getDate(3).toLocalDate(), resultSet.getDate(4).toLocalDate(), resultSet.getString(5),
						resultSet.getDouble(6), resultSet.getDouble(7), resultSet.getDouble(8), resultSet.getDouble(9),
						resultSet.getInt(10), resultSet.getInt(11)));
				tblStockOrder.setItems(observableListProduct);
			}
			preparedStatement.close();
			connection.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		addCountToTableProduct();
	}

	private void refreshToDo() throws NumberFormatException, IOException {
		DBConnect.updateToDoToFaild();
		try {
			connection = DBConnect.getConnect();
			observableListToDo.clear();
			query = "SELECT * FROM `tbltodo` WHERE status = '" + cboStatusToDo.getValue() + "' ";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				observableListToDo.add(new ToDo(resultSet.getInt(1), resultSet.getString(2), resultSet.getDate(3),
						resultSet.getString(4)));
				tblListToDo.setItems(observableListToDo);
			}
			preparedStatement.close();
			connection.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		addButtonToTableToDo();
		addCountToTableToDo();
	}

	private void refreshCustomer() throws NumberFormatException, IOException {
		observableListCustomer.clear();
		if (cboCustomerStatus.getSelectionModel().isEmpty()
				|| cboCustomerStatus.getSelectionModel().getSelectedItem().equals("All")) {
			customerQuery = "SELECT * FROM `tblcustomers` WHERE `status` != 'Active' AND removed = 0;";
			observableListCustomer.clear();
			ObservableList<Customer> observableListCustomerForActive = FXCollections.observableArrayList();
			observableListCustomerForActive = DBConnect.allActiveCustomers14DaysToExpired();
			int i = 0;
			while (observableListCustomerForActive.size() > i) {
				observableListCustomer.add(observableListCustomerForActive.get(i));
				i++;
			}
		}

		if (customerQuery == null) {
			observableListCustomer.clear();
			ObservableList<Customer> observableListCustomerForActive = FXCollections.observableArrayList();
			observableListCustomerForActive = DBConnect.allActiveCustomers14DaysToExpired();
			int i = 0;
			while (observableListCustomerForActive.size() > i) {
				observableListCustomer.add(observableListCustomerForActive.get(i));
				i++;
			}
		}

		if (customerQuery != null)
			try {
				connection = DBConnect.getConnect();
				preparedStatement = connection.prepareStatement(customerQuery);
				resultSet = preparedStatement.executeQuery();
				while (resultSet.next()) {
					observableListCustomer.add(new Customer(Integer.parseInt(resultSet.getString("ID")),
							resultSet.getString("name"), resultSet.getString("customerId"),
							resultSet.getDate("birthday").toLocalDate(), resultSet.getString("adress"),
							resultSet.getString("phone number"), resultSet.getString("email"),
							resultSet.getString("gender"), resultSet.getString("status")));
					tblCustomerTracking.setItems(observableListCustomer);

				}
				preparedStatement.close();
				connection.close();
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		addButtonToTableCustomer();
		customerStatus();
		addCountToTableCustomer();
	}

	private void addButtonToTableCustomer() throws IOException {
		Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>> cellFactory = new Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>>() {
			@Override
			public TableCell<Customer, Void> call(final TableColumn<Customer, Void> param) {
				final TableCell<Customer, Void> cell = new TableCell<Customer, Void>() {

					private final Button btn = new Button("Manage");

					{
						btn.setOnAction((ActionEvent event) -> {
							Customer customer = getTableView().getItems().get(getIndex());
							try {
								Main.manageSubscriptionScene(customer);
								DBConnect.updateAllCustomersStatus();
								refreshCustomer();
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
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};
		columnCTAction.setCellFactory(cellFactory);
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
							status.setText(observableListCustomer.get(getIndex()).getStatus());
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
		columnCTStatus.setCellFactory(cellFactory);
	}

	private void addCountToTableCustomer() throws IOException {

		Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>> cellFactory = new Callback<TableColumn<Customer, Void>, TableCell<Customer, Void>>() {
			@Override
			public TableCell<Customer, Void> call(final TableColumn<Customer, Void> param) {

				final TableCell<Customer, Void> cell = new TableCell<Customer, Void>() {

					private final Label txt = new Label();

					@Override
					public void updateItem(Void item, boolean empty) {

						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							txt.setText(String.valueOf(getIndex() + 1));
							setGraphic(txt);
						}
					}
				};
				return cell;
			}
		};
		columnCTNum.setCellFactory(cellFactory);
	}

	private void addCountToTableToDo() throws IOException {

		Callback<TableColumn<ToDo, Void>, TableCell<ToDo, Void>> cellFactory = new Callback<TableColumn<ToDo, Void>, TableCell<ToDo, Void>>() {
			@Override
			public TableCell<ToDo, Void> call(final TableColumn<ToDo, Void> param) {

				final TableCell<ToDo, Void> cell = new TableCell<ToDo, Void>() {

					private final Label txt = new Label();

					@Override
					public void updateItem(Void item, boolean empty) {

						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							txt.setText(String.valueOf(getIndex() + 1));
							setGraphic(txt);
						}
					}
				};
				return cell;
			}
		};
		columnLTDNum.setCellFactory(cellFactory);
	}

	private void addButtonToTableToDo() throws IOException {
		Callback<TableColumn<ToDo, Void>, TableCell<ToDo, Void>> cellFactory = new Callback<TableColumn<ToDo, Void>, TableCell<ToDo, Void>>() {
			@Override
			public TableCell<ToDo, Void> call(final TableColumn<ToDo, Void> param) {
				final TableCell<ToDo, Void> cell = new TableCell<ToDo, Void>() {

					private final Button edit = new Button("Edit");

					{
						edit.setOnAction((ActionEvent event) -> {
							ToDo ToDo = getTableView().getItems().get(getIndex());
							try {
								Main.editToDoScene(ToDo);
								refreshToDo();
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
					}
					private final Button delete = new Button("Delete");

					{
						delete.setOnAction((ActionEvent event) -> {
							ToDo ToDo = getTableView().getItems().get(getIndex());
							Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
							alert.setTitle("remove ToDo");
							alert.setHeaderText("remove ToDo");
							alert.setContentText("Are you sure want to remove this to do from the lsit");
							Optional<ButtonType> result = alert.showAndWait();
							if (result.isPresent() && result.get() == ButtonType.OK) {
								DBConnect.deleteToDo(ToDo);
								try {
									refreshToDo();
								} catch (NumberFormatException | IOException e) {
									e.printStackTrace();
								}
							}
						});
					}

					private final Button finish = new Button("Finish");
					{
						finish.setOnAction((event) -> {
							ToDo todo = getTableView().getItems().get(getIndex());
							todo.setStatus("Finished");
							Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
							alert.setTitle("Finish To Do");
							alert.setHeaderText("Finish To Do");
							alert.setContentText("Are you sure Finish the: " + todo.getTodo());
							Optional<ButtonType> result = alert.showAndWait();
							if (result.isPresent() && result.get() == ButtonType.OK) {
								DBConnect.updateToDo(todo);
								try {
									refreshToDo();
								} catch (NumberFormatException | IOException e) {
									e.printStackTrace();
								}
							}

						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							if (cboStatusToDo.getValue().equals("In progress")) {
								HBox managebtn = new HBox(edit, delete, finish);
								managebtn.setStyle("-fx-alignment:center");
								HBox.setMargin(edit, new Insets(2, 2, 0, 3));
								HBox.setMargin(delete, new Insets(2, 3, 0, 2));
								setGraphic(managebtn);
							} else {
								setGraphic(delete);
							}
							setText(null);
						}
					}
				};

				return cell;
			}
		};

		columnLTDAction.setCellFactory(cellFactory);
	}

	private void addCountToTableProduct() throws IOException {

		Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
			@Override
			public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {

				final TableCell<Product, Void> cell = new TableCell<Product, Void>() {

					private final Label txt = new Label();

					@Override
					public void updateItem(Void item, boolean empty) {

						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							txt.setText(String.valueOf(getIndex() + 1));
							setGraphic(txt);
						}
					}
				};
				return cell;
			}
		};
		columnSOnum.setCellFactory(cellFactory);
	}

	@FXML
	void clickAddToDo(ActionEvent event) throws IOException {
		Main.addToDoScene();
		refreshToDo();
	}

	@FXML
	void mouseEnteredProducts(MouseEvent event) {
//		System.out.println(tblStockOrder.getSelectionModel().getSelectedItem());
	}

}
