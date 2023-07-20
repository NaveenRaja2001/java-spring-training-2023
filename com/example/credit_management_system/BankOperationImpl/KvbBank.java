package com.example.credit_management_system.BankOperationImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import com.example.credit_management_system.BankOperation;
import com.example.credit_management_system.CreditCard;
import com.example.credit_management_system.Customer;

public class KvbBank implements BankOperation {
	String bankName = "KVB";
	Scanner scanner;
	Map<Long, Customer> customerDetailsArray = new HashMap<>();

	public static long randomlyGeneratedNumber() {
		long number = (long) (Math.random() * (1000000 - 9999 + 1) + 9999);
		return number;
	}

	public void addNewCustomer() {
		scanner = new Scanner(System.in);
		System.out.println("Enter the customer Name");
		String customerName = scanner.nextLine();
		long customerId = randomlyGeneratedNumber();
		customerDetailsArray.put(customerId, new Customer(customerName, customerId, bankName));

		System.out.println(customerDetailsArray);
		System.out.println("New Customer add Successfully\n\n");
	}

	public void showAllCustomer() {
		customerDetailsArray.forEach((key, value) -> {
			System.out.println("CustomerId  :" + key + "--->" + value);
		});
	}

	public Customer findCustomer(long accountNumber) {
		return customerDetailsArray.get(accountNumber);
	}

	public void issueCard() {
		Scanner scanner = new Scanner(System.in);
			Customer customer;
			if (customerDetailsArray.isEmpty()) {
				System.out.println("No customer");
				return;
			}
			customerDetailsArray.forEach((key, value) -> {
				System.out.println("CustomerId  :" + key + "--->" + value.customerName);
			});
			System.out.println("Enter the customerId to see all applied Card");
			customer = customerDetailsArray.get(scanner.nextLong());
			if (customer.creditCardDetailsArray.isEmpty()) {
				System.out.println("No CARD");
				return;
			}
			customer.creditCardDetailsArray.forEach((key, value) -> {
				if (value.creditCardStatus.equals("applied")) {
					System.out.println("CardId :" + key + "--->" + value);
				}
			});
			CreditCard creditCard = customer.creditCardDetailsArray.get(scanner.nextLong());
			creditCard.creditCardStatus = "active";
	
		System.out.println("CreditCard is succesfully activated");
	}

	public void showCard(String status) {
		customerDetailsArray.forEach((key, value) -> {
			System.out.println("CustomerId  :" + key + "--->" + value.customerName);
			value.creditCardDetailsArray.forEach((creditId, creditCard) -> {
				if (creditCard.creditCardStatus.equals(status)) {
					System.out.println(creditCard);
				}
			});
		});
	}

}
