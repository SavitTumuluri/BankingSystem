package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ValidatorTest {
	Bank bank;
	Validator validator;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		validator = new Validator(bank);
	}

	@Test
	public void run_create_command() {
		String command = "create savings 00000001 5.5";

		assertTrue(validator.validateCommand(command));
	}

	@Test
	public void run_create_and_deposit_command() {
		String command = "create savings 00000001 5.5";
		assertTrue(validator.validateCommand(command));
		String command2 = "deposit 00000001 500";
		assertTrue(validator.validateCommand(command2));
	}

	@Test
	public void deposit_without_existing() {
		String command = "deposit 00000001 100";
		assertFalse(validator.validateCommand(command));
	}

	@Test
	public void deposit_savings_upper_limit() {
		String command = "create savings 00000001 4.4";
		assertTrue(validator.validateCommand(command));
		String command2 = "deposit 00000001 2500";
		assertTrue(validator.validateCommand(command2));
	}

	@Test
	public void deposit_savings_over_upper_limit() {
		String command = "create savings 00000001 4.4";
		assertTrue(validator.validateCommand(command));
		String command2 = "deposit 00000001 2501";
		assertFalse(validator.validateCommand(command2));
	}

	@Test
	public void deposit_savings_lower_limit() {
		String command = "create savings 00000001 4.4";
		assertTrue(validator.validateCommand(command));
		String command2 = "deposit 00000001 0";
		assertTrue(validator.validateCommand(command2));
	}

	@Test
	public void deposit_savings_under_lower_limit() {
		String command = "create savings 00000001 4.4";
		assertTrue(validator.validateCommand(command));
		String command2 = "deposit 00000001 -1";
		assertFalse(validator.validateCommand(command2));
	}

	@Test
	public void deposit_checking_upper_limit() {
		String command = "create checking 00000001 4.4";
		assertTrue(validator.validateCommand(command));
		String command2 = "deposit 00000001 1000";
		assertTrue(validator.validateCommand(command2));
	}

	@Test
	public void deposit_checking_over_upper_limit() {
		String command = "create checking 00000001 4.4";
		assertTrue(validator.validateCommand(command));
		String command2 = "deposit 00000001 1001";
		assertFalse(validator.validateCommand(command2));
	}

	@Test
	public void deposit_checking_under_lower_limit() {
		String command = "create checking 00000001 4.4";
		assertTrue(validator.validateCommand(command));
		String command2 = "deposit 00000001 -1";
		assertFalse(validator.validateCommand(command2));
	}

	@Test
	public void transfer_cd_doesnt_work() {
		String command = "create cd 00000001 5.5 1000";
		assertTrue(validator.validateCommand(command));
		String command2 = "create savings 00000002 5.5";
		assertTrue(validator.validateCommand(command2));
		String command3 = "transfer 00000001 00000002 1000";
		assertFalse(validator.validateCommand(command3));
	}

	@Test
	public void checking_to_checking_transfer() {
		String command = "create checking 00000001 5.5";
		assertTrue(validator.validateCommand(command));
		String command2 = "deposit 00000001 500";
		assertTrue(validator.validateCommand(command2));
		String command3 = "create checking 00000002 5.5";
		assertTrue(validator.validateCommand(command3));
		String command4 = "transfer 00000001 00000002 400";
		assertTrue(validator.validateCommand(command4));
	}

	@Test
	public void checking_to_checking_transfer_too_much() {
		String command = "create checking 00000001 5.5";
		assertTrue(validator.validateCommand(command));
		String command2 = "deposit 00000001 500";
		assertTrue(validator.validateCommand(command2));
		String command3 = "create checking 00000002 5.5";
		assertTrue(validator.validateCommand(command3));
		String command4 = "transfer 00000001 00000002 401";
		assertFalse(validator.validateCommand(command4));
	}

	@Test
	public void checking_to_savings_transfer() {
		String command = "create checking 00000001 5.5";
		assertTrue(validator.validateCommand(command));
		String command2 = "deposit 00000001 500";
		assertTrue(validator.validateCommand(command2));
		String command3 = "create savings 00000002 5.5";
		assertTrue(validator.validateCommand(command3));
		String command4 = "transfer 00000001 00000002 400";
		assertTrue(validator.validateCommand(command4));
	}

	@Test
	public void savings_to_savings_transfer_too_much() {
		String command = "create savings 00000001 5.5";
		assertTrue(validator.validateCommand(command));
		String command2 = "deposit 00000001 2500";
		assertTrue(validator.validateCommand(command2));
		String command3 = "create savings 00000002 5.5";
		assertTrue(validator.validateCommand(command3));
		String command4 = "transfer 00000001 00000002 1001";
		assertFalse(validator.validateCommand(command4));
	}
}