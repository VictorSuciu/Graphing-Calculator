import javafx.scene.shape.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class Window extends JFrame implements ActionListener{
	
	//public JFrame frame = new JFrame("Graphing Calculator");
	public JPanel panel = new JPanel();
	public JButton test = new JButton("Test");
	JTextField equationIn = new JTextField();
	JButton graphButton = new JButton("Graph");
	
	int width = 600;
	int height = 600;
	
	double xMin = -10.0;
	double xMax = 20.0;
	double yMin = -10.0;
	double yMax = 10.0;
	
	Equation equation = new Equation("x", width, height, xMin, xMax, yMin, yMax);
	
	public Window() {
		System.out.println(equation.getPoints());
		super.setName("Graphing Calculator");
		super.setSize(width, height + 100);
		super.setVisible(true);
		super.setContentPane(panel);
		equationIn.setBounds(width / 2 - 60, height + 10, 120, 30);
		graphButton.setBounds(width / 2 + 70, height + 10, 70, 30);
		panel.add(equationIn);
		panel.add(graphButton);
		
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		Line2D yAxis = new Line2D.Float(width / 2, 0, width / 2, height);
		Line2D xAxis = new Line2D.Float(0, height / 2, width, height / 2);
		g2.draw(yAxis);
		g2.draw(xAxis);
		double y1 = 0;
		double y2 = 0;
		Line2D temp;
		g2.setColor(Color.RED);
		for(int i = 0; i < equation.getPoints().size() - 1; i++) {
			y1 = equation.getPoints().get(i).getY() + 300;
			y2 = equation.getPoints().get(i + 1).getY() + 300;
			
			if(y1 <= height && y1 >= 0) {
				temp = new Line2D.Double((int)equation.getPoints().get(i).getX() + (double)height / 2.0, 
										(y1 + (2.0 * (((double)height / 2.0) - y1))), 
									    (int)equation.getPoints().get(i + 1).getX() + (double)height / 2.0, 
									    (y2 + (2.0 * (((double)height / 2.0) - y2))));
				g2.draw(temp);
			}
		}
	}
	
	public int getWidth() {
		return width;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == graphButton) {
			String eq = equationIn.getText();
			System.out.println("Text = " + eq);
			equation = new Equation(eq, width, height, xMin, xMax, yMin, yMax);
			super.repaint();
		}
		
	}
}