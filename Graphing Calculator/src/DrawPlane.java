import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class DrawPlane {
	
	int insetsTop; //Stores the window's top bar height
	int gridCount = 0; //Counts the number of times a grid line has been drawn
	
	//These store the plane's x/y ranges
	double xMax = Window.xMax;
	double xMin = Window.xMin;
	double yMax = Window.yMax;
	double yMin = Window.yMin;
	
	//the x/yNestedGridSizes determine the size of the grid in order to 
	//properly resize the grid at any level of zoom
	double xNestedGridSize;
	double yNestedGridSize;
	
	//These store the width and height of the plane window in pixels
	int width = Window.width;
	int height = Window.height;
	
	Graphics2D g2;
	
	Equation equation; //The user's equation to be drawn
	
	//All necessary colors
	Color axisColor = Color.decode("#AAAAAA");
	Color nestedGridColor = Color.decode("#393939");
	Color gridColor = Color.decode("#515151");
	Color lineColor = Color.decode("#FBFBFB"); 
	
	/*
	 * This is the constructor. Its purpose is to define required variables
	 * and to call the drawPlane() method which draws the equation line
	 * and the grid.
	 *///----------------------------------------------1
	public DrawPlane(Equation equation, Graphics2D g2, int insetsTop, double xGrid, double yGrid) {
		this.g2 = g2;
		this.equation = equation;
		System.out.println("THIS INSETSTOP = " + insetsTop);
		this.insetsTop = insetsTop;
		xNestedGridSize = xGrid;
		yNestedGridSize = yGrid;
		drawPlane();
	}
	//----------------------------------------------1
	
	/*
	 * This drawPlane() method draws the equation line as well as the grid.
	 *///----------------------------------------------2
	public void drawPlane() {
		
		/*
		 * The Line2Ds xAxis and yAxis are the x and y axes on the plane. Their position is
		 * computed based on the pixel size of the plane window as well as the x/y ranges.
		 *///----------------------------------------------2a
		Line2D yAxis = new Line2D.Double(((double)width / 2.0) - (  ((xMax + xMin) / 2.0) * (((double)width / 2.0) / ((xMax - xMin) / 2.0))  ), 
										insetsTop + 0, 
										((double)width / 2.0) - (  ((xMax + xMin) / 2.0) * (((double)width / 2.0) / ((xMax - xMin) / 2.0))  ), 
										insetsTop + height);
		Line2D xAxis = new Line2D.Double(0, 
										insetsTop + ((double)height / 2.0) + (  ((yMax + yMin) / 2.0) * (((double)height / 2.0) / ((yMax - yMin) / 2.0))  ), 
										width, 
										insetsTop + ((double)height / 2.0) + (  ((yMax + yMin) / 2.0) * (((double)height / 2.0) / ((yMax - yMin) / 2.0))  ));
		//----------------------------------------------2a
		
		
		Line2D gridLine = new Line2D.Double(1.0, 1.0, 1.0, 1.0); //This one Line2D object is reused for every grid line
		
		/*
		 * Every variable used for drawing the grid.
		 *///----------------------------------------------2b
		
		double index = 1.0; //Counts how many grid lines have been drawn, which is used as a multiplier for their
						    //distance from an axis
		
		double startingPoint = 0.0; //If an axis is off screen, this stores the location where the grid lines should start
								   //being drawn, otherwise it would attempt to draw hundreds of grid lines until it reaches
								   //the plane window.
		
		//These variables store a number required to set the position of the grid
		double shiftGridX = (((double)width / (xMax - xMin)) * xNestedGridSize / 2.0);
		double shiftGridY = (((double)height / (yMax - yMin)) * yNestedGridSize / 2.0);
		
		//----------------------------------------------2b
		
		/*
		 * This section draws the vertical grid lines that are to the left of the y axis. This will execute
		 * if the y axis is on screen, or off screen to the right.
		 *///----------------------------------------------2c
		if(xMax <= 0 || xMax * xMin < 0) {
			
			/*
			 * If the y axis is off screen to the right, this computes the starting point where
			 * grid lines should start being drawn, to prevent hundreds of lines from being created
			 * starting from the y axis until they reach the plane window.
			 *///----------------------------------------------2c.1
			if(xMax < 0) {
				startingPoint = ((yAxis.getX1() - (double)width)) - (((yAxis.getX1() - ((double)width)) % (4.0 * shiftGridX)));
			}
			//----------------------------------------------2c.1
			
			/*
			 * A new grid line being created, setting its position using all necessary information.
			 *///----------------------------------------------2c.2
			gridLine = new Line2D.Double(yAxis.getX1() - (index * (((double)width / (xMax - xMin)) * xNestedGridSize / 2.0)) - startingPoint,
					yAxis.getY1(), 
					yAxis.getX2() - (index * (((double)width / (xMax - xMin)) * xNestedGridSize / 2.0)) - startingPoint,
					yAxis.getY2());
			//----------------------------------------------2c.2
			
			/*
			 * Continues drawing grid lines going left until it hits the left edge of the plane window.
			 *///----------------------------------------------2c.3
			while(gridLine.getX1() >= 0) {
				if(gridCount % 4 == 0) {
					g2.setColor(gridColor);
				}
				else {
					g2.setColor(nestedGridColor);
				}
				if(gridLine.getX1() <= width) {
					g2.draw(gridLine);
				}
				
				gridLine = new Line2D.Double(yAxis.getX1() - (index * (((double)width / (xMax - xMin)) * xNestedGridSize / 2.0)) - startingPoint,
						yAxis.getY1(), 
						yAxis.getX2() - (index * (((double)width / (xMax - xMin)) * xNestedGridSize / 2.0)) - startingPoint,
						yAxis.getY2());
				
				index += 1.0;
				gridCount++;
			}
			//----------------------------------------------2c.3
		}
		//----------------------------------------------2c
		
		//Reset variables for next set of grid lines
		index = 1.0;
		gridCount = 0;
		startingPoint = 0.0;
		
		/*
		 * This section draws the vertical grid lines that are to the right of the y axis. This will execute
		 * if the y axis is on screen, or off screen to the left.
		 *///----------------------------------------------2d
		if(xMin >= 0 || xMax * xMin < 0) {
			
			/*
			 * If the y axis is off screen to the left, this computes the starting point where
			 * grid lines should start being drawn, to prevent hundreds of lines from being created
			 * starting from the y axis until they reach the plane window.
			 *///----------------------------------------------2d.1
			if(xMin > 0) {
				startingPoint = (0 - yAxis.getX1()) - (((0 - yAxis.getX1()) % (4.0 * shiftGridX)));
			}
			//----------------------------------------------2d.1
			
			/*
			 * A new grid line being created, setting its position using all necessary information.
			 *///----------------------------------------------2d.2
			gridLine = new Line2D.Double(yAxis.getX1() + (index * (((double)width / (xMax - xMin)) * xNestedGridSize / 2.0)) + startingPoint,
					yAxis.getY1(), 
					yAxis.getX2() + (index * (((double)width / (xMax - xMin)) * xNestedGridSize / 2.0)) + startingPoint,
					yAxis.getY2());
			//----------------------------------------------2d.2
			
			/*
			 * Continues drawing grid lines going right until it hits the right edge of the plane window.
			 *///----------------------------------------------2d.3
			while(gridLine.getX1() <= (double)width) {
				if(gridCount % 4 == 0) {
					g2.setColor(gridColor);
				}
				else {
					g2.setColor(nestedGridColor);
				}
				if(gridLine.getX1() >= 0) {
					g2.draw(gridLine);
				}
				gridLine = new Line2D.Double(yAxis.getX1() + (index * (((double)width / (xMax - xMin)) * xNestedGridSize / 2.0)) + startingPoint,
						yAxis.getY1(), 
						yAxis.getX2() + (index * (((double)width / (xMax - xMin)) * xNestedGridSize / 2.0)) + startingPoint,
						yAxis.getY2());
				
				index += 1.0;
				gridCount++;
				
			}
			//----------------------------------------------2d.3
		}
		//----------------------------------------------2d
		
		//Reset variables for next set of grid lines
		index = 1.0;
		gridCount = 0;
		startingPoint = 0.0;
		
		/*
		 * This section draws the horizontal grid lines that are above the x axis. This will execute
		 * if the x axis is on screen, or below the screen.
		 *///----------------------------------------------2e
		if(yMin >= 0 || yMax * yMin < 0) {
			
			/*
			 * If the x axis is below the screen, this computes the starting point where
			 * grid lines should start being drawn, to prevent hundreds of lines from being created
			 * starting from the y axis until they reach the plane window.
			 *///----------------------------------------------2e.1
			if(yMin > 0) {
				startingPoint = (xAxis.getY1() - (double)height) - (((xAxis.getY1() - (double)height)) % (4.0 * shiftGridY)) - (4.0 * shiftGridY);
			}
			//----------------------------------------------2e.1
			
			/*
			 * A new grid line being created, setting its position using all necessary information.
			 *///----------------------------------------------2e.2
			gridLine = new Line2D.Double(xAxis.getX1(), 
					xAxis.getY1() - ((index * ((double)height / (yMax - yMin)) * yNestedGridSize / 2.0)) - startingPoint,
					xAxis.getX2(), 
					xAxis.getY2() - ((index * ((double)height / (yMax - yMin)) * yNestedGridSize / 2.0)) - startingPoint);
			//----------------------------------------------2e.2
			
			/*
			 * Continues drawing grid lines going up until it hits the top edge of the plane window.
			 *///----------------------------------------------2e.3
			while(gridLine.getY1() >= 0 + insetsTop) {
				if(gridCount % 4 == 0) {
					g2.setColor(gridColor);
				}
				else {
					g2.setColor(nestedGridColor);
				}
				if(gridLine.getY1() <= height + insetsTop) {
					g2.draw(gridLine);
				}
				gridLine = new Line2D.Double(xAxis.getX1(), 
						xAxis.getY1() - ((index * ((double)height / (yMax - yMin)) * yNestedGridSize / 2.0)) - startingPoint,
						xAxis.getX2(), 
						xAxis.getY2() - ((index * ((double)height / (yMax - yMin)) * yNestedGridSize / 2.0)) - startingPoint);				
				index += 1.0;
				gridCount++;
			} 
			//----------------------------------------------2e.3
		}
		//----------------------------------------------2e
		
		//Reset variables for next set of grid lines
		index = 1.0;
		gridCount = 0;
		startingPoint = 0.0;
		
		/*
		 * This section draws the horizontal grid lines that are above the x axis. This will execute
		 * if the x axis is on screen, or below the screen.
		 *///----------------------------------------------2f
		if(yMax <= 0 || yMax * yMin < 0) {
			
			/*
			 * If the x axis is below the screen, this computes the starting point where
			 * grid lines should start being drawn, to prevent hundreds of lines from being created
			 * starting from the y axis until they reach the plane window.
			 *///----------------------------------------------2f.1
			if(yMax < 0) {
				startingPoint = (0 - xAxis.getY1()) - (((0 - xAxis.getY1())) % (4.0 * shiftGridY));
			}
			//----------------------------------------------2f.1
			
			/*
			 * A new grid line being created, setting its position using all necessary information.
			 *///----------------------------------------------2f.2
			gridLine = new Line2D.Double(xAxis.getX1(), 
					xAxis.getY1() + ((index * ((double)height / (yMax - yMin)) * yNestedGridSize / 2.0)) + startingPoint, 
					xAxis.getX2(), 
					xAxis.getY2() + ((index * ((double)height / (yMax - yMin)) * yNestedGridSize / 2.0)) + startingPoint);		
			//----------------------------------------------2f.2
			
			/*
			 * Continues drawing grid lines going up until it hits the top edge of the plane window.
			 *///----------------------------------------------2f.3
			while(gridLine.getY1() <= (double)height + insetsTop) {
				if(gridCount % 4 == 0) {
					g2.setColor(gridColor);
				}
				else {
					g2.setColor(nestedGridColor);
				}
				if(gridLine.getY1() >= insetsTop) {
					g2.draw(gridLine);
				}
				gridLine = new Line2D.Double(xAxis.getX1(),
						 xAxis.getY1() + ((index * ((double)height / (yMax - yMin)) * yNestedGridSize / 2.0)) + startingPoint, 
						 xAxis.getX2(), 
						 xAxis.getY2() + ((index * ((double)height / (yMax - yMin)) * yNestedGridSize / 2.0)) + startingPoint);				
				index += 1.0;
				gridCount++;
				
			}
			//----------------------------------------------2f.3
		}
		//----------------------------------------------2f
		
		/*
		 * Only draws an axis if its coordinates would place it within
		 * the plane window.
		 *///----------------------------------------------2g
		g2.setColor(axisColor);
		if(yAxis.getX1() <= width) {
			g2.draw(yAxis);
		}
		if(xAxis.getY1() <= height + insetsTop) {
			g2.draw(xAxis);
		}
		//----------------------------------------------2g
		
		//These variables store the x/y values of the current equation point and the next
		double y1 = 0;
		double y2 = 0;
		double x1 = 0;
		double x2 = 0;
		
		//These variables store the values that translate and stretch the equation line, fitting it to the plane window
		double translateX = ((double)width / 2) - (  ((xMax + xMin) / 2.0) * (((double)width / 2.0) / ((xMax - xMin) / 2.0))  );
		double translateY = ((double)height / 2.0) + (  ((yMax + yMin) / 2.0) * (((double)height / 2.0) / ((yMax - yMin) / 2.0))  );
		
		Line2D temp; //This Line2D object is reused for every line connecting two equation points
		g2.setColor(lineColor);
		
		/*
		 * This for loop iterates through the entire equation point list and draws a line segment from each
		 * point to the one after it. Because of the high resolution of the equation point list (thousands
		 * of points very close together), the resulting chain of connected line segments looks like a solid
		 * and smooth line. This results in the completed graph.
		 *///----------------------------------------------2h
		for(int i = 0; i < equation.getPoints().size() - 1; i++) {
			
			y1 = equation.getPoints().get(i).getY();
			y2 = equation.getPoints().get(i + 1).getY();
			x1 = equation.getPoints().get(i).getX();
			x2 = equation.getPoints().get(i + 1).getX();
			
			/*
			 * If both consecutive points in the equation points list are within the plane window, it initializes
			 * a line connecting them nd draws that line. This means that if one or both points are beyond the
			 * plane window, a line is not created or drawn, preventing it from drawing vertical asymptotes such
			 * as in the equation "y = 1/x".
			 *///----------------------------------------------2h.1
			if(y1 + translateY  - (y2 * 2) <= height && y1 + translateY - (y2 * 2)  >= 0) {
				temp = new Line2D.Double(x2 + translateX, 
										 y2 + translateY - (y2 * 2) + insetsTop, 
									     x1 + translateX, 
									     y1 + translateY - (y1 * 2) + insetsTop);	
				g2.draw(temp);
			}
			//----------------------------------------------2h.1
		}
		//----------------------------------------------2h
	}
	//----------------------------------------------2
}
