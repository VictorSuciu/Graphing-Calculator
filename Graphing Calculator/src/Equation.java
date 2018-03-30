import java.util.ArrayList;

public class Equation {
	
	
	String equationString; //Stores the raw user equation input
	
	ArrayList<String> segmentedEq = new ArrayList(); //Stores tokenized user input. Tokens are distinguished by
												    //being recognized mathematical elements, such as "sin", "+", "/", ect.
	
	ArrayList<Point> points = new ArrayList(); //Stores the series of (x, y) coordinates generated using the user's equation
											  //in generatePoints()
	
	//These arrays store all the supported mathematical operators, functions, and elements
	char[] nums = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
	char[] symbols = {'(', ')', '+', '-', '*', '/', '^', '|'}; 
	char[] operators = {'+', '-', '*', '/', '^'}; 
	String[] mathElements = {"sin", "cos", "tan", "arcsin", "arccos", "arctan", "sqrt", "abs", "mod"};
	
	int index; //universal iteration index used by most methods: prepareString(), correctOrderOfOperations(), powerOperations(), and f(x)10 
	double thisX = 0; //Stores the current value of x in generatePoints() (the method that generates a list of points using
					 //the user's equation). Points are stored in ArrayList<Point> points, declared above
	
	int width; //Stores the width of the graphing plane window, set in the Window class and passed to this class as
			   //a parameter
	
	int height;//Stores the height of the graphing plane window, set in the Window class and passed to this class as
			   //a parameter
	
	//These store the x/y ranges set by the user. Passed to this class as an argument
	double xMin;
	double xMax;
	double yMin;
	double yMax;
	
	boolean placedP = false; //Used by correctOrderOfOperations() to notify that it has or has not inserted at least one parenthesis.
							 //Set to false by default, and set to true if correctOrderOfOperations has placed at least one parenthesis.
	
	//Default constructor
	public Equation() {
		
	}
	
	/*
	 * Main constructor
	 *///----------------------------------------------1
	public Equation(String s, int width, int height, double xMin, double xMax, double yMin, double yMax) {
		
		equationString = s.toLowerCase();
		index = 0;
		
		//----------------------------------------------1a
		segmentedEq = prepareString();
		/*----------------------------------------------1a
		 * prepareString() iterates through String equationString, the user's raw input, and
		 * outputs a tokenized version of that String in the form of an ArrayList.
		 * segmentedEq is being set to this tokenized equation.
		 */
		 
		//----------------------------------------------1b
		if(segmentedEq.size() > 2 && containsOpError() == false) {
			/*----------------------------------------------1b
			 * This if statement checks if segmentedEq contains anything more than the default [(, )] parentheses
			 * inserted in prepareString(). This prevents it from attempting to graph a space character, or 
			 * when an unbalanced parentheses error or an unrecognized element error is caught. 
			 */
			
			//----------------------------------------------1c
			implicitMultiplication();
			/*----------------------------------------------1c
			 * This method adds multiplication "*" operators anywhere the user has used implicit multiplication.
			 * For example, if the user entered 2x(4 + x), this method will change it to 2*x*(4 + x)
			 * This makes it readable by the equation parser f(x) as well as by the order of operations algorithm.
			 */
			
			//----------------------------------------------1d
			this.width = width;
			this.height = height;
			this.xMin = xMin;
			this.xMax = xMax;
			this.yMin = yMin;
			this.yMax = yMax;
			/*----------------------------------------------1d
			 * Sets the class fields
			 */
			
			//----------------------------------------------1e
			index = 0;
			correctOrderOfOperations();
			while(placedP == true) {
				placedP = false;
				index = 0;
				correctOrderOfOperations();
			}
			index = 0;
			powerOperations();
			/*----------------------------------------------1e
			 * This bit of code is the order of operations algorithm. It inserts parentheses into segmentedEq
			 * to force the parser f(x) to follow correct order of operations, as that algorithm correctly
			 * evaluates parentheses.
			 */
			
			//----------------------------------------------1f
			index = segmentedEq.size();
			generatePoints();
			/*----------------------------------------------1f
			 * This generates a series of points used to draw the user's equation on the plane. Stored
			 * in ArrayList<Point> points.
			 */
		}
	}
	//----------------------------------------------1
	
	/*
	 * This doesEqual() method tests if one Equation is equal to another Equation by seeing if their segmentedEq's match.
	 * Used for the memory feature to avoid adding two of the same equations in a row.
	 *///----------------------------------------------2
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
	//----------------------------------------------2
	

	/*
	 * This generates a series of points used to draw the user's equation on the plane. Stored
	 * in ArrayList<Point> points. Takes into account the x/y ranges as well as the width and 
	 * height of the plane to know how to compute the points. The point being added uses the for
	 * loop's index int i as the x value, and the value computed by plugging i into f(x) as the
	 * y value. The value of i plugged into f(x) is altered by the specific formula seen above,
	 * taking the x/y ranges, width, and height of the plane into account in order to stretch 
	 * or compress as needed. The formula seen altering i in the for loop declaration determines
	 * which segment of the function to compute points for, such as the section from x = -5 to 
	 * x = 15, or any other slice determined by the x/y ranges. All of this determines which 
	 * part of the function line is visible in the plane window. 
	 *///----------------------------------------------3
	public void generatePoints() {
		System.out.println("RAW 0 = " + f(0));
		for(double i = ((1.0 / (xMax - xMin)) * (xMax + xMin) * (double)(width / 2)) - (((double)width / 2.0)); i <= ((double)width / 2.0) + ((1.0 / (xMax - xMin)) * (xMax + xMin) * (double)(width / 2)); i += 0.02) {
			
			points.add(new Point(i, (f(i / ((double)width / ((xMax - xMin))))   ) * ((double)height / ((yMax - yMin))) ));
			
		}
	}
	//----------------------------------------------3
	
	/*
	 * This getPoints( method returns the series of points computed by generatePoints() above.
	 * Used by the DrawPlane class to draw a line using this list of points.
	 *///----------------------------------------------4
	public ArrayList<Point> getPoints() {
		return points;
	}
	//----------------------------------------------4
	

	/*
	 * This prepareString() method tokenizes the raw user input stored in the String equationString
	 * by separating it into recognized mathematical elements. These are elements such as operators
	 * parentheses, and functions such as abs(), sin(), arctan(), ect.
	 *///----------------------------------------------5
	private ArrayList<String> prepareString() {
		ArrayList<String> ret = new ArrayList();
		
		//----------------------------------------------5a
		ret.add("(");
		/*----------------------------------------------5a
		 * Every equation is automatically surrounded by a set of parentheses. The open parenthesis
		 * is added here, and the close parenthesis is added at the end of the method.
		 */
		
		index = 0; //This is the universal index used by most methods in this class.
		
		char current; //Stores the character in equationString currently being viewed in the loop
		
		int isNum; //Stores the value returned by isNum(). Returns a number the current character is a number, and 
				   //-1 if the current character is not a number.
		
		String isSymbol; //Stores the value returned by isSymbol(). Returns a symbol if the current character
						 //is a recognized symbol, and a space " " if it is not.
		
		int bal = 0; //Used to measure the ballance of open and close parentheses. If the loop reaches the end
					 //of equationString and bal is not 0, it triggers an unbalanced parentheses error.
		
		boolean gotNum = false; //gotNum is set to false by default, and gets set to true if isNum() found
							   //a number. Used to prevent increasing index too much.
		
		boolean foundElement = false; //foundElement is set to false by default, and is set to true if foundElement()
									 //finds a function such as sin() or sqrt() that is more than one character. Used
									 //to skip the other tests if it has found such an element.
		
		while(index < equationString.length()) {
			
			current = equationString.charAt(index);
			
			isNum = isNum(current);
			isSymbol = isSymbol(current);
			
			//----------------------------------------------5b
			for(String s : mathElements) {
				if(foundElement(s) == true) {
					ret.add(s);
					index += s.length() - 1;
					foundElement = true;
					break;
				}
			}
			/*----------------------------------------------5b
			 * This loop goes through the array storing all recognized multi-character functions
			 * and adds one to the tokenized ArrayList if found. Breaks from the loop once one has
			 * been found, and sets foundElement to true in order to skip the other tests.
			 */
			
			//This if statement only allows other equation elements to be tested for if a multi-character
			//function has not been found.
			if(foundElement == false) {
				
				//----------------------------------------------5c
				if(isNum != -1) {
					ret.add(getNum());
					gotNum = true;
					
				}
				/*----------------------------------------------5c
				 * Adds a double number if it finds one
				 */
				
				//----------------------------------------------5d
				else if(isSymbol != "") {
					if(isSymbol.equals("(")) {
						bal++;
					}
					else if(isSymbol.equals(")")) {
						 bal--;
					}
					ret.add(isSymbol);
				}
				/*----------------------------------------------5d
				 * Adds a symbol if it finds one, and edits int bal if it finds a parenthesis
				 */
				
				//----------------------------------------------5e
				else if(equationString.charAt(index) == 'x') {
					ret.add("x");
				}
				/*----------------------------------------------5e
				 * Adds x if it finds it
				 */
				
				//----------------------------------------------5f
				else {
					if(equationString.charAt(index) != ' ') {
						new ErrorWindow("Function Input Error", "Equation contains an unrecognized element starting with " + "'" + equationString.charAt(index) + "'");
						equationString = "";
						ret.clear();
						return ret;
					}
				}
				/*----------------------------------------------5f
				 * If it did not add anything to the tokenized ArrayList, it means that the current chracter
				 * is not supported. This triggers an unrecognized element error.
				 */
			}
			if(gotNum == false) {
				index++;
			}
			foundElement = false;
			gotNum = false;
		}
		
		
		//----------------------------------------------5g
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
		/*----------------------------------------------5g
		 * These if statements test if there is an equal balance of open and close parentheses.
		 * If this is not the case, meaning int bal is not equal to 0, it triggers an
		 * unbalanced parentheses error.
		 */
		ret.add(")");
		return ret;
	}
	//----------------------------------------------5
	
	
	/*
	 * This implicitMultiplication() method inserts multiplication "*" operators everywhere the user has used
	 * implicit multiplication. For example, if they enter 2x(4 + 5), it would insert 2*x*(4 + 5). This allows
	 * the order of operation and the f(x) parser to be able to easily read the equation, and not have to have
	 * extra logic to deal with both implicit and explicit multiplication.
	 *///----------------------------------------------6
	public void implicitMultiplication() {
		index = segmentedEq.size() - 1;
		boolean foundElement = false;
		
		//----------------------------------------------6a
		while(index > 0) {
		/*----------------------------------------------6a
		 * iterates through segmentedEq from the highest index to the lowest.						
		 */
			
			//----------------------------------------------6b
			for(String s : mathElements) {
				if(segmentedEq.get(index).equals(s)) {
					foundElement = true;
					break;
				}
			}
			/*----------------------------------------------6b
			 * Tests if element of segmentedEq is a recognize multi-character function.
			 * If it is, it sets foundElement to true and breaks from the loop.
			 */
			
			//----------------------------------------------6c
			if(foundElement == true) {
				if(segmentedEq.get(index - 1).charAt(0) == ')' 
						|| isNum(segmentedEq.get(index - 1).charAt(0)) != -1 
						|| segmentedEq.get(index - 1).charAt(0) == 'x') {
					 
					segmentedEq.add(index, "*");
				}
			}
			/*----------------------------------------------6c
			 * If foundElement is equal to true, it tests the element in segmentedEq at
			 * index - 1 for every element that would signify implicit multiplication.
			 * If it finds such an element, it adds "*" between the current element
			 * and the previous element.
			 */
			
			//----------------------------------------------6d
			if(isNum(segmentedEq.get(index).charAt(0)) != -1 ) {
				if(segmentedEq.get(index - 1).charAt(0) == ')' 
					|| segmentedEq.get(index - 1).charAt(0) == 'x') {
					
					segmentedEq.add(index, "*");
				}	
			}
			/*----------------------------------------------6d
			 * If the current equation element at index is a number, add a "*" operator between the current element
			 * and the previous element if the element at index - 1 is a close parenthesis or "x". Only in these 
			 * circumstances would it be implicit multiplication.
			 */
			
			//----------------------------------------------6e
			else if(segmentedEq.get(index).charAt(0) == '(') {
				if(segmentedEq.get(index - 1).charAt(0) == ')' 
					|| segmentedEq.get(index - 1).charAt(0) == 'x' 
					|| isNum(segmentedEq.get(index - 1).charAt(0)) != -1) {
					
					segmentedEq.add(index, "*");
				}
			}
			else if(segmentedEq.get(index).charAt(0) == 'x') {
				if(segmentedEq.get(index - 1).charAt(0) == ')' 
					|| isNum(segmentedEq.get(index - 1).charAt(0)) != -1) {
					
					segmentedEq.add(index, "*");
				}
			}
			/*----------------------------------------------7e
			 * Repeat the same actions as above for the current segmentedEq element being "(" and "x".
			 */
			
			//----------------------------------------------7f
			index--;
			foundElement = false;
			/*----------------------------------------------7f
			 * Decrement index by 1, and reset foundElement to false.
			 */
		}
	}
	//----------------------------------------------7
	
	
	/*
	 * This correctOrderOfOperation() method is the first of two methods executed in the constructor
	 * that inserts parentheses into segmentedEq to force the f(x) parser to execute order of operations
	 * correctly. This method deals with the three levels of precedence: (+ and -), (* and /), and (^).
	 * The second method, powerOperations(), deals with computing strings of exponents in the correct
	 * order.
	 *///----------------------------------------------8
	public void correctOrderOfOperations() {
		
		int preOpIndex = 0; //Stores the index in segmentedEq of the previously found operator.
						   //Used to mark where open parentheses should be placed if needed.
		
		int currentOpIndex = 0; //Stores the index in segmentedEq of the most recently found operator.
							   //Used to mark where close parentheses should be placed if needed.
		
		int bal = 0; //Stores the balance of open and close parentheses as (number of open) - (number of close).
					 //Used to ensure that there is an equal balance between open and close parentheses by the end.
		
		char currentOpChar = ' '; //Stores the char value in segmentedEq of the most recently found operator character.
								 //Used to compare precedence to the previously found operator character (preOpChar).
		
		char preOpChar = ' '; //Stores the char value in segmentedEq of the previously found operator character.
		 					 //Used to compare precedence to the most recently found operator character (currentOpChar).
		
		boolean isNegative = false; //Stores whether or not the minus "-" operator, if found, represents subtraction
								   //or a negative sign. Used to skip this symbol if it represents a negative sign.
		
		//----------------------------------------------8a
		while(index < segmentedEq.size()) {	
		/*----------------------------------------------8a
		 * Loops until index reaches the end of segmentedEq.
		 */
			isNegative = false; //Reset isNegative to false
			
			//----------------------------------------------8b
			if(segmentedEq.get(index).equals("-") && !segmentedEq.get(index - 1).equals(")") && !segmentedEq.get(index - 1).equals("x") 
					&& isNum(segmentedEq.get(index - 1).charAt(0)) == -1) {
				isNegative = true;
			}
			/*----------------------------------------------8b
			 * If the conditions of this if statement are met, it means that there is a minus "-" sign that
			 * is being used as a negative sign, so it sets isNegative to true.
			 */
			
			//----------------------------------------------8c
			if(getPrecedence(segmentedEq.get(index).charAt(0)) != -1 && isNegative == false) {
			/*----------------------------------------------12c
			 * Only consider inserting a parenthesis if the element in segmentedEq at index is an operator, and
			 * if it is not being used as a negative sign
			 */
				
				//----------------------------------------------8d
				preOpIndex = currentOpIndex;
				currentOpIndex = index;
				preOpChar = currentOpChar;
				currentOpChar = segmentedEq.get(index).charAt(0);
				/*----------------------------------------------8d
				 * Set the previous operator character to the current one, and the current one to the
				 * newly found operator. Do the same for their indexes.
				 */
				
				//----------------------------------------------12e
				if(preOpChar != ' ' && currentOpChar != ' ') {
				/*----------------------------------------------12e
				 * Only consider inserting a parenthesis if at least two operators have been found.
				 */
					
					//----------------------------------------------8f
					if(getPrecedence(preOpChar) > getPrecedence(currentOpChar) && bal > 0) {
						
						segmentedEq.add(index, ")");
						currentOpIndex++;
						index++;
						bal--;
						placedP = true;
					}
					/*----------------------------------------------8f
					 * If the previous operator has a higher precedence than the current one, insert
					 * a close parenthesis. Then, increase currentOpIndex by 1 because the current operator
					 * has been shifted forward by one index due to the parenthesis insertion. Increase index
					 * by 1 for the same reason. Decrease bal by 1 because there is now 1 more close
					 * parenthesis. Set placedP to true because it has placed at least 1 parenthesis.
					 */
					
					//----------------------------------------------8g
					else if(getPrecedence(preOpChar) < getPrecedence(currentOpChar)) {
						segmentedEq.add(preOpIndex + 1, "(");
						index++;
						currentOpIndex++;
						bal++;
						placedP = true;
					}
					/*----------------------------------------------8g
					 * If the previous operator has a lower precedence than the current one, insert
					 * an open parenthesis. Then, increase currentOpIndex by 1 because the current operator
					 * has been shifted forward by one index due to the parenthesis insertion. Increase index
					 * by 1 for the same reason. Increase bal by 1 because there is now 1 more open
					 * parenthesis. Set placedP to true because it has placed at least 1 parenthesis.
					 */
					
				}
			}
			
			//----------------------------------------------8h
			else if(isSymbol(segmentedEq.get(index).charAt(0)).equals("(") ) {
				index++;
				System.out.println("NESTED CALL");
				correctOrderOfOperations();
				
			}
			else if(isSymbol(segmentedEq.get(index).charAt(0)).equals(")")) {
				break;
			}
			/*----------------------------------------------8h
			 * If the element in segmentedEq at index is an open parenthesis, call this method recursively.
			 * If the element in segmentedEq at index is a close parenthesis, break from the loop, ending the method.
			 * This will result in each section in the equation enclosed by a set of parentheses being treated
			 * as its own equation by the nested method instance, and the same area being treated as one element by the 
			 * parent method instance, allowing correctOrderOfOperations() to work with an unlimited amount of nested
			 * parentheses present in the equation.
			 */
			
			index++;
		}
		
		//----------------------------------------------8i
		for(int i = 0; i < bal; i++) {
			segmentedEq.add(index, ")");
			index++;
		}
		/*----------------------------------------------8i
		 * This loop dumps the remaining close parentheses needed to maintain an equal number of open and close
		 * parentheses. 
		 */
	}
	//----------------------------------------------8
	
	/*
	 * This powerOperations method is the second method needed to ensure correct order of operations. This method
	 * looks for strings of exponents and inserts parentheses around them to ensure the parser computes them in
	 * the correct order. For example, the expression x^2^3^4^5 would be changed to x^(2^(3^(4^5))).
	 *///----------------------------------------------9
	public void powerOperations() {
		
		int preOpIndex = 0; //Stores the index in segmentedEq of the previously found operator.
						   //Used to mark where open parentheses should be placed if needed.

		int currentOpIndex = 0; //Stores the index in segmentedEq of the most recently found operator.
			   				   //Used to mark where close parentheses should be placed if needed.
		
		int bal = 0; //Stores the balance of open and close parentheses as (number of open) - (number of close).
		 			//Used to ensure that there is an equal balance between open and close parentheses by the end.
		boolean isNegative = false;
		//----------------------------------------------9a
		while(index < segmentedEq.size()) {	
		/*----------------------------------------------9a
		 * Loops until index reaches the end of segmentedEq.
		 */
			if(segmentedEq.get(index).equals("-") && !segmentedEq.get(index - 1).equals(")") && !segmentedEq.get(index - 1).equals("x") 
					&& isNum(segmentedEq.get(index - 1).charAt(0)) == -1) {
				isNegative = true;
			}
			//----------------------------------------------9b
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
			/*----------------------------------------------9b
			 * If the element in segmentedEq at index is an exponent "^" operator, set the index of the previous
			 * exponent to the index of the current one, and the index of the current one to index. 
			 * Next, if the index of the previous exponent (preOpIndex) is not equal to its default value of 0,
			 * insert an open parenthesis at preOpIndex. Then increase index and currentOpIndex by 1 to correct
			 * for the newly inserted parenthesis, and increase bal by 1 because there is now 1 more open parenthesis.
			 */
			
			//----------------------------------------------9c
			else if((getPrecedence(segmentedEq.get(index).charAt(0)) == 2 || getPrecedence(segmentedEq.get(index).charAt(0)) == 1)
					&& isNegative == false) {
				for(int i = 0; i < bal; i++) {
					segmentedEq.add(index, ")");
					index++;
					
				}
				bal = 0;
			}
			/*----------------------------------------------9c
			 * If the operator at index has a lower precedence than exponent (which means every other operator,
			 * dump the amount of close parentheses required to maintain an equal number of open and close parentheses.
			 * Afterwards, set bal to 0 because there are now an equal number of open and close parentheses
			 */
			
			//----------------------------------------------9d
			else if(isSymbol(segmentedEq.get(index).charAt(0)).equals("(") ) {
				index++;
				System.out.println("NESTED CALL");
				powerOperations();
				
			}
			else if(isSymbol(segmentedEq.get(index).charAt(0)).equals(")")) {
				break;
			}
			/*----------------------------------------------9d
			 * If the element in segmentedEq at index is an open parenthesis, call this method recursively.
			 * If the element in segmentedEq at index is a close parenthesis, break from the loop, ending the method.
			 * This will result in each section in the equation enclosed by a set of parentheses being treated
			 * as its own equation by the nested method instance, and the same area being treated as one element by the 
			 * parent method instance, allowing powerOperations() to work with an unlimited amount of nested
			 * parentheses present in the equation.
			 */
			
			index++;
			isNegative = false;
		}
		
		//----------------------------------------------9e
		for(int i = 0; i < bal; i++) {
			segmentedEq.add(index, ")");
			index++;
		}
		/*----------------------------------------------9e
		 * This loop dumps the amount of close parentheses required to maintain an equal number of open 
		 * and close parentheses. 
		 */
		System.out.println(segmentedEq);
	}
	//----------------------------------------------9
	
	/*
	 * This getPrecedence() method is used in correctOrderOfOperations() to compare the precedence between
	 * 2 operators. (+ an -) returns a precedence of 1. (* and /) return a precedence of 2. (^) returns a precedence
	 * of 3. A character that is not one of these 5 operators returns a precedence of -1.
	 *///----------------------------------------------10
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
	//----------------------------------------------10
	
	/*
	 * This f(x) method is the equation parser method. For every call with an x value passed as an argument, it returns
	 * the appropriate y value using the segmentedEq equation. It does this by calling itself recursively, returning itself
	 * differently depending on what element it encounters at each index. For example, encountering a number causes it to
	 * return f(number), encountering "sin" causes it to return f(Math.sin(x)), and encountering a "+ operator causes it 
	 * to return f(x) + x. index begins at the last element of segmentedEq, and decreases by 1 before each new pass through 
	 * the method until it reaches 0. When index = 0, it returns x.
	 *///----------------------------------------------11
	public double f(double x) {
		
		index--;
		
		//----------------------------------------------11a
		if(index == segmentedEq.size() - 1) {
			thisX = x;
		}
		/*----------------------------------------------11a
		 * In the first pass through this method, set the global thisX variable to x. thisX is used by f(x) when it
		 * encounters an "x" in segmentedEq, causing it to return f(thisX)
		 */
		
		//----------------------------------------------11b
		if(index >= 0) {
		/*----------------------------------------------11b
		 * Only continue passing through segmentedEq if index is greater than equal to 0 to prevent an
		 * OutOfBoundsException
		 */
			
			//----------------------------------------------11c
			if(segmentedEq.get(index).equals(")")) {
				return f(f(x));
			}
			else if(segmentedEq.get(index).equals("(")) {	
				return x;
			}
			/*----------------------------------------------11c
			 * If this method encounters an open parenthesis (in this case an open parenthesis is ")" instead of "(" because
			 * it is iterating backwards through the equation), it returns itself with itself as an argument as f(f(x)).
			 * If this method encounters a close parenthesis "(" it returns x. By doing this, this parser can handle an
			 * unlimited amount of nested parentheses in the equation. This feature is utilized by the two order of operations
			 * methods correctOrderOfOperations() and powerOperations(), allowing them to simply insert parentheses in the
			 * correct places force this parser to compute the equation in the proper order.
			 */
			
			else if(segmentedEq.get(index).equals("x")) {
				return f(thisX);
			}
			else if(segmentedEq.get(index).equals("+")) {
				return f(x) + x;
			}
			
			//----------------------------------------------11d
			else if(segmentedEq.get(index).equals("-")) {
				if(!segmentedEq.get(index - 1).equals(")") && !segmentedEq.get(index - 1).equals("x") && isNum(segmentedEq.get(index - 1).charAt(0)) == -1) {
					return f(0 - x);
				}
				else {
					return f(x) - x;
				}
			}
			/*----------------------------------------------11d
			 * When this method encounters a minus "-" sign, it needs to determine whether it is being used as a minus
			 * sign or negative sign. If it is a negative sign, it returns f(0 - x). Otherwise, it follows the way the
			 * other operators are handled, returning f(x) - x.
			 */
			
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
				return f(Math.sin(x));
			}
			else if(segmentedEq.get(index).equals("cos")) {
				return f(Math.cos(x));
			}
			else if(segmentedEq.get(index).equals("tan")) {
				return f(Math.tan(x));
			}
			else if(segmentedEq.get(index).equals("arcsin")) {
				return f(Math.asin(x));
			}
			else if(segmentedEq.get(index).equals("arccos")) {
				return f(Math.acos(x));
			}
			else if(segmentedEq.get(index).equals("arctan")) {
				return f(Math.atan(x));
			}
			else if(segmentedEq.get(index).equals("sqrt")) {
				return f(Math.sqrt(x));
			}
			else if(segmentedEq.get(index).equals("mod")) {
				return x % f(x);
			}
			else if(isNum(segmentedEq.get(index).charAt(0)) != -1) {
				double num = Double.parseDouble(segmentedEq.get(index));
				return f(num);
			}
			else {
				return (Double) null;
			}
		}
		
		//----------------------------------------------11e
		else {
			index = segmentedEq.size();
			return x;
		}
		/*----------------------------------------------11e
		 * If index is not greater than or equal 0, it resets the index to the end of segmentedEq
		 * and and returns x.
		 */
	}
	
	/*
	 * This containsOpError() is used specifically for detect any user errors in operator usage.
	 * For example, the expression "3 * 5 +" would trigger such an error in this method, because the usage
	 * of the "+" operator makes no sense.
	 *///----------------------------------------------12
	private boolean containsOpError() {
		
		//----------------------------------------------12a
		for(int i = 0; i < segmentedEq.size() - 1; i++) {
		/*----------------------------------------------12a
		 * This for loop iterates through the entirety of segmentedEq
		 */
			
			//----------------------------------------------12b
			if(isOperator(segmentedEq.get(i).charAt(0)) == true) {
			/*----------------------------------------------12b
			 * Only test for operator errors if the for loop index is at an operator in segmentedEq
			 */
				//----------------------------------------------12c
				 if(i == segmentedEq.size() - 2) {
					 System.out.println("Op Test 1 " + segmentedEq.get(i).charAt(0) + " " + isOperator(segmentedEq.get(i).charAt(0)));
					 new ErrorWindow("User Input Error", "Incorrect usage of\noperator " + "'" + segmentedEq.get(i) + "'");
					 return true;
				 }
				 /*----------------------------------------------12c
				  * If the operator is the last element in the equation, it is being used incorrectly. This
				  * will trigger an error. This if statement tests for the second to last element in segmentedEq
				  * because every equation is automatically surrounded with 1 set of parentheses, making them
				  * the first and last elements.
				  */
				 
				 //----------------------------------------------12d
				 else if(segmentedEq.get(i + 1).equals(")") || (isOperator(segmentedEq.get(i + 1).charAt(0)) == true && !segmentedEq.get(i + 1).equals("-"))) {
					 System.out.println("Op Test 2");
					 new ErrorWindow("User Input Error", "Incorrect usage of\noperator " + "'" + segmentedEq.get(i) + "'");
					 return true;
				 }
				 /*----------------------------------------------12d
				  * If the next element in the equation is a close parenthesis, or the next element in the equation
				  * is another operator except for a minus "-" sign, it means the current operator is being used incorrectly.
				  * This triggers an error. The reason it allows for minus "-" signs to be used one after the other in a
				  * string is to allow for the subtraction of a negative number, such as in the expression x--3. This would be 
				  * computed the same as x+3 in the f(x) parser. Because of this logic, would be able to support any number
				  * of minus "-" signs, computing it as addition with an even number of minus signs, and computing it as
				  * subtraction with an odd number of minus signs. The expression x-----------3 would be computed as x-3
				  * because it has an odd number of minus signs.
				  */
				 
				 //----------------------------------------------12e
				 if(i == 1) {
					 if(!segmentedEq.get(i).equals("-")) {
						 System.out.println("Op Test 3");
						 new ErrorWindow("User Input Error", "Incorrect usage of\noperator " + "'" + segmentedEq.get(i) + "'");
						 return true;
					 }
				 }
				 /*----------------------------------------------12e
				  * If this operator is locate at index 1 in segmentedEq, and it is not a minus sign (a minus "-" sign
				  * at this index could be a correctly-used negative sign), it means that this operator is being used
				  * incorrectly. This triggers an error.
				  */
				 
				 //----------------------------------------------12f
				 else if((segmentedEq.get(i - 1).equals("(") || isOperator(segmentedEq.get(i - 1).charAt(0)) == true) && !segmentedEq.get(i).equals("-")) {
					 System.out.println("Op Test 4");
					 new ErrorWindow("User Input Error", "Incorrect usage of\noperator " + "'" + segmentedEq.get(i) + "'");
					 return true;
				 }
				 /*----------------------------------------------12f
				  * If the element in the equation before the current operator is an open parenthesis or another operator, and
				  * that other operator is not a minus sign, it means that this current operator is being used incorrectly.
				  * This triggers an error.
				  */
			}
		}
		return false;
	}
	
	/*
	 * This foundElement() method is used in prepareString() to detect supported multi-character functions
	 * listed in the array mathElements. Returns true if it successfully detects the String passed to it
	 * in in equationString, and returns false if it does not.
	 *///----------------------------------------------13
	private boolean foundElement(String s) {
		if(index < equationString.length() - s.length() - 1) {
			if(equationString.substring(index, index + s.length()).equals(s)) {
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
	//----------------------------------------------13
	
	/*
	 * This method getNum() is used in prepareString() to detect numerical values. It can pull values entered
	 * by the user as both integer and double values, and converts integers to doubles.
	 *///----------------------------------------------14
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
	//----------------------------------------------14
	
	/*
	 * This method isNum() is used in prepareString() to test whether a specific character is an integer.
	 * If it returns true, meaning that the character is an integer, getNum() is called.
	 *///----------------------------------------------15
	private int isNum(char c) {
		for(int i = 0; i < nums.length; i++) {
			if(nums[i] == c) {
				return Character.getNumericValue(nums[i]);
			}
		}
		return -1;
	}
	//----------------------------------------------15
	
	/*
	 * This isSymbol() method is used by prepareString() to determine whether or not a specific
	 * character is a supported symbol. Returns true if the character is a supported symbol, and false if it is not. 
	 * All supported symbols are stored in the array symbols.
	 *///----------------------------------------------16
	private String isSymbol(char c) {
		
		for(int i = 0; i < symbols.length; i++) {
			if(symbols[i] == c) {
				return "" + symbols[i];
			}
		}
		return "";
	}
	//----------------------------------------------16
	
	/*
	 * This method isOperatro() is used by containsOpError() to determine whether or not a character
	 * is an operator. Returns true if the character is a supported operator, and false if it is not.
	 * All supported operators are stored in the array operators.
	 */
	private boolean isOperator(char c) {
		
		for(int i = 0; i < operators.length; i++) {
			if(operators[i] == c) {
				return true;
			}
		}
		return false;
	}
}
