package banking;

public class Checking extends Account {
	public Checking(String id, double apr) {
		super(id, apr);
	}

	@Override
	public boolean isDepositAmountValid(double bal) {
		return (bal >= 0) && (bal <= 1000);
	}

	@Override
	public boolean isWithdrawAmountValid(double bal) {
		return (bal <= 400) && (bal >= 0);
	}

	@Override
	public String getOutput() {
		String firstString = "Checking";
		return firstString + " " + outputPart2();
	}
}
