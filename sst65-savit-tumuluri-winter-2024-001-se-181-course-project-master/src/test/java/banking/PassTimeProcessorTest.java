package banking;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PassTimeProcessorTest {
	PassTimeProcessor passTimeProcessor;

	@BeforeEach
	public void setUp() {
		passTimeProcessor = new PassTimeProcessor(new Bank());
	}

	@Test
	public void true_is_returned_when_needed() {
		assertTrue(passTimeProcessor.processCommand("pass 1"));
	}

	@Test
	public void false_is_returned_when_word_is_wrong() {
		assertFalse(passTimeProcessor.processCommand("passe 1"));
	}
}
