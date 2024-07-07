package banking;

public class TransferProcessor extends CommandProcessor {
	public Account account2;
	double bal;

	public TransferProcessor(Bank bank) {
		super(bank);
	}

	@Override
	public boolean processCommand(String command) {
		this.parse = command.split(" ");
		if (checkCommand()) {
			updateAccount();
			findDepositBalForTransfer();
			withdrawCommand();
			depositCommand();
			return true;
		}
		return false;

	}

	public boolean checkCommand() {
		if (this.parse[0].equalsIgnoreCase("transfer")) {
			return true;
		} else {
			return false;
		}
	}

	private void withdrawCommand() {
		account1.withdrawMoney(Double.parseDouble(parse[3]));
	}

	public void findDepositBalForTransfer() {
		this.bal = Double.parseDouble(parse[3]);
		if (account1.getBalance() < this.bal) {
			this.bal = account1.getBalance();
		}
	}

	private void depositCommand() {
		account2.depositMoney(bal);
	}

	@Override
	public void updateAccount() {
		for (Account accounts : bank.getAccounts()) {
			if (accounts.getID().equals(parse[1])) {
				this.account1 = accounts;
			}
			if ((accounts.getID().equals(parse[2]))) {
				this.account2 = accounts;
			}
		}
	}
}
