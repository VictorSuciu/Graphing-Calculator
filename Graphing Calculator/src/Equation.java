import java.util.ArrayList;

public class Equation {
	
	String equationString;
	ArrayList<String> segmentedEq = new ArrayList();
	
	ArrayList<String> steps = new ArrayList();
	ArrayList<String> equation = new ArrayList();
	
	String validItems = "0 1 2 3 4 5 6 7 8 9 + - * / ^ ( ) . x";
	char[] nums = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	char[] symbols = {'(', ')', '+', '-', '*', '/', '^'}; 
	int index;
	// 5 + (x / 2)
	
	public Equation(String s) {
		equationString = s;
		index = 0;
		prepareString();
		System.out.println(segmentedEq);
	}
	
	public Point getPoint(double x) {
		
		return new Point(0, 0);
	}
	
	private void prepareString() {
		
		index = 0;
		char current;
		int isNum;
		String isSymbol;
		
		while(index < equationString.length()) {
			
			current = equationString.charAt(index);
			
			isNum = isNum(current);
			isSymbol = isSymbol(current);
			
			if(isNum != -1) {
				//String num = "";
				//num = getNum();
				segmentedEq.add(getNum());
			}
			else if(isSymbol != "") {
				segmentedEq.add(isSymbol);
			}
			else if(equationString.charAt(index) == 'x') {
				segmentedEq.add("x");
			}
			
			//spacedEquation += " ";
			index++;
		}
		
	}
	private void parseEquation() {
		
	}
	
	private boolean isSin(int index) {
		if(equationString.substring(index, index + 3).equals("sin")) {
			return true;
		}
		else {
			return false;
			
		}
	}
	private boolean isCos(int index) {
		if(equationString.substring(index, index + 3).equals("cos")) {
			return true;
		}
		else {
			return false;
			
		}
	}
	private String getNum() {
		double ret = 0.0;
		String numString = "";
		
		int test = isNum(equationString.charAt(index));
		
		while(test != -1) {
			numString += "" + test;
			System.out.println(equationString.charAt(index));
			test = isNum(equationString.charAt(index));
			index++;
		}
		if(equationString.charAt(index) == '.') {
			numString += ".";
			while(test != -1) {
				numString += "" + test;
				test = isNum(equationString.charAt(index));
				index++;
			}
		}
		else {
			numString += ".0";
		}
		
		ret = Double.parseDouble(numString);
		return numString;
	}
	private int isNum(char c) {
		for(int i = 0; i < nums.length; i++) {
			
			if(nums[i] == c) {
				return Character.getNumericValue(nums[i]);
			}
		}
		return -1;
	}
	private String isSymbol(char c) {
		
		for(int i = 0; i < symbols.length; i++) {
			if(symbols[i] == c) {
				return "" + symbols[i];
			}
		}
		return "";
	}
}
