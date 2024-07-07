package banking;

import java.util.List;

public class MasterControl {
	public Validator validator;
	public CommandProcessor processor;
	public CommandStorage storage;

	public MasterControl(Validator validator, CommandProcessor commandProcessor, CommandStorage commandStorage) {
		this.validator = validator;
		this.processor = commandProcessor;
		this.storage = commandStorage;
	}

	public CommandStorage getStorage() {
		return this.storage;
	}

	public List<String> start(List<String> input) {
		for (String command : input) {
			if (validator.validateCommand(command)) {
				processor.processCommand(command);
				storage.addValidCommand(command);
			} else {
				storage.addInvalidCommand(command);
			}
		}
		return storage.getCommands();
	}
}
