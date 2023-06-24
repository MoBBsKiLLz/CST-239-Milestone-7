package admin;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

import storefront.Product;
/**
 * AdminService class to handle incoming connections from client
 * @author migg_
 *
 */
public class AdminService {
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	private ArrayList<Product> updatedInventory;
	/**
	 * Start the AdminService and wait for connections on the specified port.
	 * 
	 * @param port Port to listen on.
	 * @throws IOException Thrown in the networking classes if something bad happened.
	 */
	public void start(int port) throws IOException {
		// Wait for a StoreFront connection
		System.out.println("Waiting for a StoreFront connection.........");
		serverSocket = new ServerSocket(port);
		clientSocket = serverSocket.accept();
		
		// If you get here then a StoreFront connected to this AdminService so create some input and output network buffers
		System.out.println("Received a StoreFront connection on port " + clientSocket.getLocalPort());
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		
		// Wait for Command (string that is terminated by a line feed character)
		String inputLine;
		while ((inputLine = in.readLine()) != null) {
			// If period command then shut the AdminService down
			if (".".equals(inputLine)) {
				System.out.println("Got a message to shut the AdminService down");
				out.println("QUIT");
				break;
			} else if ("U".equals(inputLine)) {
				// Echo an acknowledgement back to the StoreFront that Command was processed successfully
				System.out.println("Got a message to updated the Inventory Manager");
				updatedInventory = readFromFile("products.json");
				out.println("OK");
			} else if ("R".equals(inputLine)) {
				// Echo an acknowledgement back to the StoreFront that Command was processed successfully
				System.out.println("Got a message to return the Inventory Manager");
				ObjectMapper objectMapper = new ObjectMapper();
				String json = objectMapper.writeValueAsString(updatedInventory);
				out.println(json);
				
			}
		}
		
		// Exit message that AdminService is shut down
		System.out.println("AdminService is shut down");
	}
	
	/**
	 * Cleanup logic to close all the network connections.
	 * 
	 * @throws IOException Thrown in the networking classes if something bad happened.
	 */
	public void cleanup() throws IOException {
		// Close all input and output network buffers and sockets
		in.close();
		out.close();
		clientSocket.close();
		serverSocket.close();
	}
	
	/**
	 * ReadFromFile method that reads from a file
	 * @param filename Filename parameter of type String
	 * @return Returns an ArrayList of type Product
	 */
	public static ArrayList<Product> readFromFile(String filename) {
		ArrayList<Product> products = new ArrayList<Product>();
		try {
			// Open the file File to read
			File file = new File(filename);
			Scanner s = new Scanner(file);
			
			// Create list of products by reading JSON file
			while(s.hasNext()) {
				// Read a string of JSON and convert to a Product
				String json = s.nextLine();
				ObjectMapper objectMapper = new ObjectMapper();
				Product product = objectMapper.readValue(json, Product.class);
				products.add(product);
			}
			
			// Cleanup
			s.close();
		} catch (IOException e) {
			// Print exception
			e.printStackTrace();
		}
		
		return products;
	}
	
	/**
	 * Entry method for the AdminService application (for  testing only).
	 * 
	 * @param args Testing arguments
	 * @throws IOException Thrown in the networking classes if something bad happened.
	 */
	public static void main(String[] args) throws IOException {
		// Create an instance of this AdminService
		// Start the AdminService on port 6666 (which will not return until the Shutdown Command is received)
		// and then on exit clean everything up
		AdminService server = new AdminService();
		server.start(6666);
		server.cleanup();
	}

}
