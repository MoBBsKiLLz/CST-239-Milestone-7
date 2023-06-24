package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;

import product.Weapon;
import org.junit.jupiter.api.Test;

class WeaponTest {

	@Test
	@DisplayName("Test Set/Get Damage")
	public void testSetGetDamage() {
		Weapon weapon1 = new Weapon();
		weapon1.setDamage(50);
		assertEquals(50, weapon1.getDamage());
	}

}
