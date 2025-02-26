package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

public class MainPageForGymController implements Initializable{
	private BorderPane view;
	
	@FXML
	private HBox HomeBtn;

	@FXML
	private HBox LogoutBtn;

	@FXML
	private BorderPane borderPane4Scene;

	@FXML
	private HBox customerBtn;

	@FXML
	private HBox expensesBtn;

	@FXML
	private HBox productsBtn;

	@FXML
	private HBox purchaseBtn;

	@FXML
	private HBox reportsBtn;

	@FXML
	private HBox salesBtn;

	@FXML
	void CustomerClick(MouseEvent event) throws IOException {
		view = FXMLLoader.load(getClass().getResource("customerMainScene.fxml"));
		borderPane4Scene.setCenter(view);
		
		HomeBtn.setStyle("-fx-background-color: #deeaee;");
		productsBtn.setStyle("-fx-background-color: #deeaee;");
		customerBtn.setStyle("-fx-background-color: green; -fx-background-radius: 20;");
		expensesBtn.setStyle("-fx-background-color: #deeaee;");
		salesBtn.setStyle("-fx-background-color: #deeaee;");
		reportsBtn.setStyle("-fx-background-color: #deeaee;");
		purchaseBtn.setStyle("-fx-background-color: #deeaee;");
		LogoutBtn.setStyle("-fx-background-color: #deeaee;");
	}

	@FXML
	void HomeClick(MouseEvent event) throws IOException {
		view = FXMLLoader.load(getClass().getResource("homeMainScene.fxml"));
		borderPane4Scene.setCenter(view);
		
		HomeBtn.setStyle("-fx-background-color: green;-fx-background-radius: 20;");
		productsBtn.setStyle("-fx-background-color: #deeaee;");
		customerBtn.setStyle("-fx-background-color: #deeaee;");
		expensesBtn.setStyle("-fx-background-color: #deeaee;");
		salesBtn.setStyle("-fx-background-color: #deeaee;");
		reportsBtn.setStyle("-fx-background-color: #deeaee;");
		purchaseBtn.setStyle("-fx-background-color: #deeaee;");
		LogoutBtn.setStyle("-fx-background-color: #deeaee;");
	}

	@FXML
	void expensesClick(MouseEvent event) throws IOException {
		view = FXMLLoader.load(getClass().getResource("expensesMainScene.fxml"));
		borderPane4Scene.setCenter(view);
		
		HomeBtn.setStyle("-fx-background-color: #deeaee;");
		productsBtn.setStyle("-fx-background-color: #deeaee;");
		customerBtn.setStyle("-fx-background-color: #deeaee;");
		expensesBtn.setStyle("-fx-background-color: green;-fx-background-radius: 20;");
		salesBtn.setStyle("-fx-background-color: #deeaee;");
		reportsBtn.setStyle("-fx-background-color: #deeaee;");
		purchaseBtn.setStyle("-fx-background-color: #deeaee;");
		LogoutBtn.setStyle("-fx-background-color: #deeaee;");
	}
	
	@FXML
    void purchaseClick(MouseEvent event) throws IOException {		
		view = FXMLLoader.load(getClass().getResource("purchaseMainScene.fxml"));
		borderPane4Scene.setCenter(view);
		
		HomeBtn.setStyle("-fx-background-color: #deeaee;");
		productsBtn.setStyle("-fx-background-color: #deeaee;");
		customerBtn.setStyle("-fx-background-color: #deeaee;");
		expensesBtn.setStyle("-fx-background-color: #deeaee;");
		salesBtn.setStyle("-fx-background-color: #deeaee;");
		reportsBtn.setStyle("-fx-background-color: #deeaee;");
		purchaseBtn.setStyle("-fx-background-color: green;-fx-background-radius: 20;");
		LogoutBtn.setStyle("-fx-background-color: #deeaee;");
    }
	
	@FXML
    void reportsClick(MouseEvent event) throws IOException {
		view = FXMLLoader.load(getClass().getResource("statisticsMainScene.fxml"));
		borderPane4Scene.setCenter(view);
		
		HomeBtn.setStyle("-fx-background-color: #deeaee;");
		productsBtn.setStyle("-fx-background-color: #deeaee;");
		customerBtn.setStyle("-fx-background-color: #deeaee;");
		expensesBtn.setStyle("-fx-background-color: #deeaee;");
		salesBtn.setStyle("-fx-background-color: #deeaee;");
		reportsBtn.setStyle("-fx-background-color: green;-fx-background-radius: 20;");
		purchaseBtn.setStyle("-fx-background-color: #deeaee;");
		LogoutBtn.setStyle("-fx-background-color: #deeaee;");
    }

	@FXML
	void ProductClick(MouseEvent event) throws IOException {
		view = FXMLLoader.load(getClass().getResource("productMainScene.fxml"));
		borderPane4Scene.setCenter(view);
		HomeBtn.setStyle("-fx-background-color: #deeaee;");
		productsBtn.setStyle("-fx-background-color: green;-fx-background-radius: 20;");
		customerBtn.setStyle("-fx-background-color: #deeaee;");
		expensesBtn.setStyle("-fx-background-color: #deeaee;");
		salesBtn.setStyle("-fx-background-color: #deeaee;");
		reportsBtn.setStyle("-fx-background-color: #deeaee;");
		purchaseBtn.setStyle("-fx-background-color: #deeaee;");
		LogoutBtn.setStyle("-fx-background-color: #deeaee;");
	}

	@FXML
	void logoutClick(MouseEvent event) {
		HomeBtn.setStyle("-fx-background-color: #deeaee;");
		productsBtn.setStyle("-fx-background-color: #deeaee;");
		customerBtn.setStyle("-fx-background-color: #deeaee;");
		expensesBtn.setStyle("-fx-background-color: #deeaee;");
		salesBtn.setStyle("-fx-background-color: #deeaee;");
		reportsBtn.setStyle("-fx-background-color: #deeaee;");
		purchaseBtn.setStyle("-fx-background-color: #deeaee;");
		LogoutBtn.setStyle("-fx-background-color: green;-fx-background-radius: 20;");
		System.exit(0);
	}

	@FXML
	void salesClick(MouseEvent event) throws IOException {
		view = FXMLLoader.load(getClass().getResource("saleMainScene.fxml"));
		borderPane4Scene.setCenter(view);
		HomeBtn.setStyle("-fx-background-color: #deeaee;");
		productsBtn.setStyle("-fx-background-color: #deeaee;");
		customerBtn.setStyle("-fx-background-color: #deeaee;");
		expensesBtn.setStyle("-fx-background-color: #deeaee;");
		salesBtn.setStyle("-fx-background-color: green;-fx-background-radius: 20;");
		reportsBtn.setStyle("-fx-background-color: #deeaee;");
		purchaseBtn.setStyle("-fx-background-color: #deeaee;");
		LogoutBtn.setStyle("-fx-background-color: #deeaee;");
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {	
		try {
			HomeClick(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	

}
