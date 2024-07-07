package banking;

public class WithdrawProcessor extends CommandProcessor {

	public WithdrawProcessor(Bank bank) {
		super(bank);
	}

	@Override
	public boolean processCommand(String command) {
		this.parse = command.split(" ");

		if (checkCommand()) {
			updateAccount();
			withdrawCommand();
			return true;
		} else {
			return false;
		}
	}

	private boolean checkCommand() {
		return this.parse[0].equalsIgnoreCase("withdraw");
	}

	private void withdrawCommand() {
		account1.withdrawMoney(Double.parseDouble(parse[2]));
	}

}
