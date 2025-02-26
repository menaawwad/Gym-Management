import java.sql.Date;
import java.time.LocalDate;
import java.util.Formatter;

public class forRemove {
	public static boolean doubleNumbers(String str) {
		if(str.charAt(0) == '.' || str.charAt(str.length()-1)=='.') {
			return false;
		}
		int count = 0;
		int i=0;
		char c;
		while(i<str.length()) {
			c = str.charAt(i);
			if(c == '.') {
				count++;
			}else if(!Character.isDigit(c)) {
				return false;
			}
			i++;
		}
		return (count <= 1);
	}

	
	
	public static void main(String[] args) {
		
		System.out.println(LocalDate.now());

	}

}
