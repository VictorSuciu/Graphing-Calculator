import javafx.scene.shape.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.Timer;
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
	
	JLabel yEquals = new JLabel("y =", SwingConstants.CENTER);
	
	int rangeLabelSize = 60;
	
	JCheckBox crazyColorOption = new JCheckBox();
	JLabel crazyLab = new JLabel("Insane Color Mode:");
	
	String eq;
	
	int width = 600;
	int height = 600;
	int controlSize = 300;
	int equationInHeight = 70;
	
	double xMin;
	double xMax;
	double yMin;
	double yMax;

	int crazyColorMode = 0;
	
	Color jElementColor = Color.decode("#454545");
	Color jTextColor = Color.decode("#AAAAAA");
	Color jLabelColor = Color.decode("#777777");
	Color jHighlightColor = Color.decode("#DDDDDD");
	Color controlsColor = Color.decode("#1C1C1C");
	Color backgroundColor = Color.decode("#2A2A2A");
	
	MyColor myBackColor;
	MyColor myControlsColor;
	MyColor myHighColor;
	MyColor myLabelColor;
	MyColor myTextColor;
	MyColor myElementColor;
	MyColor myHighText;
	
	Font labelFont = new Font("Century Gothic", Font.PLAIN, 23);
	Font maxMinFont = new Font("Apple Mono", Font.CENTER_BASELINE, 16);
	Font typeFont = new Font("Apple Mono", Font.CENTER_BASELINE, 16);
	Font equationFont = new Font("Apple Mono", Font.CENTER_BASELINE, 33);
	
	Border textBorder = BorderFactory.createLineBorder(jElementColor);
	
	boolean repOnce = true;
	TopBarHeight topBarHeight;
	Equation equation = new Equation();
	
	Timer timer = new Timer(10, this);
	
	int colorSpeed = 5;
	
	public Window() {
		
		//super.getContentPane().setPreferredSize(new Dimension(width + controlSize, height + equationInHeight));
		//super.pack();
		
		
		System.out.println("TBH = " + super.getInsets().top);
		
		myBackColor = new MyColor(backgroundColor, colorSpeed);
		myControlsColor = new MyColor(controlsColor, colorSpeed);
		myHighColor = new MyColor(jHighlightColor, colorSpeed);
		myLabelColor = new MyColor(jLabelColor, colorSpeed);
		myTextColor = new MyColor(jTextColor, colorSpeed);
		myElementColor = new MyColor(jElementColor, colorSpeed);
		myHighText = new MyColor(0, 0, 0, colorSpeed);
		
		timer.setInitialDelay(1);
		timer.start();
		
		xMin = -10.0;
		xMax = 10.0;
		yMin = -10.0;
		yMax = 10.0;
		
		xMinIn = new JTextField(Double.toString(xMin));
		xMaxIn = new JTextField(Double.toString(xMax));
		yMinIn = new JTextField(Double.toString(yMin));
		yMaxIn = new JTextField(Double.toString(yMax));
		
		System.out.println(equation.getPoints());
		
		
		
		graphButton.setBounds(width + 45, 40, 70, 30);
		
		graphButton.setBackground(jElementColor);;
		graphButton.setForeground(jTextColor);
		
		instruction.setForeground(jLabelColor);
		instruction.setBounds(0, 15, controlSize, 60);
		
		instruction.setFont(labelFont);
		
		equationIn.setBackground(jElementColor);
		equationIn.setForeground(jTextColor);
		equationIn.setCaretColor(jTextColor);
		equationIn.setSelectionColor(jHighlightColor);
		equationIn.setFont(equationFont);
		equationIn.setBorder(textBorder);
		equationIn.setSelectedTextColor(backgroundColor);
		
		yEquals.setBackground(jElementColor);
		yEquals.setForeground(jTextColor);
		yEquals.setFont(equationFont);
		yEquals.setOpaque(true);
		
		graphButton.addActionListener(this);
		
		xMaxLab.setBounds((controlSize - (90 + (controlSize / 2))) / 2, 150, rangeLabelSize, 30);
		xMinLab.setBounds(xMaxLab.getX(), xMaxLab.getY() + 40, rangeLabelSize, 30);
		yMaxLab.setBounds(xMaxLab.getX(), xMinLab.getY() + 40, rangeLabelSize, 30);
		yMinLab.setBounds(xMaxLab.getX(), yMaxLab.getY() + 40, rangeLabelSize, 30);
		
		xMaxIn.setBounds(xMaxLab.getX() + 70, xMaxLab.getY(), (controlSize / 2) + 15, 30);
		xMinIn.setBounds(xMinLab.getX() + 70, xMinLab.getY(), (controlSize / 2) + 15, 30);
		yMaxIn.setBounds(yMaxLab.getX() + 70, yMaxLab.getY(), (controlSize / 2) + 15, 30);
		yMinIn.setBounds(yMaxLab.getX() + 70, yMinLab.getY(), (controlSize / 2) + 15, 30);
		
		crazyLab.setBounds(yMinLab.getX() + 10, yMinLab.getY() + 45, 170, 30);
		crazyColorOption.setBounds(crazyLab.getX() + 160, crazyLab.getY() + 2, 25, 25);
		crazyColorOption.addActionListener(this);
		crazyColorOption.setOpaque(false);
		
		xMinLab.setForeground(jLabelColor);
		xMaxLab.setForeground(jLabelColor);
		yMinLab.setForeground(jLabelColor);
		yMaxLab.setForeground(jLabelColor);
		
		xMinLab.setFont(maxMinFont);
		xMaxLab.setFont(maxMinFont);
		yMinLab.setFont(maxMinFont);
		yMaxLab.setFont(maxMinFont);
		
		crazyLab.setForeground(jLabelColor);
		crazyLab.setFont(maxMinFont);
		
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
		
		xMinIn.setSelectedTextColor(backgroundColor);
		xMaxIn.setSelectedTextColor(backgroundColor);
		yMinIn.setSelectedTextColor(backgroundColor);
		yMaxIn.setSelectedTextColor(backgroundColor);
		
		xMinIn.setBorder(textBorder);
		xMaxIn.setBorder(textBorder);
		yMinIn.setBorder(textBorder);
		yMaxIn.setBorder(textBorder);
		
		//controls.add(equationIn);
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
		
		controls.add(crazyColorOption);
		controls.add(crazyLab);
		
		panel.setBackground(backgroundColor);
		
		
		controls.setBackground(controlsColor);
		controls.setLayout(null);
		panel.add(controls);
		panel.add(equationIn);
		panel.add(yEquals);
		
		
		super.setName("Graphing Calculator");
		super.setSize(width + controlSize, height + equationInHeight + super.getInsets().top);
		super.setContentPane(panel);
		super.setLayout(null);
		super.addMouseListener(this);
		super.setResizable(false);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setVisible(true);
		
		//System.out.println("TBH1 = " + super.getInsets().top);
		super.setSize(width + controlSize, height + equationInHeight + super.getInsets().top);
		topBarHeight.init(super.getInsets().top);
		
		controls.setBounds(width + 1, 0, controlSize, height);
		yEquals.setBounds(0, height, 70, equationInHeight);
		equationIn.setBounds(yEquals.getWidth(), height, width + controlSize - yEquals.getWidth(), equationInHeight);
		
		equationIn.grabFocus();
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		
		Line2D yAxis = new Line2D.Double(((double)width / 2.0) - (  ((xMax + xMin) / 2.0) * (((double)width / 2.0) / ((xMax - xMin) / 2.0))  ), 
										topBarHeight.height + 0, 
										((double)width / 2.0) - (  ((xMax + xMin) / 2.0) * (((double)width / 2.0) / ((xMax - xMin) / 2.0))  ), 
										topBarHeight.height + height);
		Line2D xAxis = new Line2D.Double(0, 
										topBarHeight.height + ((double)height / 2.0) + (  ((yMax + yMin) / 2.0) * (((double)height / 2.0) / ((yMax - yMin) / 2.0))  ), 
										width, 
										topBarHeight.height + ((double)height / 2.0) + (  ((yMax + yMin) / 2.0) * (((double)height / 2.0) / ((yMax - yMin) / 2.0))  ));
		
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
										 y2 + translateY - (y2 * 2) + topBarHeight.height, 
									     x1 + translateX, 
									     y1 + translateY - (y1 * 2) + topBarHeight.height);	
				g2.draw(temp);
			}
			
		}
		equationIn.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource() == crazyColorOption) {
			crazyColorMode = Math.abs(crazyColorMode - 1);
			System.out.println("Crazy = " + crazyColorMode);
		}
		
		if(crazyColorMode == 1) {
			myControlsColor.increment();
			myBackColor.increment();
			myElementColor.increment();
			myLabelColor.increment();
			myHighColor.increment();
			myTextColor.increment();
			myHighText.increment();
			
			controls.setBackground(myControlsColor.getColor());
			panel.setBackground(myBackColor.getColor());
			
			equationIn.setBackground(myElementColor.getColor());
			equationIn.setForeground(myTextColor.getColor());
			equationIn.setSelectionColor(myHighColor.getColor());
			equationIn.setCaretColor(myTextColor.getColor());
			equationIn.setSelectedTextColor(myHighText.getColor());
			
			yEquals.setBackground(myElementColor.getColor());
			yEquals.setForeground(myTextColor.getColor());
			
			xMinLab.setForeground(myLabelColor.getColor());
			xMaxLab.setForeground(myLabelColor.getColor());
			yMinLab.setForeground(myLabelColor.getColor());
			yMaxLab.setForeground(myLabelColor.getColor());
			
			crazyLab.setForeground(myLabelColor.getColor());
			
			xMinIn.setBackground(myElementColor.getColor());
			xMaxIn.setBackground(myElementColor.getColor());
			yMinIn.setBackground(myElementColor.getColor());
			yMaxIn.setBackground(myElementColor.getColor());
			
			xMinIn.setSelectedTextColor(myHighText.getColor());
			xMaxIn.setSelectedTextColor(myHighText.getColor());
			yMinIn.setSelectedTextColor(myHighText.getColor());
			yMaxIn.setSelectedTextColor(myHighText.getColor());
			
			xMinIn.setForeground(myTextColor.getColor());
			xMaxIn.setForeground(myTextColor.getColor());
			yMinIn.setForeground(myTextColor.getColor());
			yMaxIn.setForeground(myTextColor.getColor());
			
			xMinIn.setSelectionColor(myHighColor.getColor());
			xMaxIn.setSelectionColor(myHighColor.getColor());
			yMinIn.setSelectionColor(myHighColor.getColor());
			yMaxIn.setSelectionColor(myHighColor.getColor());
			
			xMinIn.setCaretColor(myTextColor.getColor());
			xMaxIn.setForeground(myTextColor.getColor());
			yMinIn.setForeground(myTextColor.getColor());
			yMaxIn.setForeground(myTextColor.getColor());
			
			instruction.setForeground(myLabelColor.getColor());
		}
		else if(crazyColorMode == 0) {
			equationIn.setBackground(jElementColor);
			equationIn.setForeground(jTextColor);
			equationIn.setCaretColor(jTextColor);
			equationIn.setSelectionColor(jHighlightColor);
			equationIn.setFont(equationFont);
			equationIn.setBorder(textBorder);
			equationIn.setSelectedTextColor(backgroundColor);
			
			yEquals.setBackground(jElementColor);
			yEquals.setForeground(jTextColor);
			
			xMinIn.setSelectedTextColor(backgroundColor);
			xMaxIn.setSelectedTextColor(backgroundColor);
			yMinIn.setSelectedTextColor(backgroundColor);
			yMaxIn.setSelectedTextColor(backgroundColor);
			
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
			
			xMinIn.setBorder(textBorder);
			xMaxIn.setBorder(textBorder);
			yMinIn.setBorder(textBorder);
			
			xMinLab.setForeground(jLabelColor);
			xMaxLab.setForeground(jLabelColor);
			yMinLab.setForeground(jLabelColor);
			yMaxLab.setForeground(jLabelColor);
			
			crazyLab.setForeground(jLabelColor);
			
			instruction.setForeground(jLabelColor);
			
			controls.setBackground(controlsColor);
			panel.setBackground(backgroundColor);
			
			crazyColorMode = 2;
		}
		repOnce = false;
		
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
		
	}

	
}

