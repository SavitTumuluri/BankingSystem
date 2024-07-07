package banking;

public class WithdrawValidator extends Validator {

	String command;
	String id;
	double bal;
	String[] parse;
	Account account;

	public WithdrawValidator(Bank bank) {
		super(bank);
	}

	public Account getAccount() {
		return this.account;
	}

	@Override
	public boolean validateCommand(String command) {
		this.command = command;
		this.parse = this.command.split(" ");
		this.id = parse[1];
		updateAccount();
		return noExtraValue() && checkFirstWord() && idExists() && checkBal()
				&& this.account.isWithdrawAmountValid(bal);
	}

	public boolean checkFirstWord() {
		if (this.parse[0].equalsIgnoreCase("withdraw")) {
			return true;
		} else {
			return false;
		}
	}

	public void updateAccount() {
		for (Account accounts : bank.getAccounts()) {
			if (accounts.getID().equals(this.id)) {
				this.account = accounts;
			}
		}
	}

	public boolean idExists() {
		if (this.account != null) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkBal() {
		try {
			String stringBal = this.parse[2];
			this.bal = Double.parseDouble(stringBal);
			return true;
		} catch (IndexOutOfBoundsException a) {
			return false;
		} catch (NumberFormatException b) {
			return false;
		}
	}

	public boolean noExtraValue() {
		try {
			if (parse[3] != null) {
				return false;
			}
		} catch (IndexOutOfBoundsException a) {
			return true;
		}
		return false;
	}

}
