import javafx.scene.shape.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class Window extends JFrame implements ActionListener{
	
	public JPanel panel = new JPanel();
	
	JTextField equationIn = new JTextField();
	JButton graphButton = new JButton("Graph");
	JButton test = new JButton("test");
	
	JTextField xMinIn = new JTextField();
	JTextField xMaxIn = new JTextField();
	JTextField yMinIn = new JTextField();
	JTextField yMaxIn = new JTextField();
	
	JLabel xMinLab = new JLabel("X Min:");
	JLabel xMaxLab = new JLabel("X Max:");
	JLabel yMinLab = new JLabel("Y Min:");
	JLabel yMaxLab = new JLabel("Y Max:");
	
	JButton apply = new JButton("Apply Values");
	
	String eq;
	
	int width = 600;
	int height = 600;
	
	double xMin;
	double xMax;
	double yMin;
	double yMax;
	
	Equation equation = new Equation();
	
	public Window() {
		xMin = -10.0;
		xMax = 10.0;
		yMin = -10.0;
		yMax = 10.0;
		System.out.println(equation.getPoints());
		
		
		equationIn.setBounds(width, 10, 160, 30);
		graphButton.setBounds(width + 45, 40, 70, 30);
		graphButton.addActionListener(this);
		
		xMaxLab.setBounds(width + 35, 120, 45, 30);
		xMinLab.setBounds(width + 35, xMaxLab.getY() + 40, 45, 30);
		yMaxLab.setBounds(width + 35, xMinLab.getY() + 40, 45, 30);
		yMinLab.setBounds(width + 35, yMaxLab.getY() + 40, 45, 30);
		
		xMaxIn.setBounds(xMaxLab.getX() + 45, xMaxLab.getY(), 45, 30);
		xMinIn.setBounds(xMinLab.getX() + 45, xMinLab.getY(), 45, 30);
		yMaxIn.setBounds(yMaxLab.getX() + 45, yMaxLab.getY(), 45, 30);
		yMinIn.setBounds(yMaxLab.getX() + 45, yMinLab.getY(), 45, 30);
		
		apply.setBounds(width + 20, yMinLab.getY() + 40, 120, 30);
		apply.addActionListener(this);
		
		panel.add(equationIn);
		panel.add(graphButton);
		
		panel.add(xMinLab);
		panel.add(xMaxLab);
		panel.add(yMinLab);
		panel.add(yMaxLab);
		
		panel.add(xMinIn);
		panel.add(xMaxIn);
		panel.add(yMinIn);
		panel.add(yMaxIn);
		
		panel.add(apply);
		
		super.setName("Graphing Calculator");
		super.setSize(width + 160, height);
		super.setContentPane(panel);
		super.setLayout(null);
		super.setVisible(true);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		Line2D yAxis = new Line2D.Float((width / 2) - (  (((int)xMax + (int)xMin) / 2) * ((width / 2) / (((int)xMax - (int)xMin) / 2))  ), 
										0, 
										(width / 2) - (  (((int)xMax + (int)xMin) / 2) * ((width / 2) / (((int)xMax - (int)xMin) / 2))  ), 
										height);
		Line2D xAxis = new Line2D.Float(0, 
										(height / 2) - (  (((int)yMax + (int)yMin) / 2) * ((height / 2) / (((int)yMax - (int)yMin) / 2))  ), 
										width, 
										(height / 2) - (  (((int)yMax + (int)yMin) / 2) * ((height / 2) / (((int)yMax - (int)yMin) / 2))  ));
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
				temp = new Line2D.Double((int)equation.getPoints().get(i).getX() + (double)width / 2.0, 
										(y1 + (2.0 * (((double)height / 2.0) - y1))), 
									    (int)equation.getPoints().get(i + 1).getX() + (double)width / 2.0, 
									    (y2 + (2.0 * (((double)height / 2.0) - y2))));
				g2.draw(temp);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == graphButton) {
			eq = equationIn.getText();
			System.out.println("Text = " + eq);
			equation = new Equation(eq, width, height, xMin, xMax, yMin, yMax);
			super.repaint();
		}
		else if(e.getSource() == apply) {
			System.out.println("TEST START");
			if(!xMaxIn.getText().equals("")) {
				xMax = Double.parseDouble(xMaxIn.getText());
				System.out.println("xMax");
			}
			if(!xMinIn.getText().equals("")) {
				xMin = Double.parseDouble(xMinIn.getText());
				System.out.println("xMin");
			}
			if(!yMaxIn.getText().equals("")) {
				yMax = Double.parseDouble(yMaxIn.getText());
				System.out.println("yMax");
			}
			if(!yMinIn.getText().equals("")) {
				yMin = Double.parseDouble(yMinIn.getText());
				System.out.println("yMin");
			}
			equation = new Equation(eq, width, height, xMin, xMax, yMin, yMax);
			super.repaint();
			System.out.println("TEST END");
		}
		
	}
}