package application;

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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

public class addNewSaleSceneController implements Initializable {
	boolean flag = false;
	double sum = 0;
	double vat = 0;
	double profit = 0;
	int quantity = 0;

	private void txtDeleter() {
		txtProductName.setText("");
		txtBarcode.setText("");
		txtProductID.setText("");
		txtUnitCost.setText("");
		txtMinPrice.setText("");
		txtStock.setText("");
		txtSellPrice.setText("");
		txtQuantity.setText("");
		txtTotalVat.setText("");
		txtTotalBeforeVat.setText("");
		txtTotalPrice.setText("");
	}

	@FXML
	private Button btnAdd;

	@FXML
	private Button btnCancel;

	@FXML
	private Button btnConfirmAndPDF;

	@FXML
	private Button btnConfirmAndPrint;

	@FXML
	private Button btnNewSale;

	@FXML
	private ComboBox<Customer> cboCustomerList;

	@FXML
	private ComboBox<Product> cboProductList;

	@FXML
	private TableColumn<SaleDetails, Void> columnAction;

	@FXML
	private TableColumn<SaleDetails, Void> columnID;

	@FXML
	private TableColumn<SaleDetails, String> columnProductName;

	@FXML
	private TableColumn<SaleDetails, String> columnQuantity;

	@FXML
	private TableColumn<SaleDetails, String> columnRowPrice;

	@FXML
	private TableColumn<SaleDetails, String> columnUnitPrice;

	@FXML
	private TableView<SaleDetails> tblNewSale;

	@FXML
	private TextField txtBarcode;

	@FXML
	private TextField txtCustomerName;

	@FXML
	private TextField txtID;

	@FXML
	private TextField txtIdNum;

	@FXML
	private TextField txtMinPrice;

	@FXML
	private TextField txtProductID;

	@FXML
	private TextField txtProductName;

	@FXML
	private TextField txtQuantity;

	@FXML
	private TextField txtSearchCustomer;

	@FXML
	private TextField txtSearchProduct;

	@FXML
	private TextField txtSellPrice;

	@FXML
	private TextField txtStock;

	@FXML
	private TextField txtSum;

	@FXML
	private TextField txtTotal;

	@FXML
	private TextField txtTotalPrice;

	@FXML
	private TextField txtUnitCost;

	@FXML
	private TextField txtVat;
	@FXML
	private TextField txtTotalBeforeVat;
	@FXML
	private TextField txtTotalVat;

	String query = null;
	Product product = null;
	Customer customer = null;
	String productQuery = "SELECT * FROM `tblproducts` WHERE `stock` > 0 AND `active` = 1;";
	String CustomerQuery = "";
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnAdd.setDisable(true);
		cboCustomerList.setItems(null);
		try {
			cboCustomerList.setItems(DBConnect.fillComboBoxCustomer(CustomerQuery));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		cboProductList.setItems(null);
		try {
			cboProductList.setItems(DBConnect.fillComboBoxProduct(productQuery));
		} catch (SQLException e) {
			e.printStackTrace();
		}

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
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void loadData() {

//		columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		columnProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
		columnUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
		columnQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
		columnRowPrice.setCellValueFactory(new PropertyValueFactory<>("RowPrice"));
		tblNewSale.setItems(observableList);
	}

	ObservableList<SaleDetails> observableList = FXCollections.observableArrayList();

	private void refresh() throws IOException {
		addCountToTableSaleDetails();
		txtDeleter();
		product = null;
		sum = 0;
		quantity = 0;
		profit = 0;
		try {
			connection = DBConnect.getConnect();
			observableList.clear();
			query = "SELECT `tbltempsale`.*, `tblproducts`.*FROM `tbltempsale` LEFT JOIN `tblproducts` ON `tbltempsale`.`productId` = `tblproducts`.`ID`;";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				observableList.add(new SaleDetails(resultSet.getInt("tbltempsale.ID"), 0,
						resultSet.getInt("tblproducts.ID"), resultSet.getString("product"),
						resultSet.getDouble("unitPrice"), resultSet.getInt("quantity"), resultSet.getDouble("rowPrice"),
						resultSet.getDouble("rowProfit")));
				tblNewSale.setItems(observableList);

				sum += resultSet.getDouble("rowPrice");
				quantity += resultSet.getInt("quantity");
				profit += resultSet.getDouble("rowProfit");
			}
			preparedStatement.close();
			connection.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		vat = sum * 0.17;
		txtSum.setText(String.valueOf(Function.format(sum - vat)));
		txtVat.setText(String.valueOf(Function.format(vat)));
		txtTotal.setText(String.valueOf(Function.format(sum)));
	}

	private void addButtonToTable() throws IOException {
		Callback<TableColumn<SaleDetails, Void>, TableCell<SaleDetails, Void>> cellFactory = new Callback<TableColumn<SaleDetails, Void>, TableCell<SaleDetails, Void>>() {
			@Override
			public TableCell<SaleDetails, Void> call(final TableColumn<SaleDetails, Void> param) {
				final TableCell<SaleDetails, Void> cell = new TableCell<SaleDetails, Void>() {

					private final Button btn = new Button("Delete");

					{
						btn.setOnAction((ActionEvent event) -> {
							SaleDetails saleDetails = getTableView().getItems().get(getIndex());
							DBConnect.deldeteProductSaleTempTable(saleDetails);
							try {
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

	private void addCountToTableSaleDetails() throws IOException {

		Callback<TableColumn<SaleDetails, Void>, TableCell<SaleDetails, Void>> cellFactory = new Callback<TableColumn<SaleDetails, Void>, TableCell<SaleDetails, Void>>() {
			@Override
			public TableCell<SaleDetails, Void> call(final TableColumn<SaleDetails, Void> param) {

				final TableCell<SaleDetails, Void> cell = new TableCell<SaleDetails, Void>() {

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
		columnID.setCellFactory(cellFactory);
	}

	@FXML
	void onActionCustomerList(ActionEvent event) {
		if (cboCustomerList.getSelectionModel().getSelectedItem() != null) {
			customer = cboCustomerList.getSelectionModel().getSelectedItem();
			txtCustomerName.setText(customer.getName());
			txtID.setText(String.valueOf(customer.getID()));
			txtIdNum.setText(customer.getCustomerId());
		}
	}

	@FXML
	void onKeyCustomerName(KeyEvent event) throws SQLException {
		cboCustomerList.show();
		CustomerQuery = txtSearchCustomer.getText();
		cboCustomerList.setItems(null);
		cboCustomerList.setItems(DBConnect.fillComboBoxCustomer(CustomerQuery));
	}

	@FXML
	void onActionProductList(ActionEvent event) {
		if (cboProductList.getSelectionModel().getSelectedItem() != null) {
			product = cboProductList.getSelectionModel().getSelectedItem();

			txtProductName.setText(product.getName());
			txtBarcode.setText(product.getBarcode());
			txtProductID.setText(String.valueOf(product.getID()));
			txtUnitCost.setText(String.valueOf(product.getCostPrice()));
			txtMinPrice.setText(String.valueOf(product.getMinPrice()));
			txtStock.setText(String.valueOf(product.getStock()));

			txtSellPrice.setText(String.valueOf(product.getSellPrice()));
			txtQuantity.setText("1");
			txtTotalPrice.setText(String.valueOf(product.getSellPrice()));
			txtTotalBeforeVat.setText(String.valueOf(Function.format(product.getSellPrice() * 0.83)));
			txtTotalVat.setText(String
					.valueOf(Function.format(product.getSellPrice() - Function.format(product.getSellPrice()  * 0.83))));
			btnAdd.setDisable(false);
		}
	}

	@FXML
	void onKeyProductName(KeyEvent event) throws SQLException {
		cboProductList.show();
		if (txtSearchProduct.getText().equals("")) {
			productQuery = "SELECT * FROM `tblproducts` WHERE `active` = 1 AND( `product` LIKE '%%' OR `ID` LIKE '%%' OR `barcode` LIKE '%%') ;";
		} else {
			productQuery = "SELECT * FROM `tblproducts` WHERE `active` = 1 AND( `product` LIKE '%"
					+ txtSearchProduct.getText() + "%' OR `ID` LIKE '%" + txtSearchProduct.getText()
					+ "%' OR `barcode` LIKE '%" + txtSearchProduct.getText() + "%') ;";
		}
		cboProductList.setItems(null);
		cboProductList.setItems(DBConnect.fillComboBoxProduct(productQuery));
	}

	@FXML
	void clickAdd(ActionEvent event) throws IOException {
		flag = true;
		if (flag && product == null) {
			flag = false;
			Function.alert("you have to pick product from the list");
		}
		if (flag && !DBConnect.ifProductExistInSaleTemp(product.getID())) {
			flag = false;
			Function.alert("this product all ready exist in the list");
		}
		if (flag && Double.valueOf(txtSellPrice.getText()) < product.getMinPrice()) {
			Function.alert("the min price is " + cboProductList.getSelectionModel().getSelectedItem().getMinPrice()
					+ " cannot sell this product lower than that");
			flag = false;
		}
		if (flag && product.getStock() < Integer.parseInt(txtQuantity.getText())) {
			Function.alert("ther is only " + cboProductList.getSelectionModel().getSelectedItem().getStock()
					+ " in Stock you cannot sell more than that");
			flag = false;
		}
		if (flag && Double.valueOf(txtQuantity.getText()) < 1) {
			flag = false;
			Function.alert("the Quantity most be at lest 1");
		}
		if (flag) {
			SaleDetails saleDetails = new SaleDetails(0, 0, product.getID(), product.getName(),
					Function.format(Double.valueOf(txtSellPrice.getText())), Integer.parseInt(txtQuantity.getText()),
					Function.format(Double.valueOf(txtSellPrice.getText()) * Integer.parseInt(txtQuantity.getText())),
					Function.format(Double.valueOf(txtTotalPrice.getText()))
							- (Integer.parseInt(txtQuantity.getText()) * product.getCostPrice()));
			DBConnect.AddNewTempSaleProductTable(saleDetails);
			refresh();
		}
	}

	@FXML
	void clickCancel(ActionEvent event) {
		DBConnect.deldeteProductSaleTempTable();
		DBConnect.FormatProductSaleTempTable();
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}

	@FXML
	void clickConfirmAndPDF(ActionEvent event) {
		flag = true;
		if (cboCustomerList.getSelectionModel().getSelectedItem() == null) {
			Function.alert("you have to select customer");
			flag = false;
		}
		if (observableList.isEmpty()) {
			Function.alert("you have to insert product");
			flag = false;
		}

		if (flag) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setHeaderText("Confirmation");
			alert.setContentText("Are you sure you want to confirm this Sale");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				Sale sale = new Sale(LocalDate.now(), cboCustomerList.getSelectionModel().getSelectedItem().getID(),
						cboCustomerList.getSelectionModel().getSelectedItem().getName(),
						cboCustomerList.getSelectionModel().getSelectedItem().getCustomerId(), quantity, sum, profit,
						"");
				DBConnect.AddNewSale(sale);
				int saleID = DBConnect.lastSale();
				DBConnect.AddNewSaleDetails(saleID, observableList);
				DBConnect.deldeteProductSaleTempTable();
				DBConnect.FormatProductSaleTempTable();
				DBConnect.report(saleID);
				Node node = (Node) event.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.close();
			}
		}
	}

	@FXML
	void clickConfirmAndPrint(ActionEvent event) {
		flag = true;
		if (cboCustomerList.getSelectionModel().getSelectedItem() == null) {
			Function.alert("you have to select customer");
			flag = false;
		}
		if (observableList.isEmpty()) {
			Function.alert("you have to insert product");
			flag = false;
		}

		if (flag) {
			Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Confirmation");
			alert.setHeaderText("Confirmation");
			alert.setContentText("Are you sure you want to confirm this Sale");
			Optional<ButtonType> result = alert.showAndWait();
			if (result.isPresent() && result.get() == ButtonType.OK) {
				Sale sale = new Sale(LocalDate.now(), cboCustomerList.getSelectionModel().getSelectedItem().getID(),
						cboCustomerList.getSelectionModel().getSelectedItem().getName(),
						cboCustomerList.getSelectionModel().getSelectedItem().getCustomerId(), quantity, sum, profit,
						"");
				DBConnect.AddNewSale(sale);
				int saleID = DBConnect.lastSale();
				DBConnect.AddNewSaleDetails(saleID, observableList);
				DBConnect.deldeteProductSaleTempTable();
				DBConnect.FormatProductSaleTempTable();
				DBConnect.report(saleID);
				Node node = (Node) event.getSource();
				Stage stage = (Stage) node.getScene().getWindow();
				stage.close();
			}
		}
	}

	@FXML
	void clickNewSale(ActionEvent event) throws IOException {
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("reset Cart");
		alert.setHeaderText("reset Cart");
		alert.setContentText("Are you sure you want to reset the Cart");
		Optional<ButtonType> result = alert.showAndWait();
		if (result.isPresent() && result.get() == ButtonType.OK) {
			DBConnect.deldeteProductSaleTempTable();
			DBConnect.FormatProductSaleTempTable();
			refresh();
		}
	}

	@FXML
	void onKeyQuantity(KeyEvent event) {
		btnAdd.setDisable(true);
		if (Function.doubleNumbers(txtSellPrice.getText()) && Function.allNumbers(txtQuantity.getText())) {
			txtTotalPrice.setText(String.valueOf(
					Function.format(Double.valueOf(txtSellPrice.getText()) * Double.valueOf(txtQuantity.getText()))));
			txtTotalVat.setText(String.valueOf(Function.format(Double.valueOf(txtTotalPrice.getText()) * 0.17)));
			txtTotalBeforeVat.setText(String.valueOf(Function.format(Double.valueOf(txtTotalPrice.getText()) * 0.83)));
			btnAdd.setDisable(false);
		} else if (Function.doubleNumbers(txtTotalPrice.getText()) && Function.allNumbers(txtQuantity.getText())) {
			txtSellPrice.setText(String.valueOf(
					Function.format(Double.valueOf(txtTotalPrice.getText()) / Double.valueOf(txtQuantity.getText()))));
			txtTotalVat.setText(String.valueOf(Function.format(Double.valueOf(txtTotalPrice.getText()) * 0.17)));
			txtTotalBeforeVat.setText(String.valueOf(Function.format(Double.valueOf(txtTotalPrice.getText()) * 0.83)));
			btnAdd.setDisable(false);
		}

	}

	@FXML
	void onKeySellPrice(KeyEvent event) {
		btnAdd.setDisable(true);
		if (Function.doubleNumbers(txtSellPrice.getText()) && Function.allNumbers(txtQuantity.getText())) {
			txtTotalPrice.setText(String.valueOf(
					Function.format(Double.valueOf(txtSellPrice.getText()) * Double.valueOf(txtQuantity.getText()))));
			txtTotalVat.setText(String.valueOf(Function.format(Double.valueOf(txtTotalPrice.getText()) * 0.17)));
			txtTotalBeforeVat.setText(String.valueOf(Function.format(Double.valueOf(txtTotalPrice.getText()) * 0.83)));
			btnAdd.setDisable(false);
		}
	}

	@FXML
	void onKeyTotalPrice(KeyEvent event) {
		btnAdd.setDisable(true);
		if (Function.doubleNumbers(txtTotalPrice.getText()) && Function.allNumbers(txtQuantity.getText())) {
			txtSellPrice.setText(String.valueOf(
					Function.format(Double.valueOf(txtTotalPrice.getText()) / Double.valueOf(txtQuantity.getText()))));
			txtTotalVat.setText(String.valueOf(Function.format(Double.valueOf(txtTotalPrice.getText()) * 0.17)));
			txtTotalBeforeVat.setText(String.valueOf(Function.format(Double.valueOf(txtTotalPrice.getText()) * 0.83)));
			btnAdd.setDisable(false);
		}
	}

//	@FXML
//	void onKeyPrice(KeyEvent event) {
//		btnAdd.setDisable(true);
//		if (Function.doubleNumbers(txtPrice.getText()) && Function.allNumbers(txtQuantity.getText())) {
//			txtUnitCost.setText(
//					String.valueOf(Double.valueOf(txtPrice.getText()) / Double.valueOf(txtQuantity.getText())));
//			btnAdd.setDisable(false);
//		}
//	}
//
//	@FXML
//	void onKeyQuantity(KeyEvent event) {
//		btnAdd.setDisable(true);
//		if (Function.allNumbers(txtQuantity.getText()) && Function.doubleNumbers(txtUnitCost.getText())) {
//			txtPrice.setText(
//					String.valueOf(Double.valueOf(txtUnitCost.getText()) * Double.valueOf(txtQuantity.getText())));
//			btnAdd.setDisable(false);
//		} else if (Function.doubleNumbers(txtPrice.getText()) && Function.allNumbers(txtQuantity.getText())) {
//			txtUnitCost.setText(
//					String.valueOf(Double.valueOf(txtPrice.getText()) / Double.valueOf(txtQuantity.getText())));
//			btnAdd.setDisable(false);
//		}
//	}
//
//	@FXML
//	void onKeyUnitPrice(KeyEvent event) {
//		btnAdd.setDisable(true);
//		if (Function.doubleNumbers(txtUnitCost.getText()) && Function.allNumbers(txtQuantity.getText())) {
//			txtPrice.setText(
//					String.valueOf(Double.valueOf(txtUnitCost.getText()) * Double.valueOf(txtQuantity.getText())));
//			btnAdd.setDisable(false);
//		}
//	}
}
