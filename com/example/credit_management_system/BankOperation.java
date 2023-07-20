package com.example.credit_management_system;

public interface BankOperation {
	void addNewCustomer();

	void showAllCustomer();

	Customer findCustomer(long accountNumber);

	void issueCard();

	void showCard(String status);
}
