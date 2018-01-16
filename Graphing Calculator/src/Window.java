import javafx.scene.shape.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;

public class Window extends JFrame implements ActionListener, MouseListener {
	
	public JPanel panel = new JPanel();
	JPanel controls = new JPanel();
	
	JTextField equationIn = new JTextField();
	JButton graphButton = new JButton("Graph");
	JButton test = new JButton("test");
	JLabel instruction = new JLabel("<html>Click anywhere on <br/>the plane to graph!</html>", SwingConstants.CENTER);
	
	JTextField xMinIn = new JTextField();
	JTextField xMaxIn = new JTextField();
	JTextField yMinIn = new JTextField();
	JTextField yMaxIn = new JTextField();
	
	JLabel xMinLab = new JLabel("X Min:", SwingConstants.RIGHT);
	JLabel xMaxLab = new JLabel("X Max:", SwingConstants.RIGHT);
	JLabel yMinLab = new JLabel("Y Min:", SwingConstants.RIGHT);
	JLabel yMaxLab = new JLabel("Y Max:", SwingConstants.RIGHT);
	
	String eq;
	
	int width = 600;
	int height = 600;
	int controlSize = 300;
	
	double xMin;
	double xMax;
	double yMin;
	double yMax;
	
	Color jElementColor = Color.decode("#454545");
	Color jTextColor = Color.decode("#AAAAAA");
	Color jLabelColor = Color.decode("#777777");
	Color jHighlightColor = Color.decode("#DDDDDD");
	Color controlsColor = Color.decode("#1C1C1C");
	Color backgroundColor = Color.decode("#2A2A2A");
	
	Font labelFont = new Font("Century Gothic", Font.PLAIN, 22);
	Font maxMinFont = new Font("Apple Mono", Font.CENTER_BASELINE, 14);
	Font typeFont = new Font("Apple Mono", Font.CENTER_BASELINE, 13);
	
	Equation equation = new Equation();
	
	public Window() {
		xMin = -10.0;
		xMax = 10.0;
		yMin = -10.0;
		yMax = 10.0;
		
		xMinIn = new JTextField(Double.toString(xMin));
		xMaxIn = new JTextField(Double.toString(xMax));
		yMinIn = new JTextField(Double.toString(yMin));
		yMaxIn = new JTextField(Double.toString(yMax));
		
		System.out.println(equation.getPoints());
		
		
		equationIn.setBounds(0, 10, controlSize, 30);
		graphButton.setBounds(width + 45, 40, 70, 30);
		
		graphButton.setBackground(jElementColor);;
		graphButton.setForeground(jTextColor);
		
		instruction.setForeground(jLabelColor);
		instruction.setBounds(0, equationIn.getY() + 35, controlSize, 60);
		
		instruction.setFont(labelFont);
		
		equationIn.setBackground(jElementColor);
		equationIn.setForeground(jTextColor);
		equationIn.setCaretColor(jTextColor);
		equationIn.setSelectionColor(jHighlightColor);
		equationIn.setFont(typeFont);
		
		graphButton.addActionListener(this);
		
		xMaxLab.setBounds((controlSize - (65 + (controlSize / 2))) / 2, 150, 50, 30);
		xMinLab.setBounds(xMaxLab.getX(), xMaxLab.getY() + 40, 50, 30);
		yMaxLab.setBounds(xMaxLab.getX(), xMinLab.getY() + 40, 50, 30);
		yMinLab.setBounds(xMaxLab.getX(), yMaxLab.getY() + 40, 50, 30);
		
		xMaxIn.setBounds(xMaxLab.getX() + 60, xMaxLab.getY(), (controlSize / 2) + 15, 30);
		xMinIn.setBounds(xMinLab.getX() + 60, xMinLab.getY(), (controlSize / 2) + 15, 30);
		yMaxIn.setBounds(yMaxLab.getX() + 60, yMaxLab.getY(), (controlSize / 2) + 15, 30);
		yMinIn.setBounds(yMaxLab.getX() + 60, yMinLab.getY(), (controlSize / 2) + 15, 30);
		
		xMinLab.setForeground(jLabelColor);
		xMaxLab.setForeground(jLabelColor);
		yMinLab.setForeground(jLabelColor);
		yMaxLab.setForeground(jLabelColor);
		
		xMinLab.setFont(maxMinFont);
		xMaxLab.setFont(maxMinFont);
		yMinLab.setFont(maxMinFont);
		yMaxLab.setFont(maxMinFont);
		
		xMinIn.setFont(typeFont);
		xMaxIn.setFont(typeFont);
		yMinIn.setFont(typeFont);
		yMaxIn.setFont(typeFont);
		
		xMinIn.setBackground(jElementColor);
		xMaxIn.setBackground(jElementColor);
		yMinIn.setBackground(jElementColor);
		yMaxIn.setBackground(jElementColor);
		
		xMinIn.setForeground(jTextColor);
		xMaxIn.setForeground(jTextColor);
		yMinIn.setForeground(jTextColor);
		yMaxIn.setForeground(jTextColor);
		
		xMinIn.setCaretColor(jTextColor);
		xMaxIn.setCaretColor(jTextColor);
		yMinIn.setCaretColor(jTextColor);
		yMaxIn.setCaretColor(jTextColor);
		
		xMinIn.setSelectionColor(jHighlightColor);
		xMaxIn.setSelectionColor(jHighlightColor);
		yMinIn.setSelectionColor(jHighlightColor);
		yMaxIn.setSelectionColor(jHighlightColor);
		
		controls.add(equationIn);
		//panel.add(graphButton);
		controls.add(instruction);
		
		controls.add(xMinLab);
		controls.add(xMaxLab);
		controls.add(yMinLab);
		controls.add(yMaxLab);
		
		controls.add(xMinIn);
		controls.add(xMaxIn);
		controls.add(yMinIn);
		controls.add(yMaxIn);
		
		
		panel.setBackground(backgroundColor);
		
		controls.setBounds(width + 1, 0, controlSize, height);
		controls.setBackground(controlsColor);
		controls.setLayout(null);
		panel.add(controls);
		
		super.setName("Graphing Calculator");
		super.setSize(width + controlSize, height);
		super.setContentPane(panel);
		super.setLayout(null);
		super.addMouseListener(this);
		super.setResizable(false);
		super.setVisible(true);
		
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		Line2D yAxis = new Line2D.Double(((double)width / 2.0) - (  ((xMax + xMin) / 2.0) * (((double)width / 2.0) / ((xMax - xMin) / 2.0))  ), 
										0, 
										((double)width / 2.0) - (  ((xMax + xMin) / 2.0) * (((double)width / 2.0) / ((xMax - (int)xMin) / 2.0))  ), 
										height);
		Line2D xAxis = new Line2D.Double(0, 
										((double)height / 2.0) + (  ((yMax + yMin) / 2.0) * (((double)height / 2.0) / ((yMax - yMin) / 2.0))  ), 
										width, 
										((double)height / 2.0) + (  ((yMax + yMin) / 2.0) * (((double)height / 2.0) / ((yMax - yMin) / 2.0))  ));
		
		Color axisColor = Color.decode("#666666");
		g2.setColor(axisColor);
		
		g2.draw(yAxis);
		g2.draw(xAxis);
		
		double y1 = 0;
		double y2 = 0;
		double x1 = 0;
		double x2 = 0;
		
		double translateX = ((double)width / 2) - (  ((xMax + xMin) / 2.0) * (((double)width / 2.0) / ((xMax - xMin) / 2.0))  );
		double translateY = ((double)height / 2.0) + (  ((yMax + yMin) / 2.0) * (((double)height / 2.0) / ((yMax - yMin) / 2.0))  );
		
		Line2D temp;
		Color lineColor = Color.decode("#AAAAAA");
		g2.setColor(lineColor);
		
		
		for(int i = 0; i < equation.getPoints().size() - 1; i++) {
			
			y1 = equation.getPoints().get(i).getY();
			y2 = equation.getPoints().get(i + 1).getY();
			x1 = equation.getPoints().get(i).getX();
			x2 = equation.getPoints().get(i + 1).getX();
			if(y1 + translateY <= height && y1 + translateY >= 0) {
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
										 y2 + translateY - (y2 * 2), 
									     x1 + translateX, 
									     y1 + translateY - (y1 * 2));	
				g2.draw(temp);
			}
			else if(y1 + translateY > height) {
				temp = new Line2D.Double(x2 + translateX, 
						 y2 + translateY - (y2 * 2), 
					     x1 + translateX, 
					     height);	
				g2.draw(temp);	
			}
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == graphButton) {
			eq = equationIn.getText();
			System.out.println("Text = " + eq);
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
			if(eq.equals("")) {
				equation = new Equation();
			}
			else {
				equation = new Equation(eq, width, height, xMin, xMax, yMin, yMax);
			}
			super.repaint();
		}
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getX() <= width) {
			eq = equationIn.getText();
			System.out.println("Text = " + eq);
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
			if(eq.equals("")) {
				equation = new Equation();
			}
			else {
				equation = new Equation(eq, width, height, xMin, xMax, yMin, yMax);
			}
			super.repaint();
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}