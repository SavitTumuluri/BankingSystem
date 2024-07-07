package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SavingsTest {
	Savings savings;

	@BeforeEach
	public void setUp() {
		savings = new Savings("00000001", 1.11);
	}

	@Test
	public void savings_starts_empty() {
		double actual = savings.getBalance();
		assertEquals(0, actual);
	}

	@Test
	public void deposit_into_savings() {
		savings.depositMoney(2500);
		double actual = savings.getBalance();
		assertEquals(2500, actual);
	}

	@Test
	public void withdraw_negative_is_not_valid() {
		assertFalse(savings.isWithdrawAmountValid(-0.01));
	}

	@Test
	public void withdraw_0_is_valid() {
		assertTrue(savings.isWithdrawAmountValid(0));
	}

	@Test
	public void withdraw_over_1000_is_not_valid() {
		assertFalse(savings.isWithdrawAmountValid(1000.01));
	}

	@Test
	public void month_is_not_valid_returns_false() {
		savings.monthIsValid = false;
		assertFalse(savings.isWithdrawAmountValid(1000));
	}

	@Test
	public void first_month_withdraw_is_true() {
		assertTrue(savings.isWithdrawAmountValid(1000));
	}
}
