package admin;

import java.io.IOException;
/**
 * Admin Thread class to run the server
 * @author migg_
 *
 */
public class AdminThread extends Thread {
	/**
	 * Override the run method from the Thread superclass	
	 */
	// You need to override the run() to put the code that will run in this thread
	public void run() {
		// Create an instance of a AdminService
		// Start the AdminService on port 6666 (which will not return until the Shutdown Command is received)
		// and then on exit clean everything up which will exit this thread
		AdminService server = new AdminService();
		try {
			server.start(6666);
			server.cleanup();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
