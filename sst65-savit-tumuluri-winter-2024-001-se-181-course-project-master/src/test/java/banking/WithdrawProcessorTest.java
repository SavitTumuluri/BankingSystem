package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class WithdrawProcessorTest {
	@Test
	public void checkCommand_false() {
		WithdrawProcessor withdrawProcessor = new WithdrawProcessor(new Bank());
		assertFalse(withdrawProcessor.processCommand("wthdrw 12345678 500"));
	}
}
