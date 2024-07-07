package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferValidatorTest {
	Bank bank;
	Validator validator;
	TransferValidator transferValidator;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		validator = new Validator(bank);
		transferValidator = new TransferValidator(bank);
	}

	@Test
	public void transfer_checking_checking() {
		validator.validateCommand("create checking 12345678 5.5");
		validator.validateCommand("deposit 12345678 300");
		validator.validateCommand("create checking 12345679 6.6");

		assertTrue(transferValidator.validateCommand("transfer 12345678 12345679 300"));
		assertFalse(transferValidator.account1.equals(null));
	}

	@Test
	public void typo_in_transfer() {
		validator.validateCommand("create checking 12345678 5.5");
		validator.validateCommand("deposit 12345678 300");
		validator.validateCommand("create checking 12345679 6.6");
		assertFalse(transferValidator.validateCommand("transfr 12345678 12345679 300"));
	}

	@Test
	public void transfer_id_duplicate() {
		validator.validateCommand("create checking 12345678 5.5");
		validator.validateCommand("deposit 12345678 300");
		validator.validateCommand("create checking 12345679 6.6");
		assertFalse(transferValidator.validateCommand("transfer 12345678 12345678 300"));
	}

	@Test
	public void transfer_missing_id() {
		validator.validateCommand("create checking 12345678 5.5");
		validator.validateCommand("deposit 12345678 300");
		validator.validateCommand("create checking 12345679 6.6");
		assertFalse(transferValidator.validateCommand("transfer 12345678 300"));
	}

	@Test
	public void transfer_no_bal() {
		validator.validateCommand("create checking 12345678 5.5");
		validator.validateCommand("deposit 12345678 300");
		validator.validateCommand("create checking 12345679 6.6");
		assertFalse(transferValidator.validateCommand("transfer 12345678 12345679"));
	}

	@Test
	public void no_id() {
		validator.validateCommand("create checking 12345678 5.5");
		validator.validateCommand("deposit 12345678 300");
		validator.validateCommand("create checking 12345679 6.6");
		assertFalse(transferValidator.validateCommand("transfer 300"));
	}

	@Test
	public void nonexistant_id() {
		validator.validateCommand("create checking 12345678 5.5");
		validator.validateCommand("deposit 12345678 300");
		validator.validateCommand("create checking 12345679 6.6");
		assertFalse(transferValidator.validateCommand("transfer 12345678 12345454 300"));
	}

	@Test
	public void transfer_invalid_bal() {
		validator.validateCommand("create checking 12345678 5.5");
		validator.validateCommand("deposit 12345678 300");
		validator.validateCommand("create checking 12345679 6.6");
		assertFalse(transferValidator.validateCommand("transfer 12345678 12345679 4%55"));
	}

	@Test
	public void transfer_extra() {
		validator.validateCommand("create checking 12345678 5.5");
		validator.validateCommand("deposit 12345678 300");
		validator.validateCommand("create checking 12345679 6.6");
		assertFalse(transferValidator.validateCommand("transfer 12345678 12345679 400 tbj"));
	}
}
