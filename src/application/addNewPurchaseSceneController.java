package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class addNewPurchaseSceneController implements Initializable {
	double sum;
	int quantity;
	Product product;
	@FXML
	private ComboBox<Product> cboProductList;
	@FXML
	private TextField txtUnitPrice;
	@FXML
	private TextField txtQuantity;
	@FXML
	private TextField txtMinPrice;
	@FXML
	private TextField txtSellPrice;
	@FXML
	private TextField txtSearch;
	@FXML
	private Button btnAdd;
	@FXML
	private TextField txtPrice;
	@FXML
	private TextField txtProductID;
	@FXML
	private TextField txtStock;
	@FXML
	private TextField txtBarcode;
	@FXML
	private TextField txtProductName;
	@FXML
	private Button btnAddNewProduct;
	@FXML
	private TableView<PurchaseDetails> tblNewPurchase;
	@FXML
	private TableColumn<PurchaseDetails, String> columnID;
	@FXML
	private TableColumn<PurchaseDetails, String> columnProductName;
	@FXML
	private TableColumn<PurchaseDetails, String> columnUnitPrice;
	@FXML
	private TableColumn<PurchaseDetails, String> columnQuantity;
	@FXML
	private TableColumn<PurchaseDetails, String> columnRowPrice;
	@FXML
	private TableColumn<PurchaseDetails, String> columnMinPrice;
	@FXML
	private TableColumn<PurchaseDetails, String> columnSellPrice;
	@FXML
	private TableColumn<PurchaseDetails, Void> columnAction;
	@FXML
	private TextField txtTotal;
	@FXML
	private Button btnConfirm;
	@FXML
	private Button btnNewPurchase;
	@FXML
	private Button btnCancel;

	String query = null;
	String productQuery = "SELECT * FROM `tblproducts` WHERE `active` = 1 AND( `product` LIKE '%%' OR `ID` LIKE '%%' OR `barcode` LIKE '%%') ;";
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

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
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}

		btnAdd.setDisable(true);
	}

	public void getData(Product productToAdd) {
		product = productToAdd;
		if (productToAdd != null) {
			txtUnitPrice.setText(String.valueOf(productToAdd.getCostPrice()));
			if (Function.doubleNumbers(txtUnitPrice.getText()) && Function.allNumbers(txtQuantity.getText())) {
				txtPrice.setText(
						String.valueOf(Double.valueOf(txtUnitPrice.getText()) * Double.valueOf(txtQuantity.getText())));
				btnAdd.setDisable(false);
			} else {
				txtQuantity.setText("1");
				txtPrice.setText(
						String.valueOf(Double.valueOf(txtUnitPrice.getText()) * Double.valueOf(txtQuantity.getText())));
				btnAdd.setDisable(false);
			}
			txtMinPrice.setText(String.valueOf(productToAdd.getMinPrice()));
			txtSellPrice.setText(String.valueOf(productToAdd.getSellPrice()));
			txtProductName.setText(productToAdd.getName());
			txtProductID.setText(String.valueOf(productToAdd.getID()));
			txtStock.setText(String.valueOf(productToAdd.getStock()));
			txtBarcode.setText(productToAdd.getBarcode());
		}
	}

	private void loadData() {

		columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		columnProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
		columnUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
		columnQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
		columnRowPrice.setCellValueFactory(new PropertyValueFactory<>("RowPrice"));
		columnMinPrice.setCellValueFactory(new PropertyValueFactory<>("MinPrice"));
		columnSellPrice.setCellValueFactory(new PropertyValueFactory<>("SellPrice"));
		tblNewPurchase.setItems(observableList);
	}

	ObservableList<PurchaseDetails> observableList = FXCollections.observableArrayList();

	private void refresh() {
		btnAdd.setDisable(true);
		productQuery = "SELECT * FROM `tblproducts` WHERE `active` = 1 AND( `product` LIKE '%%' OR `ID` LIKE '%%' OR `barcode` LIKE '%%') ;";
		txtSearch.setText("");
		txtPrice.setText("");
		txtMinPrice.setText("");
		txtSellPrice.setText("");
		txtUnitPrice.setText("");
		txtQuantity.setText("");
		txtProductName.setText("");
		txtProductID.setText("");
		txtStock.setText("");
		txtBarcode.setText("");
		cboProductList.setItems(null);
		cboProductList.setPromptText("select Product");
		try {
			cboProductList.setItems(DBConnect.fillComboBoxProduct(productQuery));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		sum = 0;
		quantity = 0;
		try {
			connection = DBConnect.getConnect();
			observableList.clear();
			query = "SELECT `tbltempPurchase`.*, `tblproducts`.*FROM `tbltempPurchase` LEFT JOIN `tblproducts` ON `tbltempPurchase`.`productId` = `tblproducts`.`ID`;";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				observableList.add(new PurchaseDetails(resultSet.getInt("tbltempPurchase.ID"), 0,
						resultSet.getInt("tblproducts.ID"), resultSet.getString("product"),
						resultSet.getDouble("unitPrice"), resultSet.getInt("rowQuantity"),
						resultSet.getDouble("rowPrice"), resultSet.getDouble("minPrice"),
						resultSet.getDouble("sellPrice")));
				tblNewPurchase.setItems(observableList);
				sum += resultSet.getDouble("rowPrice");
				quantity += resultSet.getInt("rowQuantity");
			}
			preparedStatement.close();
			connection.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		txtTotal.setText(String.valueOf(sum));
	}

	private void addButtonToTable() throws IOException {

		Callback<TableColumn<PurchaseDetails, Void>, TableCell<PurchaseDetails, Void>> cellFactory = new Callback<TableColumn<PurchaseDetails, Void>, TableCell<PurchaseDetails, Void>>() {
			@Override
			public TableCell<PurchaseDetails, Void> call(final TableColumn<PurchaseDetails, Void> param) {
				final TableCell<PurchaseDetails, Void> cell = new TableCell<PurchaseDetails, Void>() {

					private final Button btn = new Button("Delete");

					{
						btn.setOnAction((ActionEvent event) -> {
							PurchaseDetails PurchaseDetails = getTableView().getItems().get(getIndex());
							DBConnect.deldeteProductTempTable(PurchaseDetails);
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
		columnAction.setCellFactory(cellFactory);
	}

	@FXML
	public void onActionProductList(ActionEvent event) {
		product = cboProductList.getSelectionModel().getSelectedItem();
		if (product != null) {
			txtUnitPrice.setText(String.valueOf(product.getCostPrice()));
			if (Function.doubleNumbers(txtUnitPrice.getText()) && Function.allNumbers(txtQuantity.getText())) {
				txtPrice.setText(
						String.valueOf(Double.valueOf(txtUnitPrice.getText()) * Double.valueOf(txtQuantity.getText())));
				btnAdd.setDisable(false);
			} else {
				txtQuantity.setText("1");
				txtPrice.setText(
						String.valueOf(Double.valueOf(txtUnitPrice.getText()) * Double.valueOf(txtQuantity.getText())));
				btnAdd.setDisable(false);
			}
			txtMinPrice.setText(String.valueOf(product.getMinPrice()));
			txtSellPrice.setText(String.valueOf(product.getSellPrice()));
			txtProductName.setText(product.getName());
			txtProductID.setText(String.valueOf(product.getID()));
			txtStock.setText(String.valueOf(product.getStock()));
			txtBarcode.setText(product.getBarcode());
		}
	}

	@FXML
	public void clickAdd(ActionEvent event) throws IOException {
		boolean flag = true;
		if (flag && product == null) {
			flag = false;
			Function.alert("you have to pick product from the list");
		}
		if (flag && !DBConnect.ifProductExistInPurchaseTemp(product.getID())) {
			flag = false;
			Function.alert("this product all ready exist in the list");
		}
		if (flag && Double.valueOf(txtQuantity.getText()) < 1) {
			flag = false;
			Function.alert("the Quantity most be at lest 1");
		}
		if (flag) {
//			Product product = new Product(cboProductList.getSelectionModel().getSelectedItem());
			PurchaseDetails PurchaseDetails = new PurchaseDetails(0, 0, product.getID(), product.getName(),
					Function.format(Double.valueOf(txtUnitPrice.getText())), Integer.parseInt(txtQuantity.getText()),
					Function.format(Double.valueOf(txtPrice.getText())),
					Function.format(Double.valueOf(txtMinPrice.getText())),
					Function.format(Double.valueOf(txtSellPrice.getText())));
			DBConnect.AddNewTempPurchaseProductTable(PurchaseDetails);
			// if the (cost,Min,Sell)Price change well update the product in DB
			if (Double.valueOf(txtUnitPrice.getText()) != product.getCostPrice()
					|| Double.valueOf(txtMinPrice.getText()) != product.getMinPrice()
					|| Double.valueOf(txtSellPrice.getText()) != product.getSellPrice()) {
				product.setCostPrice(Function.format(Double.valueOf(txtUnitPrice.getText())));
				product.setMinPrice(Function.format(Double.valueOf(txtMinPrice.getText())));
				product.setSellPrice(Function.format(Double.valueOf(txtSellPrice.getText())));
				DBConnect.updateProduct(product);
			}
			refresh();
		}
	}

	@FXML
	public void clickAddNewProduct(ActionEvent event) throws IOException {
		Main.addProductScene();
		refresh();
		cboProductList.setPromptText("select Product");
	}

	@FXML
	public void clickConfirm(ActionEvent event) {
		boolean flag = true;
		if (observableList.isEmpty()) {
			Function.alert("you have to insert product");
			flag = false;
		}

		if (flag) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setHeaderText("Confirmation");
			alert.setContentText("Are you sure you want to confirm this Purchase");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				Purchase Purchase = new Purchase(LocalDate.now(), quantity, sum);
				DBConnect.AddNewPurchase(Purchase);
				int PurchaseID = DBConnect.lastPurchase();
				DBConnect.AddNewPurchaseDetails(PurchaseID, observableList);
				DBConnect.deldeteProductPurchaseTempTable();
				DBConnect.FormatProductPurchaseTempTable();
				Node node = (Node) event.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.close();
			}
		}
	}

	@FXML
	public void clickNewPurchase(ActionEvent event) throws IOException {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("reset Cart");
		alert.setHeaderText("reset Cart");
		alert.setContentText("Are you sure you want to reset the Cart");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			DBConnect.deldeteProductPurchaseTempTable();
			DBConnect.FormatProductPurchaseTempTable();
			refresh();
		}
	}

	@FXML
	public void clickCancel(ActionEvent event) {
		DBConnect.deldeteProductPurchaseTempTable();
		DBConnect.FormatProductPurchaseTempTable();
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();

	}

	private boolean chickAll() {
		if (!Function.doubleNumbers(txtUnitPrice.getText()) || !Function.doubleNumbers(txtPrice.getText())
				|| !Function.doubleNumbers(txtMinPrice.getText()) || !Function.doubleNumbers(txtSellPrice.getText())
				|| !Function.allNumbers(txtQuantity.getText())) {
			return false;
		}
//		else if (Double.valueOf(txtUnitPrice.getText()) > Double.valueOf(txtMinPrice.getText())
//				|| Double.valueOf(txtMinPrice.getText()) > Double.valueOf(txtSellPrice.getText())) {
//			return false;
//		}

		return true;
	}

	@FXML
	void onKeySearch(KeyEvent event) {
		cboProductList.setItems(null);
		try {
			cboProductList.setItems(DBConnect.fillComboBoxProduct(productQuery));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		cboProductList.show();
		if (txtSearch.getText().equals("")) {
			productQuery = "SELECT * FROM `tblproducts` WHERE `active` = 1 AND( `product` LIKE '%%' OR `ID` LIKE '%%' OR `barcode` LIKE '%%') ;";
		} else {
			productQuery = "SELECT * FROM `tblproducts` WHERE `active` = 1 AND( `product` LIKE '%" + txtSearch.getText()
					+ "%' OR `ID` LIKE '%" + txtSearch.getText() + "%' OR `barcode` LIKE '%" + txtSearch.getText()
					+ "%') ;";
		}

	}

	@FXML
	public void onKeyUnitPrice(KeyEvent event) {
		btnAdd.setDisable(true);
		if (chickAll()) {
			txtPrice.setText(
					String.valueOf(Double.valueOf(txtUnitPrice.getText()) * Double.valueOf(txtQuantity.getText())));
			btnAdd.setDisable(false);
			if (Double.valueOf(txtUnitPrice.getText()) > Double.valueOf(txtMinPrice.getText())) {
				txtMinPrice.setText(txtUnitPrice.getText());
			}
			if (Double.valueOf(txtUnitPrice.getText()) > Double.valueOf(txtSellPrice.getText())) {
				txtSellPrice.setText(txtUnitPrice.getText());
			}
		}

	}

	@FXML
	public void onKeyQuantity(KeyEvent event) {
		btnAdd.setDisable(true);
		if (Function.allNumbers(txtQuantity.getText()) && Function.doubleNumbers(txtUnitPrice.getText())) {
			txtPrice.setText(
					String.valueOf(Double.valueOf(txtUnitPrice.getText()) * Double.valueOf(txtQuantity.getText())));
			btnAdd.setDisable(false);
		} else if (Function.doubleNumbers(txtPrice.getText()) && Function.allNumbers(txtQuantity.getText())) {
			txtUnitPrice.setText(
					String.valueOf(Double.valueOf(txtPrice.getText()) / Double.valueOf(txtQuantity.getText())));
		}
		if (chickAll()) {
			btnAdd.setDisable(false);
		}
	}

	@FXML
	public void onKeyPrice(KeyEvent event) {
		btnAdd.setDisable(true);
		if (chickAll()) {
			txtUnitPrice.setText(
					String.valueOf(Double.valueOf(txtPrice.getText()) / Double.valueOf(txtQuantity.getText())));
			btnAdd.setDisable(false);
			if (Double.valueOf(txtUnitPrice.getText()) > Double.valueOf(txtMinPrice.getText())) {
				txtMinPrice.setText(txtUnitPrice.getText());
			}
			if (Double.valueOf(txtUnitPrice.getText()) > Double.valueOf(txtSellPrice.getText())) {
				txtSellPrice.setText(txtUnitPrice.getText());
			}
		}
	}

	@FXML
	void onKeyMinPrice(KeyEvent event) {
		btnAdd.setDisable(true);
		if (chickAll()) {
			if (Double.valueOf(txtUnitPrice.getText()) <= Double.valueOf(txtMinPrice.getText())
					&& Double.valueOf(txtMinPrice.getText()) <= Double.valueOf(txtSellPrice.getText())) {
				btnAdd.setDisable(false);
			}
		}
	}

	@FXML
	void onKeySellPrice(KeyEvent event) {
		btnAdd.setDisable(true);
		if (chickAll()) {
			if (Double.valueOf(txtUnitPrice.getText()) <= Double.valueOf(txtMinPrice.getText())
					&& Double.valueOf(txtMinPrice.getText()) <= Double.valueOf(txtSellPrice.getText())) {
				btnAdd.setDisable(false);
			}
		}
	}
}
