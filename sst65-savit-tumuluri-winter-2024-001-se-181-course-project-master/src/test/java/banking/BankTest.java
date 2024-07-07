package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BankTest {

	Bank bank;
	Savings savings;
	CD cd;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		savings = new Savings("00000001", 2.3);
		cd = new CD("00000002", 4.51, 400);
		bank.addAccount(savings);
	}

	@Test
	public void no_accounts_in_bank() {
		bank = new Bank();
		ArrayList<Account> accounts = bank.getAccounts();

		assertTrue(accounts.isEmpty());
	}

	@Test
	public void add_account_to_bank() {
		ArrayList<Account> accounts = bank.getAccounts();

		assertEquals(1, accounts.size());
	}

	@Test
	public void add_two_accounts_to_bank() {
		bank.addAccount(cd);
		ArrayList<Account> accounts = bank.getAccounts();

		assertEquals(2, accounts.size());
	}

	@Test
	public void retrieve_account_from_bank() {
		Account actual = bank.retrieveAccount("00000001");

		assertEquals(2.3, actual.getAPR());
		/* savings apr set to 2.3 so I am checking if it is the specific one I made */
	}

	@Test
	public void deposit_money_using_ID() {
		bank.depositMoney("00000001", 123.45);

		assertEquals(123.45, savings.getBalance());
	}

	@Test
	public void deposit_money_twice_using_ID() {
		bank.depositMoney("00000001", 150.00);
		bank.depositMoney("00000001", 123.45);

		assertEquals(273.45, savings.getBalance());
	}

	@Test
	public void withdraw_money_using_ID() {
		bank.addAccount(cd);
		bank.withdrawMoney("00000002", 250);

		assertEquals(150, cd.getBalance());
	}

	@Test
	public void withdraw_money_twice_using_ID() {
		bank.addAccount(cd);
		bank.withdrawMoney("00000002", 250);
		bank.withdrawMoney("00000002", 100);

		assertEquals(50, cd.getBalance());
	}

	@Test
	public void passTime() {
		cd = new CD("00000002", 2.1, 2000);

		bank.addAccount(cd);
		bank.passTime(1);
		assertEquals(2014.04, bank.getAccounts().get(0).getBalance());
	}

}
