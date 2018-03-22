import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ErrorWindow extends JFrame implements MouseListener{
	int width;
	int height;
	JPanel panel = new JPanel();
	JLabel exclamation = new JLabel("!", SwingConstants.CENTER);
	JLabel ok = new JLabel("OK", SwingConstants.CENTER);
	JLabel errorName = new JLabel("", SwingConstants.CENTER);
	JTextArea message = new JTextArea();
	Color backColor;
	Color textColor;
	Color exColor;
	Color okColor;
	Color okMouseColor;
	Color okClickColor;
	Color nameColor;
	
	Font exFont = new Font("Apple Mono", Font.CENTER_BASELINE, 200);
	Font textFont = new Font("Century Gothic", Font.PLAIN, 31);
	Font okFont = new Font("Apple Mono", Font.CENTER_BASELINE, 30);
	Font nameFont = new Font("Century Gothic", Font.CENTER_BASELINE, 33);
	Border okBorder;
	
	String errorText;
	String nameText;
	public ErrorWindow(String nameText, String errorText) {
		width = 550;
		height = 300;
		
		this.errorText = errorText;
		this.nameText = nameText;
		
		super.setSize(width, height);
		super.setLocation(((Window.windowX + Window.windowWidth) / 2) - (width / 2), ((Window.windowY + Window.windowHeight) / 2) - (height / 2));
		super.setContentPane(panel);
		super.setVisible(true);
		super.setResizable(false);
		
		backColor = Color.decode("#c1291b");
		textColor = Color.decode("#dbdbdb");
		exColor = Color.decode("#911c11");
		okColor = Color.decode("#a82215");
		okMouseColor = Color.decode("#f22715");
		okClickColor = Color.decode("#ad2316");
		nameColor = Color.decode("#aa2317");
		
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
		
		panel.add(errorName);
		panel.add(message);
		panel.add(exclamation);
		exclamation.add(ok);
		
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		ok.setOpaque(true);
		ok.repaint();
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		ok.setOpaque(false);
		ok.repaint();
		super.dispose();
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		okBorder = BorderFactory.createLineBorder(okMouseColor, 2);
		ok.setBorder(okBorder);
		ok.setForeground(okMouseColor);
		ok.repaint();
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		okBorder = BorderFactory.createLineBorder(okColor, 2);
		ok.setBorder(okBorder);
		ok.setForeground(backColor); 
		ok.repaint();
		
	}
}
