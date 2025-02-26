package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;

public class statisticsMainSceneController implements Initializable {

	@FXML
	private LineChart<String, Double> lcExpinses;
	@FXML
	private BarChart<String, Double> bcStatus;
	@FXML
	private BarChart<String, Integer> bcSubscriptionTypes;
	@FXML
	private PieChart pcIncime;
	@FXML
	private Button btnFilter;
	@FXML
	private Button btnRefrech;
	@FXML
	private DatePicker datFrom;
	@FXML
	private DatePicker datTo;
	@FXML
	private Label txtSales;
	@FXML
	private Label txtSubscription;

	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	String allCustoemr = "SELECT COUNT(*) FROM `tblcustomers` WHERE removed = 0 ;";
	String ActiveClients = "SELECT COUNT(*) FROM `tblcustomers` WHERE `status` = 'Active' AND removed = 0 ";
	String ExpiredCustomers = "SELECT COUNT(*) FROM `tblcustomers` WHERE `status` = 'Expired' AND removed = 0 ";
	String FreezeCustomers = "SELECT COUNT(*) FROM `tblcustomers` WHERE `status` = 'Freeze' AND removed = 0 ";
	String InactiveCustomers = "SELECT COUNT(*) FROM `tblcustomers` WHERE `status` = 'Inactive' AND removed = 0 ";
	String NoStatusCustomers = "SELECT COUNT(*) FROM `tblcustomers` WHERE `status` = '' AND removed = 0 ";

	String supplies = "SELECT SUM(Cost) FROM `tblexpenses` WHERE `Type` = 'supplies'";
	String equipment = "SELECT SUM(Cost) FROM `tblexpenses` WHERE `Type` = 'equipment'";
	String variableExpenses = "SELECT SUM(Cost) FROM `tblexpenses` WHERE `Type` = 'variable Expenses'";
	String rent = "SELECT SUM(Cost) FROM `tblexpenses` WHERE `Type` = 'rent'";
	String markting = "SELECT SUM(Cost) FROM `tblexpenses` WHERE `Type` = 'markting'";
	String salaries = "SELECT SUM(Cost) FROM `tblexpenses` WHERE `Type` = 'salaries'";
	String comunication = "SELECT SUM(Cost) FROM `tblexpenses` WHERE `Type` = 'comunication'";
	String other = "SELECT SUM(Cost) FROM `tblexpenses` WHERE `Type` = 'other'";

	String Subscription = "SELECT SUM(price) FROM `tblsubscription`";
	String Sales = "SELECT SUM(SaleProfit) FROM `tblsale`";

	private void oregnalData() {
		Subscription = "SELECT SUM(price) FROM `tblsubscription`";
		Sales = "SELECT SUM(SaleProfit) FROM `tblsale`";

		supplies = "SELECT SUM(Cost) FROM `tblexpenses` WHERE `Type` = 'supplies'";
		equipment = "SELECT SUM(Cost) FROM `tblexpenses` WHERE `Type` = 'equipment'";
		variableExpenses = "SELECT SUM(Cost) FROM `tblexpenses` WHERE `Type` = 'variable Expenses'";
		rent = "SELECT SUM(Cost) FROM `tblexpenses` WHERE `Type` = 'rent'";
		markting = "SELECT SUM(Cost) FROM `tblexpenses` WHERE `Type` = 'markting'";
		salaries = "SELECT SUM(Cost) FROM `tblexpenses` WHERE `Type` = 'salaries'";
		comunication = "SELECT SUM(Cost) FROM `tblexpenses` WHERE `Type` = 'comunication'";
		other = "SELECT SUM(Cost) FROM `tblexpenses` WHERE `Type` = 'other'";
	}

	private void loadData() {
		lcExpinses.getData().clear();
		bcStatus.getData().clear();
		bcSubscriptionTypes.getData().clear();
		pcIncime.getData().clear();

		ObservableList<PieChart.Data> PieDate = FXCollections.observableArrayList(
				new PieChart.Data("Subscription", DBConnect.returnSumOfTable(Subscription)),
				new PieChart.Data("Sales", DBConnect.returnSumOfTable(Sales)));
		pcIncime.getData().addAll(PieDate);
		pcIncime.setLegendSide(Side.BOTTOM);
		pcIncime.setLegendVisible(false);
		txtSubscription
				.setText("Subscription: " + String.valueOf(Function.format(DBConnect.returnSumOfTable(Subscription))));
		txtSales.setText("Sales: " + String.valueOf(Function.format(DBConnect.returnSumOfTable(Sales))));

		XYChart.Series<String, Double> seriesCustomerStatus = new XYChart.Series<String, Double>();
		seriesCustomerStatus.getData()
				.add(new XYChart.Data<String, Double>("Active", DBConnect.returnSumOfTable(ActiveClients)));
		seriesCustomerStatus.getData()
				.add(new XYChart.Data<String, Double>("Expired", DBConnect.returnSumOfTable(ExpiredCustomers)));
		seriesCustomerStatus.getData()
				.add(new XYChart.Data<String, Double>("Freeze", DBConnect.returnSumOfTable(FreezeCustomers)));
		seriesCustomerStatus.getData()
				.add(new XYChart.Data<String, Double>("Inactive", DBConnect.returnSumOfTable(InactiveCustomers)));
		seriesCustomerStatus.getData()
				.add(new XYChart.Data<String, Double>("No Status", DBConnect.returnSumOfTable(NoStatusCustomers)));
		bcStatus.getData().add(seriesCustomerStatus);
		bcStatus.setLegendVisible(false);

		XYChart.Series<String, Double> seriesExpinses = new XYChart.Series<String, Double>();
		seriesExpinses.getData()
				.add(new XYChart.Data<String, Double>("supplies", DBConnect.returnSumOfTable(supplies)));
		seriesExpinses.getData()
				.add(new XYChart.Data<String, Double>("equipment", DBConnect.returnSumOfTable(equipment)));
		seriesExpinses.getData().add(
				new XYChart.Data<String, Double>("variable Expenses", DBConnect.returnSumOfTable(variableExpenses)));
		seriesExpinses.getData().add(new XYChart.Data<String, Double>("rent", DBConnect.returnSumOfTable(rent)));
		seriesExpinses.getData()
				.add(new XYChart.Data<String, Double>("markting", DBConnect.returnSumOfTable(markting)));
		seriesExpinses.getData()
				.add(new XYChart.Data<String, Double>("salaries", DBConnect.returnSumOfTable(salaries)));
		seriesExpinses.getData()
				.add(new XYChart.Data<String, Double>("comunication", DBConnect.returnSumOfTable(comunication)));
		seriesExpinses.getData().add(new XYChart.Data<String, Double>("other", DBConnect.returnSumOfTable(other)));
		lcExpinses.getData().add(seriesExpinses);
		lcExpinses.setLegendVisible(false);

		XYChart.Series<String, Integer> seriesSubscriptionType = new XYChart.Series<String, Integer>();
		seriesSubscriptionType.getData()
				.add(new XYChart.Data<String, Integer>("1 Month", DBConnect.returnNumOFSubscriptionType(1)));
		seriesSubscriptionType.getData()
				.add(new XYChart.Data<String, Integer>("3 Months", DBConnect.returnNumOFSubscriptionType(2)));
		seriesSubscriptionType.getData()
				.add(new XYChart.Data<String, Integer>("6 Months", DBConnect.returnNumOFSubscriptionType(3)));
		seriesSubscriptionType.getData()
				.add(new XYChart.Data<String, Integer>("1 Year", DBConnect.returnNumOFSubscriptionType(4)));
		seriesSubscriptionType.getData()
				.add(new XYChart.Data<String, Integer>("Free", DBConnect.returnNumOFSubscriptionType(5)));
		bcSubscriptionTypes.getData().add(seriesSubscriptionType);
		bcSubscriptionTypes.setLegendVisible(false);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		loadData();
		datechecker();
	}

	@FXML
	void clickFilter(ActionEvent event) {
		oregnalData();
		if (datFrom.getValue() != null && datTo.getValue() == null) {
			Subscription += "WHERE `lastUpdate` >= '" + datFrom.getValue() + "'";
			Sales += "WHERE `saleDate` >= '" + datFrom.getValue() + "'";

			supplies += " AND Date >= '" + datFrom.getValue() + "'";
			equipment += " AND Date >= '" + datFrom.getValue() + "'";
			variableExpenses += " AND Date >= '" + datFrom.getValue() + "'";
			rent += " AND Date >= '" + datFrom.getValue() + "'";
			markting += " AND Date >= '" + datFrom.getValue() + "'";
			salaries += " AND Date >= '" + datFrom.getValue() + "'";
			comunication += " AND Date >= '" + datFrom.getValue() + "'";
			other += " AND Date >= '" + datFrom.getValue() + "'";
		}
		if (datFrom.getValue() == null && datTo.getValue() != null) {
			Subscription += "WHERE `lastUpdate` <= '" + datTo.getValue() + "'";
			Sales += "WHERE `saleDate` <= '" + datTo.getValue() + "'";

			supplies += " AND Date <= '" + datTo.getValue() + "'";
			equipment += " AND Date <= '" + datTo.getValue() + "'";
			variableExpenses += " AND Date <= '" + datTo.getValue() + "'";
			rent += " AND Date <= '" + datTo.getValue() + "'";
			markting += " AND Date <= '" + datTo.getValue() + "'";
			salaries += " AND Date <= '" + datTo.getValue() + "'";
			comunication += " AND Date <= '" + datTo.getValue() + "'";
			other += " AND Date <= '" + datTo.getValue() + "'";

		}
		if (datFrom.getValue() != null && datTo.getValue() != null) {
			Subscription += "WHERE `lastUpdate` >= '" + datFrom.getValue() + "' AND `lastUpdate` <= '"
					+ datTo.getValue() + "'";
			Sales += "WHERE `lastUpdate` >= '" + datFrom.getValue() + "' AND `lastUpdate` <= '" + datTo.getValue()
					+ "'";

			supplies += " AND Date >= '" + datFrom.getValue() + "' AND Date <= '" + datTo.getValue() + "'";
			equipment += " AND Date >= '" + datFrom.getValue() + "' AND Date <= '" + datTo.getValue() + "'";
			variableExpenses += " AND Date >= '" + datFrom.getValue() + "' AND Date <= '" + datTo.getValue() + "'";
			rent += " AND Date >= '" + datFrom.getValue() + "' AND Date <= '" + datTo.getValue() + "'";
			markting += " AND Date >= '" + datFrom.getValue() + "' AND Date <= '" + datTo.getValue() + "'";
			salaries += " AND Date >= '" + datFrom.getValue() + "' AND Date <= '" + datTo.getValue() + "'";
			comunication += " AND Date >= '" + datFrom.getValue() + "' AND Date <= '" + datTo.getValue() + "'";
			other += " AND Date >= '" + datFrom.getValue() + "' AND Date <= '" + datTo.getValue() + "'";
		}
		loadData();
	}

	@FXML
	void clickRefrech(ActionEvent event) {
		datFrom.setValue(null);
		datTo.setValue(null);
		oregnalData();
		loadData();
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
