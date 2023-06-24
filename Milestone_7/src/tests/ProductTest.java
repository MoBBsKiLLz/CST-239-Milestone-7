package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import storefront.Product;

class ProductTest {

	@Test
	@DisplayName("Test Set/Get Name")
	public void testSetGetName() {
		Product product1 = new Product();
		product1.setName("testSetName");
		assertEquals("testSetName", product1.getName());
	}
	
	@Test
	@DisplayName("Test Set/Get Description")
	public void testSetGetDescription() {
		Product product1 = new Product();
		product1.setDescription("testSetDescription");
		assertEquals("testSetDescription", product1.getDescription());
	}
	
	@Test
	@DisplayName("Test Set/Get Price")
	public void testSetGetPrice() {
		Product product1 = new Product();
		product1.setPrice(2.00);
		assertEquals(2.00, product1.getPrice());
	}
	
	@Test
	@DisplayName("Test Set/Get Quantity")
	public void testSetGetQuantity() {
		Product product1 = new Product();
		product1.setQuantity(5);
		assertEquals(5, product1.getQuantity());
	}
}
