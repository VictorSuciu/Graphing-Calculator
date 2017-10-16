import javafx.scene.shape.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class Window extends JFrame{
	
	//public JFrame frame = new JFrame("Graphing Calculator");
	public JPanel panel = new JPanel();
	public JButton test = new JButton("Test");
	Line l;
	int width;
	int height;
	public Window() {
		width = 800;
		height = 600;
		l = new Line(width);
		
		super.setName("Graphing Calculator");
		super.setSize(width, 750);
		super.setVisible(true);
		super.setContentPane(panel);
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		Line2D yAxis = new Line2D.Float(400, 0, 400, 600);
		Line2D xAxis = new Line2D.Float(0, 300, 800, 300);
		g2.draw(yAxis);
		g2.draw(xAxis);
		double y1 = 0;
		double y2 = 0;
		Line2D temp;
		g2.setColor(Color.RED);
		for(int i = 0; i < l.getPoints().size() - 1; i++) {
			y1 = l.getPoints().get(i).getY() + 300;
			y2 = l.getPoints().get(i + 1).getY() + 300;
			
			if(y1 <= height && y1 >= 0) {
				temp = new Line2D.Double((int)l.getPoints().get(i).getX() + 400.0, 
										(y1 + (2.0 * (((double)height / 2.0) - y1))), 
									    (int)l.getPoints().get(i + 1).getX() + 400.0, 
									    (y2 + (2.0 * (((double)height / 2.0) - y2))));
				g2.draw(temp);
			}
		}
	}
	
	public int getWidth() {
		return width;
	}
}