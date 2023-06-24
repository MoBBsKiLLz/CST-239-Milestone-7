package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import product.Armor;

class ArmorTest {

	@Test
	@DisplayName("Test Set/Get Resistance")
	public void testSetGetResistance() {
		Armor armor1 = new Armor();
		armor1.setResistance(30);
		assertEquals(30, armor1.getResistance());
	}

}
