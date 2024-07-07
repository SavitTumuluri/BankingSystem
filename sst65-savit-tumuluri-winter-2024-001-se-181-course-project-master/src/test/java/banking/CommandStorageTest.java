package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CommandStorageTest {
	CommandStorage commandStorage;
	Bank bank;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		commandStorage = new CommandStorage(bank);
	}

	@Test
	public void add_invalid_command() {
		String command = "creat checking 00000001 5.5";
		commandStorage.addInvalidCommand(command);
		assertEquals(command, commandStorage.getInvalidCommands().get(0));
	}

	@Test
	public void get_all_invalid_commands() {
		String command = "creat checking 00000001 5.5";
		String command2 = "create checking 00000%01 5.5";

		commandStorage.addInvalidCommand(command);
		commandStorage.addInvalidCommand(command2);
		assertEquals(command, commandStorage.getInvalidCommands().get(0));
		assertEquals(command2, commandStorage.getInvalidCommands().get(1));
		assertEquals(2, commandStorage.getInvalidCommands().size());

	}

	@Test
	public void invalid_command_storage_starts_empty() {
		assertEquals(0, commandStorage.getInvalidCommands().size());
	}
}
