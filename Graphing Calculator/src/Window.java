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
	
	//These static ints store the (x, y) position of the window on the screen, as welll as its width and height.
	//Used by ErrorWindow class to determine where to place the error window
	static int windowX;
	static int windowY;
	static int windowHeight;
	static int windowWidth;
	
	
	ArrayList<Equation> memLog = new ArrayList(); //Stores a list of previously entered equations for the memory feature.
	int memIndex = 0; //Stores the current index in the above list for the memory feature
	
	//These grid variables store the grid proportions. x/yNestedGridSize stores the level of zoom for the grid,
	//such as whether to represent consecutive values of 1, 0.1, 10, ect. x/yGridSize stores how many grid
	//lines should be between every highlighted line.
	int xGridSize = 2;
	static double xNestedGridSize;
	int yGridSize = 2;
	static double yNestedGridSize = 1.0;
	
	//The two panels in the JFrame. panel holds every
	public JPanel panel = new JPanel();
	JPanel controls = new JPanel();
	
	/*
	 * All of these swing components are elements used for the GUI
	 *///----------------------------------------------i
	JTextField equationIn = new JTextField(); //Where the user types in their equation
	JLabel instruction = new JLabel("<html>Click anywhere on <br/>the plane to graph!</html>", SwingConstants.CENTER);
	//Instructions prompting the user to click the plane to graph their function
	
	Ellipse2D dPadCircle; //The direction pad used for panning
	
	//The x/y range input fields. The user can manually enter them, but they also change automatically with pan and zoom
	JTextField xMinIn = new JTextField();
	JTextField xMaxIn = new JTextField();
	JTextField yMinIn = new JTextField();
	JTextField yMaxIn = new JTextField();
	
	//The labels for the x/y range input fields. These let the user know which one is which
	JLabel xMinLab = new JLabel("X Min:", SwingConstants.RIGHT);
	JLabel xMaxLab = new JLabel("X Max:", SwingConstants.RIGHT);
	JLabel yMinLab = new JLabel("Y Min:", SwingConstants.RIGHT);
	JLabel yMaxLab = new JLabel("Y Max:", SwingConstants.RIGHT);
	
	JLabel yEquals = new JLabel("y =", SwingConstants.CENTER); //Displays a "y =" left of equationIn to let the user know
															  //to not enter "y =" into the text field
	
	//Used for zoom in/out buttons
	JLabel zoomIn = new JLabel("+", SwingConstants.CENTER);
	JLabel zoomOut = new JLabel("-", SwingConstants.CENTER);
	
	//Used for the memory buttons
	JLabel forwardMem = new JLabel(">", SwingConstants.CENTER); 
	JLabel backwardMem = new JLabel("<", SwingConstants.CENTER);
	
	//Used for the crazy color mode feature
	JCheckBox crazyColorOption = new JCheckBox();
	JLabel crazyLab = new JLabel("Insane Color Mode:");
	//----------------------------------------------i
	
	int rangeLabelSize = 60; //This is the length of the x/y range labels that display next to the x/y range text fields
	String eq; //This String stores the user's raw equation input. Grabbed from the equationIn text field
	int dPadSize = 55; //This is the size of the direction pad circle used for panning
	
	//Values used to specify and/or store sizes of GUI elements
	int controlSize = 300; //Specifies the width of the control panel
	int equationInHeight = 70; //Specifies the height of equationIn
	int insetsTop; //Stores the height of the top window bar
	
	//Reference dimension variables used in ErrorWindow class
	static int width = 700;
	static int height = 700;
	static double xMin;
	static double xMax;
	static double yMin;
	static double yMax;
	
	DrawPlane plane; //Class that draws the plane window.
	
	int crazyColorMode = 0; //Stores the state of the insane color mode feature. 1 means it its toggled
						   // on, and any other value means off.
	
	//All the colors for every GUI element
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
	
	//All the myColor objects used for the insane color mode.
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
	
	//All the fonts used for the GUI elements
	Font labelFont = new Font("Century Gothic", Font.PLAIN, 23);
	Font maxMinFont = new Font("Apple Mono", Font.CENTER_BASELINE, 16);
	Font typeFont = new Font("Apple Mono", Font.CENTER_BASELINE, 16);
	Font equationFont = new Font("Apple Mono", Font.CENTER_BASELINE, 33);
	Font zoomFont = new Font("Apple Mono", Font.CENTER_BASELINE, 28);
	Font memFont = new Font("Apple Mono", Font.CENTER_BASELINE, 40);
	
	//The borders used for the text fields and zoom buttons
	Border textBorder = BorderFactory.createLineBorder(jElementColor);
	Border zoomBorder = BorderFactory.createLineBorder(jLabelColor, 2);
	
	boolean repOnce = true;
	boolean mousePressed = false;
	
	Equation equation = new Equation(); //The Equation object used to graph the user's equation. Initialized as a blank
									   //equation here, and initialized using all required information if the user enters
									   //an equation.
	
	Timer timer = new Timer(10, this); //Timer used for the insane color mode
	
	int colorSpeed = 7; //This is the speed at which the colors cycle in the insane color mode. A higher
					   //value results in faster cycling
	
	/*
	 * This is the constructor. Its main function is to initialize the entire GUI. Inside, it places every GUI component
	 * in its correct place and sets the correct size for every element.
	 *///----------------------------------------------1
	public Window() {
		
		//Initialize all MyColors used for the insane color mode.
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
		
		/*
		 * Sets the default x/y range values.
		 *///----------------------------------------------1a
		xMin = -10.000;
		xMax = 10.000;
		yMin = -10.000;
		yMax = 10.000;
		//----------------------------------------------1a
		
		/*
		 * Sets the x/y range text field values to the default x/y ranges
		 *///----------------------------------------------1b
		xMinIn = new JTextField(Double.toString(xMin));
		xMaxIn = new JTextField(Double.toString(xMax));
		yMinIn = new JTextField(Double.toString(yMin));
		yMaxIn = new JTextField(Double.toString(yMax));
		//----------------------------------------------1b
		
		
		instruction.setBounds(0, 0, controlSize, 85);
		
		/*
		 * Sets the sizes and locations for the x/y range labels as well as the x/y
		 * range input text fields. Every one of these elements has its position set
		 * in relations to xMaxLab, ensuring that all of them have a consistent location
		 * in relation to each othe, and making it easier to edit the position of this
		 * section of the GUI
		 *///----------------------------------------------1c
		xMaxLab.setBounds((controlSize - (90 + (controlSize / 2))) / 2, 100, rangeLabelSize, 30);
		xMinLab.setBounds(xMaxLab.getX(), xMaxLab.getY() + 40, rangeLabelSize, 30);
		yMaxLab.setBounds(xMaxLab.getX(), xMinLab.getY() + 40, rangeLabelSize, 30);
		yMinLab.setBounds(xMaxLab.getX(), yMaxLab.getY() + 40, rangeLabelSize, 30);
		
		xMaxIn.setBounds(xMaxLab.getX() + 70, xMaxLab.getY(), (controlSize / 2) + 15, 30);
		xMinIn.setBounds(xMinLab.getX() + 70, xMinLab.getY(), (controlSize / 2) + 15, 30);
		yMaxIn.setBounds(yMaxLab.getX() + 70, yMaxLab.getY(), (controlSize / 2) + 15, 30);
		yMinIn.setBounds(yMaxLab.getX() + 70, yMinLab.getY(), (controlSize / 2) + 15, 30);
		//----------------------------------------------1c
		
		/*
		 * Here, the size and location of the insane color mode controls are set.
		 * They are also determined by the location of xMaxLab in order to ensure
		 * consistent spacing and ease of editing.
		 *///----------------------------------------------1d
		crazyLab.setBounds(yMinLab.getX() + 10, yMinLab.getY() + 45, 170, 30);
		crazyColorOption.setBounds(crazyLab.getX() + 160, crazyLab.getY() + 2, 25, 25);
		//----------------------------------------------1d
		
		/*
		 * Here, the size and location of the zoom in/out buttons are set. These are set
		 * in relation to the insane color mode controls in order to ensure consistent
		 * spacing and ease of editing.
		 *///----------------------------------------------1e
		zoomIn.setBounds((controlSize / 2) - 110, crazyLab.getY() + 45, 50, 50);
		zoomOut.setBounds((controlSize / 2) + 60, crazyLab.getY() + 45, 50, 50);
		//----------------------------------------------1e
		
		/*
		 * Adds all controls whose bounds were set above to the controls panel
		 *///----------------------------------------------1f
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
		//----------------------------------------------1f
		
		controls.setLayout(null);
		
		/*
		 * Adds all other elements to panel.
		 *///----------------------------------------------1g
		panel.add(controls);
		panel.add(equationIn);
		panel.add(yEquals);
		panel.add(forwardMem);
		panel.add(backwardMem);
		//----------------------------------------------1g
		
		/*
		 * Adds action and mouse listener to all elements that need to detect a click or mouse input.
		 *///----------------------------------------------1h
		crazyColorOption.addActionListener(this);
		
		zoomIn.addMouseListener(this);
		zoomOut.addMouseListener(this);
		
		forwardMem.addMouseListener(this);
		backwardMem.addMouseListener(this);
		//----------------------------------------------1h
		
		/*
		 * Sets the size of the remaining GUI elements
		 *///----------------------------------------------1i
		controls.setBounds(width + 1, 0, controlSize, height);
		yEquals.setBounds(0, height, 70, equationInHeight);
		equationIn.setBounds(yEquals.getWidth(), height, width + controlSize - yEquals.getWidth() - (2 * (equationInHeight - 15)), equationInHeight);
		forwardMem.setBounds(width + controlSize - equationInHeight + 15, equationIn.getY(), equationInHeight - 15, equationInHeight);
		backwardMem.setBounds(forwardMem.getX() - forwardMem.getWidth(), forwardMem.getY(), forwardMem.getWidth(), forwardMem.getHeight());
		//----------------------------------------------1i
		
		/*
		 * This sets the the properties of the window itself, created through the inheritance
		 * of the JFrame class. All necessary properties such as size, mouse input listener,
		 * and resizability are set here.
		 *///----------------------------------------------1j
		super.setName("Graphing Calculator");
		super.setSize(width + controlSize, height + equationInHeight + super.getInsets().top);
		super.setContentPane(panel);
		super.setLayout(null);
		super.addMouseListener(this);
		super.setResizable(false);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setVisible(true);
		//----------------------------------------------1j
		
		/*
		 * Sets the values of the static variables as well as insetsTop.
		 *///----------------------------------------------1k
		insetsTop = super.getInsets().top;
		windowX = super.getX();
		windowY = super.getY();
		windowHeight = super.getHeight();
		windowWidth = super.getWidth();
		//----------------------------------------------1k
		
		/*
		 * Reset size to account for the top bar height
		 *///----------------------------------------------11k
		super.setSize(width + controlSize, height + equationInHeight + super.getInsets().top);
		//----------------------------------------------11k

		dPadCircle = new Ellipse2D.Float(width + dPadSize, yMinIn.getY() + 190, controlSize - (2 * dPadSize), controlSize - (2 * dPadSize));
		
		equationIn.grabFocus();
		makeNewEquation();
		
	}
	//----------------------------------------------1
	
	/*
	 * This paint() method paints the plane once the user enters an equation, or moves the plane by zooming, panning,
	 * or manually setting the x/y ranges.
	 *///----------------------------------------------2
	public void paint(Graphics g) {
		super.paint(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(2));
		
		/*
		 * Paints the direction pad circle used for panning
		 *///----------------------------------------------2a
		g2.setColor(backgroundColor);
		g2.fill(dPadCircle);
		//----------------------------------------------2a
		
		/*
		 * Draws the extra lines on the direction pad, those being the highlighted circle border and
		 * the crosshair dividing lines.
		 *///----------------------------------------------2b
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
		//----------------------------------------------2b
		
		/*
		 * Draws a new plane using the DrawPlane class. This class takes into account the user's equation, x/yNestedGridSizes,
		 * and other values such as plane dimensions that aren't passed to its constructor. Then, it repaints the bottom
		 * equation input GUI elements because very steep lines can be drawn over these elements if they do not fit in the
		 * set ranges for the plane window.
		 *///----------------------------------------------2c
		new DrawPlane(equation, g2, insetsTop, xNestedGridSize, yNestedGridSize);
		yEquals.repaint();
		equationIn.repaint();
		//----------------------------------------------2c
	}
	//----------------------------------------------2
	
	/*
	 * This actionPerformed() method repeats each time the timer triggers (every 10 milliseconds). This is used to cycle the GUI colors
	 * when insane color mode is toggled on.
	 *///----------------------------------------------3
	@Override
	public void actionPerformed(ActionEvent e) {
		
		/*
		 * If the insane color mode check is ticked, sets crazyColorMode to the absolute value of itself minus 1.
		 * This will set it to 1 from a value of 0 or 2, and set it to 0 from a value of 1.
		 *///----------------------------------------------3a
		if(e.getSource() == crazyColorOption) {
			crazyColorMode = Math.abs(crazyColorMode - 1);
		}
		//----------------------------------------------3a
		
		/*
		 * If crazyColorMode is equal to 1, it means the insane color mode has been toggled on.
		 *///----------------------------------------------3b
		if(crazyColorMode == 1) {
			//----------------------------------------------3b
			
			/*
			 * This increments every MyColor, changing the color based on the value of colorSpeed. This slight incrementation
			 * over many repetitions, one every 10 milliseconds, is what causes the smooth rainbow cycling of colors.
			 *///----------------------------------------------3c
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
			//----------------------------------------------3c
			
			/*
			 * This sets each GUI element to its appropriate MyColor
			 *///----------------------------------------------3d
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
			//----------------------------------------------3d
		}
		
		/*
		 * If crazyColorMode is set to 0, it means that the insane color mode is toggled off. When this
		 * is true, it sets every GUI element to its appropriate standard color and font. crazyColorMode will always
		 * be set to 0 by default upon initialization of this class, so this is what sets the initial colors
		 * for the GUI when this application is launched.
		 *///----------------------------------------------3e
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
			
			crazyColorMode = 2; //set crazyColorMode to 2 to prevent this application from wasting resources
							    //needlessly resetting the GUI colors and fonts to the same values every 10 milliseconds
			super.repaint();
		}
		//----------------------------------------------3e
		repOnce = false;
		
	}
	//----------------------------------------------3
	
	/*
	 * This makeNewEquation() method is called every time the plane needs to be repainted. This is when a new equation
	 * is entered, or when the plane window is altered by zooming, panning, or manually editing the x/y ranges.
	 * First, it gets the equation from the equationIn text field and gets the x/y ranges from their respective text fields. 
	 * Next, it calculates the value for x/yNestedGridSize. Finally, it formats the x/y ranges to display 3 decimal places 
	 * (for visual appeal) and repaints the JFrame. Repainting will trigger the paint() method above, causing the plane to 
	 * be repainted.
	 *///----------------------------------------------4
	public void makeNewEquation() {
		
		/*
		 * Gets the equation String from equationIn.
		 *///----------------------------------------------4a
		eq = equationIn.getText();
		//----------------------------------------------4a
		
		/*
		 * Gets the x/y ranges from their respective text fields.
		 *///----------------------------------------------4b
		if(!xMaxIn.getText().equals("")) {
			xMax = Double.parseDouble(xMaxIn.getText());
		}
		if(!xMinIn.getText().equals("")) {
			xMin = Double.parseDouble(xMinIn.getText());
		}
		if(!yMaxIn.getText().equals("")) {
			yMax = Double.parseDouble(yMaxIn.getText());
		}
		if(!yMinIn.getText().equals("")) {
			yMin = Double.parseDouble(yMinIn.getText());
		}
		//----------------------------------------------4b
		
		/*
		 * This section tests if each max range is less than or equal to its respective mid range. If this
		 * is true, it triggers a range error, because having a maximum value that is small than a minimum
		 * value is impossible and it breaks the various formulas in this application that use these values. 
		 *///----------------------------------------------4c
		if(xMax <= xMin) {
			new ErrorWindow("Range Error", "'X Max' must be greater than 'X Min'");
		}
		else if(yMax <= yMin) {
			new ErrorWindow("Range Error", "'Y Max' must be greater than 'Y Min'");
		}
		//----------------------------------------------4c
		
		/*
		 * If no error is triggered, calculate x/yNestedGridSize, format the displayed x/y
		 * ranges to 3 decimal places, and repaint.
		 *///----------------------------------------------4d
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
		//----------------------------------------------4d
	}
	//----------------------------------------------4
	
	/*
	 * This getGridSize() method computes the correct grid size multiplier for x/yNestedGridSize, using the the x/y ranges. 
	 * An average max min range from 10 to 100 would set nestedGridSizeto 1, an average max min range from 10 to 100 would 
	 * set nestedGridSize to 10, and an average max min range from 0.1 to to 1 would set nestedGridSize to 0.1. This is used 
	 * in the DrawPlane class to ensure that the grid resizes to fit a zoomed in or zoomed out plane window.
	 *///----------------------------------------------5
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
	//----------------------------------------------5
	
	/*
	 * This roundRanges() method formats the the x/y ranges in their respective text fields to be
	 * displayed with three decimal points, as long as their average value is greater than of equal
	 * to 0.001, and every value is greater than or equal to 0.001. This means it will display the
	 * full unformatted value if the plane window is displaying a total length of less than 0.002
	 * or if one of the values is less than 0.001.
	 *///----------------------------------------------6
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
	//----------------------------------------------6
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	/*
	 * This mousePressed() method is triggered if the mouse is clicked within the window. This method will then perform
	 * different actions depending on which element within the window it has detected a click on. Zooming, panning,
	 * click-to-graph, and memory button clicks are all detected in this method.
	 *///----------------------------------------------7
	@Override
	public void mousePressed(MouseEvent e) {
		
		/*
		 * Only detects clicks if the insane color mode is toggled off.
		 *///----------------------------------------------7a
		if(crazyColorMode != 1) {
		//----------------------------------------------7a
			
			/*
			 * If the mouse is clicked and the cursor is inside the plane window, and if no buttons are being
			 * clicked, draw the equation in the equationIn text field. If there is nothing in equationIn, it
			 * still attempts to draw a new equation by calling makeNewEquation(), but that method will initialize
			 * a blank Equation class resulting in nothing being drawn.
			 *///----------------------------------------------7b
			if(!e.getComponent().equals(zoomIn) && !e.getComponent().equals(zoomOut) && !e.getComponent().equals(forwardMem) && !e.getComponent().equals(backwardMem) && e.getX() <= width && e.getY() <= height) {
				makeNewEquation();
			}
			//----------------------------------------------7b
			
			/*
			 * If the mouse is clicked and the cursor is inside the direction pad circle, it calculates one shift value 
			 * named shiftNum for the x ranges and another one for the y ranges. These values are computed based on the
			 * distance of the cursor from the x or y mid-lines of the d-pad, as well as the distance of the cursor from 
			 * the d-pad center. These values are then applied to their respective x/y ranges 
			 *///----------------------------------------------7c
			else if(!e.getComponent().equals(zoomIn) && !e.getComponent().equals(zoomOut) && !e.getComponent().equals(forwardMem) && !e.getComponent().equals(backwardMem) && dPadCircle.contains(e.getX(), e.getY())) {
				
				Point dPadCenter = new Point(dPadCircle.getX() + (dPadCircle.getWidth() / 2), dPadCircle.getY() + (dPadCircle.getHeight() / 2));
				double shiftNum = 0.0;
				
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
			//----------------------------------------------7c
			
			/*
			 * If the mouse is clicked on the forwardMem button (the right arrow that moves forward
			 * in memory), it first sets the button to a lighter color to give feedback to the user,
			 * then attempts to move the memory index memIndex forward in memory. It only moves memIndex
			 * forward if it is not already on the last index.
			 *///----------------------------------------------7d
			else if(e.getComponent().equals(forwardMem)) {
				forwardMem.setBackground(Color.decode("#555555"));
				forwardMem.repaint();
				if(memIndex < memLog.size() - 1) {
					memIndex++;
					equationIn.setText(memLog.get(memIndex).equationString);
				}
				
			}
			//----------------------------------------------7d
			
			/*
			 * If the mouse is clicked on the backwardMem button (the left arrow that moves backward
			 * in memory), it first sets the button to a lighter color to give feedback to the user,
			 * then attempts to move the memory index memIndex backward in memory. It only moves memIndex
			 * backward if it is not already on the first index.
			 *///----------------------------------------------7e
			else if(e.getComponent().equals(backwardMem)) {
				backwardMem.setBackground(Color.decode("#555555"));
				backwardMem.repaint();
				if(memIndex > 0) {
					memIndex--;
					equationIn.setText(memLog.get(memIndex).equationString);
				}
				
			}
			//----------------------------------------------7e
			
			/*
			 * If the zoom in or out button is clicked, it increases the ranges by a value proportional
			 * to the current average range. This causes an even-looking zoom distance no matter
			 * how zoomed in or out the plane already is.
			 *///----------------------------------------------7f
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
			//----------------------------------------------7f
		}
	}
	//----------------------------------------------7
	
	/*
	 * This method mouseRelease() detects the mouse is no longer pressed. 
	 * If the mouse is no longer pressed down on a button, that button
	 * is reset to its default color.
	 *///----------------------------------------------8
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
	//----------------------------------------------8
	
	/*
	 * This method mouseEntered() detects when the mouse has entered an element, any one
	 * of the buttons in this case. If it detects that the cursor has entered a button,
	 * it highlights that button in a brighter color in order to give the user feedback.
	 *///----------------------------------------------9
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
	//----------------------------------------------9
	
	/*
	 * This method mouseExited() detects when the mouse has exited an element, any one
	 * of the buttons in this case. If it detects that the cursor has exited a button,
	 * it resets that button to its default color.
	 *///----------------------------------------------10
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
	//----------------------------------------------10
	
}

