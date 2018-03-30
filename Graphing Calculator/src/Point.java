
public class Point {
	
	/*
	 * CLASS OVERVIEW
	 * 
	 * This Point class stores a point as a pair of x and y values.
	 * Used in Equation.generatePoints() to store a list of points
	 * generated using the user's equation.
	 */
	
	double x;
	double y;
	
	public Point(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	public double getY() {
		return y;
	}
}
