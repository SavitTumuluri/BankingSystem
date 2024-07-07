package banking;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class Account {
	public double balance;
	public int monthsPassed;
	private String id;
	private double apr;

	public Account(String id, double apr) {
		this.id = id;
		this.apr = apr;
	}

	public double getAPR() {
		DecimalFormat decimalFormat = new DecimalFormat("0.00");
		decimalFormat.setRoundingMode(RoundingMode.FLOOR);
		return Double.valueOf(decimalFormat.format(apr));
	}

	public String getID() {
		return id;
	}

	public double getBalance() {
		DecimalFormat df = new DecimalFormat("0.00");
		return Double.valueOf(df.format(balance));
	}

	public String getOutput() {
		return "";
	}

	public void depositMoney(double money) {
		this.balance += money;
	}

	public void withdrawMoney(double money) {
		if (balance - money <= 0) {
			this.balance = 0.00;
		} else {
			this.balance -= money;
		}
	}

	public boolean isCD() {
		return false;
	}

	public boolean isDepositAmountValid(double bal) {
		/* intentionally unused */
		return bal >= 0;
	}

	public boolean isWithdrawAmountValid(double bal) {
		/* intentionally unused */

		return bal >= 0;
	}

	public boolean passTime(int months) {
		int loopNum = months;
		while (loopNum > 0) {
			monthsPassed += 1;
			if (getBalance() <= 0) {
				return false;
			}
			if (getBalance() < 100) {
				withdrawMoney(25);
			}
			double decimalAPR = apr / 100;
			double divideBy12 = decimalAPR / 12;
			depositMoney(getBalance() * divideBy12);
			loopNum -= 1;
		}
		return true;
	}

	public void validMonth() {
		/* intentionally unused */
	}

	public String outputPart2() {
		String secondString = this.getID();
		String thirdString = String.format("%.2f", this.getBalance());
		String fourthString = String.format("%.2f", this.getAPR());
		return secondString + " " + thirdString + " " + fourthString;
	}

}
