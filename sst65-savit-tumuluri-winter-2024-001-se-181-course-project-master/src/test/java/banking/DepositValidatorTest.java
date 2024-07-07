package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DepositValidatorTest {
	DepositValidator depositValidator;
	CreateValidator createValidator;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		createValidator = new CreateValidator(bank);
		depositValidator = new DepositValidator(bank);

	}

	public void create_and_validate_savings() {
		String command = "create savings 00000001 5.5";
		assertTrue(createValidator.validateCommand(command));
	}

	@Test
	public void deposit_into_checking() {
		String command = "create checking 00000001 5.5";
		String command2 = "deposit 00000001 100";

		assertTrue(createValidator.validateCommand(command));
		assertTrue(depositValidator.validateCommand(command2));

	}

	@Test
	public void deposit_into_savings() {
		create_and_validate_savings();
		String command = "deposit 00000001 100";
		assertTrue(depositValidator.validateCommand(command));
	}

	@Test
	public void deposit_into_cd() {
		String command = "create cd 00000001 5.5 2000";
		String command2 = "deposit 00000001 100";

		assertTrue(createValidator.validateCommand(command));
		assertFalse(depositValidator.validateCommand(command2));
	}

	@Test
	public void deposit_spelled_wrong() {
		create_and_validate_savings();
		String command2 = "depost 00000001 100";
		assertFalse(depositValidator.validateCommand(command2));
	}

	@Test
	public void bal_amount_has_symbol() {
		create_and_validate_savings();
		String command2 = "deposit 00000001 10%0";

		assertFalse(depositValidator.validateCommand(command2));
	}

	@Test
	public void cannot_deposit_negative() {
		create_and_validate_savings();
		String command2 = "deposit 00000001 -1";
		assertFalse(depositValidator.validateCommand(command2));
	}

	@Test
	public void case_insensitive() {
		String command = "create savings 00000001 5.5";
		String command2 = "DepOSit 00000001 500";

		assertTrue(createValidator.validateCommand(command));
		assertTrue(depositValidator.validateCommand(command2));

	}

	@Test
	public void deposit_into_wrong_id() {
		String command = "create savings 00000001 5.5";
		String command2 = "deposit 00000002 500";

		assertTrue(createValidator.validateCommand(command));
		assertFalse(depositValidator.validateCommand(command2));
	}

	@Test
	public void no_bal() {
		String command = "create savings 00000001 5.5";
		String command2 = "deposit 00000001";

		assertTrue(createValidator.validateCommand(command));
		assertFalse(depositValidator.validateCommand(command2));
	}

	@Test
	public void extra_value() {
		create_and_validate_savings();
		String command2 = "deposit 00000001 700 a";

		assertFalse(depositValidator.validateCommand(command2));
	}
}
