package se.his.it413g.theQueue.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPanel;

/**
 * The top panel that is added to the main frame.
 * 
 * @author jacobmilton
 *
 */
@SuppressWarnings("serial")
public class Panel1_Top extends JPanel implements GUI {

	private TextField queueName;
	private Button1_Small queueButton;

	protected Panel1_Top() {
		format();
		add();
	}

	@Override
	public void format() {
		this.setLayout(new GridLayout(2, 1));
		this.setBackground(new Color(0x14171A));
		this.setPreferredSize(new Dimension(0, 100));
	}

	@Override
	public void add() {
		setQueueName(new TextField());
		this.add(getQueueName());
		getQueueName().setText("Enter your name");

		setQueueButton(new Button1_Small());
		this.add(getQueueButton());
		getQueueButton().setText("Que Up");
	}

	public TextField getQueueName() {
		return queueName;
	}

	private void setQueueName(TextField queueName) {
		this.queueName = queueName;
	}

	public Button1_Small getQueueButton() {
		return queueButton;
	}

	private void setQueueButton(Button1_Small queueButton) {
		this.queueButton = queueButton;
	}

}
