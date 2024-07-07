package banking;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateValidatorTest {
	CreateValidator createValidator;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		createValidator = new CreateValidator(bank);
	}

	@Test
	public void create_checking_account() {
		String command = "create checking 00000001 5.5";

		assertTrue(createValidator.validateCommand(command));
	}

	@Test
	public void create_savings_account() {
		String command = "create savings 00000001 5.5";

		assertTrue(createValidator.validateCommand(command));
	}

	@Test
	public void create_cd_account() {
		String command = "create cd 00000001 5.5 2000";

		assertTrue(createValidator.validateCommand(command));
	}

	@Test
	public void typo_in_create() {
		String command = "creat checking 00000001 5.5";

		assertFalse(createValidator.validateCommand(command));
	}

	@Test
	public void typo_in_account_type() {
		String command = "create chcking 00000001 5.5";

		assertFalse(createValidator.validateCommand((command)));
	}

	@Test
	public void letter_typo_in_id() {
		String command = "create savings 0000s002 6.6";

		assertFalse(createValidator.validateCommand(command));
	}

	@Test
	public void special_typo_in_id() {
		String command = "create savings 0000%002 6.6";

		assertFalse(createValidator.validateCommand(command));
	}

	@Test
	public void extra_number_in_id() {
		String command = "create checking 000000001 3.3";

		assertFalse(createValidator.validateCommand(command));
	}

	@Test
	public void missing_number_in_id() {
		String command = "create checking 0000001 3.3";

		assertFalse(createValidator.validateCommand(command));
	}

	@Test
	public void dupicate_id_doesnt_work() {
		String command = "create savings 00000001 4.4";
		String command2 = "create checking 00000001 3.3";

		assertTrue(createValidator.validateCommand(command));
		assertFalse(createValidator.validateCommand(command2));
		assertEquals(createValidator.getBank().getAccounts().size(), 1);
	}

	@Test
	public void apr_over_range() {
		String command = "create savings 00000001 10.1";

		assertFalse(createValidator.validateCommand(command));
	}

	@Test
	public void apr_under_range_or_negative() {
		String command = "create savings 00000001 -0.1";

		assertFalse(createValidator.validateCommand(command));
	}

	@Test
	public void apr_upper_bound() {
		String command = "create savings 12345678 10.0";

		assertTrue(createValidator.validateCommand(command));
	}

	@Test
	public void apr_lower_bound() {
		String command = "create savings 12345678 0.0";

		assertTrue(createValidator.validateCommand(command));
	}

	@Test
	public void apr_is_not_number() {
		String command = "create savings 00000001 a.1";

		assertFalse(createValidator.validateCommand(command));
	}

	@Test
	public void create_cd_symbol_in_balance() {
		String command = "create cd 00000001 5.4 1[000";

		assertFalse(createValidator.validateCommand(command));
	}

	@Test
	public void cd_balance_over_range() {
		String command = "create cd 00000001 5.4 10001";

		assertFalse(createValidator.validateCommand(command));
	}

	@Test
	public void cd_balance_under_range() {
		String command = "create cd 12345678 3.3 999";

		assertFalse(createValidator.validateCommand(command));
	}

	@Test
	public void cd_balance_upper_bound() {
		String command = "create cd 00000001 5.4 10000";

		assertTrue(createValidator.validateCommand(command));
	}

	@Test
	public void cd_balance_lower_bound() {
		String command = "create cd 00000001 5.4 1000";

		assertTrue(createValidator.validateCommand(command));
	}

	@Test
	public void case_insensitive() {
		String command = "Create SaVings 00000002 4.4";

		assertTrue(createValidator.validateCommand(command));
	}

	@Test
	public void create_extra_val() {
		String command = "Create SaVings 00000002 4.4 fsfd";

		assertFalse(createValidator.validateCommand(command));
	}

	@Test
	public void create_extra_val_cd() {
		String command = "Create cd 00000002 4.4 5000 500";

		assertFalse(createValidator.validateCommand(command));
	}
}
