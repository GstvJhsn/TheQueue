package se.his.it413g.theQueue.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The bottom panel that is added to the main frame.
 * 
 * @author jacobmilton
 *
 */
@SuppressWarnings("serial")
public class Panel3_Bottom_Supervisor extends JPanel implements GUI {

	private TextField message;
	private Button1_Small helpNextButton;
	private JLabel[] statusList;
	private Label1_Soft statusLabel;

	public Panel3_Bottom_Supervisor() {
		format();
		add();
	}

	@Override
	public void format() {
		this.setLayout(new GridLayout(7, 1, 0, 0));
		this.setBackground(new Color(0x14171A));
		this.setPreferredSize(new Dimension(0, 300));
	}

	@Override
	public void add() {
		setMessage(new TextField());
		this.add(getMessage());
		getMessage().setText("Now I can help you");

		setHelpNextButton(new Button1_Small());
		this.add(getHelpNextButton());
		getHelpNextButton().setEnabled(false);
		getHelpNextButton().setText("Help Next");

		setStatusList(new JLabel[5]);
		for (int i = 0; i < 5; i++) {
			setStatusLabel(new Label1_Soft());
			this.add(getStatusLabel());
			getStatusList()[i] = getStatusLabel();
		}
	}

	public TextField getMessage() {
		return message;
	}

	private void setMessage(TextField message) {
		this.message = message;
	}

	public Button1_Small getHelpNextButton() {
		return helpNextButton;
	}

	private void setHelpNextButton(Button1_Small helpNextButton) {
		this.helpNextButton = helpNextButton;
	}

	public JLabel[] getStatusList() {
		return statusList;
	}

	private void setStatusList(JLabel[] statusList) {
		this.statusList = statusList;
	}

	private Label1_Soft getStatusLabel() {
		return statusLabel;
	}

	private void setStatusLabel(Label1_Soft statusLabel) {
		this.statusLabel = statusLabel;
	}

}
