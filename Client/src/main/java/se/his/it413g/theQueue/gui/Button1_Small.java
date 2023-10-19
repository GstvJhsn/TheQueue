package se.his.it413g.theQueue.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

/**
 * A small button that can be added to a panel.
 * 
 * @author jacobmilton
 *
 */
@SuppressWarnings("serial")
public class Button1_Small extends JButton implements GUI {

	Button1_Small() {
		format();
	}

	@Override
	public void format() {
		this.setFont(new Font("FUTURA", Font.PLAIN, 20));
		this.setForeground(new Color(0x1DA1F2));
		this.setBackground(new Color(0x14171A));
		this.setBorderPainted(true);
		this.setText("Set text");
	}

	@Override
	public void add() {
	}

}
