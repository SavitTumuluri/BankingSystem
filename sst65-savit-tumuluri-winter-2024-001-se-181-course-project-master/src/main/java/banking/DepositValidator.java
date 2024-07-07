package banking;

public class DepositValidator extends Validator {
	Account account;
	private String command;
	private String[] parse;
	private String id;
	private double bal;

	public DepositValidator(Bank bank) {
		super(bank);
	}

	@Override
	public boolean validateCommand(String command) {
		this.command = command;
		this.parse = this.command.split(" ");
		this.id = parse[1];
		updateAccount();
		return commandIsValid();
	}

	public boolean checkFirstWord() {
		return this.parse[0].equalsIgnoreCase("deposit");
	}

	public void updateAccount() {
		for (Account accounts : bank.getAccounts()) {
			if (accounts.getID().equals(this.id)) {
				this.account = accounts;
			}
		}
	}

	public boolean idExists() {
		return this.account != null;
	}

	public boolean isNotCD() {
		return !account.isCD();
	}

	public boolean checkBal() {
		try {
			if (Double.parseDouble(parse[2]) >= 0) {
				bal = Double.parseDouble(parse[2]);
				return true;
			} else {
				return false;
			}
		} catch (IndexOutOfBoundsException | NumberFormatException a) {
			return false;
		}
	}

	public boolean depositValidAmount() {
		return this.account.isDepositAmountValid(bal);
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

	@Override
	public boolean commandIsValid() {
		return checkFirstWord() && idExists() && isNotCD() && checkBal() && depositValidAmount() && noExtraValue();
	}

}
