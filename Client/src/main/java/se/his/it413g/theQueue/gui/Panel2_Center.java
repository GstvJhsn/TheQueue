package se.his.it413g.theQueue.gui;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * The center panel that is added to the main frame.
 * 
 * @author jacobmilton
 *
 */
@SuppressWarnings("serial")
public class Panel2_Center extends JPanel implements GUI {

	private JLabel[] queueList;
	private Label2_Strong headingStudents;
	private Label2_Strong headingSupervisors;
	private Label1_Soft student;
	private Label1_Soft supervisor;

	protected Panel2_Center() {
		format();
		add();
	}

	@Override
	public void format() {
		this.setLayout(new GridLayout(6, 2, 1, 1));
		this.setBackground(new Color(0x14171A));
	}

	@Override
	public void add() {
		setQueueList(new JLabel[12]);

		setHeadingStudents(new Label2_Strong());
		this.add(getHeadingStudents());
		getQueueList()[0] = getHeadingStudents();
		getHeadingStudents().setText("STUDENTS");

		setHeadingSupervisors(new Label2_Strong());
		this.add(getHeadingSupervisors());
		getQueueList()[1] = getHeadingSupervisors();
		getHeadingSupervisors().setText("SUPERVISORS");

		for (int i = 2; i < 12; i += 2) {
			setStudent(new Label1_Soft());
			this.add(getStudent());
			getQueueList()[i] = getStudent();

			setSupervisor(new Label1_Soft());
			this.add(getSupervisor());
			getQueueList()[i + 1] = getSupervisor();
		}
	}

	public JLabel[] getQueueList() {
		return queueList;
	}

	private void setQueueList(JLabel[] queueList) {
		this.queueList = queueList;
	}

	private Label2_Strong getHeadingStudents() {
		return headingStudents;
	}

	private void setHeadingStudents(Label2_Strong headingStudents) {
		this.headingStudents = headingStudents;
	}

	private Label2_Strong getHeadingSupervisors() {
		return headingSupervisors;
	}

	private void setHeadingSupervisors(Label2_Strong headingSupervisors) {
		this.headingSupervisors = headingSupervisors;
	}

	private Label1_Soft getStudent() {
		return student;
	}

	private void setStudent(Label1_Soft student) {
		this.student = student;
	}

	private Label1_Soft getSupervisor() {
		return supervisor;
	}

	private void setSupervisor(Label1_Soft supervisor) {
		this.supervisor = supervisor;
	}

}
