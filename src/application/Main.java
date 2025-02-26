package application;
	
import java.io.IOException;
import java.sql.Date;
import javafx.application.Application;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.fxml.FXMLLoader;


public class Main extends Application {
	private static Stage primaryStage;
	public static boolean close = false;
	public static String whiteBoard = "";
	
	@Override
	public void start(Stage primaryStage) {
		try {
			DBConnect.updateCustomerStatusByTime();		
			DBConnect.updateAllCustomersStatus();
			Parent root = FXMLLoader.load(getClass().getResource("MainPageForGym.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setMaximized(true);
			primaryStage.show();		
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void addToDoScene() throws IOException {
		FXMLLoader loader = new FXMLLoader();  
		loader.setLocation(Main.class.getResource("addNewToDo.fxml"));
		AnchorPane addToDo = loader.load();
		
		Stage addToDoStage = new Stage();
		addToDoStage.setTitle("Add New ToDo");
		addToDoStage.initModality(Modality.APPLICATION_MODAL);
		addToDoStage.initOwner(primaryStage);
		Scene scene = new Scene(addToDo);
		addToDoStage.setResizable(false);
		addToDoStage.setScene(scene);
		addToDoStage.showAndWait();
	}
	
	public static void editToDoScene(ToDo ToDo) throws IOException {
		FXMLLoader loader = new FXMLLoader();  
		loader.setLocation(Main.class.getResource("editToDo.fxml"));
		AnchorPane editToDo = loader.load();
		
		editToDoController displayToDo = loader.getController();
		displayToDo.getData(ToDo);
		
		Stage editToDoStage = new Stage();
		editToDoStage.setTitle("Edit ToDo");
		editToDoStage.initModality(Modality.APPLICATION_MODAL);
		editToDoStage.initOwner(primaryStage);
		Scene scene = new Scene(editToDo);
		editToDoStage.setResizable(false);
		editToDoStage.setScene(scene);
		editToDoStage.showAndWait();
	}
	
	public static void addCustomerScene() throws IOException {
		FXMLLoader loader = new FXMLLoader();  
		loader.setLocation(Main.class.getResource("addNewCustomerScene.fxml"));
		AnchorPane addCustomer = loader.load();
		
		Stage addCustomerStage = new Stage();
		addCustomerStage.setTitle("Add New Customer");
		addCustomerStage.initModality(Modality.APPLICATION_MODAL);
		addCustomerStage.initOwner(primaryStage);
		Scene scene = new Scene(addCustomer);
		addCustomerStage.setResizable(false);
		addCustomerStage.setScene(scene);
		addCustomerStage.showAndWait();
	}
	
	public static void retrieveCustomerScene() throws IOException {
		FXMLLoader loader = new FXMLLoader();  
		loader.setLocation(Main.class.getResource("retrieveCustomerScene.fxml"));
		AnchorPane retrieveCustomer = loader.load();
		
		Stage retrieveCustomerStage = new Stage();
		retrieveCustomerStage.setTitle("retrieve Customer");
		retrieveCustomerStage.initModality(Modality.APPLICATION_MODAL);
		retrieveCustomerStage.initOwner(primaryStage);
		Scene scene = new Scene(retrieveCustomer);
		retrieveCustomerStage.setResizable(false);
		retrieveCustomerStage.setScene(scene);
		retrieveCustomerStage.showAndWait();
	}
	
	public static void manageSubscriptionScene(Customer customer) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(Main.class.getResource("MangeSubscription.fxml"));
		AnchorPane manageSubscription = loader.load();
		
		MangeSubscriptionController displayCustomer = loader.getController();
		displayCustomer.getCustomer(customer);
		
		Stage manageSubscriptionStage = new Stage();
		manageSubscriptionStage.setTitle("ManageSubscription");
		manageSubscriptionStage.initModality(Modality.APPLICATION_MODAL);
		manageSubscriptionStage.initOwner(primaryStage);
		Scene scene = new Scene(manageSubscription);
		manageSubscriptionStage.setResizable(false);
		manageSubscriptionStage.setScene(scene);
		manageSubscriptionStage.showAndWait();
	}
		
	public static void editCustomerScene(Customer customer) throws IOException {
		FXMLLoader loader = new FXMLLoader();  
		loader.setLocation(Main.class.getResource("editCustomer.fxml"));
		AnchorPane editCustomer = loader.load();
		
		editCustomerController displayCustomer = loader.getController();
		displayCustomer.getCustomer(customer);
		
		Stage editCustomerStage = new Stage();
		editCustomerStage.setTitle("Edit Customer");
		editCustomerStage.initModality(Modality.APPLICATION_MODAL);
		editCustomerStage.initOwner(primaryStage);
		Scene scene = new Scene(editCustomer);
		editCustomerStage.setResizable(false);
		editCustomerStage.setScene(scene);
		editCustomerStage.showAndWait();
	}
	
	public static void confirmationFreeze(String name,String IDNum,String daysLift,Subscription subs) throws IOException {		
		FXMLLoader loader = new FXMLLoader();  
		loader.setLocation(Main.class.getResource("confirmationPause.fxml"));
		BorderPane confirmationFreeze = loader.load();
		
		confirmationPauseController displayInfo = loader.getController();
		displayInfo.getData(name,IDNum,daysLift,subs);
		Stage confirmationFreezeStage = new Stage();
		confirmationFreezeStage.setTitle("Edit Customer");
		confirmationFreezeStage.initModality(Modality.APPLICATION_MODAL);
		confirmationFreezeStage.initOwner(primaryStage);
		Scene scene = new Scene(confirmationFreeze);
		confirmationFreezeStage.setResizable(false);
		confirmationFreezeStage.setScene(scene);
		confirmationFreezeStage.showAndWait();
	}
	public static void confirmationActivate(String name,String IDNum,String daysLift,Subscription subs) throws IOException {		
		FXMLLoader loader = new FXMLLoader();  
		loader.setLocation(Main.class.getResource("confirmationActivate.fxml"));
		BorderPane confirmationActivate = loader.load();
		
		confirmationActivateController displayInfo = loader.getController();
		displayInfo.getData(name,IDNum,daysLift,subs);
		Stage confirmationActivateStage = new Stage();
		confirmationActivateStage.setTitle("Edit Customer");
		confirmationActivateStage.initModality(Modality.APPLICATION_MODAL);
		confirmationActivateStage.initOwner(primaryStage);
		Scene scene = new Scene(confirmationActivate);
		confirmationActivateStage.setResizable(false);
		confirmationActivateStage.setScene(scene);
		confirmationActivateStage.showAndWait();
	}
	public static void confirmationExtend(String name,String IDNum,String daysLift,Subscription subs,Date oldDate) throws IOException {		
		FXMLLoader loader = new FXMLLoader();  
		loader.setLocation(Main.class.getResource("confirmationExtend.fxml"));
		BorderPane confirmationExtend = loader.load();
		
		confirmationExtendController displayInfo = loader.getController();
		displayInfo.getData(name,IDNum,daysLift,subs,oldDate);
		Stage confirmationExtendStage = new Stage();
		confirmationExtendStage.setTitle("Edit Customer");
		confirmationExtendStage.initModality(Modality.APPLICATION_MODAL);
		confirmationExtendStage.initOwner(primaryStage);
		Scene scene = new Scene(confirmationExtend);
		confirmationExtendStage.setResizable(false);
		confirmationExtendStage.setScene(scene);
		confirmationExtendStage.showAndWait();
	}
	public static void confirmationNew(String name,String IDNum,String daysLift,Subscription subs) throws IOException {		
		FXMLLoader loader = new FXMLLoader();  
		loader.setLocation(Main.class.getResource("confirmationNew.fxml"));
		BorderPane confirmationNew = loader.load();
		
		confirmationNewController displayInfo = loader.getController();
		displayInfo.getData(name,IDNum,daysLift,subs);
		Stage confirmationNewStage = new Stage();
		confirmationNewStage.setTitle("Edit Customer");
		confirmationNewStage.initModality(Modality.APPLICATION_MODAL);
		confirmationNewStage.initOwner(primaryStage);
		Scene scene = new Scene(confirmationNew);
		confirmationNewStage.setResizable(false);
		confirmationNewStage.setScene(scene);
		confirmationNewStage.showAndWait();
	}
	
	public static void addProductScene() throws IOException {
		FXMLLoader loader = new FXMLLoader();  
		loader.setLocation(Main.class.getResource("addNewProduct.fxml"));
		AnchorPane addProduct = loader.load();
		
		Stage addProductStage = new Stage();
		addProductStage.setTitle("Add New Product");
		addProductStage.initModality(Modality.APPLICATION_MODAL);
		addProductStage.initOwner(primaryStage);
		Scene scene = new Scene(addProduct);
		addProductStage.setResizable(false);
		addProductStage.setScene(scene);
		addProductStage.showAndWait();
	}
	
	
	public static void editProductScene(Product Product) throws IOException {
		FXMLLoader loader = new FXMLLoader();  
		loader.setLocation(Main.class.getResource("editProduct.fxml"));
		AnchorPane editProduct = loader.load();
		
		editProductController displayProduct = loader.getController();
		displayProduct.getProduct(Product);
		
		Stage editProductStage = new Stage();
		editProductStage.setTitle("Edit Product");
		editProductStage.initModality(Modality.APPLICATION_MODAL);
		editProductStage.initOwner(primaryStage);
		Scene scene = new Scene(editProduct);
		editProductStage.setResizable(false);
		editProductStage.setScene(scene);
		editProductStage.showAndWait();
	}
	
	public static void retrieveProductScene() throws IOException {
		FXMLLoader loader = new FXMLLoader();  
		loader.setLocation(Main.class.getResource("retrieveProductScene.fxml"));
		AnchorPane retrieveProduct = loader.load();
		
		Stage retrieveProductStage = new Stage();
		retrieveProductStage.setTitle("retrieve Product");
		retrieveProductStage.initModality(Modality.APPLICATION_MODAL);
		retrieveProductStage.initOwner(primaryStage);
		Scene scene = new Scene(retrieveProduct);
		retrieveProductStage.setResizable(false);
		retrieveProductStage.setScene(scene);
		retrieveProductStage.showAndWait();
	}
	
	public static void addSaleScene() throws IOException {
		FXMLLoader loader = new FXMLLoader();  
		loader.setLocation(Main.class.getResource("addNewSaleScene.fxml"));
		AnchorPane addSale = loader.load();
		
		Stage addSaleStage = new Stage();
		addSaleStage.setTitle("Add New Sale");
		addSaleStage.initModality(Modality.APPLICATION_MODAL);
		addSaleStage.initOwner(primaryStage);
		Scene scene = new Scene(addSale);
		addSaleStage.setResizable(false);
		addSaleStage.setScene(scene);
		addSaleStage.showAndWait();
	}
	
	public static void viewSaleDetailScene(Sale sale) throws IOException {
		FXMLLoader loader = new FXMLLoader();  
		loader.setLocation(Main.class.getResource("viewSaleDetailsScene.fxml"));
		AnchorPane viewSaleDetails = loader.load();
		
		viewSaleDetailsSceneController displayProduct = loader.getController();
		displayProduct.getData(sale);
		
		Stage viewSaleDetailsStage = new Stage();
		viewSaleDetailsStage.setTitle("Edit Product");
		viewSaleDetailsStage.initModality(Modality.APPLICATION_MODAL);
		viewSaleDetailsStage.initOwner(primaryStage);
		Scene scene = new Scene(viewSaleDetails);
		viewSaleDetailsStage.setResizable(false);
		viewSaleDetailsStage.setScene(scene);
		viewSaleDetailsStage.show();
	} 
	
	public static void addPurchaseScene() throws IOException {
		FXMLLoader loader = new FXMLLoader();  
		loader.setLocation(Main.class.getResource("addNewPurchaseScene.fxml"));
		AnchorPane addPurchase = loader.load();
		
		Stage addPurchaseStage = new Stage();
		addPurchaseStage.setTitle("Add New Purchase");
		addPurchaseStage.initModality(Modality.APPLICATION_MODAL);
		addPurchaseStage.initOwner(primaryStage);
		Scene scene = new Scene(addPurchase);
		addPurchaseStage.setResizable(false);
		addPurchaseStage.setScene(scene);
		addPurchaseStage.showAndWait();
	}
	
	public static void addPurchaseScene(Product product) throws IOException {
		FXMLLoader loader = new FXMLLoader();  
		loader.setLocation(Main.class.getResource("addNewPurchaseScene.fxml"));
		AnchorPane addPurchase = loader.load();
		
		addNewPurchaseSceneController displayProduct = loader.getController();
		displayProduct.getData(product);
		
		Stage addPurchaseStage = new Stage();
		addPurchaseStage.setTitle("Add New Purchase");
		addPurchaseStage.initModality(Modality.APPLICATION_MODAL);
		addPurchaseStage.initOwner(primaryStage);
		Scene scene = new Scene(addPurchase);
		addPurchaseStage.setResizable(false);
		addPurchaseStage.setScene(scene);
		addPurchaseStage.showAndWait();
	}
	
	public static void viewPurchaseDetailScene(Purchase Purchase) throws IOException {
		FXMLLoader loader = new FXMLLoader();  
		loader.setLocation(Main.class.getResource("viewPurchaseDetails.fxml"));
		AnchorPane viewPurchaseDetails = loader.load();
		
		viewPurchaseDetailsController displayProduct = loader.getController();
		displayProduct.getData(Purchase);
		
		Stage viewPurchaseDetailsStage = new Stage();
		viewPurchaseDetailsStage.setTitle("Edit Product");
		viewPurchaseDetailsStage.initModality(Modality.APPLICATION_MODAL);
		viewPurchaseDetailsStage.initOwner(primaryStage);
		Scene scene = new Scene(viewPurchaseDetails);
		viewPurchaseDetailsStage.setResizable(false);
		viewPurchaseDetailsStage.setScene(scene);
		viewPurchaseDetailsStage.show();
	} 
	
	public static void addExpensesScene() throws IOException {
		FXMLLoader loader = new FXMLLoader();  
		loader.setLocation(Main.class.getResource("addNewExpenses.fxml"));
		AnchorPane addExpenses = loader.load();
		
		Stage addExpensesStage = new Stage();
		addExpensesStage.setTitle("Add New Expenses");
		addExpensesStage.initModality(Modality.APPLICATION_MODAL);
		addExpensesStage.initOwner(primaryStage);
		Scene scene = new Scene(addExpenses);
		addExpensesStage.setResizable(false);
		addExpensesStage.setScene(scene);
		addExpensesStage.showAndWait();
	}
	
	public static void editExpensesScene(Expenses Expenses) throws IOException {
		FXMLLoader loader = new FXMLLoader();  
		loader.setLocation(Main.class.getResource("editExpenses.fxml"));
		AnchorPane editExpenses = loader.load();
		
		editExpensesController displayExpenses = loader.getController();
		displayExpenses.getData(Expenses);
		
		Stage editExpensesStage = new Stage();
		editExpensesStage.setTitle("Edit Expenses");
		editExpensesStage.initModality(Modality.APPLICATION_MODAL);
		editExpensesStage.initOwner(primaryStage);
		Scene scene = new Scene(editExpenses);
		editExpensesStage.setResizable(false);
		editExpensesStage.setScene(scene);
		editExpensesStage.showAndWait();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

