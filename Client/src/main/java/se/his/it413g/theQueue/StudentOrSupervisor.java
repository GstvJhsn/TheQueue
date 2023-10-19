package se.his.it413g.theQueue;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import se.his.it413g.theQueue.gui.Frame1_Choice;
import se.his.it413g.theQueue.gui.Frame2_Main;
import se.his.it413g.theQueue.gui.Panel3_Bottom_Student;
import se.his.it413g.theQueue.gui.Panel3_Bottom_Supervisor;

/**
 * This will start the first frame, with the option to choose Student or Supervisor client.
 * 
 * @author jacobmilton
 *
 */
public class StudentOrSupervisor implements ActionListener {

	private Frame2_Main mainFrame;
	private String[] arg;
	private Frame1_Choice startFrame;
	private Panel3_Bottom_Student bottomPanelStudent;
	private Panel3_Bottom_Supervisor bottomPanelSupervisor;
	private Boolean isStudent;

	StudentOrSupervisor(String[] arg) {
		setArg(arg);
		setStartFrame(new Frame1_Choice());
		setBottomPanelStudent(new Panel3_Bottom_Student());
		setBottomPanelSupervisor(new Panel3_Bottom_Supervisor());
		addActionListener();
	}

	private void addActionListener() {
		getStartFrame().getStudentButton().addActionListener(this);
		getStartFrame().getSupervisorButton().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		getStartFrame().dispose();
		setMainFrame(new Frame2_Main());

		if (e.getSource() == getStartFrame().getStudentButton()) {
			setIsStudent(true);
			getMainFrame().add(getBottomPanelStudent(), BorderLayout.SOUTH);
		}

		if (e.getSource() == getStartFrame().getSupervisorButton()) {
			setIsStudent(false);
			getMainFrame().add(getBottomPanelSupervisor(), BorderLayout.SOUTH);
		}

		new TheQueue(getArg(), getMainFrame(), getBottomPanelStudent(), getBottomPanelSupervisor(), getIsStudent());
	}

	private Frame2_Main getMainFrame() {
		return mainFrame;
	}

	private void setMainFrame(Frame2_Main mainFrame) {
		this.mainFrame = mainFrame;
	}

	private String[] getArg() {
		return arg;
	}

	private void setArg(String[] arg) {
		this.arg = arg;
	}

	private Frame1_Choice getStartFrame() {
		return startFrame;
	}

	private void setStartFrame(Frame1_Choice startFrame) {
		this.startFrame = startFrame;
	}

	private Panel3_Bottom_Student getBottomPanelStudent() {
		return bottomPanelStudent;
	}

	private void setBottomPanelStudent(Panel3_Bottom_Student bottomPanelStudent) {
		this.bottomPanelStudent = bottomPanelStudent;
	}

	private Panel3_Bottom_Supervisor getBottomPanelSupervisor() {
		return bottomPanelSupervisor;
	}

	private void setBottomPanelSupervisor(Panel3_Bottom_Supervisor bottomPanelSupervisor) {
		this.bottomPanelSupervisor = bottomPanelSupervisor;
	}

	private Boolean getIsStudent() {
		return isStudent;
	}

	private void setIsStudent(Boolean isStudent) {
		this.isStudent = isStudent;
	}

}
