package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import app.StoreFront;
import storefront.Product;

class StoreFrontTest {

	@Test
	@DisplayName("Test Read From String")
	public void testReadFromString() {
		ArrayList<Product> products = StoreFront.readFromString("[{\"name\": \"Light Armor\","
				+ "\"description\": \"Light armor with low resistance.\", \"price\": 5.99,\"quantity\": 7}]");
		assertEquals("Light Armor", products.get(0).getName());
		assertEquals("Light armor with low resistance.", products.get(0).getDescription());
		assertEquals(5.99, products.get(0).getPrice());
		assertEquals(7, products.get(0).getQuantity());
	}

}
