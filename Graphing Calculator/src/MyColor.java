import java.awt.Color;

public class MyColor {
	int r;
	int g;
	int b;
	int colorCycle;
	int colorSpeed;
	int cOffset;
	
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
	public void increment() {
		if((r == 255 && g == 0 && b == 0) || colorCycle == 0) {
			colorCycle = 0;
			System.out.println("Cycle 0 " + colorSpeed);
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
			System.out.println("Cycle 1 " + colorSpeed);
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
			System.out.println("Cycle 2");
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
		System.out.println(r + " " + g + " " + b);
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