import javafx.scene.shape.*;
import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class Window extends JFrame{
	
	//public JFrame frame = new JFrame("Graphing Calculator");
	public JPanel panel = new JPanel();
	public JButton test = new JButton("Test");

	
	public Window() {
		
		super.setName("Graphing Calculator");
		super.setSize(800, 600);
		super.setVisible(true);
		super.setContentPane(panel);
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		Line2D yAxis = new Line2D.Float(400, 0, 400, 600);
		Line2D xAxis = new Line2D.Float(0, 300, 800, 300);
		g2.draw(yAxis);
		g2.draw(xAxis);
	}
}
