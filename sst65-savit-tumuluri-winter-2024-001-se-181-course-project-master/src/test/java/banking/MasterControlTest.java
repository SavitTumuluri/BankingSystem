package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class MasterControlTest {

	MasterControl masterControl;
	List<String> input;

	@BeforeEach
	public void setUp() {
		Bank bank = new Bank();
		input = new ArrayList<>();
		masterControl = new MasterControl(new Validator(bank), new CommandProcessor(bank), new CommandStorage(bank));
	}

	private void assertSingleCommand(String command, List<String> actual) {
		assertEquals(1, actual.size());
		assertEquals(command, actual.get(0));
	}

	@Test
	public void typo_in_create_command_is_invalid() {
		input.add("creat checking 12345678 1.0");

		List<String> actual = masterControl.start(input);
		assertSingleCommand("creat checking 12345678 1.0", actual);
	}

	@Test
	public void typo_in_deposit_command_is_invalid() {
		input.add("depositt 12345678 100");

		List<String> actual = masterControl.start(input);
		assertSingleCommand("depositt 12345678 100", actual);
	}

	@Test
	public void two_typos_in_commands_both_invalid() {
		input.add("creat checking 12345678 1.0");
		input.add("depositt 12345678 100");

		List<String> actual = masterControl.start(input);
		assertEquals(actual.size(), 2);
		assertEquals("creat checking 12345678 1.0", actual.get(0));
		assertEquals("depositt 12345678 100", actual.get(1));

	}

	@Test
	public void two_duplicate_commands_is_invalid() {
		input.add("create checking 12345678 1.0");
		input.add("create checking 12345678 1.0");

		List<String> actual = masterControl.start(input);
		assertEquals(2, actual.size());
		assertEquals(masterControl.getStorage().invalidCommands.get(0), "create checking 12345678 1.0");
		assertEquals(masterControl.getStorage().validCommands.get(0), "create checking 12345678 1.0");

	}

	@Test
	public void transfer_checking_to_savings() {
		input.add("create checking 12345678 1.0");
		input.add("create savings 00000001 1.0");
		input.add("deposit 12345678 500");
		input.add("transfer 12345678 00000001 200");
		List<String> actual = masterControl.start(input);
		assertEquals("Checking 12345678 300.00 1.00", actual.get(0));
		assertEquals("deposit 12345678 500", actual.get(1));
		assertEquals("transfer 12345678 00000001 200", actual.get(2));
		assertEquals("Savings 00000001 200.00 1.00", actual.get(3));
		assertEquals("transfer 12345678 00000001 200", actual.get(4));
		assertEquals(actual.size(), 5);
	}

	@Test
	public void transfer_savings_to_checking() {
		input.add("create savings 12345678 1.0");
		input.add("create checking 00000001 1.0");
		input.add("deposit 12345678 1000");
		input.add("transfer 12345678 00000001 400");
		List<String> actual = masterControl.start(input);
		assertEquals("Savings 12345678 600.00 1.00", actual.get(0));
		assertEquals("deposit 12345678 1000", actual.get(1));
		assertEquals("transfer 12345678 00000001 400", actual.get(2));
		assertEquals("Checking 00000001 400.00 1.00", actual.get(3));
		assertEquals("transfer 12345678 00000001 400", actual.get(4));
		assertEquals(actual.size(), 5);
	}

	@Test
	void sample_make_sure_this_passes_unchanged_or_you_will_fail() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Deposit 12345678 5000");
		input.add("creAte cHecKing 98765432 0.01");
		input.add("Deposit 98765432 300");
		input.add("Transfer 98765432 12345678 300");
		input.add("Pass 1");
		input.add("Create cd 23456789 1.2 2000");
		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Transfer 98765432 12345678 300", actual.get(2));
		assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
		assertEquals("Deposit 12345678 5000", actual.get(4));
	}

	@Test
	public void extraValueInCreate() {
		input.add("Create savings 12345678 0.6");
		input.add("Create savings 12345698 0.6 f");
		input.add("Deposit 12345678 700");
		input.add("Deposit 12345678 5000");
		input.add("creAte cHecKing 98765432 0.01");
		input.add("Deposit 98765432 300");
		input.add("Transfer 98765432 12345678 300");
		input.add("Pass 1");
		input.add("Create cd 23456789 1.2 2000");
		List<String> actual = masterControl.start(input);

		assertEquals(6, actual.size());
		assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Transfer 98765432 12345678 300", actual.get(2));
		assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
		assertEquals("Create savings 12345698 0.6 f", actual.get(4));
		assertEquals("Deposit 12345678 5000", actual.get(5));
	}

	@Test
	public void extraValueInDeposit() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Deposit 12345678 700 a");
		input.add("Deposit 12345678 5000");
		input.add("creAte cHecKing 98765432 0.01");
		input.add("Deposit 98765432 300");
		input.add("Transfer 98765432 12345678 300");
		input.add("Pass 1");
		input.add("Create cd 23456789 1.2 2000");
		List<String> actual = masterControl.start(input);

		assertEquals(6, actual.size());
		assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Transfer 98765432 12345678 300", actual.get(2));
		assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
		assertEquals("Deposit 12345678 700 a", actual.get(4));
		assertEquals("Deposit 12345678 5000", actual.get(5));
	}

	@Test
	public void extraValueInPass() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Deposit 12345678 5000");
		input.add("creAte cHecKing 98765432 0.01");
		input.add("Deposit 98765432 300");
		input.add("Transfer 98765432 12345678 300");
		input.add("Pass 1");
		input.add("Pass 1 3");
		input.add("Create cd 23456789 1.2 2000");
		List<String> actual = masterControl.start(input);

		assertEquals(6, actual.size());
		assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Transfer 98765432 12345678 300", actual.get(2));
		assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
		assertEquals("Pass 1 3", actual.get(5));
		assertEquals("Deposit 12345678 5000", actual.get(4));
	}

	@Test
	public void extraValueInTransfer() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Deposit 12345678 5000");
		input.add("creAte cHecKing 98765432 0.01");
		input.add("Deposit 98765432 300");
		input.add("Transfer 98765432 12345678 300");
		input.add("Transfer 98765432 12345678 300 r");
		input.add("Pass 1");
		input.add("Create cd 23456789 1.2 2000");
		List<String> actual = masterControl.start(input);

		assertEquals(6, actual.size());
		assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Transfer 98765432 12345678 300", actual.get(2));
		assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
		assertEquals("Transfer 98765432 12345678 300 r", actual.get(5));
		assertEquals("Deposit 12345678 5000", actual.get(4));
	}

	@Test
	public void missingValueInDeposit() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Deposit 12345678");
		input.add("creAte cHecKing 98765432 0.01");
		input.add("Deposit 98765432 300");
		input.add("Transfer 98765432 12345678 300");
		input.add("Pass 1");
		input.add("Create cd 23456789 1.2 2000");
		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Transfer 98765432 12345678 300", actual.get(2));
		assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
		assertEquals("Deposit 12345678", actual.get(4));
	}

	@Test
	public void missingValueInTransfer() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("creAte cHecKing 98765432 0.01");
		input.add("Deposit 98765432 300");
		input.add("Transfer 98765432 12345678 300");
		input.add("Transfer 98765432 300");
		input.add("Pass 1");
		input.add("Create cd 23456789 1.2 2000");
		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 1000.50 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("Transfer 98765432 12345678 300", actual.get(2));
		assertEquals("Cd 23456789 2000.00 1.20", actual.get(3));
		assertEquals("Transfer 98765432 300", actual.get(4));
	}

	@Test
	public void withdraw_cd() {
		String command = "create cd 12345678 5.5 5000";
		List<String> input = new ArrayList<>();
		input.add(command);
		input.add("pass 12");
		input.add("withdraw 12345678 6227.25");
		List<String> actual = masterControl.start(input);

		assertEquals("Cd 12345678 0.00 5.50", actual.get(0));
		assertEquals("withdraw 12345678 6227.25", actual.get(1));
		assertEquals(2, actual.size());
	}

	@Test
	public void deposit_twice_in_savings_doesnt_work_before_passtime() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("withdraw 12345678 600");
		input.add("Deposit 12345678 700");
		input.add("withdraw 12345678 600");
		input.add("Pass 1");
		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 800.40 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("withdraw 12345678 600", actual.get(2));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("withdraw 12345678 600", actual.get(2));
	}

	@Test
	public void test_under_100() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 99.99");
		input.add("Pass 1");
		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("Savings 12345678 75.03 0.60", actual.get(0));
		assertEquals("Deposit 12345678 99.99", actual.get(1));
	}

	@Test
	public void only_integer_in_passtime() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 99.99");
		input.add("Pass 1.0");
		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("Savings 12345678 99.99 0.60", actual.get(0));
		assertEquals("Deposit 12345678 99.99", actual.get(1));
		assertEquals("Pass 1.0", actual.get(2));
	}

	@Test
	public void test_under_25() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 25");
		input.add("Pass 1");
		List<String> actual = masterControl.start(input);

		assertEquals(2, actual.size());
		assertEquals("Savings 12345678 0.00 0.60", actual.get(0));
		assertEquals("Deposit 12345678 25", actual.get(1));
	}

	@Test
	public void passtime_extra_character() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 99.99");
		input.add("Pass 1 q");
		List<String> actual = masterControl.start(input);

		assertEquals(3, actual.size());
		assertEquals("Savings 12345678 99.99 0.60", actual.get(0));
		assertEquals("Deposit 12345678 99.99", actual.get(1));
		assertEquals("Pass 1 q", actual.get(2));
	}

	@Test
	public void withdraw_extra_character() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 2000");
		input.add("withdraw 12345678 700 a");
		input.add("Create checking 87654321 5.5");
		List<String> actual = masterControl.start(input);

		assertEquals(4, actual.size());
		assertEquals("Savings 12345678 2000.00 0.60", actual.get(0));
		assertEquals("Deposit 12345678 2000", actual.get(1));
		assertEquals("Checking 87654321 0.00 5.50", actual.get(2));
		assertEquals("withdraw 12345678 700 a", actual.get(3));
	}

	@Test
	public void transfer_right_amount() {
		input.add("Create savings 12345678 0.6");
		input.add("Deposit 12345678 700");
		input.add("Create savings 13245678 0.5");
		input.add("transfer 12345678 13245678 701");
		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 0.00 0.60", actual.get(0));
		assertEquals("Deposit 12345678 700", actual.get(1));
		assertEquals("transfer 12345678 13245678 701", actual.get(2));
		assertEquals("Savings 13245678 700.00 0.50", actual.get(3));
		assertEquals("transfer 12345678 13245678 701", actual.get(4));
	}

	@Test
	public void cd_removal_test() {
		input.add("Create cd 12345678 0.6 2000");
		input.add("Pass 12");
		input.add("withdraw 12345678 10000");
		input.add("pass 1");
		input.add("create checking 87654321 0.5");
		List<String> actual = masterControl.start(input);

		assertEquals(1, actual.size());
		assertEquals("Checking 87654321 0.00 0.50", actual.get(0));
	}

	@Test
	public void transfer_twice_before_pass_savings() {
		input.add("Create savings 12345678 0.6");
		input.add("Create savings 12345679 0.5");
		input.add("deposit 12345678 2000");
		input.add("transfer 12345678 12345679 700");
		input.add("transfer 12345678 12345679 700");
		input.add("Create checking 00000000 3.3");
		List<String> actual = masterControl.start(input);

		assertEquals(7, actual.size());
		assertEquals("Savings 12345678 1300.00 0.60", actual.get(0));
		assertEquals("deposit 12345678 2000", actual.get(1));
		assertEquals("transfer 12345678 12345679 700", actual.get(2));
		assertEquals("Savings 12345679 700.00 0.50", actual.get(3));
		assertEquals("transfer 12345678 12345679 700", actual.get(4));
		assertEquals("Checking 00000000 0.00 3.30", actual.get(5));
		assertEquals("transfer 12345678 12345679 700", actual.get(6));
	}

	@Test
	public void withdraw_twice_before_pass_savings() {
		input.add("Create savings 12345678 0.6");
		input.add("deposit 12345678 2000");
		input.add("withdraw 12345678 700");
		input.add("withdraw 12345678 700");
		input.add("Create checking 00000000 3.3");
		List<String> actual = masterControl.start(input);

		assertEquals(5, actual.size());
		assertEquals("Savings 12345678 1300.00 0.60", actual.get(0));
		assertEquals("deposit 12345678 2000", actual.get(1));
		assertEquals("withdraw 12345678 700", actual.get(2));
		assertEquals("Checking 00000000 0.00 3.30", actual.get(3));
		assertEquals("withdraw 12345678 700", actual.get(4));
	}
}
