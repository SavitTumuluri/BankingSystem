package banking;

public class CreateValidator extends Validator {
	private String command;
	private String[] parse;
	private String accountType;
	private String id;
	private double apr;
	private double bal;

	CreateValidator(Bank bank) {
		super(bank);
	}

	@Override
	public boolean validateCommand(String command) {
		this.command = command;
		this.parse = this.command.split(" ");
		this.accountType = parse[1];
		if ((commandIsValid()) && (this.bank.checkDuplicateID(createAccount()) == true)) {
			this.bank.addAccount(createAccount());
			return true;
		} else {
			return false;
		}
	}

	public Bank getBank() {
		return this.bank;
	}

	public Account createAccount() {
		if (this.accountType.equalsIgnoreCase("cd")) {
			Account account = new CD(id, apr, bal);
			return account;
		} else if (this.accountType.equalsIgnoreCase("checking")) {
			Account account = new Checking(id, apr);
			return account;
		} else if (this.accountType.equalsIgnoreCase("savings")) {
			Account account = new Savings(id, apr);
			return account;
		} else {
			return null;
		}
	}

	public boolean checkFirstWord() {
		if (this.parse[0].equalsIgnoreCase("create")) {
			return true;
		} else {
			return false;
		}
	}

	public boolean checkIDSpelling() {
		String anID = parse[2];
		String specialChars = "/ * ! @ # $ % ^ & * ( ) \\\" { } _ [ ] | \\\\ ? / < > , . -";
		String[] parsedSpecialChars = specialChars.split(" ");
		for (String special : parsedSpecialChars) {
			if ((anID.length() == 8) && (!anID.matches(".*[a-z].*")) && (!anID.contains(special))) {
				updateID();
			} else {
				return false;
			}
		}
		return true;
	}

	private void updateID() {
		this.id = parse[2];
	}

	private void updateAPR(double apr) {
		this.apr = apr;
	}

	private void updateBal(double bal) {
		this.bal = bal;
	}

	public boolean checkAPRValidity() {
		String anApr = parse[3];
		try {
			if ((Double.parseDouble(anApr) <= 10) && (Double.parseDouble(anApr) >= 0)) {
				updateAPR(Double.parseDouble(anApr));
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException a) {
			return false;
		}
	}

	public boolean checkBal() {
		try {
			if (Double.parseDouble(parse[4]) <= 10000 && Double.parseDouble(parse[4]) >= 1000) {
				updateBal(Double.parseDouble(parse[4]));
				return true;
			} else {
				return false;
			}
		} catch (IndexOutOfBoundsException a) {
			return true;
		} catch (NumberFormatException b) {
			return false;
		}
	}

	public boolean noExtraValue() {
		try {
			if (parse[4] != null && accountType.equalsIgnoreCase("cd") && parse[5] != null) {
				return false;
			}
		} catch (IndexOutOfBoundsException a) {
			return true;
		}
		return false;
	}

	@Override
	public boolean commandIsValid() {
		if (checkFirstWord() && checkIDSpelling() && checkAPRValidity() && checkBal() && createAccount() != null
				&& noExtraValue()) {
			return true;
		} else {
			return false;
		}
	}

}
