import java.awt.Color;

public class MyColor {
	/*
	 * CLASS OVERVIEW
	 * 
	 * This class is used for the insane color mode, storing and computing the next
	 * color values for each GUI element in the Window class after every timer trigger.
	 * RGB values are computed based on their respective sin function. 
	 * 
	 * R = 127 * sin((x + (4 * offset)) / (500 / PI)) + 128
	 * G = 127 * sin((x + 333.33 + (4 * offset)) / (500 / PI)) + 128
	 * B = 127 * sin((x + 666.66 + (4 * offset)) / (500 / PI)) + 128
	 * 
	 * These three graphs each have a minimum of 1, a maximum of 255, and a period of 1000.
	 * They are evenly spaced throughout the plane, allowing for a smooth cycle of colors.
	 * x iterates from 0 to 1000, then resets to 0 and repeats.
	 */
	
	int r; //Stores the red value from 0 - 255
	int g; //Stores the green value from 0 - 255
	int b; //Stores the blue value from 0 - 255
	int colorCycle; //Stores which stage of the color cycling process this object is on
	int colorSpeed; //This value determines how fast the color cycling is. A higher value will result in faster cycling.
	int cOffset; //This value determines the starting RGB value of this MyColor object
	double x = 0.0; //this value stores the current x value in the r, g, and b functions
	
	/*
	 * This constructor initializes the object using RGB values passed to it.
	 *///----------------------------------------------1
	public MyColor(int r, int g, int b, int cSpeed) {
		this.r = r;
		this.g = g;
		this.b = b;
		cOffset = 0;
		colorSpeed = cSpeed;
		colorCycle = 0;
		prepareColor();
	}
	//----------------------------------------------1
	
	/*
	 * This constructor initializes the object using a Color object passed to it.
	 *///----------------------------------------------2
	public MyColor(Color c, int cSpeed) {
		r = c.getRed();
		g = c.getGreen();
		b = c.getBlue();
		cOffset = 0;
		colorSpeed = cSpeed;
		colorCycle = 0;
		prepareColor();
	}
	//----------------------------------------------2
	
	/*
	 * This increment() method is called from the Window class after each timer trigger.
	 * It increments the RGB values of this object based on the sin functions explained
	 * in the CLASS OVERVIEW above.
	 *///----------------------------------------------3
	public void increment() {
		/*
		 * This increments x, and resets it to 0 if it passes 1000
		 *///----------------------------------------------3a
		x += (double)colorSpeed;
		if(x > 1000) {
			x = 0;
		}
		//----------------------------------------------3a
		
		/*
		 * Here, the RGB values are being set using the sin functions explained in
		 * the CLASS OVERVIEW above.
		 *///----------------------------------------------3b
		r = (int)(127.0 * Math.sin((x + (4.0 * (double)cOffset)) / ((500.0) / Math.PI)) + 128.0);;
		g = (int)(127.0 * Math.sin((x + 333.33 + (4.0 * (double)cOffset)) / ((500.0) / Math.PI)) + 128.0);
		b = (int)(127.0 * Math.sin((x + 666.66 + (4.0 * (double)cOffset)) / ((500.0) / Math.PI)) + 128.0);
		//----------------------------------------------3b
		
	}
	//----------------------------------------------3
	
	/*
	 * This method prepareColor() sets this object's starting RGB values to the average of 
	 * the RGB values of the color passed to the constructor. This causes the same colors
	 * to be cycled through no matter what color was passed to the constructor.
	 *///----------------------------------------------4
	public void prepareColor() {
		cOffset = (r + g + b) / 3;
		g = 255 - cOffset;
		r = cOffset;
		b = 0;
	}
	//----------------------------------------------4
	
	/*
	 * This method getColor() returns the current color value of this object.
	 *///----------------------------------------------5
	public Color getColor() {
		return new Color(r, g, b);
	}
	//----------------------------------------------5
}