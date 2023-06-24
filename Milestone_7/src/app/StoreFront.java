package app;

import storefront.*;
import store.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.*;
import com.fasterxml.jackson.databind.*;

/**
 * StoreFront class, driver
 * @author migg_
 *
 */
public class StoreFront {
	private Socket clientSocket;
	private PrintWriter out;
	private BufferedReader in;
	
	/**
	 * Connect to the remote Server on the specified IP address and Port
	 * @param ip Remote IP Address to connect to
	 * @param port Remote Port to connect to
	 * 
	 * @throws UnknownHostException Thrown if network resolution exception.
	 * @throws IOException Thrown if anything bad happens from any of the networking classes.
	 */
	public void start(String ip, int port) throws UnknownHostException, IOException {
		// Connect to the Remote Server on the specified IP Address and Port
		clientSocket = new Socket(ip, port);
		
		// Create some input and output network buffers to communicate back and forth with the Server
		out = new PrintWriter(clientSocket.getOutputStream(), true);
		in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	}

	/**
	 * Send a Message to the Server
	 * 
	 * @param cmd Command to send.
	 * @return Response back from the Server
	 * @throws IOException Thrown in the networking classes if something bad happened.
	 */
	public String sendMessage(String cmd) throws IOException {
		// Send/Print a Message to Server with a terminating line feed
		out.println(cmd);
		
		// Return the response from the Server
		return in.readLine();
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
	}
	
	/**
	 * ReadFromString method that reads from a file
	 * @param jsonArray String parameter of type String
	 * @return Returns an ArrayList of type Product
	 */
	public static ArrayList<Product> readFromString(String jsonArray) {
		ArrayList<Product> products = new ArrayList<Product>();
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			Product[] productArray = objectMapper.readValue(jsonArray, Product[].class);
			for(int i = 0; i < productArray.length; i++) {
				products.add(productArray[i]);
			}
		} catch (IOException e) {
			// Print exception
			e.printStackTrace();
		}
		
		return products;
	}
	
	/**
	 * Main method
	 * @param args Default parameter
	 * @throws IOException Thrown in the IO if something bad happened.
	 * @throws UnknownHostException Thrown in the server if something bad happened.
	 * @throws InterruptedException Thrown in the thread classes if something bad happened.
	 */
    public static void main(String[] args) throws UnknownHostException, IOException, InterruptedException {
    	// Create a StoreFront and connect to the remote Server on the specified IP Address and Port
		StoreFront storeFront = new StoreFront();
		storeFront.start("127.0.0.1", 6666);
				
		// Send Message to the Server and on the 9th one send a Shutdown Command to Server
		String command;
		String response;
		
		Store<Product> myStore = new Store<Product>(); // Create new store
		InventoryManager<Product> inventory = new InventoryManager<Product>();
		ArrayList<Product> inventoryList = new ArrayList<Product>();
		ArrayList<ComparableProduct> sortedList = new ArrayList<ComparableProduct>();
		ArrayList<Product> receipt = new ArrayList<Product>(); // Create a receipt
		
		command = "U";
		response = storeFront.sendMessage(command);
		
		// Print out the Server response and if we get a QUIT response exit this program
		System.out.println("Server response was " + response);
		
		// Sleep for a bit so the Server can run for a awhile
		Thread.sleep(5000);
		
		command = "R";
		response = storeFront.sendMessage(command);
		
		inventoryList = readFromString(response);
		
		// Add inventory to sortList
		inventoryList.forEach(p ->{
			ComparableProduct newProduct = new ComparableProduct(p.getName(), p.getDescription(), p.getPrice(), p.getQuantity());
			sortedList.add(newProduct);
		});
		
		// Sort sortList 
		Collections.sort(sortedList);
		// Add sorted products to the sorted inventory
		// Loop through the sortedList
		for(int s = 0; s < sortedList.size(); s++) {
			// Loop through the inventoryList
			for(int i = 0; i < inventoryList.size(); i++) {
				if(sortedList.get(s).getName().compareTo(inventoryList.get(i).getName()) == 0) {
					// Add matching inventory item to the sorted inventory
					inventory.addToInventory(inventoryList.get(i));
				}
			}
		}
		// Add sorted inventory to the store inventory
		myStore.setInventory(inventory);
		
		System.out.println("WELCOME TO THE EQUIPMENT STORE!");
		System.out.println();
		
		// Show the inventory
		myStore.getInventory().showInventory();
		
		// Ask user what they would like to purchase
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter -1 at any time to end.");
		System.out.print("Enter the product you want to purchase: ");
		String product = input.nextLine();
		String addProduct = "";
		System.out.println();
		
		if(product.compareTo("-1") != 0) {
		    System.out.print("Enter the quantity you want to purchase: ");
		    String quantity = input.nextLine();
		    
		    System.out.println();
		    
		    if(quantity.compareTo("-1") != 0) {
				while(product.compareTo("-1") != 0 && quantity.compareTo("-1") != 0) {
				    // AddProduct value
					if (addProduct.toUpperCase().compareTo("N") != 0) {
						// Add product to shopping cart
						boolean addToCart = myStore.getShoppingCart().addToCart(product, 
								Integer.parseInt(quantity), myStore.getInventory());
						if (addToCart) {
							System.out.println("Product was added to the shopping cart");
							System.out.println();
						}
						else if (!addToCart){
							System.out.println("Product was not added to the shopping cart");
							System.out.println();
						}
				    }
				    
				    // Ask user if they would like to check-out
					myStore.getShoppingCart().showShoppingCart();
				    System.out.print("Would you like to check-out? (Y/N) ");
				    String checkOut = input.nextLine();
				    if(checkOut.toUpperCase().compareTo("Y") == 0) {
						System.out.println();
						receipt = myStore.purchase();
						
						if(receipt != null) {
						    break;
						}
				    }
				    else if (checkOut.compareTo("-1") == 0) {
					break;
				    }
				    
				    System.out.println();
				    
				    // Ask user if they would like to remove an item
				    System.out.print("Would you like to remove a product? (Y/N) ");
				    String remove = input.nextLine();
				    if(remove.toUpperCase().compareTo("Y") == 0) {
						System.out.println();
						System.out.print("Enter the name of the product: ");
						product = input.nextLine();
						System.out.println();
						System.out.print("Enter the quantity: ");
						quantity = input.nextLine();
						myStore.getShoppingCart().removeFromCart(product, Integer.parseInt(quantity));
				    }
				    else if (remove.compareTo("-1") == 0) {
				    	break;
				    }
				    
				    System.out.println();
				    
				    // Ask user if they would like to add another product
				    System.out.print("Would you like to add another product? (Y/N) ");
				    addProduct = input.nextLine();
				    System.out.println();
				    if(addProduct.toUpperCase().compareTo("Y") == 0) {
						System.out.print("Enter the product you want to purchase: ");
						product = input.nextLine();
						System.out.println();
						System.out.print("Enter the quantity you want to purchase: ");
						quantity = input.nextLine();
						System.out.println();
				    }
				    else if (addProduct.compareTo("-1") == 0) {
				    	break;
				    }
				}
		    }
		}
		
		if (receipt != null && receipt.size() > 0) {
			System.out.println();
			System.out.println("RECEIPT");
			receipt.forEach(r -> {
				System.out.println("Product: " + r.getName() + " Qty: " + r.getQuantity());
			});
			
			System.out.println();
			
		    // The user if they want to cancel their purchase
		    System.out.print("Would you like to cancel your purchase? (Y/N) ");
		    String cancel = input.nextLine();
		    if (cancel.toUpperCase().compareTo("Y") == 0) {
		    	myStore.cancel(receipt);
		    }
		}
		
		System.out.println();
		System.out.println("Goodbye.");
		input.close();
		
		// On exit clean everything up
		storeFront.cleanup();
    }

}
