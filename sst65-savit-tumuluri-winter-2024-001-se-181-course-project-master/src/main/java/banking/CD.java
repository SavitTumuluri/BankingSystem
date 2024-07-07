package banking;

public class CD extends Account {
	public boolean monthIsValid;

	public CD(String id, double apr, double bal) {
		super(id, apr);
		depositMoney(bal);
		this.monthIsValid = false;
	}

	@Override
	public void validMonth() {
		if (this.monthsPassed >= 12) {
			this.monthIsValid = true;
		}
	}

	@Override
	public boolean isCD() {
		return true;
	}

	@Override
	public boolean isWithdrawAmountValid(double balance) {
		return (!(balance < this.getBalance())) && (monthIsValid);
	}

	@Override
	public boolean passTime(int months) {
		int loopNum = months * 4;
		while (loopNum > 0) {
			this.monthsPassed += 1;
			if (balance <= 0) {
				return false;
			}
			double decimalAPR = this.getAPR() / 100;
			double divideBy12 = decimalAPR / 12;
			this.balance += this.balance * divideBy12;
			loopNum -= 1;
		}
		validMonth();
		return true;
	}

	@Override
	public String getOutput() {
		String firstString = "Cd";
		return firstString + " " + outputPart2();
	}
}
