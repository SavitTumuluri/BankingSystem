package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CreateProcessorTest {
	CreateProcessor createProcessor;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		createProcessor = new CreateProcessor(bank);
	}

	@Test
	public void check_create() {
		assertTrue(createProcessor.processCommand("create"));
	}

	@Test
	public void check_create_false() {
		assertFalse(createProcessor.processCommand("not create"));
	}
}
