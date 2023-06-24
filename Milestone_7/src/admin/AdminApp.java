package admin;
/**
 * Admin App class to call the AdminThread class
 * @author migg_
 *
 */
public class AdminApp {
	/**
	 * Test the server thread classes 
	 * @param args Argument for main method
	 * @throws InterruptedException Thrown in the thread classes if something bad happened.
	 */
	public static void main(String[] args) throws InterruptedException {
		// Create and instance of the AdminThread class and run it
		AdminThread serverThread = new AdminThread();
		
		do {
			serverThread.run();
			System.out.println(".");
			Thread.sleep(5000);
		} while(serverThread.isAlive());
	}

}
