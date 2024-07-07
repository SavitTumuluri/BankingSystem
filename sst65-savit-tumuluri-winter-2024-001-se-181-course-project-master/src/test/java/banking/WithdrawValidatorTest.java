package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WithdrawValidatorTest {
	WithdrawValidator withdrawValidator;
	MasterControl masterControl;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		masterControl = new MasterControl(new CreateValidator(bank), new CommandProcessor(bank),
				new CommandStorage(bank));
		withdrawValidator = new WithdrawValidator(bank);

	}

	public void create_checking() {
		String command = "create checking 12345678 5.5";
		String command2 = "deposit 12345678 500";
		List<String> input = new ArrayList<>();
		input.add(command);
		input.add(command2);
		masterControl.start(input);
	}

	@Test
	public void withdraw_from_checking_command() {
		create_checking();
		String command = "withdraw 12345678 100";
		assertTrue(withdrawValidator.validateCommand(command));
		assertFalse(withdrawValidator.getAccount().equals(null));
	}

	@Test
	public void withdraw_invalid_id_command() {
		create_checking();
		String command = "withdraw 12345645 500";
		assertFalse(withdrawValidator.validateCommand(command));
	}

	@Test
	public void withdraw_more_than_bal() {
		String command = "create checking 12345678 5.5";
		String command2 = "deposit 12345678 250";
		List<String> input = new ArrayList<>();
		input.add(command);
		input.add(command2);
		masterControl.start(input);

		String command3 = "withdraw 12345678 251";
		assertTrue(withdrawValidator.validateCommand(command3));
	}

	@Test
	public void invalid_withdraw_no_bal() {
		create_checking();
		String command = "withdraw 12345678";
		assertFalse(withdrawValidator.validateCommand(command));
	}

	@Test
	public void invalid_withdraw_invalid_bal() {
		create_checking();
		String command = "withdraw 12345678 #523";
		assertFalse(withdrawValidator.validateCommand(command));
	}

	@Test
	public void withdraw_from_savings() {
		String command = "create savings 12345678 5.5";
		String command2 = "deposit 12345678 500";
		List<String> input = new ArrayList<>();
		input.add(command);
		input.add(command2);
		masterControl.start(input);

		String command3 = "withdraw 12345678 250";
		assertTrue(withdrawValidator.validateCommand(command3));
	}

	@Test
	public void withdraw_spelled_wrong() {
		create_checking();
		String command = "withdaw 12345678 250";
		assertFalse(withdrawValidator.validateCommand(command));
	}

	@Test
	public void withdraw_more_than_checking() {
		create_checking();
		String command = "withdraw 12345678 401";
		assertFalse(withdrawValidator.validateCommand(command));
	}

	@Test
	public void withdraw_more_than_savings() {
		String command = "create savings 12345678 5.5";
		String command2 = "deposit 12345678 1500";
		List<String> input = new ArrayList<>();
		input.add(command);
		input.add(command2);
		masterControl.start(input);

		String command3 = "withdraw 12345678 1001";
		assertFalse(withdrawValidator.validateCommand(command3));
	}

	@Test
	public void withdraw_less_than_cd() {
		String command = "create cd 12345678 5.5 5000";
		List<String> input = new ArrayList<>();
		input.add(command);
		masterControl.start(input);

		String command3 = "withdraw 12345678 4999";
		assertFalse(withdrawValidator.validateCommand(command3));
	}

}
