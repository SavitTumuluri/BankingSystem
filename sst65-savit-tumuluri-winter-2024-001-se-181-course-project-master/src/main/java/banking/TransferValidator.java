package banking;

import java.util.ArrayList;

public class TransferValidator extends Validator {
	String command;
	String[] parse;
	ArrayList<String> idList;
	Account account1;
	Account account2;

	public TransferValidator(Bank bank) {
		super(bank);
	}

	@Override
	public boolean validateCommand(String command) {
		this.command = command;
		this.parse = this.command.split(" ");
		idList = new ArrayList<>();
		try {
			if (checkDuplicateID() && id_exists(parse[1]) && id_exists(parse[2])) {
				return checkFirstWord() && noExtraValue() && checkBalExists() && !account1.isCD() && !account2.isCD();
			} else {
				return false;
			}
		} catch (IndexOutOfBoundsException a) {
			return false;
		}
	}

	public boolean noExtraValue() {
		try {
			if (parse[4] != null) {
				return false;
			}
		} catch (IndexOutOfBoundsException a) {
			return true;
		}
		return false;
	}

	public boolean checkFirstWord() {
		return parse[0].equalsIgnoreCase("transfer");
	}

	public boolean checkDuplicateID() {
		return !parse[1].equals(parse[2]);
	}

	public boolean id_exists(String id) {
		for (Account accounts : bank.getAccounts()) {
			if (accounts.getID().equals(id)) {
				if (id.equals(parse[1])) {
					account1 = accounts;
				} else {
					account2 = accounts;
				}
				return true;
			}
		}
		return false;
	}

	public boolean checkBalExists() {
		try {
			double bal = Double.parseDouble(parse[3]);
			return account1.isWithdrawAmountValid(bal) && account2.isDepositAmountValid(bal);
		} catch (NumberFormatException a) {
			return false;
		}
	}
}
