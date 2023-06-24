package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import admin.AdminService;
import storefront.Product;

class AdminServiceTest {

	@Test
	@DisplayName("Test Read From File")
	public void testReadFromFile() {
		ArrayList<Product> products = AdminService.readFromFile("test.json");
		assertEquals("Light Armor", products.get(0).getName());
		assertEquals("Light armor with low resistance.", products.get(0).getDescription());
		assertEquals(5.99, products.get(0).getPrice());
		assertEquals(7, products.get(0).getQuantity());
	}

}
