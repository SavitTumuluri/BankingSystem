package banking;

public class PassTimeProcessor extends CommandProcessor {

	public int months;

	public PassTimeProcessor(Bank bank) {
		super(bank);
	}

	@Override
	public boolean processCommand(String command) {
		this.parse = command.split(" ");
		if (checkCommand()) {
			getMonths();
			bank.passTime(months);
			return true;
		} else {
			return false;
		}
	}

	private void getMonths() {
		this.months = Integer.parseInt(parse[1]);
	}

	public boolean checkCommand() {
		return this.parse[0].equalsIgnoreCase("pass");
	}
}
