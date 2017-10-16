import java.util.ArrayList;

public class Line {
	
	ArrayList<Point> points = new ArrayList();
	int width;
	
	double xMin = -10.0;
	double xMax = 10.0;
	double yMin = -10.0;
	double yMax = 10.0;
	
	Equation e = new Equation("");
	
	//Equation e = new Equation("(x / (10 * 1.5)) * (x / (10 * 1.5))");
	//Equation e = new Equation("(x * x) / (x / 10)");
	public Line(int width) {
		this.width = width;
		generatePoints();
	}
	//
	public void generatePoints() {
		
		for(double i = 0 - ((double)width / 2.0); i <= (double)width / 2.0; i += 0.01) {
			//points.add(new Point(i, (100.0 * Math.sin(((double)i) / 40.0)) ));	
			//points.add(new Point((double)i, ((-0.5 * (i - 400)) + 300) ));
			//points.add(new Point((double)i, (Math.pow(i / 10.0,  2))));
			//points.add(new Point((double)i, 1.0 / (0.001 * (double)i)));
			points.add(new Point(i, e.f(i)));
		}
	}
	
	public ArrayList<Point> getPoints() {
		return points;
	}
}
