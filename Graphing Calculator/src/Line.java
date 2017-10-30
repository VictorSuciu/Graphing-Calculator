import java.util.ArrayList;

public class Line {
	
	ArrayList<Point> points = new ArrayList();
	int width;
	int height; 
	
	double xMin = -1.0;
	double xMax = 1.0;
	double yMin = -1.0;
	double yMax = 1.0;
	
	Equation e = new Equation("x^2");
	
	//Equation e = new Equation("(x / (10 * 1.5)) * (x / (10 * 1.5))");
	//Equation e = new Equation("(x * x) / (x / 10)");
	public Line(int width, int height) {
		this.width = width;
		this.height = height;
		generatePoints();
	}
	//
	public void generatePoints() {
		
		for(double i = 0 - ((double)width / 2.0); i <= (double)width / 2.0; i += 0.01) {
			
			points.add(new Point(i, (e.f(i / ((width / 2) / ((xMax - xMin) / 2)) ) * ((height / 2) / ((yMax - yMin) / 2)) ) - (((yMax + yMin) / 2) * (height / (yMax - yMin)))          ));
		}
	}
	
	public ArrayList<Point> getPoints() {
		return points;
	}
}
