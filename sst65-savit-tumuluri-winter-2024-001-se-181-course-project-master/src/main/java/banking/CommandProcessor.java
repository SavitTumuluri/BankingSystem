package banking;

public class CommandProcessor {
	public Bank bank;
	public String[] parse;
	public Account account1;
	CreateProcessor createProcessor;
	WithdrawProcessor withdrawProcessor;
	DepositProcessor depositProcessor;
	TransferProcessor transferProcessor;
	PassTimeProcessor passTimeProcessor;

	public CommandProcessor(Bank bank) {
		this.bank = bank;
	}

	public boolean processCommand(String command) {
		return isPassTimeCommand(command) || isTransferCommand(command) || isDepositCommand(command)
				|| isWithdrawCommand(command) || isCreatedCommand(command);
	}

	public boolean isPassTimeCommand(String command) {
		passTimeProcessor = new PassTimeProcessor(bank);
		return passTimeProcessor.processCommand(command);
	}

	public boolean isTransferCommand(String command) {
		transferProcessor = new TransferProcessor(bank);
		return transferProcessor.processCommand(command);
	}

	public boolean isDepositCommand(String command) {
		depositProcessor = new DepositProcessor(bank);
		return depositProcessor.processCommand(command);
	}

	public boolean isWithdrawCommand(String command) {
		withdrawProcessor = new WithdrawProcessor(bank);
		return withdrawProcessor.processCommand(command);
	}

	public boolean isCreatedCommand(String command) {
		createProcessor = new CreateProcessor(bank);
		return createProcessor.processCommand(command);
	}

	public void updateAccount() {
		for (Account accounts : bank.getAccounts()) {
			if (accounts.getID().equals(parse[1])) {
				this.account1 = accounts;
			}
		}
	}

}
