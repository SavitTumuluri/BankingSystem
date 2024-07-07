package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CDTest {
	CD cd;

	@BeforeEach
	public void setUp() {
		cd = new CD("00000000", 0.00, 20.23);

	}

	@Test
	public void cd_balance_is_set() {
		double actual = cd.getBalance();

		assertEquals(20.23, actual);
	}

	@Test
	public void months_have_to_be_12_or_more_to_pass_false() {
		cd.monthsPassed = 11;
		cd.validMonth();
		assertFalse(cd.monthIsValid);
	}

	@Test
	public void months_have_to_be_12_or_more_to_pass_true() {
		cd.monthsPassed = 12;
		cd.validMonth();
		assertTrue(cd.monthIsValid);
	}
}
