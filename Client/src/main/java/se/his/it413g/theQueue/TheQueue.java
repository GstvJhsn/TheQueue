package se.his.it413g.theQueue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;
import org.zeromq.SocketType;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Socket;

import se.his.it413g.theQueue.gui.Frame2_Main;
import se.his.it413g.theQueue.gui.Panel3_Bottom_Student;
import se.his.it413g.theQueue.gui.Panel3_Bottom_Supervisor;

/**
 * This class handles the logic of the application: What happens when buttons
 * are pressed in the GUI, what will be sent or received from the server, and
 * what effect will that have.
 * 
 * @author jacobmilton
 *
 */
public class TheQueue implements ActionListener {

	private String[] arg;
	private Frame2_Main frame;
	private Panel3_Bottom_Student bottomPanelStudent;
	private Panel3_Bottom_Supervisor bottomPanelSupervisor;
	private Boolean isStudent;

	private ZContext context;
	private Socket socket;
	private String jsonString;

	private String subscribe;
	private String queueName;
	private String ticket;
	private String message;
	private JSONArray arrayStudents;
	private JSONArray arraySupervisors;
	private String nameStudent;
	private String nameStudentFirst;
	private String nameSupervisor;
	private String client;
	private String helpNext;
	private JSONObject jsonObject;

	public TheQueue(String[] arg, Frame2_Main frame, Panel3_Bottom_Student bottomPanelStudent,
			Panel3_Bottom_Supervisor bottomPanelSupervisor, Boolean isStudent) {
		setArg(arg);
		setFrame(frame);
		setIsStudent(isStudent);
		setBottomPanelStudent(bottomPanelStudent);
		setBottomPanelSupervisor(bottomPanelSupervisor);
		getFrame().getTopPanel().getQueueButton().addActionListener(this);
		connect();
		timer();
	}

	// Connect and subscribe to messages from server
	private void connect() {
		setContext(new ZContext());
		setSocket(getContext().createSocket(SocketType.DEALER));

		for (int i = 0; i < getArg().length; i++) {
			getSocket().connect(getArg()[i]);
		}

		setSubscribe("{\"subscribe\": true}");
		for (int i = 0; i < getArg().length; i++) {
			getSocket().send(getSubscribe());
		}
	}

	// Send messages to the servers
	private void send() {
		if (getJsonString() != null && getJsonString().equals("{}")) {
			getSocket().send("{}");
		}

		if (getTicket() != null) {
			for (int i = 0; i < getArg().length; i++) {
				getSocket().send(getTicket());
			}
			setTicket(null);
		}

		if (getHelpNext() != null) {
			for (int i = 0; i < getArg().length; i++) {
				getSocket().send(getHelpNext());
			}
			setHelpNext(null);
		}
	}

	// Receive messages from the servers
	private void receive() {
		byte[] response = getSocket().recv();
		setJsonString(new String(response, ZMQ.CHARSET));
		setJsonObject(new JSONObject(getJsonString()));
		System.out.println(getJsonString());

		if (getJsonObject().has("queue")) {

			// Reset names
			for (int i = 2; i < 12; i++) {
				getFrame().getCenterPanel().getQueueList()[i].setText("-");
			}

			// Print Student names
			setArrayStudents(getJsonObject().getJSONArray("queue"));
			int limit = getArrayStudents().length() > 5 ? 5 : getArrayStudents().length();

			for (int i = 0; i < limit; i++) {
				setNameStudent(getArrayStudents().getJSONObject(i).getString("name"));
				getFrame().getCenterPanel().getQueueList()[i + i + 2].setText(getNameStudent());
			}

			// Print Supervisor names
			setArraySupervisors(getJsonObject().getJSONArray("supervisors"));
			limit = getArraySupervisors().length() > 5 ? 5 : getArraySupervisors().length();

			for (int i = 0; i < limit; i++) {
				setNameSupervisor(getArraySupervisors().getJSONObject(i).getString("name"));
				String status = getArraySupervisors().getJSONObject(i).getString("status");

				if (status.equals("available")) {
					getFrame().getCenterPanel().getQueueList()[i + i + 3].setText(getNameSupervisor());
				}

				if (status.equals("occupied")) {
					JSONObject ticket = getArraySupervisors().getJSONObject(i).getJSONObject("client");
					setClient(ticket.getString("name"));

					getBottomPanelStudent().getStatusList()[i]
							.setText(getNameSupervisor() + " is currently helping " + getClient());
					getBottomPanelSupervisor().getStatusList()[i]
							.setText(getNameSupervisor() + " is currently helping " + getClient());
				}

				// Print message to Client
				if (getQueueName().equals(getClient())) {
					String message = getArraySupervisors().getJSONObject(i).getString("clientMessage");
					getBottomPanelStudent().getMessage().setText(message);
				}
			}
			getFrame().repaint();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {

		// Queue button is pressed
		if (e.getSource() == getFrame().getTopPanel().getQueueButton()) {
			setQueueName(getFrame().getTopPanel().getQueueName().getText());

			if (getArrayStudents().length() < 5 && getIsStudent()) {
				setTicket("{\"enterQueue\": true, \"name\": \"" + getQueueName() + "\"}");
			}

			if (getArraySupervisors().length() < 5 && getIsStudent().equals(false)) {
				setTicket("{\"enterSupervisor\": true, \"name\": \"" + getQueueName() + "\"}");
				getBottomPanelSupervisor().getHelpNextButton().setEnabled(true);
				getBottomPanelSupervisor().getHelpNextButton().addActionListener(this);
			}

			getFrame().getTopPanel().getQueueName().setEnabled(false);
			getFrame().getTopPanel().getQueueButton().setEnabled(false);
		}

		// Help next button is pressed
		if (e.getSource() == getBottomPanelSupervisor().getHelpNextButton()) {
			if (getArrayStudents().length() > 0) {
				setMessage(getBottomPanelSupervisor().getMessage().getText());

				setHelpNext("{\"helpNext\": true, \"name\": \"" + getQueueName() + "\", \"clientMessage\": \""
						+ getMessage() + "\"}");
			}
		}
		send();
	}

	// Timer
	class TimerListen extends TimerTask {
		public static int i = 1;

		public void run() {
			send();
			receive();
		}
	}

	private void timer() {
		Timer timer = new Timer();

		TimerTask task = new TimerListen();

		timer.schedule(task, 0, 1000);
	}

	// Getters and Setters
	public String[] getArg() {
		return arg;
	}

	public void setArg(String[] arg) {
		this.arg = arg;
	}

	public Frame2_Main getFrame() {
		return frame;
	}

	public void setFrame(Frame2_Main frame) {
		this.frame = frame;
	}

	public Panel3_Bottom_Student getBottomPanelStudent() {
		return bottomPanelStudent;
	}

	public void setBottomPanelStudent(Panel3_Bottom_Student bottomPanelStudent) {
		this.bottomPanelStudent = bottomPanelStudent;
	}

	public Panel3_Bottom_Supervisor getBottomPanelSupervisor() {
		return bottomPanelSupervisor;
	}

	public void setBottomPanelSupervisor(Panel3_Bottom_Supervisor bottomPanelSupervisor) {
		this.bottomPanelSupervisor = bottomPanelSupervisor;
	}

	public Boolean getIsStudent() {
		return isStudent;
	}

	public void setIsStudent(Boolean isStudent) {
		this.isStudent = isStudent;
	}

	public ZContext getContext() {
		return context;
	}

	public void setContext(ZContext context) {
		this.context = context;
	}

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}

	public String getJsonString() {
		return jsonString;
	}

	public void setJsonString(String jsonString) {
		this.jsonString = jsonString;
	}

	public String getSubscribe() {
		return subscribe;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}

	public String getQueueName() {
		return queueName;
	}

	public void setQueueName(String queueName) {
		this.queueName = queueName;
	}

	public String getTicket() {
		return ticket;
	}

	public void setTicket(String ticket) {
		this.ticket = ticket;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public JSONArray getArrayStudents() {
		return arrayStudents;
	}

	public void setArrayStudents(JSONArray arrayStudents) {
		this.arrayStudents = arrayStudents;
	}

	public JSONArray getArraySupervisors() {
		return arraySupervisors;
	}

	public void setArraySupervisors(JSONArray arraySupervisors) {
		this.arraySupervisors = arraySupervisors;
	}

	public String getNameStudent() {
		return nameStudent;
	}

	public void setNameStudent(String nameStudent) {
		this.nameStudent = nameStudent;
	}

	public String getNameStudentFirst() {
		return nameStudentFirst;
	}

	public void setNameStudentFirst(String nameStudentFirst) {
		this.nameStudentFirst = nameStudentFirst;
	}

	public String getNameSupervisor() {
		return nameSupervisor;
	}

	public void setNameSupervisor(String nameSupervisor) {
		this.nameSupervisor = nameSupervisor;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getHelpNext() {
		return helpNext;
	}

	public void setHelpNext(String helpNext) {
		this.helpNext = helpNext;
	}

	public JSONObject getJsonObject() {
		return jsonObject;
	}

	public void setJsonObject(JSONObject jsonObject) {
		this.jsonObject = jsonObject;
	}

}
