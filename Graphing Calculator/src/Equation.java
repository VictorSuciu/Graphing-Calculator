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
		segmentedEq = prepareString(equationString);
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
		for(double i = 0 - ((double)width / 2.0); i <= (double)width / 2.0; i += 0.01) {
			
			points.add(new Point(i, (f((i + (((xMax + xMin) / 2) * (width / (xMax - xMin))) ) / ((width / 2) / ((xMax - xMin) / 2)    ) 
					+ (((yMax + yMin) / 2) * (height / (yMax - yMin)))) * ((height / 2) / ((yMax - yMin) / 2)) ) 
					- (((yMax + yMin) / 2) * (height / (yMax - yMin)))          ));
		}
	}
	public ArrayList<Point> getPoints() {
		return points;
	}
	
	private ArrayList<String> prepareString(String s) {
		ArrayList<String> ret = new ArrayList();
		index = 0;
		char current;
		int isNum;
		String isSymbol;
		
		while(index < s.length()) {
			
			current = s.charAt(index);
			
			isNum = isNum(current);
			isSymbol = isSymbol(current);
			
			if(isNum != -1) {
				ret.add(getNum());
				
			}
			else if(isSymbol != "") {
				ret.add(isSymbol);
			}
			else if(s.charAt(index) == 'x') {
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
			index++;
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
				return Math.cos(f(x));
			}
			else if(segmentedEq.get(index).equals("tan")) {
				return Math.tan(f(x));
			}
			else if(segmentedEq.get(index).equals("sqrt")) {
				return Math.sqrt(f(x));
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
	//2*x + ((x * 10))
	
	private String getNum() {
		
		double ret = 0.0;
		String numString = "";
		int advanceBy = 0;
		int numIndex = index;
		int test = isNum(equationString.charAt(numIndex));
		
		numString += "" + isNum(equationString.charAt(index));
		if(index < equationString.length() - 1) {
			while(isNum(equationString.charAt(index + 1)) != -1) {
				index++;
				if(index == equationString.length() - 1) {
					break;
				}
				numString += "" + isNum(equationString.charAt(index));
			}
		}
		if(index < equationString.length() -1) {
			if(equationString.charAt(index + 1) == '.') {
				numString += ".";
				index += 2;
				numString += "" + isNum(equationString.charAt(index));
				while(isNum(equationString.charAt(index + 1)) != -1) {
					index++;
					numString += "" + isNum(equationString.charAt(index));
			
				}
				
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