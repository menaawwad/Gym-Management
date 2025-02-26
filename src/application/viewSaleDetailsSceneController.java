package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class viewSaleDetailsSceneController {

	int saleID;
	@FXML
	private Button btnCancel;
	@FXML
	private Button btnPrint;

	@FXML
	private TableColumn<SaleDetails, String> columnID;

	@FXML
	private TableColumn<SaleDetails, String> columnProductID;

	@FXML
	private TableColumn<SaleDetails, String> columnProductName;

	@FXML
	private TableColumn<SaleDetails, String> columnQuantity;

	@FXML
	private TableColumn<SaleDetails, String> columnRowPrice;

	@FXML
	private TableColumn<SaleDetails, String> columnRowProfit;

	@FXML
	private TableColumn<SaleDetails, String> columnUnitPrice;

	@FXML
	private TableView<SaleDetails> tblNewSale;

	@FXML
	private Label lblSaleNumber;

	@FXML
	private Label lbllCustomerID;

	@FXML
	private Label lbllCustomerName;

	public void getData(Sale sale) {
		saleID = sale.getID();
		lblSaleNumber.setText(String.valueOf(sale.getID()));
		lbllCustomerID.setText(String.valueOf(sale.getCustomer()));
		lbllCustomerName.setText(String.valueOf(sale.getCustomerName()));
		loadData();
		refresh();
	}

	String query = null;
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	private void loadData() {
		

		columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		columnProductID.setCellValueFactory(new PropertyValueFactory<>("ProductID"));
		columnProductName.setCellValueFactory(new PropertyValueFactory<>("productName"));
		columnUnitPrice.setCellValueFactory(new PropertyValueFactory<>("UnitPrice"));
		columnQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
		columnRowPrice.setCellValueFactory(new PropertyValueFactory<>("RowPrice"));
		columnRowProfit.setCellValueFactory(new PropertyValueFactory<>("RowProfit"));
		tblNewSale.setItems(observableList);
	}

	ObservableList<SaleDetails> observableList = FXCollections.observableArrayList();

	private void refresh() {

		try {
			connection = DBConnect.getConnect();
			observableList.clear();
			query = "SELECT `tblsaledetails`.*, `tblproducts`.* FROM `tblsaledetails` LEFT JOIN `tblproducts` ON `tblsaledetails`.`productId` = `tblproducts`.`ID` WHERE `tblsaledetails`.`saleID` = "
					+ saleID + ";";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				observableList.add(new SaleDetails(resultSet.getInt("tblsaledetails.ID"), resultSet.getInt("saleID"),
						resultSet.getInt("tblproducts.ID"), resultSet.getString("product"),
						resultSet.getDouble("unitPrice"), resultSet.getInt("quantity"), resultSet.getDouble("rowPrice"),
						resultSet.getDouble("rowProfit")));
				tblNewSale.setItems(observableList);
			}
			preparedStatement.close();
			connection.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
    void clickPrint(ActionEvent event) {
		DBConnect.report(saleID);
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
    }
	
	@FXML
	void clickCancel(ActionEvent event) {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}
}
