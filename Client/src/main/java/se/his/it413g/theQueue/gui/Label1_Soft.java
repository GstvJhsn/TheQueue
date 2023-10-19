package se.his.it413g.theQueue.gui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

/**
 * A label with soft text that can be added to a panel.
 * 
 * @author jacobmilton
 *
 */
@SuppressWarnings("serial")
public class Label1_Soft extends JLabel implements GUI {

	public Label1_Soft() {
		format();
	}

	@Override
	public void format() {
		this.setFont(new Font("FUTURA", Font.PLAIN, 20));
		this.setForeground(new Color(0x657786));
		this.setHorizontalAlignment(Label1_Soft.CENTER);
		this.setText("-");
	}

	@Override
	public void add() {
	}

}
