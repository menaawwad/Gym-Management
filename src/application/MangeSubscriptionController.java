package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.RadioButton;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;

public class MangeSubscriptionController implements Initializable {

	Customer customer;
	Subscription subscription;
	SubscriptionType subscriptionType;
	int type = 0;
	int months = 0;
	Long daysLift = (long) 0;
	@FXML
	private TextField txtID;
	@FXML
	private TextField txtName;
	@FXML
	private TextField txtIdNum;
	@FXML
	private TextField txtPrice;
	@FXML
	private TextField txtStatus;
	@FXML
	private TextField txtDays;
	@FXML
	private DatePicker datStartingDate;
	@FXML
	private DatePicker datEndingDate;
	@FXML
	private DatePicker datUpdatedEndingDate;
	@FXML
	private DatePicker datUpdatedStartingDate;
	@FXML
	private RadioButton opt6Months;
	@FXML
	private ToggleGroup grpSubscriptionType;
	@FXML
	private RadioButton opt1Year;
	@FXML
	private RadioButton opt3Months;
	@FXML
	private RadioButton opt1Month;
	@FXML
	private RadioButton optFree;
	@FXML
	private ToggleButton tglBtnActivate;
	@FXML
	private ToggleButton tglBtnFreeze;
	@FXML
	private TableView<Subscription> tblvSubscription;
	@FXML
	private TableColumn<Subscription, String> coulmnSubscriptionID;
	@FXML
	private TableColumn<Subscription, String> coulmnRegistrationDate;
	@FXML
	private TableColumn<Subscription, String> coulmnStartingDate;
	@FXML
	private TableColumn<Subscription, String> coulmnEndingDate;
	@FXML
	private TableColumn<Subscription, String> coulmnSubscriptionType;
	@FXML
	private TableColumn<Subscription, String> coulmnPrice;
	@FXML
	private TableColumn<Subscription, String> coulmnStatus;
	@FXML
	private TableColumn<Subscription, String> coulmnLastUpdate;
	@FXML
	private TableColumn<Subscription, String> coulmnPauseDate;
	@FXML
	private Button btnCancel;
	@FXML
	private Button btnProceed;

	String query = null;
	Connection connection = null;
	PreparedStatement preparedStatement = null;
	ResultSet resultSet = null;

//	this function starting the page property and disable the unnecessary buttons and fill the time and days lift 
	public void getCustomer(Customer cust) {
		customer = new Customer(DBConnect.selectCustomer(cust.getID()));
		txtID.setText(Integer.toString(cust.getID()));
		txtName.setText(cust.getName());
		txtIdNum.setText(cust.getCustomerId());
		datStartingDate.setDisable(true);
		datStartingDate.setStyle("-fx-opacity: 1");
		datStartingDate.getEditor().setStyle("-fx-opacity: 1");
		datEndingDate.setDisable(true);
		datEndingDate.setStyle("-fx-opacity: 1");
		datEndingDate.getEditor().setStyle("-fx-opacity: 1");
		loadData();
		refresh();
		tglBtnActivate.setDisable(true);
		tglBtnFreeze.setDisable(true);
		btnProceed.setDisable(true);
		if (!observableList.isEmpty()) {
			subscription = new Subscription(observableList.get(0));
			txtStatus.setText(observableList.get(0).getStatus());
			txtStatus.setStyle("-fx-background-color: green;-fx-text-fill: white;");
			datStartingDate.setValue(observableList.get(0).getStartinDate().toLocalDate());
			datEndingDate.setValue(observableList.get(0).getEndDate().toLocalDate());

			if (txtStatus.getText().equals("Active")) {
				txtStatus.setStyle("-fx-background-color: green;-fx-text-fill: white;");
				daysLift = ChronoUnit.DAYS.between(LocalDate.now(), datEndingDate.getValue());
				txtDays.setText(daysLift.toString());
				tglBtnActivate.setDisable(true);
				tglBtnFreeze.setDisable(false);
				datUpdatedStartingDate.setDisable(true);
				datUpdatedStartingDate.setStyle("-fx-opacity: 1");
				datUpdatedStartingDate.getEditor().setStyle("-fx-opacity: 1");
			}
			if (txtStatus.getText().equals("Inactive")) {
				txtStatus.setStyle("-fx-background-color: yellow;");
				daysLift = ChronoUnit.DAYS.between(datStartingDate.getValue(), datEndingDate.getValue());
				txtDays.setText(daysLift.toString());
				tglBtnFreeze.setDisable(false);
				tglBtnActivate.setDisable(false);
			}
			if (txtStatus.getText().equals("Freeze")) {
				txtStatus.setStyle("-fx-background-color: blue;-fx-text-fill: white;");
				daysLift = ChronoUnit.DAYS.between(
						observableList.get(0).getPauseDate().toLocalDate(),
						datEndingDate.getValue());
				txtDays.setText(daysLift.toString());
				tglBtnFreeze.setDisable(true);
				tglBtnActivate.setDisable(false);
			}
			if (txtStatus.getText().equals("Expired")) {
				txtStatus.setStyle("-fx-background-color: red;-fx-text-fill: white;");
				txtDays.setText(daysLift.toString());
				tglBtnActivate.setDisable(true);
				tglBtnFreeze.setDisable(true);
			}

		}
	}

	@FXML
	void clickSubscriptionType(ActionEvent event) {
		if (opt1Year.isSelected()) {
			type = 4;
			months = 12;
		} else if (opt6Months.isSelected()) {
			type = 3;
			months = 6;
		} else if (opt3Months.isSelected()) {
			type = 2;
			months = 3;
		} else if (opt1Month.isSelected()) {
			type = 1;
			months = 1;
		} else if (optFree.isSelected()) {
			type = 5;
			months = 0;
		}
		subscriptionType = new SubscriptionType(DBConnect.getSubscriptionType(type));
		String price = Double.toString(subscriptionType.getPrice());
		btnProceed.setDisable(false);

		if (txtStatus.getText().equals("") || txtStatus.getText().equals("Expired")) {
			datUpdatedStartingDate.setValue(LocalDate.now());
			if (months != 0) {
				datUpdatedEndingDate.setValue(LocalDate.now().plusMonths(months).minusDays(1));
				txtPrice.setText(price);
				txtDays.setText(Long.toString(
						ChronoUnit.DAYS.between(datUpdatedStartingDate.getValue(), datUpdatedEndingDate.getValue())));
			}
		}
		if (txtStatus.getText().equals("Active") || txtStatus.getText().equals("Inactive")) {
			datUpdatedStartingDate.setValue(datStartingDate.getValue());
			{
				if (months != 0) {
					datUpdatedEndingDate.setValue(datEndingDate.getValue().plusMonths(months).minusDays(1));
					txtPrice.setText(price);
					txtDays.setText(
							Long.toString(ChronoUnit.DAYS.between(LocalDate.now(), datUpdatedEndingDate.getValue())));
				}
			}
		}
		if (txtStatus.getText().equals("Freeze")) {
			datUpdatedStartingDate.setValue(datStartingDate.getValue());
			if (months != 0) {
				datUpdatedEndingDate.setValue(datEndingDate.getValue().plusMonths(months).minusDays(1));
				txtPrice.setText(price);
				txtDays.setText(Long.toString(ChronoUnit.DAYS.between(subscription.getPauseDate().toLocalDate(),
						datUpdatedEndingDate.getValue())));
			}
		}
	}

	@FXML
	void clickActivate(ActionEvent event) {
		txtPrice.setText("0");
		if (observableList.get(0).getStatus().equals("Inactive")
				&& tglBtnActivate.isSelected()) {
			datUpdatedStartingDate.setValue(LocalDate.now());
			Long daysLeft = ChronoUnit.DAYS.between(datStartingDate.getValue(), datEndingDate.getValue());
			datUpdatedEndingDate.setValue(LocalDate.now().plusDays(daysLeft));
			btnProceed.setDisable(false);
			txtPrice.setText("0");
			txtDays.setText(daysLift.toString());
		} else if (tglBtnActivate.isSelected() && tglBtnActivate.isSelected()) {
			btnProceed.setDisable(false);
			txtPrice.setText("50");
			datUpdatedStartingDate.setValue(datStartingDate.getValue());

			Long daysLeft = ChronoUnit.DAYS.between(
					observableList.get(0).getPauseDate().toLocalDate(),
					observableList.get(0).getEndDate().toLocalDate());
			datUpdatedEndingDate.setValue(LocalDate.now().plusDays(daysLeft));
			txtDays.setText(daysLift.toString());
		} else
			btnProceed.setDisable(true);
	}

	@FXML
	void clickFreeze(ActionEvent event) {
		txtPrice.setText("0");
		if (tglBtnFreeze.isSelected()) {
			btnProceed.setDisable(false);
			txtDays.setText(daysLift.toString());
		} else
			btnProceed.setDisable(true);
	}

	@FXML
	public void clickCancel(ActionEvent event) {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}

	@FXML
	void actionDateEnd(ActionEvent event) {
		if (optFree.isSelected())
			if (datUpdatedStartingDate.getValue() != null && datUpdatedEndingDate.getValue() != null) {
				if (datUpdatedStartingDate.getValue().isBefore(LocalDate.now())) {
					txtDays.setText(Function.daysBetween(LocalDate.now(), datUpdatedEndingDate.getValue()));
				} else
					txtDays.setText(
							Function.daysBetween(datUpdatedStartingDate.getValue(), datUpdatedEndingDate.getValue()));
			}
	}

	@FXML
	void actionDateStart(ActionEvent event) {
		if (optFree.isSelected())
			if (datUpdatedEndingDate.getValue() != null && datUpdatedStartingDate.getValue() != null) {
				if (datUpdatedStartingDate.getValue().isBefore(LocalDate.now()))
					txtDays.setText(Function.daysBetween(LocalDate.now(), datUpdatedEndingDate.getValue()));
				else
					txtDays.setText(
							Function.daysBetween(datUpdatedStartingDate.getValue(), datUpdatedEndingDate.getValue()));
			}
	}

	@FXML
	void clickDateEnd(MouseEvent event) {
		if (txtStatus.getText().equals("Active") || txtStatus.getText().equals("Inactive"))
			datUpdatedStartingDate.setValue(datStartingDate.getValue());
		optFree.setSelected(true);
		type = 5;
		btnProceed.setDisable(false);
	}

	@FXML
	void clickDateStart(MouseEvent event) {
		optFree.setSelected(true);
		type = 5;
	}

	@FXML
	void typedDateEnd(InputMethodEvent event) {
//		System.out.println("yes");
		if (txtStatus.getText().equals("Active") || txtStatus.getText().equals("Inactive"))
			datUpdatedStartingDate.setValue(datStartingDate.getValue());
		optFree.setSelected(true);
		type = 5;
		btnProceed.setDisable(false);
	}

	@FXML
	void typedDateStart(InputMethodEvent event) {
		if (event.equals(event)) {
			optFree.setSelected(true);
			type = 5;
//			System.out.println("yes");
		}
	}

	@FXML
	public void clickProceed(ActionEvent event) throws IOException {
		boolean flag = true;

		if (optFree.isSelected() || opt1Month.isSelected() || opt3Months.isSelected() || opt6Months.isSelected()
				|| opt1Year.isSelected()) {
			if (datUpdatedStartingDate.getValue() != null && datUpdatedEndingDate.getValue() != null) {
				if (ChronoUnit.DAYS.between(datUpdatedStartingDate.getValue(), datUpdatedEndingDate.getValue()) < 0) {
					flag = false;
					Function.alert("please chick the date, ending date cannot be before starting day ");
				}
				if (datEndingDate.getValue() != null)
					if (ChronoUnit.DAYS.between(datEndingDate.getValue(), datUpdatedEndingDate.getValue()) < 1) {
						flag = false;
						Function.alert("please chick the date,the new ending date cannot be before old ending date ");
					}

			} else {
				flag = false;
				Function.alert("please chick the date ther is somthing not valid ");
			}
		}

		if (!Function.doubleNumbers(txtPrice.getText())) {
			Function.alert("please chick the price ");
			flag = false;
		}

		if (flag) {
			if (observableList.isEmpty() || txtStatus.getText().equals("Expired")) {
				String status = "Active";
				if (datUpdatedStartingDate.getValue().isAfter(LocalDate.now()))
					status = "Inactive";
				Subscription sub = new Subscription(Integer.valueOf(txtID.getText()), type,
						DBConnect.getSubscriptionType(type).getType(), Double.valueOf(txtPrice.getText()),
						Date.valueOf(LocalDate.now()), Date.valueOf(datUpdatedStartingDate.getValue()),
						Date.valueOf(datUpdatedEndingDate.getValue()), Date.valueOf(LocalDate.now()), null, status);
				Main.confirmationNew(txtName.getText(), txtIdNum.getText(), txtDays.getText(), sub);
				if (Main.close) {
					Main.close = false;
					Exit(event);
				}

			} else if (tglBtnFreeze.isSelected()) {
				if (txtStatus.getText().equals("Inactive")) {
					Subscription sub = new Subscription(Integer.valueOf(txtID.getText()),
							observableList.get(0).getSubscriptionType(),
							DBConnect
									.getSubscriptionType(
											observableList.get(0).getSubscriptionType())
									.getType(),
							0, observableList.get(0).getRegistrationdate(),
							Date.valueOf(LocalDate.now()),
							Date.valueOf(LocalDate.now().plusDays(
									ChronoUnit.DAYS.between(datStartingDate.getValue(), datEndingDate.getValue()))),
							Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), "Freeze");
					Main.confirmationFreeze(txtName.getText(), txtIdNum.getText(), txtDays.getText(), sub);
					if (Main.close) {
						Main.close = false;
						Exit(event);
					}
				} else {
					Subscription sub = new Subscription(Integer.valueOf(txtID.getText()),
							observableList.get(0).getSubscriptionType(),
							DBConnect
									.getSubscriptionType(
											observableList.get(0).getSubscriptionType())
									.getType(),
							0, observableList.get(0).getRegistrationdate(),
							Date.valueOf(datStartingDate.getValue()), Date.valueOf(datEndingDate.getValue()),
							Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()), "Freeze");
					Main.confirmationFreeze(txtName.getText(), txtIdNum.getText(), txtDays.getText(), sub);
					if (Main.close) {
						Main.close = false;
						Exit(event);
					}
				}

			} else if (tglBtnActivate.isSelected()) {
				Subscription sub = new Subscription(Integer.valueOf(txtID.getText()),
						observableList.get(0).getSubscriptionType(),
						DBConnect.getSubscriptionType(
								observableList.get(0).getSubscriptionType()).getType(),
						Double.valueOf(txtPrice.getText()),
						observableList.get(0).getRegistrationdate(),
						Date.valueOf(datUpdatedStartingDate.getValue()), Date.valueOf(datUpdatedEndingDate.getValue()),
						Date.valueOf(LocalDate.now()), null, "Active");
				Main.confirmationActivate(txtName.getText(), txtIdNum.getText(), txtDays.getText(), sub);
				if (Main.close) {
					Main.close = false;
					Exit(event);
				}
			}

			else if (observableList.get(0).getStatus().equals("Active")) {
				Subscription sub = new Subscription(Integer.valueOf(txtID.getText()), type,
						DBConnect.getSubscriptionType(type).getType(), Double.valueOf(txtPrice.getText()),
						observableList.get(0).getRegistrationdate(),
						observableList.get(0).getStartinDate(),
						Date.valueOf(datUpdatedEndingDate.getValue()), Date.valueOf(LocalDate.now()), null, "Active");
				Main.confirmationExtend(txtName.getText(), txtIdNum.getText(), txtDays.getText(), sub,
						Date.valueOf(datEndingDate.getValue()));
				if (Main.close) {
					Main.close = false;
					Exit(event);
				}

			} else if (observableList.get(0).getStatus().equals("Inactive")) {
				Subscription sub = new Subscription(Integer.valueOf(txtID.getText()), type,
						DBConnect.getSubscriptionType(type).getType(), Double.valueOf(txtPrice.getText()),
						observableList.get(0).getRegistrationdate(),
						Date.valueOf(datUpdatedStartingDate.getValue()), Date.valueOf(datUpdatedEndingDate.getValue()),
						Date.valueOf(LocalDate.now()), null, "Inactive");
				Main.confirmationExtend(txtName.getText(), txtIdNum.getText(), txtDays.getText(), sub,
						Date.valueOf(datEndingDate.getValue()));
				if (Main.close) {
					Main.close = false;
					Exit(event);
				}

			} else if (txtStatus.getText().equals("Freeze")) {
				Subscription sub = new Subscription(Integer.valueOf(txtID.getText()),
						observableList.get(0).getSubscriptionType(),
						DBConnect.getSubscriptionType(
								observableList.get(0).getSubscriptionType()).getType(),
						Double.valueOf(txtPrice.getText()),
						observableList.get(0).getRegistrationdate(),
						Date.valueOf(datUpdatedStartingDate.getValue()), Date.valueOf(datUpdatedEndingDate.getValue()),
						Date.valueOf(LocalDate.now()), observableList.get(0).getPauseDate(),
						"Freeze");
				Main.confirmationExtend(txtName.getText(), txtIdNum.getText(), txtDays.getText(), sub,
						Date.valueOf(datEndingDate.getValue()));
				if (Main.close) {
					Main.close = false;
					Exit(event);
				}
			}

		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
	}

	private void Exit(ActionEvent event) {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}

	private void loadData() {
		coulmnSubscriptionID.setCellValueFactory(new PropertyValueFactory<>("Id"));
		coulmnRegistrationDate.setCellValueFactory(new PropertyValueFactory<>("registrationdate"));
		coulmnStartingDate.setCellValueFactory(new PropertyValueFactory<>("StartinDate"));
		coulmnEndingDate.setCellValueFactory(new PropertyValueFactory<>("EndDate"));
		coulmnSubscriptionType.setCellValueFactory(new PropertyValueFactory<>("strsubscriptionType"));
		coulmnPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
		coulmnStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
		coulmnLastUpdate.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
		coulmnPauseDate.setCellValueFactory(new PropertyValueFactory<>("PauseDate"));
		tblvSubscription.setItems(observableList);
	}

	ObservableList<Subscription> observableList = FXCollections.observableArrayList();

	private void refresh() {
		try {
			connection = DBConnect.getConnect();
			observableList.clear();
			query = "SELECT `tblsubscription`.*, `tblsubscriptiontype`.* FROM `tblsubscription` LEFT JOIN `tblsubscriptiontype` ON `tblsubscription`.`subscriptionType` = `tblsubscriptiontype`.`ID` WHERE `tblsubscription`.`customernumber` = "
					+ customer.getID() + " ORDER BY `tblsubscription`.`ID` DESC ;";
			preparedStatement = connection.prepareStatement(query);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				observableList.add(new Subscription(resultSet.getInt("ID"), resultSet.getInt("customernumber"),
						resultSet.getInt("subscriptionType"), resultSet.getString("type"), resultSet.getDouble("price"),
						resultSet.getDate("registrationdate"), resultSet.getDate("startingdate"),
						resultSet.getDate("endingdate"), resultSet.getDate("lastUpdate"),
						resultSet.getDate("pauseDate"), resultSet.getString("status")));
				tblvSubscription.setItems(observableList);
			}
			preparedStatement.close();
			connection.close();
			resultSet.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
}
