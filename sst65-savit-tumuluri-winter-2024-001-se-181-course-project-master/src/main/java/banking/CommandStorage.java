package banking;

import java.util.ArrayList;

public class CommandStorage {
	Bank bank;
	ArrayList<String> invalidCommands = new ArrayList<>();
	ArrayList<String> allCommandsOutput = new ArrayList<>();
	ArrayList<String> validCommands = new ArrayList<>();
	private ArrayList<String> accountOutputList = new ArrayList<>();

	public CommandStorage(Bank bank) {
		this.bank = bank;
	}

	public void addInvalidCommand(String command) {
		this.invalidCommands.add(command);
	}

	public void addValidCommand(String command) {
		this.validCommands.add(command);
	}

	public ArrayList<String> getInvalidCommands() {
		return invalidCommands;
	}

	public void getAccountString() {
		for (Account accounts : this.bank.getAccounts()) {
			accountOutputList.add(accounts.getOutput());
		}
	}

	public void addRelatedAccountCommands() {
		for (String accountOutput : accountOutputList) {
			allCommandsOutput.add(accountOutput);
			for (String commands : validCommands) {
				String[] parse = accountOutput.split(" ");
				String[] parse2 = commands.split(" ");
				String firstWord = parse2[0].toLowerCase();
				if (commands.contains(parse[1]) && !firstWord.equals("create")) {
					allCommandsOutput.add(commands);
				}
			}
		}
	}

	public ArrayList<String> getCommands() {
		getAccountString();
		addRelatedAccountCommands();
		allCommandsOutput.addAll(invalidCommands);
		return allCommandsOutput;
	}
}
