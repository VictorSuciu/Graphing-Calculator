import java.util.ArrayList;

public class Equation {
	
	String equationString;
	ArrayList<String> segmentedEq = new ArrayList();
	ArrayList<Point> points = new ArrayList();
	ArrayList<String> equation = new ArrayList();
	ArrayList<Double> powerStack
	;
	String validItems = "0 1 2 3 4 5 6 7 8 9 + - * / ^ ( ) . x";
	char[] nums = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	char[] symbols = {'(', ')', '+', '-', '*', '/', '^', '|'}; 
	char[] operators = {'+', '-', '*', '/', '^'}; 
	int index;
	double thisX = 0;
	int absCount = 0;

	int width;
	int height;
	
	double xMin;
	double xMax;
	double yMin;
	double yMax;
	
	boolean placedP = false;
	
	public Equation() {
		
	}
	public Equation(String s, int width, int height, double xMin, double xMax, double yMin, double yMax) {
		equationString = s.toLowerCase();
		index = 0;
		
		segmentedEq = prepareString();
		if(segmentedEq.size() > 0 && containsOpError() == false) {
			
			System.out.println(segmentedEq);
			
			implicitMultiplication();
			System.out.println(segmentedEq);
			
			this.width = width;
			this.height = height;
			
			this.xMin = xMin;
			this.xMax = xMax;
			this.yMin = yMin;
			this.yMax = yMax;
			
			index = 0;
			correctOrderOfOperations();
			while(placedP == true) {
				placedP = false;
				index = 0;
				correctOrderOfOperations();
			}
			index = 0;
			powerOperations();
			
			index = segmentedEq.size();
			
			generatePoints();
		}
	}
	
	public boolean doesEqual(Equation e) {
		if(e.segmentedEq.size() != segmentedEq.size()) {
			return false;
		}
		for(int i = 0; i < segmentedEq.size(); i++) {
			if(!e.segmentedEq.get(i).equals(segmentedEq.get(i))) {
				return false;
			}
		}
		return true;
	}
	
	public void generatePoints() {
		System.out.println("RAW 0 = " + f(0));
		for(double i = ((1.0 / (xMax - xMin)) * (xMax + xMin) * (double)(width / 2)) - (((double)width / 2.0)); i <= ((double)width / 2.0) + ((1.0 / (xMax - xMin)) * (xMax + xMin) * (double)(width / 2)); i += 0.02) {
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
		ret.add("(");
		index = 0;
		char current;
		int isNum;
		String isSymbol;
		int bal = 0;
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
				if(isSymbol.equals("(")) {
					bal++;
				}
				else if(isSymbol.equals(")")) {
					 bal--;
				}
				ret.add(isSymbol);
			}
			else if(equationString.charAt(index) == 'x') {
				ret.add("x");
			}
			else if(isSin() == true) {
				ret.add("sin");
				index += 2;
			}
			else if(isCos() == true) {
				ret.add("cos");
				index += 2;
			}
			else if(isTan() == true) {
				ret.add("tan");
				index += 2;
			}
			else if(isSQRT() == true) {
				ret.add("sqrt");
				index += 3;
			}
			else if(isAbs() == true) {
				ret.add("abs");
				index += 2;
			}
			else if(isMod() == true) {
				ret.add("mod");
				index += 2;
			}
			else if(isArcSin() == true) {
				index += 5;
				ret.add("arcsin");
			}
			else if(isArcCos() == true) {
				index += 5;
				ret.add("arccos");
			}
			else if(isArcTan() == true) {
				index += 5;
				ret.add("arctan");
			}
			else {
				if(equationString.charAt(index) != ' ') {
					new ErrorWindow("Function Input Error", "Equation contains an unrecognized element starting with " + "'" + equationString.charAt(index) + "'");
					equationString = "";
					ret.clear();
					return ret;
				}
			}
			//spacedEquation += " ";
			if(gotNum == false) {
				index++;
			}
			gotNum = false;
		}
		
		if(bal > 0) {
			new ErrorWindow("User Input Error", "Equation contains unbalanced parentheses. There is an excess of " + bal +  " '('");
			equationString = "";
			ret.clear();
			return ret;
		}
		else if(bal < 0) {
			new ErrorWindow("User Input Error", "Equation contains unbalanced parentheses. There is an excess of " + Math.abs(bal) +  " ')'");
			equationString = "";
			ret.clear();
			return ret;
			
		}
		ret.add(")");
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
			else if(segmentedEq.get(index).charAt(0) == 'a') {
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
		
		int preOpIndex = 0;
		int currentOpIndex = 0;
		int bal = 0;
		char currentOpChar = ' ';
		char preOpChar = ' ';
		boolean isNegative = false;
		
		while(index < segmentedEq.size()) {	
			isNegative = false;
			
			if(segmentedEq.get(index).equals("-") && !segmentedEq.get(index - 1).equals(")") && !segmentedEq.get(index - 1).equals("x") 
					&& isNum(segmentedEq.get(index - 1).charAt(0)) == -1) {
				isNegative = true;
			}
			
			if(getPrecedence(segmentedEq.get(index).charAt(0)) != -1 && isNegative == false) {
				preOpIndex = currentOpIndex;
				currentOpIndex = index;
				preOpChar = currentOpChar;
				currentOpChar = segmentedEq.get(index).charAt(0);
				
				if(preOpChar != ' ' && currentOpChar != ' ') {
					
					if(getPrecedence(preOpChar) > getPrecedence(currentOpChar) && bal > 0) {
						
						segmentedEq.add(index, ")");
						currentOpIndex++;
						index++;
						bal--;
						placedP = true;
					}
					else if(getPrecedence(preOpChar) < getPrecedence(currentOpChar)) {
						segmentedEq.add(preOpIndex + 1, "(");
						index++;
						currentOpIndex++;
						bal++;
						placedP = true;
					}
					
				}
				System.out.println(preOpChar + " " + currentOpChar + " " + segmentedEq);
			}
			else if(isSymbol(segmentedEq.get(index).charAt(0)).equals("(") ) {
				index++;
				System.out.println("NESTED CALL");
				correctOrderOfOperations();
				
			}
			else if(isSymbol(segmentedEq.get(index).charAt(0)).equals(")")) {
				break;
			}
			
			//(x + (3 * (4 ^ (x ^ (2 + 3)))))
			//(x + 3 / 2 + (x + 1 / 5) * 1)
			
			index++;
			System.out.println(" Bal=" + bal);
		}
		
		for(int i = 0; i < bal; i++) {
			segmentedEq.add(index, ")");
			index++;
		}
		System.out.println(preOpChar + " " + currentOpChar + " " + segmentedEq);
		//index++;
		System.out.println("END");
	}
	//////////////////////////////////////////
	
	public void powerOperations() {
		int preOpIndex = 0;
		int currentOpIndex = 0;
		int bal = 0;
		
		while(index < segmentedEq.size()) {	
			
			if(segmentedEq.get(index).charAt(0) == '^') {
				preOpIndex = currentOpIndex;
				currentOpIndex = index;
				if(preOpIndex != 0) {
					segmentedEq.add(preOpIndex + 1, "(");
					index++;
					currentOpIndex++;
					bal++;
				}
			}
			else if(getPrecedence(segmentedEq.get(index).charAt(0)) == 2 || getPrecedence(segmentedEq.get(index).charAt(0)) == 1) {
				for(int i = 0; i < bal; i++) {
					segmentedEq.add(index, ")");
					index++;
					
				}
				bal = 0;
			}
			else if(isSymbol(segmentedEq.get(index).charAt(0)).equals("(") ) {
				index++;
				System.out.println("NESTED CALL");
				powerOperations();
				
			}
			else if(isSymbol(segmentedEq.get(index).charAt(0)).equals(")")) {
				break;
			}
			
			index++;
		}
		for(int i = 0; i < bal; i++) {
			segmentedEq.add(index, ")");
			index++;
		}
		System.out.println(segmentedEq);
	}
	
	//////////////////////////////////////////
	public int getPrecedence(char c) {
		if(c == '+' || c == '-') {
			return 1;
		}
		else if(c == '*' || c == '/') {
			return 2;
		}
		else if(c == '^') {
			return 3;
		}
		else {
			return -1;
		}
		
	}
	public double f(double x) {
		boolean power = false;
		
		index--;
		//System.out.println(index + " " + eq);
		if(index == segmentedEq.size() - 1) {
			thisX = x;
			//System.out.println("Set X");
		}
		if(index >= 0) {
			if(segmentedEq.get(index).equals(")")) {
				//powerStack = new ArrayList();
				return f(f(x));
			}
			else if(segmentedEq.get(index).equals("(")) {
				
				return x;
			}
			else if(segmentedEq.get(index).equals("x")) {
				return f(thisX);
			}
			else if(segmentedEq.get(index).equals("+")) {
				return f(x) + x;
			}
			else if(segmentedEq.get(index).equals("-")) {
				if(!segmentedEq.get(index - 1).equals(")") && !segmentedEq.get(index - 1).equals("x") && isNum(segmentedEq.get(index - 1).charAt(0)) == -1) {
					return f(0 - x);
				}
				else {
					//System.out.println("MINUS");
					return f(x) - x;
				}

			}
			else if(segmentedEq.get(index).equals("*")) {
				return f(x) * x;
			}
			else if(segmentedEq.get(index).equals("/")) {
				return f(x) / x;
			}
			else if(segmentedEq.get(index).equals("^")) {
				
				return Math.pow(f(x), x);
				
				
			}
			else if(segmentedEq.get(index).equals("abs")) {
				return f(Math.abs(x));
			}
			else if(segmentedEq.get(index).equals("sin")) {
				//index++;
				return f(Math.sin(x));
			}
			else if(segmentedEq.get(index).equals("cos")) {
				//index++;
				return f(Math.cos(x));
			}
			else if(segmentedEq.get(index).equals("tan")) {
				//index++;
				return f(Math.tan(x));
			}
			else if(segmentedEq.get(index).equals("arcsin")) {
				//index++;
				return f(Math.asin(x));
			}
			else if(segmentedEq.get(index).equals("arccos")) {
				//index++;
				return f(Math.acos(x));
			}
			else if(segmentedEq.get(index).equals("arctan")) {
				//index++;
				return f(Math.atan(x));
			}
			else if(segmentedEq.get(index).equals("sqrt")) {
				//index++;
				return f(Math.sqrt(x));
			}
			else if(segmentedEq.get(index).equals("mod")) {
				return x % f(x);
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
			index = segmentedEq.size();
			//System.out.println("x = " + x);
			//System.out.println();
			return x;
		}
	}
	public double getPower(ArrayList<Double> l, int i, double ret) {
		
		if(i < l.size() - 1) {
			//ret = l.get(i);
			return Math.pow(ret, getPower(l, i + 1, ret));
		}
		else {
			System.out.println("POWERSTACK SIZE = " + l.size());
			return ret;
		}
	}
	private boolean containsOpError() {
		for(int i = 0; i < segmentedEq.size() - 1; i++) {
			if(isOperator(segmentedEq.get(i).charAt(0)) == true) {
				 if(i == segmentedEq.size() - 2) {
					 System.out.println("Op Test 1 " + segmentedEq.get(i).charAt(0) + " " + isOperator(segmentedEq.get(i).charAt(0)));
					 new ErrorWindow("User Input Error", "Incorrect usage of\noperator " + "'" + segmentedEq.get(i) + "'");
					 return true;
				 }
				 else if(segmentedEq.get(i + 1).equals(")") || (isOperator(segmentedEq.get(i + 1).charAt(0)) == true && !segmentedEq.get(i + 1).equals("-"))) {
					 System.out.println("Op Test 2");
					 new ErrorWindow("User Input Error", "Incorrect usage of\noperator " + "'" + segmentedEq.get(i) + "'");
					 return true;
				 }
				 if(i == 1) {
					 if(!segmentedEq.get(i).equals("-")) {
						 System.out.println("Op Test 3");
						 new ErrorWindow("User Input Error", "Incorrect usage of\noperator " + "'" + segmentedEq.get(i) + "'");
						 return true;
					 }
				 }
				 else if((segmentedEq.get(i - 1).equals("(") && !segmentedEq.get(i).equals("-")) || (isOperator(segmentedEq.get(i - 1).charAt(0)) == true && !segmentedEq.get(i).equals("-"))) {
					 System.out.println("Op Test 4");
					 new ErrorWindow("User Input Error", "Incorrect usage of\noperator " + "'" + segmentedEq.get(i) + "'");
					 return true;
				 }
			}
		}
		return false;
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
	private boolean isAbs() {
		if(index < equationString.length() - 2) {
			if(equationString.substring(index, index + 3).equals("abs")) {
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
	private boolean isMod() {
		if(index < equationString.length() - 2) {
			if(equationString.substring(index, index + 3).equals("mod")) {
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
	private boolean isArcSin() {
		if(index < equationString.length() - 5) {
			if(equationString.substring(index, index + 6).equals("arcsin")) {
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
	private boolean isArcCos() {
		if(index < equationString.length() - 5) {
			if(equationString.substring(index, index + 6).equals("arccos")) {
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
	private boolean isArcTan() {
		if(index < equationString.length() - 5) {
			if(equationString.substring(index, index + 6).equals("arctan")) {
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
	private boolean isOperator(char c) {
		
		for(int i = 0; i < operators.length; i++) {
			if(operators[i] == c) {
				return true;
			}
		}
		return false;
	}
}
