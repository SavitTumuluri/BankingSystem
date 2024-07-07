package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandProcessorTest {
	Bank bank;
	CommandProcessor commandProcessor;
	Validator validator;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandProcessor = new CommandProcessor(bank);
		validator = new Validator(bank);
	}

	@Test
	public void process_create() {
		String command = "create checking 12345678 5.5";

		assertTrue(validator.validateCommand(command));
		assertTrue(commandProcessor.processCommand(command));
	}

	@Test
	public void process_pass_time() {
		String command = "create checking 12345678 5.5";
		String command2 = "pass 1";

		assertTrue(validator.validateCommand(command));
		assertTrue(commandProcessor.processCommand(command));
		assertTrue(validator.validateCommand(command2));
		assertTrue(commandProcessor.processCommand(command2));
	}

	@Test
	public void process_pass_time_wrong() {
		String command = "create checking 12345678 5.5";
		String command2 = "passr 1";

		assertTrue(validator.validateCommand(command));
		assertTrue(commandProcessor.processCommand(command));
		assertFalse(validator.validateCommand(command2));
		assertFalse(commandProcessor.processCommand(command2));
	}

	@Test
	public void process_deposit() {
		String command = "create checking 12345678 5.5";

		assertTrue(validator.validateCommand(command));
		assertTrue(commandProcessor.processCommand(command));
		assertEquals(0, bank.getAccounts().get(0).getBalance());

		String command2 = "deposit 12345678 100";
		assertTrue(validator.validateCommand(command2));
		assertTrue(commandProcessor.processCommand(command2));
		assertEquals(100, bank.getAccounts().get(0).getBalance());
	}

	@Test
	public void process_deposit_twice() {
		String command = "create checking 12345678 5.5";

		assertTrue(validator.validateCommand(command));
		assertTrue(commandProcessor.processCommand(command));
		assertEquals(0, bank.getAccounts().get(0).getBalance());

		String command2 = "deposit 12345678 100";
		assertTrue(validator.validateCommand(command2));
		assertTrue(commandProcessor.processCommand(command2));
		assertEquals(100, bank.getAccounts().get(0).getBalance());

		assertTrue(commandProcessor.processCommand(command2));
		assertEquals(200, bank.getAccounts().get(0).getBalance());
	}

	@Test
	public void withdraw_from_checking() {
		String command = "create checking 12345678 5.5";
		assertTrue(validator.validateCommand(command));
		assertTrue(commandProcessor.processCommand(command));
		assertEquals(0, bank.getAccounts().get(0).getBalance());

		String command2 = "deposit 12345678 500";
		assertTrue(validator.validateCommand(command2));
		assertTrue(commandProcessor.processCommand(command2));
		assertEquals(500, bank.getAccounts().get(0).getBalance());

		String command3 = "withdraw 12345678 250";
		assertTrue(validator.validateCommand(command3));
		assertTrue(commandProcessor.processCommand(command3));
		assertEquals(250, bank.getAccounts().get(0).getBalance());
	}

	@Test
	public void withdraw_over_balance() {
		String command = "create checking 12345678 5.5";
		assertTrue(validator.validateCommand(command));
		assertTrue(commandProcessor.processCommand(command));
		assertEquals(0, bank.getAccounts().get(0).getBalance());

		String command2 = "deposit 12345678 250";
		assertTrue(validator.validateCommand(command2));
		assertTrue(commandProcessor.processCommand(command2));
		assertEquals(250, bank.getAccounts().get(0).getBalance());

		String command3 = "withdraw 12345678 251";
		assertTrue(validator.validateCommand(command3));
		assertTrue(commandProcessor.processCommand(command3));
		assertEquals(0, bank.getAccounts().get(0).getBalance());
	}

	@Test
	public void transfer_checking_checking() {
		String command = "create checking 12345678 5.5";
		assertTrue(validator.validateCommand(command));
		assertTrue(commandProcessor.processCommand(command));
		assertEquals(0, bank.getAccounts().get(0).getBalance());

		String command2 = "deposit 12345678 250";
		assertTrue(validator.validateCommand(command2));
		assertTrue(commandProcessor.processCommand(command2));
		assertEquals(250, bank.getAccounts().get(0).getBalance());

		String command3 = "create checking 12345679 5.5";
		assertTrue(validator.validateCommand(command3));
		assertTrue(commandProcessor.processCommand(command3));
		assertEquals(0, bank.getAccounts().get(1).getBalance());

		String command4 = "transfer 12345678 12345679 100";
		assertTrue(validator.validateCommand(command4));
		assertTrue(commandProcessor.processCommand(command4));
		assertEquals(100, bank.getAccounts().get(1).getBalance());
		assertEquals(150, bank.getAccounts().get(0).getBalance());
	}

	@Test
	public void transfer_checking_to_savings() {
		String command = "create checking 12345678 1.0";
		String command2 = "create savings 00000001 1.0";
		String command3 = "deposit 12345678 500";
		String command4 = "transfer 12345678 00000001 200";

		validator.validateCommand(command);
		commandProcessor.processCommand(command);
		validator.validateCommand(command2);
		commandProcessor.processCommand(command2);
		assertTrue(validator.validateCommand(command3));
		commandProcessor.processCommand(command3);

		assertTrue(validator.validateCommand(command4));
		assertTrue(commandProcessor.processCommand(command4));
		assertEquals(300, bank.getAccounts().get(0).getBalance());
		assertEquals(200, bank.getAccounts().get(1).getBalance());
	}

	@Test
	public void transfer_savings_to_savings() {
		String command = "create savings 12345678 1.0";
		String command2 = "create savings 00000001 1.0";
		String command3 = "deposit 12345678 500";
		String command4 = "transfer 12345678 00000001 200";

		validator.validateCommand(command);
		commandProcessor.processCommand(command);
		validator.validateCommand(command2);
		commandProcessor.processCommand(command2);
		assertTrue(validator.validateCommand(command3));
		commandProcessor.processCommand(command3);

		assertTrue(validator.validateCommand(command4));
		assertTrue(commandProcessor.processCommand(command4));
		assertEquals(300, bank.getAccounts().get(0).getBalance());
		assertEquals(200, bank.getAccounts().get(1).getBalance());
	}

	@Test
	public void transfer_too_much_withdraw_and_deposit() {
		String command = "create savings 12345678 1.0";
		String command2 = "create checking 00000001 1.0";
		String command3 = "deposit 12345678 1001";
		String command4 = "transfer 12345678 00000001 1001";

		validator.validateCommand(command);
		commandProcessor.processCommand(command);
		validator.validateCommand(command2);
		commandProcessor.processCommand(command2);
		assertTrue(validator.validateCommand(command3));
		commandProcessor.processCommand(command3);

		assertFalse(validator.validateCommand(command4));
	}

}
