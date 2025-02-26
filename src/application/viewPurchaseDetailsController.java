package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
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
import javafx.event.ActionEvent;

import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class viewPurchaseDetailsController implements Initializable {
	int purchaseID;
	int totalQuantity = 0;
	double totalPrice = 0;
	@FXML
	private Label lblPurchaseNumber;
	@FXML
	private Label lbllDate;
	@FXML
	private TableView<PurchaseDetails> tblPurchaseDetails;
	@FXML
	private TableColumn<PurchaseDetails, Void> columnID;
	@FXML
	private TableColumn<PurchaseDetails, String> columnProductID;
	@FXML
	private TableColumn<PurchaseDetails, String> columnProductName;
	@FXML
	private TableColumn<PurchaseDetails, String> columnUnitPrice;
	@FXML
	private TableColumn<PurchaseDetails, String> columnQuantity;
	@FXML
	private TableColumn<PurchaseDetails, String> columnMinPrice;
	@FXML
	private TableColumn<PurchaseDetails, String> columnSellPrice;
	@FXML
	private TableColumn<PurchaseDetails, String> columnRowPrice;
	@FXML
	private Button btnCancel;
	@FXML
	private TextField txtTotalPrice;
	@FXML
	private TextField txtTotalQuantity;

	String query = null;
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	public void getData(Purchase purchase) {
		purchaseID = purchase.getID();
		lblPurchaseNumber.setText(String.valueOf(purchase.getID()));
		lbllDate.setText(purchase.getDate().toString());
		loadData();
		refresh();
	}

	private void loadData() {

//		columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		columnProductID.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
		columnProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
		columnUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
		columnQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
		columnMinPrice.setCellValueFactory(new PropertyValueFactory<>("MinPrice"));
		columnSellPrice.setCellValueFactory(new PropertyValueFactory<>("SellPrice"));
		columnRowPrice.setCellValueFactory(new PropertyValueFactory<>("RowPrice"));
		tblPurchaseDetails.setItems(observableList);
	}

	ObservableList<PurchaseDetails> observableList = FXCollections.observableArrayList();

	private void refresh() {

		try {
			connection = DBConnect.getConnect();
			observableList.clear();
			query = "SELECT `tblpurchasedetails`.*, `tblproducts`.* FROM `tblpurchasedetails` LEFT JOIN `tblproducts` ON `tblpurchasedetails`.`ProductID` = `tblproducts`.`ID` WHERE `tblpurchasedetails`.`purchaseID` ="
					+ purchaseID + ";";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				observableList.add(new PurchaseDetails(resultSet.getInt("ID"), resultSet.getInt("purchaseID"),
						resultSet.getInt("ProductID"), resultSet.getString("product"), resultSet.getDouble("unitPrice"),
						resultSet.getInt("rowQuantity"), resultSet.getDouble("rowPrice"),
						resultSet.getDouble("minPrice"), resultSet.getDouble("sellPrice")));
				tblPurchaseDetails.setItems(observableList);

				totalPrice += resultSet.getDouble("rowPrice");
				totalQuantity += resultSet.getInt("rowQuantity");
			}
			preparedStatement.close();
			connection.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		txtTotalPrice.setText(String.valueOf(totalPrice));
		txtTotalQuantity.setText(String.valueOf(totalQuantity));
	}

	private void addCountToTablePurchaseDetails() throws IOException {

		Callback<TableColumn<PurchaseDetails, Void>, TableCell<PurchaseDetails, Void>> cellFactory = new Callback<TableColumn<PurchaseDetails, Void>, TableCell<PurchaseDetails, Void>>() {
			@Override
			public TableCell<PurchaseDetails, Void> call(final TableColumn<PurchaseDetails, Void> param) {

				final TableCell<PurchaseDetails, Void> cell = new TableCell<PurchaseDetails, Void>() {

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
	public void clickCancel(ActionEvent event) {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			addCountToTablePurchaseDetails();
		} catch (IOException e) {

			e.printStackTrace();
		}

	}
}
