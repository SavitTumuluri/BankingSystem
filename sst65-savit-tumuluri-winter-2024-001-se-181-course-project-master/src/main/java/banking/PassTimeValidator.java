package banking;

public class PassTimeValidator extends Validator {
	private String command;
	private String[] parse;

	public PassTimeValidator(Bank bank) {
		super(bank);
	}

	@Override
	public boolean validateCommand(String command) {
		this.command = command;
		this.parse = this.command.split(" ");
		return commandIsValid();
	}

	public boolean checkFirstWord() {
		return this.parse[0].equalsIgnoreCase("pass");
	}

	public boolean monthIsValidInRange() {
		try {
			int monthCount = Integer.parseInt(parse[1]);
			return 0 < monthCount && monthCount < 61;
		} catch (IndexOutOfBoundsException | NumberFormatException a) {
			return false;
		}
	}

	public boolean noExtraCharacters() {
		try {
			if (parse[2] != null) {
				return false;
			}
		} catch (IndexOutOfBoundsException a) {
			return true;
		}
		return false;
	}

	@Override
	public boolean commandIsValid() {
		return noExtraCharacters() && checkFirstWord() && monthIsValidInRange();
	}
}
