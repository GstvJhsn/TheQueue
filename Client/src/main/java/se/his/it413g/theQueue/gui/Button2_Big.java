package se.his.it413g.theQueue.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;

/**
 * A big button that can be added to a panel.
 * 
 * @author jacobmilton
 *
 */
@SuppressWarnings("serial")
public class Button2_Big extends JButton implements GUI {

	Button2_Big() {
		format();
	}

	@Override
	public void format() {
		this.setFont(new Font("FUTURA", Font.PLAIN, 50));
		this.setForeground(new Color(0x1DA1F2));
		this.setBackground(new Color(0x14171A));
		this.setOpaque(true);
		this.setBorderPainted(false);
		this.setText("Set text");
	}

	@Override
	public void add() {
	}

}
