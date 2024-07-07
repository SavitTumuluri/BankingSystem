package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeValidatorTest {
	PassTimeValidator passTimeValidator;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		passTimeValidator = new PassTimeValidator(bank);
	}

	@Test
	public void pass_is_first_word() {
		passTimeValidator.validateCommand("pass 1");
		assertTrue(passTimeValidator.checkFirstWord());
	}

	@Test
	public void pass_is_not_first_word() {
		passTimeValidator.validateCommand("passt 1");
		assertFalse(passTimeValidator.checkFirstWord());
	}

	@Test
	public void month_under_range() {
		passTimeValidator.validateCommand("pass 0");
		assertFalse(passTimeValidator.monthIsValidInRange());
	}

	@Test
	public void month_negative() {
		passTimeValidator.validateCommand("pass -1");
		assertFalse(passTimeValidator.monthIsValidInRange());
	}

	@Test
	public void month_over_range() {
		passTimeValidator.validateCommand("pass 61");
		assertFalse(passTimeValidator.monthIsValidInRange());
	}

	@Test
	public void month_upper_bound() {
		passTimeValidator.validateCommand("pass 60");
		assertTrue(passTimeValidator.monthIsValidInRange());
	}

	@Test
	public void month_lower_bound() {
		passTimeValidator.validateCommand("pass 1");
		assertTrue(passTimeValidator.monthIsValidInRange());
	}

	@Test
	public void extra_part() {
		assertFalse(passTimeValidator.validateCommand("pass 1 q"));
	}
}
