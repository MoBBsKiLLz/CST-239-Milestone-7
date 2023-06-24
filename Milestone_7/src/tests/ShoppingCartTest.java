package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import store.InventoryManager;
import store.ShoppingCart;
import storefront.Product;

class ShoppingCartTest {

	@Test
	@DisplayName("Test Set/Get Shopping Cart")
	public void testSetGetShoppingCart() {
		ShoppingCart<Product> inventory1 = new ShoppingCart<Product>();
		ArrayList<Product> list = new ArrayList<Product>();
		inventory1.setShoppingCart(list);
		assertEquals(list, inventory1.getShoppingCart());
	}
	
	@Test
	@DisplayName("Test Add To Shopping Cart")
	public void testAddToCart() {
		ShoppingCart<Product> cart1 = new ShoppingCart<Product>();
		InventoryManager<Product> inventory1 = new InventoryManager<Product>();
		Product product1 = new Product("Product 1", "Test Product", 2.99, 1);
		inventory1.addToInventory(product1);
		assertEquals(true, cart1.addToCart("Product 1", 1, inventory1));
	}
	
	@Test
	@DisplayName("Test Remove From Shopping Cart")
	public void testRemoveFromCart() {
		ShoppingCart<Product> cart1 = new ShoppingCart<Product>();
		InventoryManager<Product> inventory1 = new InventoryManager<Product>();
		Product product1 = new Product("Product 1", "Test Product", 2.99, 1);
		inventory1.addToInventory(product1);
		cart1.addToCart("Product 1", 1, inventory1);
		assertEquals(true, cart1.removeFromCart("Product 1", 1));
	}
}
