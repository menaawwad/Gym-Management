package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

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

import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class retrieveProductSceneController implements Initializable{
	@FXML
	private TextField textFieldSearch;
	@FXML
	private TableView <Product>ProductTable;
	@FXML
	private TableColumn <Product,String> columnID;
	@FXML
	private TableColumn <Product,String> columnName;
	@FXML
	private TableColumn <Product,String> columnRegestrDate;
	@FXML
	private TableColumn <Product,String> columnLastUpdate;
	@FXML
	private TableColumn <Product,String> columnBarcode;
	@FXML
	private TableColumn <Product,String> columnWeight;
	@FXML
	private TableColumn <Product,String> columnCostPrice;
	@FXML
	private TableColumn <Product,String> columnSellPrice;
	@FXML
	private TableColumn <Product,String> columnMinPrice;
	@FXML
	private TableColumn <Product,String> columnMinOrder;
	@FXML
	private TableColumn <Product,String> columnSock;
	@FXML
	private Button BtnCancel;

	String query = null;
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Customer customer = null;
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			addButtonToTable();
		} catch (IOException e) {
			e.printStackTrace();
		}
		loadData();
		try {
			refresh();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
	}
	
	private void loadData() {
		

		columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		columnName.setCellValueFactory(new PropertyValueFactory<>("Name"));
		columnRegestrDate.setCellValueFactory(new PropertyValueFactory<>("RegisterDate"));
		columnLastUpdate.setCellValueFactory(new PropertyValueFactory<>("LastUpdate"));
		columnBarcode.setCellValueFactory(new PropertyValueFactory<>("Barcode"));		
		columnCostPrice.setCellValueFactory(new PropertyValueFactory<>("CostPrice"));
		columnSellPrice.setCellValueFactory(new PropertyValueFactory<>("SellPrice"));
		columnMinPrice.setCellValueFactory(new PropertyValueFactory<>("MinPrice"));
		columnMinOrder.setCellValueFactory(new PropertyValueFactory<>("MinOrder"));
		columnWeight.setCellValueFactory(new PropertyValueFactory<>("Weight"));
		columnSock.setCellValueFactory(new PropertyValueFactory<>("Stock"));
		ProductTable.setItems(observableList);
	}

	ObservableList<Product> observableList = FXCollections.observableArrayList();
	
	
	private void refresh() throws NumberFormatException, IOException {

		try {
			connection = DBConnect.getConnect();
			observableList.clear();
			query = "SELECT * FROM `tblProducts` WHERE active = 0";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				observableList.add(new Product(resultSet.getInt(1), resultSet.getString(2),
						resultSet.getDate(3).toLocalDate(), resultSet.getDate(4).toLocalDate(), resultSet.getString(5),
						resultSet.getDouble(6), resultSet.getDouble(7), resultSet.getDouble(8), resultSet.getDouble(9),
						resultSet.getInt(10), resultSet.getInt(11)));
				ProductTable.setItems(observableList);
				// Initial filtered list
				FilteredList<Product> filterdData = new FilteredList<>(observableList, b -> true);

				textFieldSearch.textProperty().addListener((observable, oldValue, newValue) -> {
					filterdData.setPredicate(Product -> {
						// If no search value then display all
						if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
							return true;
						}

						String input = newValue.toLowerCase();

						if (Product.getName().toLowerCase().indexOf(input) > -1) {
							return true;// means we found match in name
						} else if (String.valueOf(Product.getID()).toLowerCase().indexOf(input) > -1) {
							return true;// means we found match in Product ID
						} else if (Product.getName().toLowerCase().indexOf(input) > -1) {
							return true;// means we found match in Product Phone Number
						}
//						else if (Integer.toString(Product.getID()).toLowerCase().indexOf(input) > -1) {
//							return true;// means we found match in Product Phone Number
//						}
						else
							return false;
					});
				});
				SortedList<Product> sortedData = new SortedList<>(filterdData);

				// Bind sorted result with table view
				sortedData.comparatorProperty().bind(ProductTable.comparatorProperty());

				// apply filtered and sorted data to the table view
				ProductTable.setItems(sortedData);

			}
			preparedStatement.close();
			connection.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	private void addButtonToTable() throws IOException {
		TableColumn<Product, Void> retrieveColumn = new TableColumn("Retrieve");

		Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
			@Override
			public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
				final TableCell<Product, Void> cell = new TableCell<Product, Void>() {

					private final Button btn = new Button("Retrieve");

					{
						btn.setOnAction((ActionEvent event) -> {
							Product Product = getTableView().getItems().get(getIndex());
							DBConnect.deldeteRetrieveProduct(Product.getID(), 1);
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
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};

		retrieveColumn.setCellFactory(cellFactory);

		ProductTable.getColumns().add(retrieveColumn);

	}
	
	// Event Listener on TextField[#textFieldSearch].onKeyTyped
	@FXML
	public void searchKeyTyped(KeyEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#BtnCancel].onAction
	@FXML
	public void clickCancel(ActionEvent event) {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}
}
