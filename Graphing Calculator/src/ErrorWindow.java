import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ErrorWindow extends JFrame implements MouseListener{
	
	//Dimensions of the error window
	int width;
	int height;
	
	JPanel panel = new JPanel(); //the JPanel on which every GUI element is placed
	
	JLabel exclamation = new JLabel("!", SwingConstants.CENTER); //Displays an exclamation point on the left. Purely for aesthetics
	JLabel ok = new JLabel("OK", SwingConstants.CENTER); //This is the "OK" button that closes the error window when clicked
	JLabel errorName = new JLabel("", SwingConstants.CENTER); //This displays the category of error on the top of the error window.
	JTextArea message = new JTextArea(); //This displays the error message in the main area on the window
	
	//All used colors
	Color backColor;
	Color textColor;
	Color exColor;
	Color okColor;
	Color okMouseColor;
	Color okClickColor;
	Color nameColor;
	
	//All used fonts
	Font exFont = new Font("Apple Mono", Font.CENTER_BASELINE, 200);
	Font textFont = new Font("Century Gothic", Font.PLAIN, 31);
	Font okFont = new Font("Apple Mono", Font.CENTER_BASELINE, 30);
	Font nameFont = new Font("Century Gothic", Font.CENTER_BASELINE, 33);
	Border okBorder;
	
	String errorText; //Stores the specified error message. Used by JTextArea message
	String nameText; //Stores the error category. Used by JLabel errorName.
	
	/*
	 * This is the constructor. Its purpose is to build the error window, placing the GUI elements
	 * and setting their attributes.
	 *///----------------------------------------------1
	public ErrorWindow(String nameText, String errorText) {
		
		/*
		 * Set dimensions for the window
		 *///----------------------------------------------1a
		width = 550;
		height = 300;
		//----------------------------------------------1a
		
		/*
		 * Define errorText and nameText
		 *///----------------------------------------------1b
		this.errorText = errorText;
		this.nameText = nameText;
		//----------------------------------------------1b
		
		/*
		 * Set the window's attributes, such as location, dimensions, and resizability.
		 *///----------------------------------------------1c
		super.setSize(width, height);
		super.setLocation(((Window.windowX + Window.windowWidth) / 2) - (width / 2), ((Window.windowY + Window.windowHeight) / 2) - (height / 2));
		super.setContentPane(panel);
		super.setVisible(true);
		super.setResizable(false);
		//----------------------------------------------1c
		
		/*
		 * Define every color used for the window
		 *///----------------------------------------------1d
		backColor = Color.decode("#c1291b");
		textColor = Color.decode("#dbdbdb");
		exColor = Color.decode("#911c11");
		okColor = Color.decode("#a82215");
		okMouseColor = Color.decode("#f22715");
		okClickColor = Color.decode("#ad2316");
		nameColor = Color.decode("#aa2317");
		//----------------------------------------------1d
		
		/*
		 * Build the window layout, placing every element in its place and setting
		 * its attributes.
		 *///----------------------------------------------1e
		okBorder = BorderFactory.createLineBorder(okColor, 3);
		
		panel.setLayout(null);
		panel.setBackground(backColor);
		
		exclamation.setBounds(0, 0 - (3 * super.getInsets().top), 110, height + (3 * super.getInsets().top));
		exclamation.setBackground(exColor);
		exclamation.setForeground(backColor);
		exclamation.setFont(exFont);
		exclamation.setOpaque(true);
		
		errorName.setBounds(exclamation.getWidth(), 0, width - exclamation.getWidth(), 50);
		errorName.setText(nameText);
		errorName.setForeground(textColor);
		errorName.setBackground(nameColor);
		errorName.setOpaque(true);
		errorName.setFont(nameFont);
		
		message.setBounds(exclamation.getWidth() + 10, errorName.getHeight(), width - exclamation.getWidth() - 20, height);
		message.setText(errorText);
		message.setLineWrap(true);
		message.setWrapStyleWord(true);
		message.setEditable(false);
		message.setOpaque(false);
		message.setSelectedTextColor(textColor);
		message.setSelectionColor(backColor);
		message.setFocusable(false);
		message.setFont(textFont);
		message.setForeground(textColor);
		
		ok.setBounds(0, height - 50 + (2 * super.getInsets().top), exclamation.getWidth(), 50);
		ok.setFont(okFont);
		ok.setBackground(okClickColor);
		ok.setBorder(okBorder);
		ok.setForeground(backColor);
		ok.addMouseListener(this);
		//----------------------------------------------1e
		
		/*
		 * Add the GUI elements to the window.
		 *///----------------------------------------------1f
		panel.add(errorName);
		panel.add(message);
		panel.add(exclamation);
		exclamation.add(ok);
		//----------------------------------------------1f
	}
	//----------------------------------------------1
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}
	
	/*
	 * This method mousePressed() detects if the OK button has been pressed and
	 * sets it to a lighter color, giving the user feedback.
	 *///----------------------------------------------2
	@Override
	public void mousePressed(MouseEvent e) {
		ok.setOpaque(true);
		ok.repaint();
		
	}
	//----------------------------------------------2
	
	/*
	 * This method mouseReleased() detects then the mouse is no longer being pressed on the
	 * OK button, causing it to close this error window.
	 *///----------------------------------------------3
	@Override
	public void mouseReleased(MouseEvent e) {
		ok.setOpaque(false);
		ok.repaint();
		super.dispose();
		
	}
	//----------------------------------------------3
	
	/*
	 * This method mouseEntered() detects when the mouse cursor is touching the OK button,
	 * causing it to highlight the button in a lighter color to give the user feedback.
	 *///----------------------------------------------4
	@Override
	public void mouseEntered(MouseEvent e) {
		okBorder = BorderFactory.createLineBorder(okMouseColor, 2);
		ok.setBorder(okBorder);
		ok.setForeground(okMouseColor);	
	}
	//----------------------------------------------4
	
	/*
	 * This method mouseExited() detects when the mouse cursor is no longer touching the OK button,
	 * causing it to reset the button to its default color to give the user feedback.
	 *///----------------------------------------------5
	@Override
	public void mouseExited(MouseEvent e) {
		okBorder = BorderFactory.createLineBorder(okColor, 2);
		ok.setBorder(okBorder);
		ok.setForeground(backColor); 
		ok.repaint();
	}
	//----------------------------------------------5
}
