package application;

import java.awt.Toolkit;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Formatter;
import javafx.scene.control.Alert;

public class Function {

	public static boolean allNumbers(String str) {
		if(str.isEmpty() || str == null)
			return false;
		char c;
		int i = 0;
		while (i < str.length()) {
			c = str.charAt(i);
			if (!Character.isDigit(c))
				return false;
			i++;
		}
		return true;
	}

	public static boolean doubleNumbers(String str) {
		if(str.isEmpty()|| str == null)
			return false;
		if (str.charAt(0) == '.' || str.charAt(str.length() - 1) == '.') {
			return false;
		}
		int count = 0;
		int i = 0;
		char c;
		while (i < str.length()) {
			c = str.charAt(i);
			if (c == '.') {
				count++;
			} else if (!Character.isDigit(c)) {
				return false;
			}
			i++;
		}
		return (count <= 1);
	}

	public static void alert(String str) {
		Toolkit.getDefaultToolkit().beep();
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setHeaderText(null);
		alert.setContentText(str);
		alert.showAndWait();
	}
	
	public static String daysBetween(LocalDate statrt,LocalDate end) {
		Long days = ChronoUnit.DAYS.between(statrt,end);
		return days.toString();
	}
	
	public static double format(double x) {
		Formatter formatter = new Formatter();
		return Double.valueOf(formatter.format("%.2f", x).toString()) ;
		
	}
}
