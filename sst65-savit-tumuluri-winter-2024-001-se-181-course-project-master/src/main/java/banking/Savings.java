package banking;

public class Savings extends Account {

	public boolean monthIsValid;

	public Savings(String id, double apr) {
		super(id, apr);
		monthIsValid = true;
	}

	@Override
	public void validMonth() {
		this.monthIsValid = (this.monthsPassed > 0);
	}

	@Override
	public boolean isDepositAmountValid(double bal) {
		return (bal >= 0) && (bal <= 2500);
	}

	@Override
	public boolean isWithdrawAmountValid(double bal) {
		if ((bal <= 1000) && (bal >= 0) && monthIsValid) {
			this.monthsPassed = 0;
			validMonth();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public String getOutput() {
		String firstString = "Savings";
		return firstString + " " + outputPart2();
	}
}
