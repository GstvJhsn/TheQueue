package se.his.it413g.theQueue;

/**
 * This class will start the application. Type command line arguments to specify
 * which ports (servers) to connect to.
 * 
 * @author jacobmilton
 *
 */
public class Run {

	public static void main(String[] args) {
		if (args.length < 1) {
			System.out.println("Please specify a URL");
			System.exit(0);
		}

		new StudentOrSupervisor(args);
	}

}
