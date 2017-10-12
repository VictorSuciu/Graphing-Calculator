import java.util.ArrayList;

public class Equation {
	
	String equationString;
	ArrayList<String> segmentedEq = new ArrayList();
	
	ArrayList<String> steps = new ArrayList();
	ArrayList<String> equation = new ArrayList();
	ArrayList<Integer> indexes = new ArrayList();
	
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
		index = -1;
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
			else if(isSin() == true) {
				segmentedEq.add("sin");
			}
			else if(isCos() == true) {
				segmentedEq.add("cos");
			}
			//spacedEquation += " ";
			index++;
		}
		
	}
	private void generateEquation() {
		int parentheseCount = 0;
		int currentIndex = 0;
		String currentItem = "";
		String isSymbol = "";
		
		for(int i = 0; i < segmentedEq.size(); i++) {
			currentItem = segmentedEq.get(i);
			
			if(currentItem.length() == 1) {
				isSymbol = isSymbol(currentItem.charAt(0));
				
				
			}
		}
	}
	public double f(double x) {
		index++;
		if(index < segmentedEq.size()) {
			if(segmentedEq.get(index).equals("(")) {
				return f(f(x));
			}
			else if(segmentedEq.get(index).equals(")")) {
				return f(x);
			}
			else if(segmentedEq.get(index).equals("x")) {
				return f(x);
			}
			else if(segmentedEq.get(index).equals("+")) {
				return x + f(x);
			}
			else if(segmentedEq.get(index).equals("-")) {
				return x - f(x);
			}
			else if(segmentedEq.get(index).equals("*")) {
				return x * f(x);
			}
			else if(segmentedEq.get(index).equals("/")) {
				return x / f(x);
			}
			else if(segmentedEq.get(index).equals("sin")) {
				return Math.sin(f(x));
			}
			else if(segmentedEq.get(index).equals("cos")) {
				return Math.cos(f(x));
			}
			else if(isNumBool(segmentedEq.get(index).charAt(0)) == true) {
				double num = Double.parseDouble(segmentedEq.get(index));
				return f(num);
			}
			else {
				return (Double) null;
			}
			
		}
		else {
			index = -1;
			return x;
		}
	}
	private boolean isSin() {
		if(index < equationString.length() - 2) {
			if(equationString.substring(index, index + 3).equals("sin")) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return false;
		}
	}
	private boolean isCos() {
		if(index < equationString.length() - 2) {
			if(equationString.substring(index, index + 3).equals("cos")) {
				return true;
			}
			else {
				return false;
			}
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
			if(index >= equationString.length() - 1) {
				break;
			}
			index++;
			test = isNum(equationString.charAt(index));
			System.out.println(equationString.charAt(index) + " " + test);
			
		}
		//System.out.println("end");
		
		if(equationString.charAt(index) == '.') {
			numString += ".";
			index++;
			test = test = isNum(equationString.charAt(index));
			while(test != -1) {
				numString += "" + test;
				index++;
				test = isNum(equationString.charAt(index));
				
			}
			index--;
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
	private boolean isNumBool(char c) {
		for(int i = 0; i < nums.length; i++) {
			if(nums[i] == c) {
				return true;
			}
		}
		return false;
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
