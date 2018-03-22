import javafx.scene.shape.*;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;

import javafx.scene.shape.*;

public class Window extends JFrame implements ActionListener, MouseListener {
	static int windowX;
	static int windowY;
	static int windowHeight;
	static int windowWidth;
	ArrayList<Equation> memLog = new ArrayList();
	int memIndex = 0;
	
	int xGridSize = 2;
	static double xNestedGridSize;
	int yGridSize = 2;
	static double yNestedGridSize = 1.0;
	
	public JPanel panel = new JPanel();
	JPanel controls = new JPanel();
	
	JTextField equationIn = new JTextField();
	JButton graphButton = new JButton("Graph");
	JButton test = new JButton("test");
	JLabel instruction = new JLabel("<html>Click anywhere on <br/>the plane to graph!</html>", SwingConstants.CENTER);
	
	Ellipse2D dPadCircle;
	boolean drawMoveControls = true;
	int dPadSize = 55;
	
	JTextField xMinIn = new JTextField();
	JTextField xMaxIn = new JTextField();
	JTextField yMinIn = new JTextField();
	JTextField yMaxIn = new JTextField();
	
	JLabel xMinLab = new JLabel("X Min:", SwingConstants.RIGHT);
	JLabel xMaxLab = new JLabel("X Max:", SwingConstants.RIGHT);
	JLabel yMinLab = new JLabel("Y Min:", SwingConstants.RIGHT);
	JLabel yMaxLab = new JLabel("Y Max:", SwingConstants.RIGHT);
	
	JLabel yEquals = new JLabel("y =", SwingConstants.CENTER);
	
	JLabel zoomIn = new JLabel("+", SwingConstants.CENTER);
	JLabel zoomOut = new JLabel("-", SwingConstants.CENTER);
	
	JLabel forwardMem = new JLabel(">", SwingConstants.CENTER); 
	JLabel backwardMem = new JLabel("<", SwingConstants.CENTER);
	
	int rangeLabelSize = 60;
	
	JCheckBox crazyColorOption = new JCheckBox();
	JLabel crazyLab = new JLabel("Insane Color Mode:");
	
	String eq;
	
	static int width = 700;
	static int height = 700;
	int controlSize = 300;
	int equationInHeight = 70;
	int insetsTop;
	static double xMin;
	static double xMax;
	static double yMin;
	static double yMax;
	
	DrawPlane plane;
	
	int crazyColorMode = 0;
	
	Color jElementColor = Color.decode("#454545");
	Color jTextColor = Color.decode("#AAAAAA");
	Color jLabelColor = Color.decode("#777777");
	Color jHighlightColor = Color.decode("#DDDDDD");
	Color controlsColor = Color.decode("#1C1C1C");
	Color backgroundColor = Color.decode("#2A2A2A");
	Color memColor = Color.decode("#6A6A6A");
	Color memBackColor = Color.decode("#3C3C3C");
	Color axisColor = Color.decode("#AAAAAA");
	Color nestedGridColor = Color.decode("#393939");
	Color gridColor = Color.decode("#515151");
	Color lineColor = Color.decode("#FBFBFB");
	
	MyColor myBackColor;
	MyColor myControlsColor;
	MyColor myHighColor;
	MyColor myLabelColor;
	MyColor myTextColor;
	MyColor myElementColor;
	MyColor myHighText;
	MyColor myBlack;
	MyColor myMemColor;
	MyColor myMemBackColor;
	
	Font labelFont = new Font("Century Gothic", Font.PLAIN, 23);
	Font maxMinFont = new Font("Apple Mono", Font.CENTER_BASELINE, 16);
	Font typeFont = new Font("Apple Mono", Font.CENTER_BASELINE, 16);
	Font equationFont = new Font("Apple Mono", Font.CENTER_BASELINE, 33);
	Font zoomFont = new Font("Apple Mono", Font.CENTER_BASELINE, 28);
	Font memFont = new Font("Apple Mono", Font.CENTER_BASELINE, 40);
	
	Border textBorder = BorderFactory.createLineBorder(jElementColor);
	Border zoomBorder = BorderFactory.createLineBorder(jLabelColor, 2);
	
	boolean repOnce = true;
	boolean mousePressed = false;
	
	Equation equation = new Equation();
	
	Timer timer = new Timer(10, this);
	
	int colorSpeed = 7;
	
	public Window() {
		
		//super.getContentPane().setPreferredSize(new Dimension(width + controlSize, height + equationInHeight));
		//super.pack();
		xNestedGridSize = 1.0;
		
		System.out.println("TBH = " + super.getInsets().top);
		
		myBackColor = new MyColor(backgroundColor, colorSpeed);
		myControlsColor = new MyColor(controlsColor, colorSpeed);
		myHighColor = new MyColor(jHighlightColor, colorSpeed);
		myLabelColor = new MyColor(jLabelColor, colorSpeed);
		myTextColor = new MyColor(jTextColor, colorSpeed);
		myElementColor = new MyColor(jElementColor, colorSpeed);
		myHighText = new MyColor(0, 0, 0, colorSpeed);
		myBlack = new MyColor(Color.BLACK, colorSpeed);
		myMemColor = new MyColor(memColor, colorSpeed);
		myMemBackColor = new MyColor(memBackColor, colorSpeed);
		
		timer.setInitialDelay(1);
		timer.start();
		
		xMin = -10.000;
		xMax = 10.000;
		yMin = -10.000;
		yMax = 10.000;
		
		xMinIn = new JTextField(Double.toString(xMin));
		xMaxIn = new JTextField(Double.toString(xMax));
		yMinIn = new JTextField(Double.toString(yMin));
		yMaxIn = new JTextField(Double.toString(yMax));
		
		System.out.println(equation.getPoints());
		
		graphButton.setBounds(width + 45, 40, 70, 30);
		instruction.setBounds(0, 0, controlSize, 85);
		
		graphButton.setBackground(jElementColor);;
		graphButton.setForeground(jTextColor);
		
		xMaxLab.setBounds((controlSize - (90 + (controlSize / 2))) / 2, 100, rangeLabelSize, 30);
		xMinLab.setBounds(xMaxLab.getX(), xMaxLab.getY() + 40, rangeLabelSize, 30);
		yMaxLab.setBounds(xMaxLab.getX(), xMinLab.getY() + 40, rangeLabelSize, 30);
		yMinLab.setBounds(xMaxLab.getX(), yMaxLab.getY() + 40, rangeLabelSize, 30);
		
		xMaxIn.setBounds(xMaxLab.getX() + 70, xMaxLab.getY(), (controlSize / 2) + 15, 30);
		xMinIn.setBounds(xMinLab.getX() + 70, xMinLab.getY(), (controlSize / 2) + 15, 30);
		yMaxIn.setBounds(yMaxLab.getX() + 70, yMaxLab.getY(), (controlSize / 2) + 15, 30);
		yMinIn.setBounds(yMaxLab.getX() + 70, yMinLab.getY(), (controlSize / 2) + 15, 30);
		
		crazyLab.setBounds(yMinLab.getX() + 10, yMinLab.getY() + 45, 170, 30);
		crazyColorOption.setBounds(crazyLab.getX() + 160, crazyLab.getY() + 2, 25, 25);
		
		
		zoomIn.setBounds((controlSize / 2) - 110, crazyLab.getY() + 45, 50, 50);
		zoomOut.setBounds((controlSize / 2) + 60, crazyLab.getY() + 45, 50, 50);
		
		//controls.add(equationIn);
		//panel.add(graphButton);
		controls.add(instruction);
		
		controls.add(xMinLab);
		controls.add(xMaxLab);
		controls.add(yMinLab);
		controls.add(yMaxLab);
		
		controls.add(zoomIn);
		controls.add(zoomOut);
		
		controls.add(xMinIn);
		controls.add(xMaxIn);
		controls.add(yMinIn);
		controls.add(yMaxIn);
		
		controls.add(crazyColorOption);
		controls.add(crazyLab);
		
		controls.setLayout(null);
		panel.add(controls);
		panel.add(equationIn);
		panel.add(yEquals);
		
		crazyColorOption.addActionListener(this);
		
		zoomIn.addMouseListener(this);
		zoomOut.addMouseListener(this);
		
		forwardMem.addMouseListener(this);
		backwardMem.addMouseListener(this);
		
		super.setName("Graphing Calculator");
		super.setSize(width + controlSize, height + equationInHeight + super.getInsets().top);
		super.setContentPane(panel);
		super.setLayout(null);
		super.addMouseListener(this);
		super.setResizable(false);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setVisible(true);
		
		insetsTop = super.getInsets().top;
		windowX = super.getX();
		windowY = super.getY();
		windowHeight = super.getHeight();
		windowWidth = super.getWidth();
		
		//System.out.println("TBH1 = " + super.getInsets().top);
		super.setSize(width + controlSize, height + equationInHeight + super.getInsets().top);
		
		controls.setBounds(width + 1, 0, controlSize, height);
		yEquals.setBounds(0, height, 70, equationInHeight);
		equationIn.setBounds(yEquals.getWidth(), height, width + controlSize - yEquals.getWidth() - (2 * (equationInHeight - 15)), equationInHeight);
		forwardMem.setBounds(width + controlSize - equationInHeight + 15, equationIn.getY(), equationInHeight - 15, equationInHeight);
		backwardMem.setBounds(forwardMem.getX() - forwardMem.getWidth(), forwardMem.getY(), forwardMem.getWidth(), forwardMem.getHeight());
		
		panel.add(forwardMem);
		panel.add(backwardMem);

		dPadCircle = new Ellipse2D.Float(width + dPadSize, yMinIn.getY() + 190, controlSize - (2 * dPadSize), controlSize - (2 * dPadSize));
		
		equationIn.grabFocus();
		makeNewEquation();
		
	}
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		
		g2.setStroke(new BasicStroke(2));
		g2.setColor(backgroundColor);
		
		
		g2.fill(dPadCircle);
		
		Line2D dPadLineVer = new Line2D.Float((int)(dPadCircle.getX() + (dPadCircle.getWidth() / 2)), 
											(int)dPadCircle.getY(), 
											(int)(dPadCircle.getX() + (dPadCircle.getWidth() / 2)), 
											(int)(dPadCircle.getY() + dPadCircle.getWidth()));
		Line2D dPadLineHor = new Line2D.Float((int)dPadCircle.getX(), 
				(int)(dPadCircle.getY() + (dPadCircle.getHeight() / 2)), 
				(int)(dPadCircle.getX() + dPadCircle.getWidth()), 
				(int)(dPadCircle.getY() + (dPadCircle.getHeight() / 2)));
		Ellipse2D dPadCircle2 = new Ellipse2D.Float((int)dPadCircle.getX(), (int)dPadCircle.getY(), (int)dPadCircle.getWidth(), (int)dPadCircle.getHeight());
		g2.setColor(gridColor);
		g2.draw(dPadCircle);
		g2.draw(dPadLineHor);
		g2.draw(dPadLineVer);
		System.out.println("INSETSTOP = " + insetsTop);
		plane = new DrawPlane(equation, g2, insetsTop, xNestedGridSize, yNestedGridSize);
		//plane.drawGrid();
		yEquals.repaint();
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
			myBlack.increment();
			myMemColor.increment();
			myMemBackColor.increment();
			
			textBorder = BorderFactory.createLineBorder(myElementColor.getColor());
			
			controls.setBackground(myControlsColor.getColor());
			panel.setBackground(myBackColor.getColor());
			
			equationIn.setBackground(myElementColor.getColor());
			equationIn.setForeground(myTextColor.getColor());
			equationIn.setSelectionColor(myHighColor.getColor());
			equationIn.setCaretColor(myTextColor.getColor());
			equationIn.setSelectedTextColor(myHighText.getColor());
			equationIn.setBorder(textBorder);
			
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
			
			xMinIn.setBorder(textBorder);
			xMaxIn.setBorder(textBorder);
			yMinIn.setBorder(textBorder);
			yMaxIn.setBorder(textBorder);
			
			instruction.setForeground(myLabelColor.getColor());
			instruction.setBackground(myBlack.getColor());
			
			forwardMem.setForeground(myMemColor.getColor());
			forwardMem.setBackground(myMemBackColor.getColor());
			
			backwardMem.setForeground(myMemColor.getColor());
			backwardMem.setBackground(myMemBackColor.getColor());
			
			zoomIn.setForeground(myLabelColor.getColor());
			zoomOut.setForeground(myLabelColor.getColor());
			
			zoomBorder = BorderFactory.createLineBorder(myLabelColor.getColor(), 2);
			zoomIn.setBorder(zoomBorder);
			zoomOut.setBorder(zoomBorder);
		}
		else if(crazyColorMode == 0) {
			textBorder = BorderFactory.createLineBorder(jElementColor);
			zoomBorder = BorderFactory.createLineBorder(jLabelColor, 2);
			
			instruction.setForeground(jLabelColor);
			instruction.setBackground(Color.BLACK);
			instruction.setOpaque(true);
			
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
			
			forwardMem.setForeground(memColor);
			forwardMem.setFont(memFont);
			forwardMem.setBackground(memBackColor);
			forwardMem.setOpaque(true);
			
			backwardMem.setForeground(memColor);
			backwardMem.setFont(memFont);
			backwardMem.setBackground(memBackColor);
			backwardMem.setOpaque(true);
			
			xMinLab.setForeground(jLabelColor);
			xMaxLab.setForeground(jLabelColor);
			yMinLab.setForeground(jLabelColor);
			yMaxLab.setForeground(jLabelColor);
			
			xMinLab.setFont(maxMinFont);
			xMaxLab.setFont(maxMinFont);
			yMinLab.setFont(maxMinFont);
			yMaxLab.setFont(maxMinFont);
			
			zoomIn.setForeground(jLabelColor);
			zoomOut.setForeground(jLabelColor);
			
			zoomIn.setBackground(Color.decode("#3C3C3C"));
			zoomOut.setBackground(Color.decode("#3C3C3C"));
			
			zoomIn.setFont(zoomFont);
			zoomOut.setFont(zoomFont);
			
			zoomIn.setBorder(zoomBorder);
			zoomOut.setBorder(zoomBorder);
			
			crazyLab.setForeground(jLabelColor);
			crazyLab.setFont(maxMinFont);
			crazyColorOption.setOpaque(false);
			
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
			
			panel.setBackground(backgroundColor);
			controls.setBackground(controlsColor);
			
			crazyColorMode = 2;
			super.repaint();
		}
		repOnce = false;
		
	}
	//0.10001 - 1 = 10^-1
	//1.00001 - 10 = 10^0
	//10.00001 - 100 = 10^1
	
	public void makeNewEquation() {
		
		
		eq = equationIn.getText();
		System.out.println("Text = " + eq);
		
		if(!xMaxIn.getText().equals("")) {
			xMax = Double.parseDouble(xMaxIn.getText());
			System.out.println("xMax = " + xMax);
		}
		if(!xMinIn.getText().equals("")) {
			xMin = Double.parseDouble(xMinIn.getText());
			System.out.println("xMin = " + xMin);
		}
		if(!yMaxIn.getText().equals("")) {
			yMax = Double.parseDouble(yMaxIn.getText());
			System.out.println("yMax = " + yMax);
		}
		if(!yMinIn.getText().equals("")) {
			yMin = Double.parseDouble(yMinIn.getText());
			System.out.println("yMin = " + yMin);
		}
		if(xMax <= xMin) {
			System.out.println("xRange Error");
			new ErrorWindow("Range Error", "'X Max' must be greater than 'X Min'");
		}
		else if(yMax <= yMin) {
			new ErrorWindow("Range Error", "'Y Max' must be greater than 'Y Min'");
		}
		else {
			setGridSize();
			
			if(eq.equals("")) {
				equation = new Equation();
			}
			else {
				equation = new Equation(eq, width, height, xMin, xMax, yMin, yMax);
				System.out.println("EQUATION SIZE = " + equation.segmentedEq.size());
				if(memLog.size() > 0) {
					if(!equation.doesEqual(memLog.get(memLog.size() - 1))) {
						memLog.add(equation);
						memIndex = memLog.size() - 1;
					}
				}
				else if(equation.segmentedEq.size() > 2) {
					memLog.add(equation);
					memIndex = memLog.size() - 1;
				}
				System.out.println("MEMLOG = " + memLog);
			}
			roundRanges();
			super.repaint();
		}
	
	}
	public void setGridSize() {
		System.out.println("GRIDSIZE TEST = " + ((xMax - xMin) / 2));
		if(((xMax - xMin) / 2) > 10) {
			double i = 10.0;
			double count = 0.0;
			while(i < ((xMax - xMin) / 2) - ((xMax - xMin) / 1000)) {
				i = Math.pow(10, count) * 100;
				count += 1;
			}
			xNestedGridSize = Math.pow(10.0, count); 
		}
		else {
			double count = 0.0;
			double i = 1.0;
			while(i > ((xMax - xMin) / 2) + ((xMax - xMin) / 1000)) {
				i = Math.pow(10, count) / 10;
				count -= 1;
			}
			xNestedGridSize = Math.pow(10.0, count); 
		}
		if(((yMax - yMin) / 2) > 10) {
			double i = 10.0;
			double count = 0.0;
			while(i < ((yMax - yMin) / 2) - ((yMax - yMin) / 1000)) {
				i = Math.pow(10, count) * 100;
				count += 1;
			}
			yNestedGridSize = Math.pow(10.0, count); 
		}
		else {
			double count = 0.0;
			double i = 1.0;
			while(i > ((yMax - yMin) / 2) + ((yMax - yMin) / 1000)) {
				i = Math.pow(10, count) / 10;
				count -= 1;
			}
			yNestedGridSize = Math.pow(10.0, count); 
		}
		
	}
	public void roundRanges() {
		if(Math.abs(Double.parseDouble(xMaxIn.getText())) >= 0.001 && Math.abs((Double.parseDouble(xMaxIn.getText()) - Double.parseDouble(xMinIn.getText())) / 2) >= 0.001) {
			xMaxIn.setText(String.format("%.3f", Double.parseDouble(xMaxIn.getText())));
			xMinIn.setText(String.format("%.3f", Double.parseDouble(xMinIn.getText())));
		}
		if(Math.abs(Double.parseDouble(xMinIn.getText())) >= 0.001 && Math.abs((Double.parseDouble(xMaxIn.getText()) - Double.parseDouble(xMinIn.getText())) / 2) >= 0.001) {
			xMinIn.setText(String.format("%.3f", Double.parseDouble(xMinIn.getText())));
		}
		if(Math.abs(Double.parseDouble(yMaxIn.getText())) >= 0.001 && Math.abs((Double.parseDouble(yMaxIn.getText()) - Double.parseDouble(yMinIn.getText())) / 2) >= 0.001) {
			yMaxIn.setText(String.format("%.3f", Double.parseDouble(yMaxIn.getText())));
			yMinIn.setText(String.format("%.3f", Double.parseDouble(yMinIn.getText())));
		}
		if(Math.abs(Double.parseDouble(yMinIn.getText())) >= 0.001 && Math.abs((Double.parseDouble(yMaxIn.getText()) - Double.parseDouble(yMinIn.getText())) / 2) >= 0.001) {
			yMinIn.setText(String.format("%.3f", Double.parseDouble(yMinIn.getText())));
		}
		
		
	}
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(crazyColorMode != 1) {
			if(!e.getComponent().equals(zoomIn) && !e.getComponent().equals(zoomOut) && !e.getComponent().equals(forwardMem) && !e.getComponent().equals(backwardMem) && e.getX() <= width && e.getY() <= height) {
				makeNewEquation();
			}
			else if(!e.getComponent().equals(zoomIn) && !e.getComponent().equals(zoomOut) && !e.getComponent().equals(forwardMem) && !e.getComponent().equals(backwardMem) && dPadCircle.contains(e.getX(), e.getY())) {
				
				Point dPadCenter = new Point(dPadCircle.getX() + (dPadCircle.getWidth() / 2), dPadCircle.getY() + (dPadCircle.getHeight() / 2));
				double shiftNum = 0.0;
				//shift range by ((xMax - xMin) / 2) * ((e.getX() - dPadCenter.getX()) / (dPadCircle.getWidth() / 2))
				
				shiftNum = ((xMax - xMin) / 2.0) * (Math.pow(((e.getX() - dPadCenter.getX()) / (dPadCircle.getWidth())), 2.0) / 3.0 * 5.0);
				if(e.getX() < dPadCenter.getX()) {
					shiftNum *= -1.0;
				}
				xMaxIn.setText(Double.toString(xMax + shiftNum));
				xMinIn.setText(Double.toString(xMin + shiftNum));
				
				shiftNum = ((yMax - yMin) / 2.0) *  (Math.pow((double)((e.getY() - dPadCenter.getY()) / (double)(dPadCircle.getHeight())), 2.0) / 3.0 * 5.0);
				if(e.getY() > dPadCenter.getY()) {
					shiftNum *= -1.0;
				}
				yMaxIn.setText(Double.toString(yMax + shiftNum));
				yMinIn.setText(Double.toString(yMin + shiftNum));
				makeNewEquation();
				mousePressed = true;
				
				}
			else if(e.getComponent().equals(forwardMem)) {
				forwardMem.setBackground(Color.decode("#555555"));
				forwardMem.repaint();
				if(memIndex < memLog.size() - 1) {
					memIndex++;
					equationIn.setText(memLog.get(memIndex).equationString);
				}
				
			}
			else if(e.getComponent().equals(backwardMem)) {
				backwardMem.setBackground(Color.decode("#555555"));
				backwardMem.repaint();
				if(memIndex > 0) {
					memIndex--;
					equationIn.setText(memLog.get(memIndex).equationString);
				}
				
			}
			else if(e.getComponent().equals(zoomIn)) {
				zoomIn.setOpaque(true);
				zoomIn.repaint();
				
				xMaxIn.setText(Double.toString(xMax - ((xMax - xMin) / 15.0)));
				xMinIn.setText(Double.toString(xMin + ((xMax - xMin) / 15.0)));
				yMaxIn.setText(Double.toString(yMax - ((yMax - yMin) / 15.0)));
				yMinIn.setText(Double.toString(yMin + ((yMax - yMin) / 15.0)));
				
				makeNewEquation();
			}
			else if(e.getComponent().equals(zoomOut)) {
				zoomOut.setOpaque(true);
				zoomOut.repaint();
				
				xMaxIn.setText(Double.toString(xMax + ((xMax - xMin) / 15.0)));
				xMinIn.setText(Double.toString(xMin - ((xMax - xMin) / 15.0)));
				yMaxIn.setText(Double.toString(yMax + ((yMax - yMin) / 15.0)));
				yMinIn.setText(Double.toString(yMin - ((yMax - yMin) / 15.0)));
				
				makeNewEquation();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mousePressed = false;
		if(crazyColorMode != 1) {
			if(e.getComponent().equals(zoomIn)) {
				zoomIn.setOpaque(false);
				zoomIn.repaint();
			}
			else if(e.getComponent().equals(zoomOut)) {
				zoomOut.setOpaque(false);
				zoomOut.repaint();
			}
			else if(e.getComponent().equals(forwardMem)) {
				forwardMem.setBackground(memBackColor);
				forwardMem.repaint();
			}
			else if(e.getComponent().equals(backwardMem)) {
				backwardMem.setBackground(memBackColor);
				backwardMem.repaint();
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		if(crazyColorMode != 1) {
			if(e.getComponent().equals(zoomIn)) {
				zoomBorder = BorderFactory.createLineBorder(Color.decode("#BBBBBB"), 2);
				zoomIn.setBorder(zoomBorder);
			}
			if(e.getComponent().equals(zoomOut)) {
				zoomBorder = BorderFactory.createLineBorder(Color.decode("#BBBBBB"), 2);
				zoomOut.setBorder(zoomBorder);
			}
			else if(e.getComponent().equals(forwardMem)) {
				forwardMem.setForeground(Color.decode("#DDDDDD"));
				forwardMem.repaint();
			}
			else if(e.getComponent().equals(backwardMem)) {
				backwardMem.setForeground(Color.decode("#DDDDDD"));
				backwardMem.repaint();
			}
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		if(crazyColorMode != 1) {
			if(e.getComponent().equals(zoomIn)) {
				zoomBorder = BorderFactory.createLineBorder(jLabelColor, 2);
				zoomIn.setBorder(zoomBorder);
			}
			if(e.getComponent().equals(zoomOut)) {
				zoomBorder = BorderFactory.createLineBorder(jLabelColor, 2);
				zoomOut.setBorder(zoomBorder);
			}
			else if(e.getComponent().equals(forwardMem)) {
				forwardMem.setForeground(memColor);
				forwardMem.repaint();
			}
			else if(e.getComponent().equals(backwardMem)) {
				backwardMem.setForeground(memColor);
				backwardMem.repaint();
			}
		}
	}

	
}

