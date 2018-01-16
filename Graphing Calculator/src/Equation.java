import java.util.ArrayList;

public class Equation {
	
	String equationString;
	ArrayList<String> segmentedEq = new ArrayList();
	ArrayList<Point> points = new ArrayList();
	ArrayList<String> equation = new ArrayList();
	
	String validItems = "0 1 2 3 4 5 6 7 8 9 + - * / ^ ( ) . x";
	char[] nums = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	char[] symbols = {'(', ')', '+', '-', '*', '/', '^', '|'}; 
	int index;
	double thisX = 0;
	int absCount = 0;

	int width;
	int height;
	
	double xMin;
	double xMax;
	double yMin;
	double yMax;
	
	public Equation() {
		
	}
	public Equation(String s, int width, int height, double xMin, double xMax, double yMin, double yMax) {
		equationString = s;
		index = 0;
		
		segmentedEq = prepareString();
		System.out.println(segmentedEq);
		
		implicitMultiplication();
		System.out.println(segmentedEq);
		
		this.width = width;
		this.height = height;
		
		this.xMin = xMin;
		this.xMax = xMax;
		this.yMin = yMin;
		this.yMax = yMax;
		
		index = -1;
		
		generatePoints();
	}
	
	
	public void generatePoints() {
		System.out.println("RAW 0 = " + f(0));
		for(double i = ((1.0 / (xMax - xMin)) * (xMax + xMin) * 300.0) - (((double)width / 2.0)); i <= ((double)width / 2.0) + ((1.0 / (xMax - xMin)) * (xMax + xMin) * 300.0); i += 0.01) {
			/*
			points.add(new Point(i, (f((i + (((xMax + xMin) / 2) * (width / (xMax - xMin))) ) / ((width / 2) / ((xMax - xMin) / 2)    ) 
					+ (((yMax + yMin) / 2) * (height / (yMax - yMin)))) * ((height / 2) / ((yMax - yMin) / 2)) ) 
					- (((yMax + yMin) / 2) * (height / (yMax - yMin)))          ));
			*/
			points.add(new Point(i, (f(i / ((double)width / ((xMax - xMin))))   ) * ((double)height / ((yMax - yMin))) ));
			
			//double test = ((f(i / (width / ((xMax - xMin) / 2)))   ) * (height / ((yMax - yMin) / 2)) );
			//System.out.println("X = " + i + "\nY = " + test + "\n");
			
		}
		//System.out.println("Unstretched X = " + f(((double)width / 2.0) - 1.0));
		
	}
	public ArrayList<Point> getPoints() {
		return points;
	}
	
	private ArrayList<String> prepareString() {
		ArrayList<String> ret = new ArrayList();
		index = 0;
		char current;
		int isNum;
		String isSymbol;
		boolean gotNum = false;
		
		while(index < equationString.length()) {
			
			current = equationString.charAt(index);
			
			isNum = isNum(current);
			isSymbol = isSymbol(current);
			
			if(isNum != -1) {
				ret.add(getNum());
				gotNum = true;
				
			}
			else if(isSymbol != "") {
				ret.add(isSymbol);
			}
			else if(equationString.charAt(index) == 'x') {
				ret.add("x");
			}
			else if(isSin() == true) {
				ret.add("sin");
			}
			else if(isCos() == true) {
				ret.add("cos");
			}
			else if(isTan() == true) {
				ret.add("tan");
			}
			else if(isSQRT() == true) {
				ret.add("sqrt");
			}
			//spacedEquation += " ";
			if(gotNum == false) {
				index++;
			}
			gotNum = false;
		}
		return ret;
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
	public void implicitMultiplication() {
		index = segmentedEq.size() - 1;
		while(index > 0) {
									
			if(isNum(segmentedEq.get(index).charAt(0)) != -1) {
				if(segmentedEq.get(index - 1).charAt(0) == ')') {
					segmentedEq.add(index, "*");
				}
				else if(segmentedEq.get(index - 1).charAt(0) == 'x') {
					segmentedEq.add(index, "*");
				}
					
			}
			else if(segmentedEq.get(index).charAt(0) == '(') {
				if(segmentedEq.get(index - 1).charAt(0) == ')') {
					segmentedEq.add(index, "*");
				}
				else if(segmentedEq.get(index - 1).charAt(0) == 'x') {
					segmentedEq.add(index, "*");
				}
				if(isNum(segmentedEq.get(index - 1).charAt(0)) != -1) {
					segmentedEq.add(index, "*");
				}
			}
			else if(segmentedEq.get(index).charAt(0) == 'x') {
				if(segmentedEq.get(index - 1).charAt(0) == ')') {
					segmentedEq.add(index, "*");
				}
				if(isNum(segmentedEq.get(index - 1).charAt(0)) != -1) {
					segmentedEq.add(index, "*");
				}
			}
			else if(segmentedEq.get(index).charAt(0) == 's') {
				if(segmentedEq.get(index - 1).charAt(0) == ')') {
					segmentedEq.add(index, "*");
				}
				if(isNum(segmentedEq.get(index - 1).charAt(0)) != -1) {
					segmentedEq.add(index, "*");
				}
				else if(segmentedEq.get(index - 1).charAt(0) == 'x') {
					segmentedEq.add(index, "*");
				}
			}
			else if(segmentedEq.get(index).charAt(0) == 'c') {
				if(segmentedEq.get(index - 1).charAt(0) == ')') {
					segmentedEq.add(index, "*");
				}
				if(isNum(segmentedEq.get(index - 1).charAt(0)) != -1) {
					segmentedEq.add(index, "*");
				}
				else if(segmentedEq.get(index - 1).charAt(0) == 'x') {
					segmentedEq.add(index, "*");
				}
			}
			else if(segmentedEq.get(index).charAt(0) == 't') {
				if(segmentedEq.get(index - 1).charAt(0) == ')') {
					segmentedEq.add(index, "*");
				}
				if(isNum(segmentedEq.get(index - 1).charAt(0)) != -1) {
					segmentedEq.add(index, "*");
				}
				else if(segmentedEq.get(index - 1).charAt(0) == 'x') {
					segmentedEq.add(index, "*");
				}
			}
			
			index--;
			
		}
	}
	public void correctOrderOfOperations() {
		index = 0;
		int parentheses = 0;
		int operator1 = 0;
		int operator2 = 0;
		int currentOperator = 0;
		while(index < segmentedEq.size()) {
									
			
			if(segmentedEq.get(index).charAt(0) == '(') {
				parentheses++;
			}
			if(segmentedEq.get(index).charAt(0) == ')') {
				parentheses--;
			}
			if(segmentedEq.get(index).charAt(0) == '+' || segmentedEq.get(index).charAt(0) == '-') {
				parentheses--;
			}
			if(segmentedEq.get(index).charAt(0) == '*' || segmentedEq.get(index).charAt(0) == '/') {
				parentheses--;
			}
			if(segmentedEq.get(index).charAt(0) == '^') {
				parentheses--;
			}
			
			index++;
			
		}
	}
	public double f(double x) {
		boolean power = false;
		index++;
		//System.out.println(index + " " + eq);
		if(index == 0) {
			thisX = x;
			//System.out.println("thisX = " + thisX);
		}
		if(index < segmentedEq.size()) {
			if(segmentedEq.get(index).equals("(")) {
				return f(f(x));
			}
			else if(segmentedEq.get(index).equals(")")) {
				return x;
			}
			else if(segmentedEq.get(index).equals("x")) {
				return f(thisX);
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
			
			else if(segmentedEq.get(index).equals("^")) {
				return Math.pow(x, f(x));
			}
			else if(segmentedEq.get(index).equals("|")) {
				absCount++;
				//System.out.println(absCount);
				if(absCount % 2 != 0) {
					return f(Math.abs(f(x)));
				}
				else {
					
					return Math.abs(x);
				}
				
			}
			else if(segmentedEq.get(index).equals("sin")) {
				index++;
				return f(Math.sin(f(x)));
			}
			else if(segmentedEq.get(index).equals("cos")) {
				index++;
				return f(Math.cos(f(x)));
			}
			else if(segmentedEq.get(index).equals("tan")) {
				index++;
				return f(Math.tan(f(x)));
			}
			else if(segmentedEq.get(index).equals("sqrt")) {
				index++;
				return f(Math.sqrt(f(x)));
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
			absCount = 2;
			index = -1;
			//System.out.println("x = " + x);
			//System.out.println();
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
	//test
	private boolean isTan() {
		if(index < equationString.length() - 2) {
			if(equationString.substring(index, index + 3).equals("tan")) {
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
	private boolean isSQRT() {
		if(index < equationString.length() - 3) {
			if(equationString.substring(index, index + 4).equals("sqrt")) {
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
		String numString = "";
		
		while(index < equationString.length() && isNum(equationString.charAt(index)) != -1) {
			numString += "" + isNum(equationString.charAt(index));
			index++;
		}
		if(index < equationString.length() - 1) {
			if(equationString.charAt(index) == '.') {
				index++;
				numString += ".";
				while(index < equationString.length() && isNum(equationString.charAt(index)) != -1) {
					numString += "" + isNum(equationString.charAt(index));
					index++;
				}
			}
			else {
				numString += ".0";
			}
			
		}
		else {
			numString += ".0";
		}
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