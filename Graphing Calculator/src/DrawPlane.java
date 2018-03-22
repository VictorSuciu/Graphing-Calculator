import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

public class DrawPlane {
	
	int insetsTop;
	int gridCount = 0;
	double xMax = Window.xMax;
	double xMin = Window.xMin;
	double yMax = Window.yMax;
	double yMin = Window.yMin;
	
	double xNestedGridSize;
	double yNestedGridSize;
	
	int width = Window.width;
	int height = Window.height;
	
	Graphics2D g2;
	
	Equation equation;
	
	Color axisColor = Color.decode("#AAAAAA");
	Color nestedGridColor = Color.decode("#393939");
	Color gridColor = Color.decode("#515151");
	Color lineColor = Color.decode("#FBFBFB"); 
	
	public DrawPlane(Equation equation, Graphics2D g2, int insetsTop, double xGrid, double yGrid) {
		this.g2 = g2;
		this.equation = equation;
		System.out.println("THIS INSETSTOP = " + insetsTop);
		this.insetsTop = insetsTop;
		xNestedGridSize = xGrid;
		yNestedGridSize = yGrid;
		drawPlane();
	}
	public void drawPlane() {
		
		
		Line2D yAxis = new Line2D.Double(((double)width / 2.0) - (  ((xMax + xMin) / 2.0) * (((double)width / 2.0) / ((xMax - xMin) / 2.0))  ), 
										insetsTop + 0, 
										((double)width / 2.0) - (  ((xMax + xMin) / 2.0) * (((double)width / 2.0) / ((xMax - xMin) / 2.0))  ), 
										insetsTop + height);
		Line2D xAxis = new Line2D.Double(0, 
										insetsTop + ((double)height / 2.0) + (  ((yMax + yMin) / 2.0) * (((double)height / 2.0) / ((yMax - yMin) / 2.0))  ), 
										width, 
										insetsTop + ((double)height / 2.0) + (  ((yMax + yMin) / 2.0) * (((double)height / 2.0) / ((yMax - yMin) / 2.0))  ));
		
		Line2D gridLine = new Line2D.Double(1.0, 1.0, 1.0, 1.0);
		double gridSpacing = 40.0;
		double index = 1.0;
		double gridLineX = (index * ((double)width / (gridSpacing / xNestedGridSize)));
		double gridLineY = (index * ((double)height / (gridSpacing / xNestedGridSize)));
		double startingPoint = 0.0;
		double shiftGridX = (((double)width / (xMax - xMin)) * xNestedGridSize / 2.0);
		double shiftGridY = (((double)height / (yMax - yMin)) * yNestedGridSize / 2.0);
		
		if(xMax <= 0 || xMax * xMin < 0) {
			if(xMax < 0) {
				startingPoint = ((yAxis.getX1() - (double)width)) - (((yAxis.getX1() - ((double)width)) % (4.0 * shiftGridX)));
			}
			gridLine = new Line2D.Double(yAxis.getX1() - (index * (((double)width / (xMax - xMin)) * xNestedGridSize / 2.0)) - startingPoint,
					yAxis.getY1(), 
					yAxis.getX2() - (index * (((double)width / (xMax - xMin)) * xNestedGridSize / 2.0)) - startingPoint,
					yAxis.getY2());
			
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
		}
		index = 1.0;
		gridCount = 0;
		startingPoint = 0.0;
		if(xMin >= 0 || xMax * xMin < 0) {
			if(xMin > 0) {
				startingPoint = (0 - yAxis.getX1()) - (((0 - yAxis.getX1()) % (4.0 * shiftGridX)));
			}
			gridLine = new Line2D.Double(yAxis.getX1() + (index * (((double)width / (xMax - xMin)) * xNestedGridSize / 2.0)) + startingPoint,
					yAxis.getY1(), 
					yAxis.getX2() + (index * (((double)width / (xMax - xMin)) * xNestedGridSize / 2.0)) + startingPoint,
					yAxis.getY2());
			
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
		}
		//YYYYYYYYYYYYYYYYYYYYYYYYYYY
		
		index = 1.0;
		gridCount = 0;
		startingPoint = 0.0;
		if(yMin >= 0 || yMax * yMin < 0) {
			if(yMin > 0) {
				startingPoint = (xAxis.getY1() - (double)height) - (((xAxis.getY1() - (double)height)) % (4.0 * shiftGridY)) - (4.0 * shiftGridY);
			}
			gridLine = new Line2D.Double(xAxis.getX1(), 
					xAxis.getY1() - ((index * ((double)height / (yMax - yMin)) * yNestedGridSize / 2.0)) - startingPoint,
					xAxis.getX2(), 
					xAxis.getY2() - ((index * ((double)height / (yMax - yMin)) * yNestedGridSize / 2.0)) - startingPoint);
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
		}
		index = 1.0;
		gridCount = 0;
		startingPoint = 0.0;
		if(yMax <= 0 || yMax * yMin < 0) {
			if(yMax < 0) {
				startingPoint = (0 - xAxis.getY1()) - (((0 - xAxis.getY1())) % (4.0 * shiftGridY));
			}
			gridLine = new Line2D.Double(xAxis.getX1(), 
					xAxis.getY1() + ((index * ((double)height / (yMax - yMin)) * yNestedGridSize / 2.0)) + startingPoint, 
					xAxis.getX2(), 
					xAxis.getY2() + ((index * ((double)height / (yMax - yMin)) * yNestedGridSize / 2.0)) + startingPoint);		
			
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
		}
		
		g2.setColor(axisColor);
		if(yAxis.getX1() <= width) {
			g2.draw(yAxis);
		}
		if(xAxis.getY1() <= height + insetsTop) {
			g2.draw(xAxis);
		}
		
		
		double y1 = 0;
		double y2 = 0;
		double x1 = 0;
		double x2 = 0;
		
		double translateX = ((double)width / 2) - (  ((xMax + xMin) / 2.0) * (((double)width / 2.0) / ((xMax - xMin) / 2.0))  );
		double translateY = ((double)height / 2.0) + (  ((yMax + yMin) / 2.0) * (((double)height / 2.0) / ((yMax - yMin) / 2.0))  );
		
		Line2D temp;
		g2.setColor(lineColor);
		
		
		for(int i = 0; i < equation.getPoints().size() - 1; i++) {
			
			y1 = equation.getPoints().get(i).getY();
			y2 = equation.getPoints().get(i + 1).getY();
			x1 = equation.getPoints().get(i).getX();
			x2 = equation.getPoints().get(i + 1).getX();
			
			//y1 + translateY  - (y2 * 2) <= height && y1 + translateY - (y2 * 2)  >= 0
			
			if(y1 + translateY  - (y2 * 2) <= height && y1 + translateY - (y2 * 2)  >= 0) {
				if(i == 0) {
					double test = equation.getPoints().get(0).getX() + translateX;
					double test2 = xMax + xMin;
					System.out.println("\nX1 = " + test + "\nMaxMin = " + xMax + "/" + xMin + "\n");
				}
				
				/*
				temp = new Line2D.Double(x1 + (double)width / 2.0, 
										 y1 + (2.0 * (((double)height / 2.0) - y1)), 
									     x2 + (double)width / 2.0, 
									     y2 + (2.0 * (((double)height / 2.0) - y2)));
				*/
				temp = new Line2D.Double(x2 + translateX, 
										 y2 + translateY - (y2 * 2) + insetsTop, 
									     x1 + translateX, 
									     y1 + translateY - (y1 * 2) + insetsTop);	
				g2.draw(temp);
			}
			
		}
	}
}
