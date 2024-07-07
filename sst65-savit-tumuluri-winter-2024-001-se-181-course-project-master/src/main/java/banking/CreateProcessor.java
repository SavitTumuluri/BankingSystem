package banking;

public class CreateProcessor extends CommandProcessor {
	public CreateProcessor(Bank bank) {
		super(bank);
	}

	@Override
	public boolean processCommand(String command) {
		this.parse = command.split(" ");
		return checkCommand();
	}

	public boolean checkCommand() {
		if (this.parse[0].equalsIgnoreCase("create")) {
			return true;
		} else {
			return false;
		}
	}
}
