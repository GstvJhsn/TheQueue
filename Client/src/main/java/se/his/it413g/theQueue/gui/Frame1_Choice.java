package se.his.it413g.theQueue.gui;

import java.awt.GridLayout;

import javax.swing.JFrame;

/**
 * The start frame of the GUI.
 * 
 * @author jacobmilton
 *
 */
@SuppressWarnings("serial")
public class Frame1_Choice extends JFrame implements GUI {

	private Button2_Big studentButton;
	private Button2_Big supervisorButton;

	public Frame1_Choice() {
		format();
	}

	@Override
	public void format() {
		this.setTitle("Student or Supervisor");
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setSize(600, 500);
		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.setLayout(new GridLayout(2, 1, 0, 0));
		add();
		this.setVisible(true);
	}

	@Override
	public void add() {
		setStudentButton(new Button2_Big());
		this.add(getStudentButton());
		getStudentButton().setText("STUDENT");
		getStudentButton().setToolTipText("Click here if you're a Student");

		setSupervisorButton(new Button2_Big());
		this.add(getSupervisorButton());
		getSupervisorButton().setText("SUPERVISOR");
		getSupervisorButton().setToolTipText("Click here if you're a Supervisor");
	}

	public Button2_Big getStudentButton() {
		return studentButton;
	}

	private void setStudentButton(Button2_Big studentButton) {
		this.studentButton = studentButton;
	}

	public Button2_Big getSupervisorButton() {
		return supervisorButton;
	}

	private void setSupervisorButton(Button2_Big supervisorButton) {
		this.supervisorButton = supervisorButton;
	}

}
