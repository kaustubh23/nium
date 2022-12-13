package com.nium.interview.transfers;

public class Account {
	private long acNumber;
	private double balance;

	public long getAcNumber() {
		return acNumber;
	}

	public void setAcNumber(long acNumber) {
		this.acNumber = acNumber;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Account(long acNumber, double balance) {
		super();
		this.acNumber = acNumber;
		this.balance = balance;
	}

	public static Account setAccount(Transactions transaction) {

		return new Account(transaction.getDestinationAcc(), transaction.getAmount());

	}
}
