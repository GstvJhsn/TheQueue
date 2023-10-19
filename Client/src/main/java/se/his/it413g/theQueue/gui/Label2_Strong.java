package se.his.it413g.theQueue.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

/**
 * A label with strong text that can be added to a panel.
 * 
 * @author jacobmilton
 *
 */
@SuppressWarnings("serial")
public class Label2_Strong extends JLabel implements GUI {

	Label2_Strong() {
		format();
	}

	@Override
	public void format() {
		this.setFont(new Font("FUTURA", Font.PLAIN, 20));
		this.setForeground(new Color(0x1DA1F2));
		this.setHorizontalAlignment(Label2_Strong.CENTER);
		this.setText("Set text");
	}

	@Override
	public void add() {
	}

}
