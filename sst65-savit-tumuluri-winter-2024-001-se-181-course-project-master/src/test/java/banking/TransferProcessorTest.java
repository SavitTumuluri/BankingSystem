package banking;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransferProcessorTest {
	Bank bank;
	TransferProcessor transferProcessor;

	@BeforeEach
	public void setUp() {
		bank = new Bank();
		transferProcessor = new TransferProcessor(bank);
		transferProcessor.account1 = new Savings("12345678", 5.5);
		transferProcessor.parse = ("transfer 12345678 87654321 100").split(" ");

	}

	@Test
	public void transfer_processor_test_findDepositBalForTransfer() {
		transferProcessor.bal = 100;
		transferProcessor.account1.depositMoney(101);
		transferProcessor.findDepositBalForTransfer();
		assertEquals(transferProcessor.bal, 100);
	}

	@Test
	public void transfer_processor_test_findDepositBalForTransfer_other_condition() {
		transferProcessor.bal = 101;
		transferProcessor.account1.depositMoney(100);
		transferProcessor.findDepositBalForTransfer();
		assertEquals(transferProcessor.bal, 100);
	}
}
