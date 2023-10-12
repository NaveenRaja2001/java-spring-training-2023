package com.example.credit_management_system;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import com.example.credit_management_system.BankOperationImpl.AdminBank;
import CreditCardImpl.PlatinumCard;
import CreditCardImpl.SilverCard;

public class Customer {
	Scanner scanner;
	public Map<Long, CreditCard> creditCardDetailsArray = new HashMap<>();
	public String customerName;
	Long accountNumber;
	String bankName;

	public Customer(String customerName, Long customerNumber, String bankName) {
		this.customerName = customerName;
		accountNumber = customerNumber;
		this.bankName = bankName;
	}

	public void addingNewCreditCard() {
		scanner = new Scanner(System.in);
		if (creditCardDetailsArray.size() > 4) {
			System.out.print("Number of maximum creditCard reached");
			return;
		}
		System.out.println("enter password");
		String password = scanner.nextLine();
		long creditCardId = AdminBank.randomlyGeneratedNumber();
		System.out.println("""
				Choose the type of card....
				Platinum
				Silver
				""");
		String cardType = scanner.nextLine();
		System.out.println(customerName);
		if (cardType.equals("Platinum")) {
			creditCardDetailsArray.put(creditCardId,
					new PlatinumCard(12000, password, creditCardId, "applied", cardType));
		}
		if (cardType.equals("Silver")) {
			
			creditCardDetailsArray.put(creditCardId, new SilverCard(8000, password, creditCardId, "applied", cardType));
		}
		System.out.println(creditCardDetailsArray);
		System.out.println("CreditCard applied sucessfully");
	}

	public int checkBalance(long accountNumber) {
		CreditCard creditCard = creditCardDetailsArray.get(accountNumber);
		if (creditCard != null && creditCard.creditCardStatus.equals("active")) {
			return creditCard.balance;
		}
		System.out.println("account Number is not Found");
		return 0;
	}

	public void blockCreditCard(long accountNumber) {
		CreditCard creditCard = creditCardDetailsArray.get(accountNumber);
		if (creditCard != null && creditCard.creditCardStatus.equals("active")) {
			creditCard.creditCardStatus = "blocked";
			System.out.print("Card block successfully");
			return;
		}
		System.out.println("account Number is not Found");
	}

	public void withdrawAndDeposit(int amount, String status) {
		try (Scanner scanner = new Scanner(System.in)) {
			System.out.println("Enter the accNo");
			Long accountNumber = Long.parseLong(scanner.nextLine());
			System.out.println("Enter the password");
			String password = scanner.nextLine();

			CreditCard creditCard = creditCardDetailsArray.get(accountNumber);
			if (password.equals(creditCard.password) && status.equals("withdraw")
					&& creditCard.creditCardStatus.equals("active")) {
				creditCard.withdrawal(amount);
				return;
			}
			if (password.equals(creditCard.password) && status.equals("deposit")
					&& creditCard.creditCardStatus.equals("active")) {
				creditCard.deposit(amount);
				return;
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		System.out.println("account number or password is incorrect");
	}

	@Override
	public String toString() {
		return "Customer [creditCardDetailsArray=" + creditCardDetailsArray + ", customerName=" + customerName
				+ ", accountNumber=" + accountNumber + ", bankName=" + bankName + "]";
	}
}
