package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class purchaseMainSceneController implements Initializable {
	int sum;
	@FXML
	private Button btnAddNewPurchase;
	@FXML
	private Button btnRefrech;
	@FXML
	private DatePicker datFrom;
	@FXML
	private DatePicker datTo;
	@FXML
	private Button btnSearch;
	@FXML
	private TableView<Purchase> tblPurchase;
	@FXML
	private TableColumn<Purchase, String> columnID;
	@FXML
	private TableColumn<Purchase, String> columnDate;
	@FXML
	private TableColumn<Purchase, String> columnQuantity;
	@FXML
	private TableColumn<Purchase, String> columnPurchasePrice;
	@FXML
	private TableColumn<Purchase, Void> columnAction;
	@FXML
	private TextField txtSearch;
	@FXML
	private TextField txtSum;

	String query = "SELECT * FROM `tblpurchase` ORDER BY `tblpurchase`.`ID` DESC";
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		loadData();
		try {
			refresh();
		} catch (IOException e) {
			e.printStackTrace();
		}
		txtSearch.setOnKeyTyped(event -> {
			try {
				refresh();
			} catch (NumberFormatException | IOException e) {
				e.printStackTrace();
			}
		});
		datechecker();
	}

	private void loadData() {

		columnID.setCellValueFactory(new PropertyValueFactory<>("ID"));
		columnDate.setCellValueFactory(new PropertyValueFactory<>("Date"));
		columnQuantity.setCellValueFactory(new PropertyValueFactory<>("Quantity"));
		columnPurchasePrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
		tblPurchase.setItems(observableList);
	}

	ObservableList<Purchase> observableList = FXCollections.observableArrayList();

	private void refresh() throws NumberFormatException, IOException {
		filter();
		sum = 0;
		try {
			connection = DBConnect.getConnect();
			observableList.clear();
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				observableList.add(new Purchase(resultSet.getInt("ID"), resultSet.getDate("PurchaseDate").toLocalDate(),
						resultSet.getInt("quantity"), resultSet.getDouble("Price")));

				sum += resultSet.getDouble("Price");
				tblPurchase.setItems(observableList);
			}
			preparedStatement.close();
			connection.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		txtSum.setText(String.valueOf(sum));
		addButtonToTable();
	}

	private void addButtonToTable() throws IOException {
		Callback<TableColumn<Purchase, Void>, TableCell<Purchase, Void>> cellFactory = new Callback<TableColumn<Purchase, Void>, TableCell<Purchase, Void>>() {
			@Override
			public TableCell<Purchase, Void> call(final TableColumn<Purchase, Void> param) {
				final TableCell<Purchase, Void> cell = new TableCell<Purchase, Void>() {

					private final Button btnPrchAction = new Button("View");

					{
						btnPrchAction.setOnAction((ActionEvent event) -> {

							Purchase Purchase = getTableView().getItems().get(getIndex());
							try {
								Main.viewPurchaseDetailScene(Purchase);
							} catch (IOException e) {
								e.printStackTrace();
							}

						});
						btnPrchAction.setStyle("-fx-pref-width:150");
					}

					@Override
					public void updateItem(Void item, boolean empty) {
						super.updateItem(item, empty);
						if (empty) {
							setGraphic(null);
						} else {

							setGraphic(btnPrchAction);
						}
					}
				};
				return cell;
			}
		};

		columnAction.setCellFactory(cellFactory);

	}

	@FXML
	public void clickAddNewPruchase(ActionEvent event) throws IOException {
		Main.addPurchaseScene();
		refresh();
	}

	@FXML
	public void clickRefrech(ActionEvent event) throws IOException {
		datFrom.setValue(null);
		datTo.setValue(null);
		txtSearch.setText("");
		query = "SELECT * FROM `tblpurchase` ORDER BY `tblpurchase`.`ID` DESC";
		refresh();
	}

	@FXML
	public void clickSearch(ActionEvent event) throws NumberFormatException, IOException {
		filter();
		refresh();
	}

	private void filter() {
		query = "SELECT * FROM `tblpurchase` WHERE ID LIKE '%" + txtSearch.getText() + "%' ";
		if (datFrom.getValue() != null) {
			query += " AND purchaseDate >= '" + datFrom.getValue().toString() + "' ";
		}
		if (datTo.getValue() != null) {
			query += " AND purchaseDate <= '" + datTo.getValue().toString() + "' ";
		}
		query += " ORDER BY `tblpurchase`.`ID` DESC";
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
