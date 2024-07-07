package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class AccountTest {
	Account account;

	@BeforeEach
	public void setUp() {
		account = new Checking("00000000", 0.00);
	}

	@Test
	public void apr_population_in_account() {
		account = new Checking("00000000", 1.21);
		double actual = account.getAPR();

		assertEquals(1.21, actual);
	}

	@Test
	public void id_population_in_account() {
		account = new Checking("00000002", 1.00);
		String actual = account.getID();

		assertEquals("00000002", actual);
	}

	@Test
	public void deposit_money_in_account() {
		account.depositMoney(20.52);
		double actual = account.getBalance();

		assertEquals(20.52, actual);
	}

	@Test
	public void deposit_money_in_account_twice() {
		account.depositMoney(20.52);
		account.depositMoney(10);
		double actual = account.getBalance();

		assertEquals(30.52, actual);
	}

	@Test
	public void withdraw_money_from_account() {
		account.depositMoney(40.52);
		account.withdrawMoney(30.00);
		double actual = account.getBalance();

		assertEquals(10.52, actual);
	}

	@Test
	public void withdraw_money_from_account_twice() {
		account.depositMoney(50);
		account.withdrawMoney(10.20);
		account.withdrawMoney(10.80);
		double actual = account.getBalance();

		assertEquals(29, actual);
	}

	@Test
	public void withdrawing_over_balance_returns_0() {
		account.depositMoney(1.54);
		account.withdrawMoney(1.55);
		double actual = account.getBalance();

		assertEquals(0.00, actual);
	}

	@Test
	public void withdraw_0_works() {
		account.withdrawMoney(0);
		double actual = account.getBalance();

		assertEquals(0.00, actual);
	}

	@Test
	public void floor_apr_works() {
		account = new Checking("00000000", 1.219);
		double actual = account.getAPR();

		assertEquals(1.21, actual);
	}

	@Test
	public void passtime_under_100() {
		account = new Checking("00000000", 1.21);
		account.depositMoney(99.99);
		account.passTime(1);

		assertEquals(75.07, account.getBalance());
		assertEquals(account.monthsPassed, 1);
	}

	@Test
	public void pass_time_100() {
		account = new Checking("00000000", 1.21);
		account.depositMoney(100);
		account.passTime(1);

		assertEquals(100.10, account.getBalance());
		assertEquals(account.monthsPassed, 1);
	}

	@Test
	public void pass_time_twice() {
		account = new Checking("00000000", 1.21);
		account.depositMoney(100);
		account.passTime(2);

		assertEquals(100.20, account.getBalance());
		assertEquals(account.monthsPassed, 2);
	}

	@Test
	public void withdraw_all_money() {
		account.depositMoney(100);
		account.withdrawMoney(99.99);

		assertEquals(account.getBalance(), 0.01);
	}

}
