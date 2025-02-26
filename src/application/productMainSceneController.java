package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class productMainSceneController implements Initializable {
	@FXML
	private Button addNewProductBtn;
	@FXML
	private TextField textFieldSearch;
	@FXML
	private Button btnNewSearch;
	@FXML
	private Button deleteBtn;
	@FXML
	private Button btnRetrieve;
	@FXML
	private TableView<Product> ProductTable;
	@FXML
	private TableColumn<Product, String> columnID;
	@FXML
	private TableColumn<Product, String> columnName;
	@FXML
	private TableColumn<Product, String> columnRegestrDate;
	@FXML
	private TableColumn<Product, String> columnLastUpdate;
	@FXML
	private TableColumn<Product, String> columnBarcode;
	@FXML
	private TableColumn<Product, String> columnWeight;
	@FXML
	private TableColumn<Product, String> columnCostPrice;
	@FXML
	private TableColumn<Product, String> columnSellPrice;
	@FXML
	private TableColumn<Product, String> columnMinPrice;
	@FXML
	private TableColumn<Product, String> columnMinOrder;
	@FXML
	private TableColumn<Product, String> columnSock;
	@FXML
	private TableColumn<Product, Void> columnAction;

	String query = null;
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

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

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadData();
		try {
			refresh();
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		textFieldSearch.setOnKeyTyped(event -> {
			try {
				refresh();
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
		});
	}

	private void filter() {
		query = "SELECT * FROM `tblproducts` WHERE active = 1 AND ( ID LIKE '%" + textFieldSearch.getText()
				+ "%' OR product LIKE '%" + textFieldSearch.getText() + "%' OR barcode LIKE '%"
				+ textFieldSearch.getText() + "%') ORDER BY ID DESC";
	}

	private void refresh() throws NumberFormatException, IOException {
		filter();
		try {
			connection = DBConnect.getConnect();
			observableList.clear();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				observableList.add(new Product(resultSet.getInt(1), resultSet.getString(2),
						resultSet.getDate(3).toLocalDate(), resultSet.getDate(4).toLocalDate(), resultSet.getString(5),
						resultSet.getDouble(6), resultSet.getDouble(7), resultSet.getDouble(8), resultSet.getDouble(9),
						resultSet.getInt(10), resultSet.getInt(11)));
				ProductTable.setItems(observableList);
			}
			preparedStatement.close();
			connection.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		addButtonToTable();
	}

	private void addButtonToTable() throws IOException {

		Callback<TableColumn<Product, Void>, TableCell<Product, Void>> cellFactory = new Callback<TableColumn<Product, Void>, TableCell<Product, Void>>() {
			@Override
			public TableCell<Product, Void> call(final TableColumn<Product, Void> param) {
				final TableCell<Product, Void> cell = new TableCell<Product, Void>() {

					private final Button btn = new Button("Edit");

					{
						btn.setOnAction((ActionEvent event) -> {
							Product Product = getTableView().getItems().get(getIndex());
							try {
								Main.editProductScene(Product);
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
							setGraphic(btn);
						}
					}
				};
				return cell;
			}
		};
		columnAction.setCellFactory(cellFactory);

	}

	@FXML
	public void addNewProductClick(ActionEvent event) throws IOException {
		Main.addProductScene();
		refresh();
	}

	@FXML
	public void clickNewSearch(ActionEvent event) throws IOException {
		textFieldSearch.setText("");
		refresh();
	}

	@FXML
	public void deleteClick(ActionEvent event) throws NumberFormatException, IOException {
		if (!ProductTable.getSelectionModel().isEmpty()) {
			String name = ProductTable.getSelectionModel().getSelectedItem().getName();
			int ID = ProductTable.getSelectionModel().getSelectedItem().getID();
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("remove Product");
			alert.setHeaderText("remove Product");
			alert.setContentText("Are you sure want to remove " + name + " ID:" + ID + " ?");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				DBConnect.deldeteRetrieveProduct(ID, 0);
				refresh();
			}
		}
	}

	@FXML
	public void clickRetrieve(ActionEvent event) throws IOException {
		Main.retrieveProductScene();
		refresh();
	}
}
