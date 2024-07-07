package banking;

public class DepositProcessor extends CommandProcessor {

	public DepositProcessor(Bank bank) {
		super(bank);
	}

	@Override
	public boolean processCommand(String command) {
		this.parse = command.split(" ");
		if (checkCommand()) {
			updateAccount();
			depositCommand();
			return true;
		} else {
			return false;
		}
	}

	public boolean checkCommand() {
		return this.parse[0].equalsIgnoreCase("deposit");
	}

	private void depositCommand() {
		account1.depositMoney(Double.parseDouble(parse[2]));
	}

}
