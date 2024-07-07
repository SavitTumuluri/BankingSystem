package banking;

public class Validator {
	public Bank bank;
	public CreateValidator createValidator;
	public DepositValidator depositValidator;
	public WithdrawValidator withdrawValidator;
	public TransferValidator transferValidator;
	public PassTimeValidator passTimeValidator;
	private String command;

	public Validator(Bank bank) {
		this.bank = bank;
	}

	public boolean validateCommand(String command) {
		this.command = command;
		return commandIsValid();
	}

	public boolean isPassTimeCommand() {
		passTimeValidator = new PassTimeValidator(bank);
		return passTimeValidator.validateCommand(command);
	}

	public boolean isCreateCommand() {
		createValidator = new CreateValidator(bank);
		return createValidator.validateCommand(command);
	}

	public boolean isDepositCommand() {
		depositValidator = new DepositValidator(bank);
		return depositValidator.validateCommand(command);
	}

	public boolean isWithdrawCommand() {
		withdrawValidator = new WithdrawValidator(bank);
		return withdrawValidator.validateCommand(command);
	}

	public boolean isTransferCommand() {
		transferValidator = new TransferValidator(bank);
		return transferValidator.validateCommand(command);
	}

	public boolean commandIsValid() {
		return isPassTimeCommand() || isCreateCommand() || isDepositCommand() || isWithdrawCommand()
				|| isTransferCommand();
	}
}
