package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import store.InventoryManager;
import storefront.Product;

class InventoryManagerTest {

	@Test
	@DisplayName("Test Set/Get Inventory")
	public void testSetGetInventory() {
		InventoryManager<Product> inventory1 = new InventoryManager<Product>();
		ArrayList<Product> list = new ArrayList<Product>();
		inventory1.setInventory(list);
		assertEquals(list, inventory1.getInventory());
	}
	
	@Test
	@DisplayName("Test Add To Inventory")
	public void testAddToInventory() {
		InventoryManager<Product> inventory1 = new InventoryManager<Product>();
		Product product1 = new Product(); 
		assertEquals(true, inventory1.addToInventory(product1));
	}
	
	@Test
	@DisplayName("Test Remove From Inventory")
	public void testRemoveFromInventory() {
		InventoryManager<Product> inventory1 = new InventoryManager<Product>();
		Product product1 = new Product("Product 1", "Test Product", 2.99, 1);
		inventory1.addToInventory(product1);
		assertEquals(true, inventory1.removeFromInventory("Product 1", 1));
	}
}
