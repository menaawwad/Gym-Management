package application;

import java.sql.*;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;

public class DBConnect {

	private static String url = "jdbc:mysql://localhost:3306/gym";
	private static String USERNAME = "root";
	private static String PASSWORD = "";
	private static Connection connection;

	public static Connection getConnect() {
		try {
			connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
		} catch (Exception e) {
			System.out.println(e);
		}
		return connection;
	}

	public static double returnSumOfTable(String Quary) {
		double x = 0;
		try {
			connection = DBConnect.getConnect();
			PreparedStatement preparedStatement = connection.prepareStatement(Quary);
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				x = resultSet.getDouble(1);
			}

			connection.close();
			preparedStatement.close();
			resultSet.close();
		} catch (Exception e) {

		}
		return x;
	}

	public static void AddNewToDo(ToDo todo) {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String insert = "";
			if (todo.getDate() != null) {
				insert = "INSERT INTO `tbltodo` (`ID`, `toDo`, `date`,`status`) VALUES (NULL, '" + todo.getTodo()
						+ "', '" + todo.getDate() + "','" + todo.getStatus() + "')";
			} else {
				insert = "INSERT INTO `tbltodo` (`ID`, `toDo`, `date`,`status`) VALUES (NULL, '" + todo.getTodo()
						+ "', NULL,'" + todo.getStatus() + "')";
			}
			st.executeUpdate(insert);
			st.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void updateToDo(ToDo todo) {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();

			String update = "";
			if (todo.getDate() != null) {
				update = "UPDATE `tbltodo` SET `toDo` = '" + todo.getTodo() + "',`date` = '" + todo.getDate()
						+ "' , `status` = '" + todo.getStatus() + "' WHERE `tbltodo`.`ID` = " + todo.getID() + ";";
			} else {
				update = "UPDATE `tbltodo` SET `toDo` = '" + todo.getTodo() + "',`date` = NULL , `status` = '"
						+ todo.getStatus() + "' WHERE `tbltodo`.`ID` = " + todo.getID() + ";";
			}
			st.executeUpdate(update);
			st.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void updateToDoToFaild() {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String update = "UPDATE `tbltodo` SET `status`='Faild' WHERE `status`='In progress' AND `date` < '"
					+ LocalDate.now().toString() + "';";

			st.executeUpdate(update);
			st.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}

	}

	public static void deleteToDo(ToDo todo) {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String Delete = "DELETE FROM `tbltodo` WHERE `ID`= " + todo.getID() + ";";

			st.executeUpdate(Delete);
			st.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void AddNewCustomer(Customer customer) {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String insert = "INSERT INTO gym.tblcustomers(`name`, `customerId`, `adress`, `gender`, `birthday`, `phone number`, `email`, `status`,`removed`) VALUES ('"
					+ customer.getName() + "','" + customer.getCustomerId() + "','" + customer.getAdress() + "','"
					+ customer.getGender() + "','" + customer.getBirthday() + "','" + customer.getPhoneNumber() + "','"
					+ customer.getEmail() + "','',0);";
			st.executeUpdate(insert);
			st.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void deldeteCustomer(String customerId) {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String update = "UPDATE gym.tblcustomers SET `removed`= 1 WHERE customerId ='" + customerId + "'";

			st.executeUpdate(update);
			st.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void retrieveCustomer(String customerId) {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String update = "UPDATE gym.tblcustomers SET `removed`= 0 WHERE customerId ='" + customerId + "'";

			st.executeUpdate(update);
			st.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static boolean ifCustomerExist(String customerId) {
		boolean flag = true;
		try {
			Connection con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM `tblcustomers` WHERE customerId = '" + customerId + "'; ");
			while (rs.next()) {
				flag = false;
			}
			stmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return flag;
	}
	public static boolean ifProductExist(String ProductBarcode) {
		boolean flag = true;
		try {
			Connection con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM `tblproducts` WHERE barcode = '" + ProductBarcode + "'; ");
			while (rs.next()) {
				flag = false;
			}
			stmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return flag;
	}

	public static void updateCustomer(Customer customer) {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String update = "UPDATE gym.tblcustomers SET `name`='" + customer.getName() + "',`adress`='"
					+ customer.getAdress() + "',`birthday`='" + customer.getBirthday() + "',`phone number`='"
					+ customer.getPhoneNumber() + "',`email`='" + customer.getEmail() + "' WHERE customerId ='"
					+ customer.getCustomerId() + "'";

			st.executeUpdate(update);
			st.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void updateCustomerStatusByTime() {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String update = "UPDATE gym.tblsubscription SET `status`='Expired' WHERE status = 'Active' AND `endingdate` < '"
					+ LocalDate.now() + "'";
			st.executeUpdate(update);
//			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String update = "UPDATE gym.tblsubscription SET `status`='Active' WHERE status = 'Inactive' AND `startingdate` < '"
					+ LocalDate.now() + "'  OR `startingdate` = '" + LocalDate.now() + "'";
			st.executeUpdate(update);
//			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String update = "UPDATE gym.tblsubscription SET `status`='Inactive' WHERE status = 'Active' AND `startingdate` > '"
					+ LocalDate.now() + "'";
			st.executeUpdate(update);
//			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void updateCustomersStatus(String status, int ID) {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String update = "UPDATE gym.tblcustomers SET `status`='" + status + "' WHERE ID = " + ID + "";
			st.executeUpdate(update);
//			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

//	public static void getSubscriptionByCustomerID(int ID) {
//		try {
//			Statement st = ConnectToDatabase();
//			ResultSet rs = st.executeQuery(
//					"SELECT * FROM `tblsubscription` WHERE customernumber = " + ID + " ORDER BY ID DESC LIMIT 1;");
//			while (rs.next()) {
//				updateCustomersStatus(rs.getString("status"), ID);
//			}st.close();con.close();
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//
//	}
	/////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////
	public static ObservableList<Customer> allActiveCustomers() {
		ObservableList<Customer> observableList = FXCollections.observableArrayList();
		try {
			String query = "SELECT * FROM `tblcustomers` WHERE `status` = 'Active' AND  removed = 0";
			Connection connection = getConnect();
			PreparedStatement st = connection.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				observableList.add(new Customer(Integer.parseInt(rs.getString("ID")), rs.getString("name"),
						rs.getString("customerId"), rs.getDate("birthday").toLocalDate(), rs.getString("adress"),
						rs.getString("phone number"), rs.getString("email"), rs.getString("gender"),
						rs.getString("status")));
			}
			connection.close();
			st.close();
			rs.close();
			connection.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return observableList;
	}

	public static LocalDate getEndingDateByCustomerID(int ID) {
		LocalDate date = LocalDate.now();
		try {
			String query = "SELECT * FROM `tblsubscription` WHERE customernumber = " + ID
					+ " ORDER BY ID DESC LIMIT 1;";
			Connection connection = getConnect();
			PreparedStatement st = connection.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				date = rs.getDate("endingdate").toLocalDate();
			}
			st.close();
			rs.close();
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return date;
	}

	public static ObservableList<Customer> allActiveCustomers14DaysToExpired() {
		ObservableList<Customer> observableList = FXCollections.observableArrayList();
		ObservableList<Customer> NewobservableList = FXCollections.observableArrayList();
		LocalDate date;
		observableList = allActiveCustomers();
		int i = 0;
		while (observableList.size() > i) {
			date = getEndingDateByCustomerID(observableList.get(i).getID());
			if (date.isBefore(LocalDate.now().plusDays(14))) {
				NewobservableList.add(observableList.get(i));
			}
			i++;
		}
		return NewobservableList;
	}

	/////////////////////////////////////////////////////////////////////////////
	/////////////////////////////////////////////////////////////////////////////

	public static void updateAllCustomersStatus() {
		try {
			String query = "SELECT * FROM `tblsubscription` WHERE 1;";
			Connection connection = getConnect();
			PreparedStatement st = connection.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				updateCustomersStatus(rs.getNString("status"), rs.getInt("customernumber"));
			}
			st.close();
			rs.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static SubscriptionType getSubscriptionType(int ID) {
		try {
			String query = "SELECT * FROM gym.tblsubscriptiontype WHERE `ID` = " + ID + ";";
			Connection connection = getConnect();
			PreparedStatement st = connection.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				SubscriptionType subscriptionType = new SubscriptionType(rs.getInt("ID"), rs.getString("type"),
						rs.getDouble("price"));
				return subscriptionType;
			}
			st.close();
			rs.close();

		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public static Customer selectCustomer(int ID) {
		try {
			String query = "SELECT * FROM gym.tblcustomers WHERE `Id` = " + ID + ";";
			Connection connection = getConnect();
			PreparedStatement st = connection.prepareStatement(query);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				Customer customer = new Customer(rs.getInt("ID"), rs.getString("name"), rs.getString("customerId"),
						rs.getDate("birthday").toLocalDate(), rs.getString("adress"), rs.getString("phone number"),
						rs.getString("email"), rs.getString("gender"), rs.getString("status"));
				return customer;
			}
			st.close();
			rs.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return null;
	}

	public static void AddSubscription(Subscription subscription) {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String insert = "INSERT INTO `tblsubscription` (`ID`, `customernumber`, `registrationdate`, `subscriptionType`, `price`, `startingdate`, `endingdate`, `lastUpdate`, `pauseDate`, `status`) VALUES (NULL,'"
					+ subscription.getCustomerNumber() + "','" + subscription.getRegistrationdate() + "','"
					+ subscription.getSubscriptionType() + "','" + subscription.getPrice() + "','"
					+ subscription.getStartinDate() + "','" + subscription.getEndDate() + "','"
					+ subscription.getLastUpdate() + "',NULL,'" + subscription.getStatus() + "');";
			st.executeUpdate(insert);
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
//		updateCustomerStatus(subscription);
	}

	public static void FreezeSubscription(Subscription subscription) {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String insert = "INSERT INTO `tblsubscription` (`ID`, `customernumber`, `registrationdate`, `subscriptionType`, `price`, `startingdate`, `endingdate`, `lastUpdate`, `pauseDate`, `status`) VALUES (NULL,'"
					+ subscription.getCustomerNumber() + "','" + subscription.getRegistrationdate() + "','"
					+ subscription.getSubscriptionType() + "','" + subscription.getPrice() + "','"
					+ subscription.getStartinDate() + "','" + subscription.getEndDate() + "','"
					+ subscription.getLastUpdate() + "','" + subscription.getPauseDate() + "','"
					+ subscription.getStatus() + "');";
			st.executeUpdate(insert);
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
//		updateCustomerStatus(subscription);
	}

//	public static void updateCustomerStatus(Subscription subscription) {
//		java.sql.Connection con;
//		try {
//			Class.forName("com.mysql.cj.jdbc.Driver");
//			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
//			Statement st = con.createStatement();
//			String update = "UPDATE gym.tblcustomers SET `status`='" + subscription.getStatus() + "' WHERE `ID` = "
//					+ subscription.getCustomerNumber() + ";";
//			st.executeUpdate(update);con.close();
//		} catch (Exception e) {
//			System.out.println(e);
//		}
//	}

	///////////////////////////////////////////////////

	public static void AddNewProduct(Product product) {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String insert = "INSERT INTO `tblproducts` (`ID`, `product`, `regdate`, `lastupdate`, `barcode`, `costPrice`, `sellPrice`, `minPrice`, `weight`, `stock`, `minOrder`, `active`) VALUES (NULL, '"
					+ product.getName() + "', '" + LocalDate.now() + "', '" + LocalDate.now() + "', '"
					+ product.getBarcode() + "', '" + product.getCostPrice() + "', '" + product.getSellPrice() + "', '"
					+ product.getMinPrice() + "', '" + product.getWeight() + "', '0', '" + product.getMinOrder()
					+ "', '1');";
			st.executeUpdate(insert);
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void deldeteRetrieveProduct(int ID, int status) {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String update = "UPDATE gym.tblproducts SET `active`='" + status + "' WHERE `ID` ='" + ID + "'";

			st.executeUpdate(update);
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void deldeteProductSaleTempTable(SaleDetails saleDetails) {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String update = "DELETE FROM `tbltempsale` WHERE `tbltempsale`.`ID` = " + saleDetails.getID() + ";";

			st.executeUpdate(update);
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void deldeteProductTempTable(PurchaseDetails PurchaseDetails) {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String update = "DELETE FROM `tbltempPurchase` WHERE `tbltempPurchase`.`ID` = " + PurchaseDetails.getID()
					+ ";";

			st.executeUpdate(update);
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void deldeteProductSaleTempTable() {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String update = "DELETE FROM `tbltempsale`;";

			st.executeUpdate(update);
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void FormatProductSaleTempTable() {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String update = "ALTER TABLE `tbltempsale` AUTO_INCREMENT = 0;";

			st.executeUpdate(update);
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void updateProduct(Product product) {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String update = "UPDATE `tblproducts` SET `product` = '" + product.getName() + "', `lastupdate` = '"
					+ product.getLastUpdate() + "', `barcode` = '" + product.getBarcode() + "', `costPrice` = '"
					+ product.getCostPrice() + "', `sellPrice` = '" + product.getSellPrice() + "', `minPrice` = '"
					+ product.getMinPrice() + "', `weight` = '" + product.getWeight() + "', `stock` = '"
					+ product.getStock() + "', `minOrder` = '" + product.getMinOrder() + "' WHERE `tblproducts`.`ID` = "
					+ product.getID() + ";";
			st.executeUpdate(update);
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

//	public static ObservableList<Customer> fillComboBoxCustomer() throws SQLException {
//		Connection conn = DriverManager.getConnection(url, USERNAME, PASSWORD);
//		ObservableList<Customer> observableList = FXCollections.observableArrayList();
//		ResultSet resultSet = conn.createStatement().executeQuery(
//				"SELECT `tblcustomers`.`ID`, `tblcustomers`.`name`, `tblcustomers`.`customerId`FROM `tblcustomers`;");
//		while (resultSet.next()) {
//			observableList.add(new Customer(Integer.parseInt(resultSet.getString("ID")), resultSet.getString("name"),
//					resultSet.getString("customerId")));
//		}
//		resultSet.close();
//		conn.close();
//		return observableList;
//	}

	public static ObservableList<Customer> fillComboBoxCustomer(String str) throws SQLException {
		Connection conn = DriverManager.getConnection(url, USERNAME, PASSWORD);
		ObservableList<Customer> observableList = FXCollections.observableArrayList();
		ResultSet resultSet = conn.createStatement().executeQuery(
				"SELECT * FROM `tblcustomers` WHERE `name` LIKE '%" + str + "%' OR customerId LIKE '%" + str + "%';");
		while (resultSet.next()) {
			observableList.add(new Customer(Integer.parseInt(resultSet.getString("ID")), resultSet.getString("name"),
					resultSet.getString("customerId")));
		}
		resultSet.close();
		conn.close();
		return observableList;
	}

//	public static ObservableList<Product> fillComboBoxProductForSale() throws SQLException {
//		Connection conn = DriverManager.getConnection(url, USERNAME, PASSWORD);
//		ObservableList<Product> observableList = FXCollections.observableArrayList();
//		ResultSet resultSet = conn.createStatement()
//				.executeQuery("SELECT * FROM `tblproducts` WHERE `stock` > 0 AND `active` = 1;");
//		while (resultSet.next()) {
//			observableList.add(new Product(resultSet.getInt(1), resultSet.getString(2),
//					resultSet.getDate(3).toLocalDate(), resultSet.getDate(4).toLocalDate(), resultSet.getString(5),
//					resultSet.getDouble(6), resultSet.getDouble(7), resultSet.getDouble(8), resultSet.getDouble(9),
//					resultSet.getInt(10), resultSet.getInt(11)));
//		}
//		resultSet.close();
//		conn.close();
//		return observableList;
//	}

	public static ObservableList<Product> fillComboBoxProduct(String str) throws SQLException {
		Connection conn = DriverManager.getConnection(url, USERNAME, PASSWORD);
		ObservableList<Product> observableList = FXCollections.observableArrayList();
		ResultSet resultSet = conn.createStatement().executeQuery(str);
		while (resultSet.next()) {
			observableList.add(new Product(resultSet.getInt(1), resultSet.getString(2),
					resultSet.getDate(3).toLocalDate(), resultSet.getDate(4).toLocalDate(), resultSet.getString(5),
					resultSet.getDouble(6), resultSet.getDouble(7), resultSet.getDouble(8), resultSet.getDouble(9),
					resultSet.getInt(10), resultSet.getInt(11)));
		}
		resultSet.close();
		conn.close();
		return observableList;
	}

	public static void AddNewTempSaleProductTable(SaleDetails saleDetails) {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String insert = "INSERT INTO `tbltempsale` (`ID`, `productId`, `unitPrice`, `quantity`, `rowPrice`, `rowProfit`) VALUES (NULL, '"
					+ saleDetails.getProductID() + "', '" + saleDetails.getUnitPrice() + "', '"
					+ saleDetails.getQuantity() + "', '" + saleDetails.getRowPrice() + "', '"
					+ saleDetails.getRowProfit() + "');";
			st.executeUpdate(insert);
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static boolean ifProductExistInSaleTemp(int ID) {
		boolean flag = true;
		try {
			Connection con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM `tbltempsale` WHERE `productId`= " + ID + ";");
			while (rs.next()) {
				flag = false;
			}
			stmt.close();
			rs.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return flag;
	}

	public static void AddNewSale(Sale sale) {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String insert = "INSERT INTO `tblsale` (`ID`, `customerID`, `totalPrice`, `SaleProfit`, `quantity`, `saleDate`, `note`) VALUES (NULL, '"
					+ sale.getCustomer() + "', '" + sale.getTotalPrice() + "', '" + sale.getSaleProfit() + "', '"
					+ sale.getQuantity() + "', '" + sale.getDate().toString() + "', NULL);";
			st.executeUpdate(insert);
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static int lastSale() {
		try {
			Connection con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM `tblsale` ORDER BY ID DESC LIMIT 1; ");
			while (rs.next()) {
				return rs.getInt("ID");
			}
			rs.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		return 0;
	}

	public static void updateProductRemoveFromStock(int amount, int ID) {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String update = "UPDATE `tblproducts` SET `stock` = `stock`- '" + amount + "' WHERE `tblproducts`.`ID` = "
					+ ID + ";";
			st.executeUpdate(update);
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void updateProductAddFromStock(int amount, int ID) {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String update = "UPDATE `tblproducts` SET `stock` = `stock`+ '" + amount + "' WHERE `tblproducts`.`ID` = "
					+ ID + ";";
			st.executeUpdate(update);
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void AddNewSaleDetails(int saleID, ObservableList<SaleDetails> observableList) {
		int i = 0;
		while (observableList.size() > i) {
			java.sql.Connection con;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection(url, USERNAME, PASSWORD);
				Statement st = con.createStatement();
				String insert = "INSERT INTO `tblsaledetails` (`ID`, `saleID`, `productId`, `unitPrice`, `quantity`, `rowPrice`, `rowProfit`) VALUES (NULL, '"
						+ saleID + "', '" + observableList.get(i).getProductID() + "', '"
						+ observableList.get(i).getUnitPrice() + "', '" + observableList.get(i).getQuantity() + "', '"
						+ observableList.get(i).getRowPrice() + "', '" + observableList.get(i).getRowProfit() + "');";
				st.executeUpdate(insert);
				st.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			updateProductRemoveFromStock(observableList.get(i).getQuantity(), observableList.get(i).getProductID());
			i++;
		}
	}

	public static void report(int ID) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url, USERNAME, PASSWORD);
			String sql = "SELECT `tblsaledetails`.*, `tblsale`.*, tblproducts.product, `tblcustomers`.* , `tblcustomers`.`customerId` AS customerIDCard FROM tblsaledetails LEFT JOIN tblsale ON tblsaledetails.`saleID` = tblsale.`ID` LEFT JOIN tblproducts ON tblsaledetails.`productId` = tblproducts.`ID` LEFT JOIN tblcustomers ON tblsale.`customerID` = tblcustomers.`ID` WHERE tblsale.ID = "
					+ ID + ";";

			JasperDesign jdesing = JRXmlLoader
					.load("C:\\tachneon\\gym project fx\\Gymmanagement\\src\\application\\report.jrxml");
			JRDesignQuery updateQuery = new JRDesignQuery();
			updateQuery.setText(sql);

			jdesing.setQuery(updateQuery);

			JasperReport jreprot = JasperCompileManager.compileReport(jdesing);
			JasperPrint jasperPrint = JasperFillManager.fillReport(jreprot, null, connection);

			JasperViewer.viewReport(jasperPrint, false);
			connection.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static boolean ifProductExistInPurchaseTemp(int ID) {
		boolean flag = true;
		try {
			Connection con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM `tbltempPurchase` WHERE `productId`= " + ID + ";");
			while (rs.next()) {
				flag = false;
			}
			stmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		return flag;
	}

	public static void AddNewTempPurchaseProductTable(PurchaseDetails PurchaseDetails) {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String insert = "INSERT INTO `tbltempPurchase` (`ID`, `productId`, `unitPrice`, `rowQuantity`, `rowPrice`) VALUES (NULL, '"
					+ PurchaseDetails.getProductID() + "', '" + PurchaseDetails.getUnitPrice() + "', '"
					+ PurchaseDetails.getQuantity() + "', '" + PurchaseDetails.getRowPrice() + "');";
			st.executeUpdate(insert);
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void AddNewPurchase(Purchase Purchase) {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String insert = "INSERT INTO `tblpurchase` (`ID`, `purchaseDate`, `quantity`, `price`) VALUES (NULL, '"
					+ Purchase.getDate() + "', '" + Purchase.getQuantity() + "', '" + Purchase.getPrice() + "');";
			st.executeUpdate(insert);
			st.close();
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static int lastPurchase() {
		try {
			Connection con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM `tblPurchase` ORDER BY ID DESC LIMIT 1; ");
			while (rs.next()) {
				return rs.getInt("ID");
			}
			stmt.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}

		return 0;
	}

	public static void AddNewPurchaseDetails(int PurchaseID, ObservableList<PurchaseDetails> observableList) {
		int i = 0;
		while (observableList.size() > i) {
			java.sql.Connection con;
			try {
				Class.forName("com.mysql.cj.jdbc.Driver");
				con = DriverManager.getConnection(url, USERNAME, PASSWORD);
				Statement st = con.createStatement();
				String insert = "INSERT INTO `tblPurchasedetails` (`ID`, `PurchaseID`, `productId`, `unitPrice`, `rowQuantity`, `rowPrice`) VALUES (NULL, '"
						+ PurchaseID + "', '" + observableList.get(i).getProductID() + "', '"
						+ observableList.get(i).getUnitPrice() + "', '" + observableList.get(i).getQuantity() + "', '"
						+ observableList.get(i).getRowPrice() + "');";
				st.executeUpdate(insert);
				st.close();
				con.close();
			} catch (Exception e) {
				System.out.println(e);
			}
			updateProductAddFromStock(observableList.get(i).getQuantity(), observableList.get(i).getProductID());
			i++;
		}
	}

	public static void deldeteProductPurchaseTempTable() {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String update = "DELETE FROM `tbltempPurchase`;";

			st.executeUpdate(update);
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void FormatProductPurchaseTempTable() {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String update = "ALTER TABLE `tbltempPurchase` AUTO_INCREMENT = 0;";

			st.executeUpdate(update);
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void AddNewExpenses(Expenses expenses) {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String insert = "INSERT INTO `tblexpenses` (`ID`, `Name`, `Date`, `Cost`,`Type`, `Note`) VALUES (NULL, '"
					+ expenses.getName() + "', '" + expenses.getExpensesDate() + "', '" + expenses.getCost() + "','"
					+ expenses.getType() + "', '" + expenses.getNote() + "');";
			st.executeUpdate(insert);
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void updateExpenses(Expenses expenses) {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String update = "UPDATE `tblexpenses` SET `Name` = '" + expenses.getName() + "', `Date` = '"
					+ expenses.getExpensesDate() + "', `Cost` = '" + expenses.getCost() + "', `Note` = '"
					+ expenses.getNote() + "', `Type` = '" + expenses.getType() + "' WHERE `tblexpenses`.`ID` = "
					+ expenses.getID() + ";";

			st.executeUpdate(update);
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static void deleteExpenses(Expenses expenses) {
		java.sql.Connection con;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(url, USERNAME, PASSWORD);
			Statement st = con.createStatement();
			String update = "DELETE FROM `tblexpenses` WHERE `tblexpenses`.`ID` = " + expenses.getID() + ";";

			st.executeUpdate(update);
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	public static int returnNumOFSubscriptionType(int num) {
		int x = 0;
		try {
			Connection connection = DBConnect.getConnect();
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT * FROM `tblcustomers` WHERE `status` != '' AND `status` != 'Expired'");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int y = resultSet.getInt("ID");
				x += returnSubscriptionTypeByID(y, num);
			}

			connection.close();
			preparedStatement.close();
			resultSet.close();
		} catch (Exception e) {

		}
		return x;
	}

	public static int returnSubscriptionTypeByID(int ID, int num) {
		int x = 0;
		try {
			Connection connection = DBConnect.getConnect();
			PreparedStatement preparedStatement = connection.prepareStatement(
					"SELECT * FROM `tblsubscription` WHERE customernumber = " + ID + " ORDER BY ID DESC LIMIT 1;");
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				int y = resultSet.getInt("subscriptionType");
				if (y == num) {
					x++;
				}
			}

			connection.close();
			preparedStatement.close();
			resultSet.close();
		} catch (Exception e) {

		}
		return x;
	}
}
