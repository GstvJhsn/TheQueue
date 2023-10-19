package se.his.it413g.theQueue.gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * The frame of the main application.
 * 
 * @author jacobmilton
 *
 */
@SuppressWarnings("serial")
public class Frame2_Main extends JFrame implements GUI {

	private Panel1_Top topPanel;
	private Panel2_Center centerPanel;

	public Frame2_Main() {
		format();
	}

	@Override
	public void format() {
		this.setTitle("The Queue");
		this.setSize(600, 800);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(new BorderLayout());
		add();
		this.setVisible(true);
	}

	@Override
	public void add() {
		setTopPanel(new Panel1_Top());
		setCenterPanel(new Panel2_Center());
		this.add(getTopPanel(), BorderLayout.NORTH);
		this.add(getCenterPanel(), BorderLayout.CENTER);
	}

	public Panel1_Top getTopPanel() {
		return topPanel;
	}

	private void setTopPanel(Panel1_Top topPanel) {
		this.topPanel = topPanel;
	}

	public Panel2_Center getCenterPanel() {
		return centerPanel;
	}

	private void setCenterPanel(Panel2_Center centerPanel) {
		this.centerPanel = centerPanel;
	}

}
