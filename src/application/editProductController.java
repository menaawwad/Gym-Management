package application;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

import javafx.event.ActionEvent;

public class editProductController {
	private Product newProduct;
	boolean flag;

	@FXML
	private TextField WeightTextField;

	@FXML
	private TextField barcodeTextField1;

	@FXML
	private Button cancelButton;

	@FXML
	private TextField costPriceTextField;

	@FXML
	private TextField minOrderTextField;

	@FXML
	private TextField minPriceTextField;

	@FXML
	private TextField nameTextField;

	@FXML
	private Button saveButton;

	@FXML
	private TextField sellPriceTextField;

	@FXML
	private TextField stockTextField;

	public void getProduct(Product product) {
		newProduct = new Product(product);

		nameTextField.setText(product.getName());
		barcodeTextField1.setText(product.getBarcode());
		costPriceTextField.setText(String.valueOf(product.getCostPrice()));
		sellPriceTextField.setText(String.valueOf(product.getSellPrice()));
		minPriceTextField.setText(String.valueOf(product.getMinPrice()));
		minOrderTextField.setText(String.valueOf(product.getMinOrder()));
		WeightTextField.setText(String.valueOf(product.getWeight()));
		stockTextField.setText(Integer.toString(product.getStock()));
	}

	@FXML
	void cancelClick(ActionEvent event) {
		Node node = (Node) event.getSource();
		Stage stage = (Stage) node.getScene().getWindow();
		stage.close();
	}

	@FXML
	void saveClick(ActionEvent event) {
		flag = true;
		if (nameTextField.getText().equals("")) {
			flag = false;
			Function.alert("please enter product name");
		}
		if (barcodeTextField1.getText().equals("")) {
			flag = false;
			Function.alert("please enter product barcode");
		} else if (!Function.allNumbers(barcodeTextField1.getText())) {
			flag = false;
			Function.alert("barcode contains only numbers");
		}
		if (costPriceTextField.getText().equals("")) {
			flag = false;
			Function.alert("please enter cost price");
		} else if (!Function.doubleNumbers(costPriceTextField.getText())) {
			flag = false;
			Function.alert("cost price contins only numbers");
		}
		if (sellPriceTextField.getText().equals("")) {
			flag = false;
			Function.alert("please enter sell price");
		} else if (!Function.doubleNumbers(sellPriceTextField.getText())) {
			flag = false;
			Function.alert("sell price contins only numbers");
		}
		if (minPriceTextField.getText().equals("")) {
			flag = false;
			Function.alert("please enter min price");
		} else if (!Function.doubleNumbers(minPriceTextField.getText())) {
			flag = false;
			Function.alert("min price contins only numbers");
		}
		if (minOrderTextField.getText().equals("")) {
			flag = false;
			Function.alert("please enter min Order");
		} else if (!Function.allNumbers(minOrderTextField.getText())) {
			flag = false;
			Function.alert("min Order contins only numbers");
		}
		if (WeightTextField.getText().equals("")) {
			flag = false;
			Function.alert("please enter Weight");
		} else if (!Function.doubleNumbers(WeightTextField.getText())) {
			flag = false;
			Function.alert("Weight contins only numbers");
		}
		if (stockTextField.getText().equals("")) {
			flag = false;
			Function.alert("please enter Stock");
		} else if (!Function.allNumbers(stockTextField.getText())) {
			flag = false;
			Function.alert("Stock contins only numbers");
		}

		if (flag) {
			if (Double.valueOf(sellPriceTextField.getText()) < Double.valueOf(minPriceTextField.getText())) {
				flag = false;
				Function.alert("Sell price cannot be lower than min price");
			} 
		}
		if (flag) {
			String name = nameTextField.getText();
			String barcode = barcodeTextField1.getText();
			double costPrice = Function.format(Double.valueOf(costPriceTextField.getText()));
			double sellPrice = Function.format(Double.valueOf(sellPriceTextField.getText()));
			double minPrice = Function.format(Double.valueOf(minPriceTextField.getText()));
			double weight = Double.valueOf(WeightTextField.getText());
			int minOrder = Integer.valueOf(minOrderTextField.getText());
			int stock = Integer.valueOf(stockTextField.getText());
			Product product = new Product(newProduct.getID(),name, newProduct.getRegisterDate(), LocalDate.now(), barcode, costPrice,
					sellPrice, minPrice, weight, stock, minOrder);
			DBConnect.updateProduct(product);
			Node node = (Node) event.getSource();
			Stage stage = (Stage) node.getScene().getWindow();
			stage.close();
		}
	}
}
