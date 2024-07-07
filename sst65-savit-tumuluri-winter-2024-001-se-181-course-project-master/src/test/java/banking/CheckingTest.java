package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckingTest {

	Checking checking;

	@BeforeEach
	public void setUp() {
		checking = new Checking("00000000", 0.00);

	}

	@Test
	public void checking_starts_empty() {
		Checking checking = new Checking("00000000", 0.00);
		double actual = checking.getBalance();

		assertEquals(0, actual);
	}

	@Test
	public void negative_deposit_is_not_valid() {
		assertFalse(checking.isDepositAmountValid(-1));
	}

	@Test
	public void over_1000_deposit_is_not_valid() {
		assertFalse(checking.isDepositAmountValid(1000.1));
	}

	@Test
	public void negative_withdraw_is_not_valid() {
		assertFalse(checking.isWithdrawAmountValid(-1));
	}

	@Test
	public void over_400_withdraw_is_not_valid() {
		assertFalse(checking.isWithdrawAmountValid(400.1));
	}

	@Test
	public void a_400_withdraw_is_valid() {
		assertTrue(checking.isWithdrawAmountValid(400));
	}

	@Test
	public void a_0_withdraw_is_valid() {
		assertTrue(checking.isWithdrawAmountValid(0));
	}

	@Test
	public void a_1000_deposit_is_valid() {
		assertTrue(checking.isDepositAmountValid(1000));
	}

	@Test
	public void a_0_deposit_is_valid() {
		assertTrue(checking.isDepositAmountValid(0));
	}
}
