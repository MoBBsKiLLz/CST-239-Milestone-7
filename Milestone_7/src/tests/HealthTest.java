package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;

import product.Health;
import org.junit.jupiter.api.Test;

class HealthTest {

	@Test
	@DisplayName("Test Set/Get Amount")
	public void testSetGetPrice() {
		Health health1 = new Health();
		health1.setAmount(100);
		assertEquals(100, health1.getAmount());
	}

}
