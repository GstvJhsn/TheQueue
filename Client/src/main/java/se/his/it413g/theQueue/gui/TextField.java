package se.his.it413g.theQueue.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JTextField;

/**
 * A text field that is added to the top and bottom panel respectively.
 * 
 * @author jacobmilton
 *
 */
@SuppressWarnings("serial")
public class TextField extends JTextField implements GUI {

	TextField() {
		format();
	}

	@Override
	public void format() {
		this.setFont(new Font("FUTURA", Font.PLAIN, 20));
		this.setForeground(new Color(0x657786));
		this.setBackground(new Color(0x14171A));
		this.setHorizontalAlignment(TextField.CENTER);
		this.setCaretColor(new Color(0x1DA1F2));
	}

	@Override
	public void add() {
	}

}
