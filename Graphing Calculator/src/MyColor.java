import java.awt.Color;

public class MyColor {
	int r;
	int g;
	int b;
	int colorCycle;
	int colorSpeed;
	int cOffset;
	double x = 0.0;
	boolean oldIncrement = false;
	
	public MyColor(int r, int g, int b, int cSpeed) {
		this.r = r;
		this.g = g;
		this.b = b;
		cOffset = 0;
		colorSpeed = cSpeed;
		colorCycle = 0;
		prepareColor();
	}
	public MyColor(Color c, int cSpeed) {
		r = c.getRed();
		g = c.getGreen();
		b = c.getBlue();
		cOffset = 0;
		colorSpeed = cSpeed;
		colorCycle = 0;
		prepareColor();
	}
	public void oldIncrement() {
		if((r == 255 && g == 0 && b == 0) || colorCycle == 0) {
			colorCycle = 0;
			
			if(r > 0 && g < 255) {
				r-= colorSpeed;
				g+= colorSpeed;
			}
			else {
				colorCycle = 1;
			}
		}
		else if((r == 0 && g == 255 && b == 0) || colorCycle == 1) {
			colorCycle = 1;
			
			if(g > 0 && b < 255) {
				g-= colorSpeed;
				b+= colorSpeed;
			}
			else {
				colorCycle = 2;
			}
		}
		else if((r == 0 && g == 0 && b == 255) || colorCycle == 2) {
			colorCycle = 2;
			
			if(b > 0 && r < 255) {
				b-= colorSpeed;
				r+= colorSpeed;
			}
			else {
				colorCycle = 0;
			}
		}
		if(r < 0) {
			r = 0;
		}
		if(g < 0) {
			g = 0;
		}
		if(b < 0) {
			b = 0;
		}
		if(r > 255) {
			r = 255;
		}
		if(g > 255) {
			g = 255;
		}
		if(b > 255) {
			b = 255;
		}
	}
	public void increment() {
		if(oldIncrement == true) {
			oldIncrement();
		}
		else {
			x += (double)colorSpeed;
			if(x > 1000) {
				x = 0;
			}
			
			r = (int)(127.0 * Math.sin((x + (4.0 * (double)cOffset)) / ((500.0) / Math.PI)) + 128.0);;
			g = (int)(127.0 * Math.sin((x + 333.33 + (4.0 * (double)cOffset)) / ((500.0) / Math.PI)) + 128.0);
			b = (int)(127.0 * Math.sin((x + 666.66 + (4.0 * (double)cOffset)) / ((500.0) / Math.PI)) + 128.0);
			/*
			r = (int)((255.0 / Math.PI) * Math.asin(Math.sin((x - (4.0 * (double)cOffset)) / (500 / Math.PI))) + 128);
			g = (int)((255.0 / Math.PI) * Math.asin(Math.sin((x - 333.33 - (4.0 * (double)cOffset)) / (500 / Math.PI))) + 128);
			b = (int)((255.0 / Math.PI) * Math.asin(Math.sin((x - 666.66 - (4.0 * (double)cOffset)) / (500 / Math.PI))) + 128);
			*/
			if(r < 0) {
				r = 0;
			}
			if(g < 0) {
				g = 0;
			}
			if(b < 0) {
				b = 0;
			}
		}
		
	}
	public void prepareColor() {
		cOffset = (r + g + b) / 3;
		g = 255 - cOffset;
		r = cOffset;
		b = 0;
	}
	public Color getColor() {
		return new Color(r, g, b);
	}
}
