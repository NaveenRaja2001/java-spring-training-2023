package com.example.credit_management_system;

public abstract class CreditCard {
	int balance;
	String password;
	long accountNumber;
	public String creditCardStatus;
	String cardType;

	public CreditCard(int balance, String password, long accountNumber, String creditCardStatus, String cardType) {
		this.balance = balance;
		this.password = password;
		this.accountNumber = accountNumber;
		this.creditCardStatus = creditCardStatus;
		this.cardType = cardType;
	}

	public abstract int limit();

	public void withdrawal(int amount) {
		if (amount > balance) {
			System.out.println("Infficient balance");
			return;
		}
		balance = balance - amount;
		System.out.println("Your current balance is " + balance);
	}

	public void deposit(int amount) {
		balance = balance + amount;
		System.out.println(amount + "amount has deposited successfully");

	}

	@Override
	public String toString() {
		return "CreditCard [balance=" + balance + ", password=" + password + ", accountNumber=" + accountNumber
				+ ", creditCardStatus=" + creditCardStatus + ", cardType=" + cardType + "]";
	}
}
