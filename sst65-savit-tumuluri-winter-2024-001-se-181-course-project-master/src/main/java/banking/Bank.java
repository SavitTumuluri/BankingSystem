package banking;

import java.util.ArrayList;

public class Bank {
	public ArrayList<Account> accountList = new ArrayList<>();

	public ArrayList<Account> getAccounts() {
		return accountList;
	}

	public void addAccount(Account account) {
		accountList.add(account);
	}

	public Account retrieveAccount(String id) {
		for (Account accounts : accountList) {
			if (accounts.getID().equals(id)) {
				return accounts;
			}
		}
		return null;
	}

	public void depositMoney(String id, double money) {
		Account account = retrieveAccount(id);
		account.depositMoney(money);
	}

	public void withdrawMoney(String id, double money) {
		Account account = retrieveAccount(id);
		account.withdrawMoney(money);
	}

	public boolean checkDuplicateID(Account account) {
		for (Account accounts : accountList) {
			if ((accounts.getID().equals(account.getID()))) {
				return false;
			}
		}
		return true;
	}

	public void passTime(int months) {
		ArrayList<Account> newAccountList = new ArrayList<>();
		for (Account accounts : accountList) {
			if (accounts.passTime(months) == true) {
				newAccountList.add(accounts);
			}
		}
		accountList = newAccountList;
	}
}
