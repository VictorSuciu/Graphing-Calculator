import java.util.ArrayList;

public class Equation extends Window{
	
	String equationString;
	
	ArrayList<String> steps = new ArrayList();
	ArrayList<String> equation = new ArrayList();
	
	String validItems = "0 1 2 3 4 5 6 7 8 9 + - * / ^ sin cos tan ( ) .";
	// 5 + (x / 2)
	
	public Equation(String s) {
		equationString = s;
	}
	
	public Point getPoint(double x) {
		
		return new Point(0, 0);
	}
	
	private void prepareString() {
		for(int i = 0; i < equationString.length(); i++) {
			
		}
	}
	private void parseEquation() {
		
	}
}
