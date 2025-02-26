package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
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
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class saleMainSceneController implements Initializable {

	double sum = 0;
	double profit = 0;

	@FXML
	private Button btnAddNewSale;
	@FXML
	private TextField txtSearch;
	@FXML
	private Button btnSearchByDate;
	@FXML
	private Button btnRefrech;

	@FXML
	private TableColumn<Sale, String> columnCustomerCardID;

	@FXML
	private TableColumn<Sale, String> columnCustomerID;

	@FXML
	private TableColumn<Sale, String> columnCustomerName;

	@FXML
	private TableColumn<Sale, String> columnDate;

	@FXML
	private TableColumn<Sale, String> columnQuantity;

	@FXML
	private TableColumn<Sale, String> columnSaleID;

	@FXML
	private TableColumn<Sale, String> columnSalePrice;

	@FXML
	private TableColumn<Sale, String> columnSaleProfit;
	@FXML
	private TableColumn<Sale, Void> columnAction;

	@FXML
	private TableView<Sale> tblSale;

	@FXML
	private TextField txtSum;
	@FXML
	private TextField txtProfit;
	@FXML
	private DatePicker datFrom;

	@FXML
	private DatePicker datTo;

	String query = "SELECT `tblsale`.*, `tblcustomers`.*FROM `tblsale` LEFT JOIN `tblcustomers` ON `tblsale`.`customerID` = `tblcustomers`.`ID` ORDER BY `tblsale`.`ID` DESC ;";
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;
	Customer customer = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		txtSearch.setTooltip(new Tooltip("Search by customer Name  or Customer ID or customer Card ID or Sale ID"));
		loadData();
		try {
			refresh();
		} catch (IOException e) {
			e.printStackTrace();
		}
		txtSearch.setOnKeyTyped(event->{
			try {
				refresh();
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
		});
		datechecker();
	}

	private void loadData() {

		columnSaleID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		columnDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
		columnCustomerID.setCellValueFactory(new PropertyValueFactory<>("Customer"));
		columnCustomerCardID.setCellValueFactory(new PropertyValueFactory<>("CustomerCardID"));
		columnQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
		columnSalePrice.setCellValueFactory(new PropertyValueFactory<>("TotalPrice"));
		columnSaleProfit.setCellValueFactory(new PropertyValueFactory<>("SaleProfit"));
		columnCustomerName.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));

		tblSale.setItems(observableList);

	}

	ObservableList<Sale> observableList = FXCollections.observableArrayList();

	private void refresh() throws NumberFormatException, IOException {
		filter();
		sum = 0;
		profit = 0;

		try {
			connection = DBConnect.getConnect();
			observableList.clear();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				observableList.add(new Sale(resultSet.getInt("tblsale.ID"), resultSet.getDate("saleDate").toLocalDate(),
						resultSet.getInt("tblcustomers.ID"), resultSet.getString("name"),
						resultSet.getString("tblcustomers.customerId"), resultSet.getInt("quantity"),
						resultSet.getDouble("totalPrice"), resultSet.getDouble("SaleProfit"),
						resultSet.getString("note")));

				sum += resultSet.getDouble("totalPrice");
				profit += resultSet.getDouble("SaleProfit");
				tblSale.setItems(observableList);
//				// Initial filtered list
//				FilteredList<Sale> filterdData = new FilteredList<>(observableList, b -> true);
//
//				txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
//					filterdData.setPredicate(Sale -> {
//						// If no search value then display all
//						if (newValue.isEmpty() || newValue.isBlank() || newValue == null) {
//							return true;
//						}
//
//						String input = newValue.toLowerCase();
//
//						if (String.valueOf(Sale.getID()).toLowerCase().indexOf(input) > -1) {
//							return true;// means we found match in name
//						} else if (Sale.getDate().toString().toLowerCase().indexOf(input) > -1) {
//							return true;// means we found match in Sale ID
//						} else if (Sale.getCustomerName().toLowerCase().indexOf(input) > -1) {
//							return true;// means we found match in Sale Phone Number
//						} else if (Sale.getCustomerCardID().toLowerCase().indexOf(input) > -1) {
//							return true;// means we found match in Sale Phone Number
//						} else
//							return false;
//					});
//				});
//				SortedList<Sale> sortedData = new SortedList<>(filterdData);
//
//				// Bind sorted result with table view
//				sortedData.comparatorProperty().bind(tblSale.comparatorProperty());
//
//				// apply filtered and sorted data to the table view
//				tblSale.setItems(sortedData);
//
			}
			preparedStatement.close();
			connection.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		txtSum.setText(String.valueOf(Function.format(sum)));
		txtProfit.setText(String.valueOf(Function.format(profit)));
		addButtonToTable();
	}

	private void addButtonToTable() throws IOException {
		Callback<TableColumn<Sale, Void>, TableCell<Sale, Void>> cellFactory = new Callback<TableColumn<Sale, Void>, TableCell<Sale, Void>>() {
			@Override
			public TableCell<Sale, Void> call(final TableColumn<Sale, Void> param) {
				final TableCell<Sale, Void> cell = new TableCell<Sale, Void>() {

					private final Button btnView = new Button("view");

					{
						btnView.setOnAction((ActionEvent event) -> {
							Sale Sale = getTableView().getItems().get(getIndex());
							try {
								Main.viewSaleDetailScene(Sale);
							} catch (IOException e) {
								e.printStackTrace();
							}
						});
					}
					private final Button btnPDF = new Button("Print");

					{
						btnPDF.setOnAction((ActionEvent event) -> {
							DBConnect.report(getTableView().getItems().get(getIndex()).getID());
						});
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {
							HBox managebtn = new HBox(btnView, btnPDF);
							managebtn.setStyle("-fx-alignment:center");
							HBox.setMargin(btnView, new Insets(2, 2, 0, 3));
							HBox.setMargin(btnPDF, new Insets(2, 3, 0, 2));

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

	@FXML
	void clickRefrech(ActionEvent event) throws NumberFormatException, IOException {
		query = "SELECT `tblsale`.*, `tblcustomers`.*FROM `tblsale` LEFT JOIN `tblcustomers` ON `tblsale`.`customerID` = `tblcustomers`.`ID` ORDER BY `tblsale`.`ID` DESC ;";
		txtSearch.setText("");
		datFrom.setValue(null);
		datTo.setValue(null);
		refresh();
	}

	@FXML
	public void clickAddNewSale(ActionEvent event) throws IOException {
		Main.addSaleScene();
		refresh();
	}

	@FXML
	void clickSearchByDate(ActionEvent event) throws NumberFormatException, IOException {
		refresh();
	}

	private void filter() {
		query = "SELECT `tblsale`.*, `tblcustomers`.*FROM `tblsale` LEFT JOIN `tblcustomers` ON `tblsale`.`customerID` = `tblcustomers`.`ID` WHERE ( `tblsale`.`ID` LIKE '%"
				+ txtSearch.getText() + "%' OR `tblcustomers`.`ID` LIKE '%" + txtSearch.getText()
				+ "%' OR `tblcustomers`.`customerId` LIKE '%" + txtSearch.getText()
				+ "%' OR `tblcustomers`.`name` LIKE '%" + txtSearch.getText() + "%') ";
		if (datFrom.getValue() != null) {
			query += " AND `tblsale`.`saleDate` >= '" + datFrom.getValue().toString() + "' ";
		}
		if (datTo.getValue() != null) {
			query += " AND `tblsale`.`saleDate` <= '" + datTo.getValue().toString() + "' ";
		}
		query += "ORDER BY `tblsale`.`ID` DESC";
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
