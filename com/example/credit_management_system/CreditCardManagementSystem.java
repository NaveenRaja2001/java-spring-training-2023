package com.example.credit_management_system;

import java.util.Scanner;
import com.example.credit_management_system.BankOperationImpl.AdminBank;
import com.example.credit_management_system.BankOperationImpl.KvbBank;

public class CreditCardManagementSystem {
	public static BankOperation bankAdministratorKVB = new KvbBank();
	public static BankOperation bankAdministratorAdmin = new AdminBank();

	public static void main(String args[]) {
		try (Scanner scanner = new Scanner(System.in)) {
			BankOperation bankAdministrator = null;
			int bankOption = 0;
			System.out.println("""
					1. KVB
					2. Admin
					""");
			try {
				bankOption = scanner.nextInt();
			} catch (Exception e) {
				System.err.println("Enter the number properly !!!");
				main(args);
			}
			if (bankOption == 1) {
				bankAdministrator = bankAdministratorKVB;
			} else if (bankOption == 2) {
				bankAdministrator = bankAdministratorAdmin;
			} else {
				System.err.println("Bank is not found");
				main(null);
			}
			Customer customer;
			while (true) {
				int choice = 0;
				System.out.println("Select the user type:-");
				System.out.println("1.  Bank Admin");
				System.out.println("2.  Customer");
				try {
					choice = scanner.nextInt();
				} catch (Exception e) {
					System.out.println("pls enter the number");
					main(args);
				}
				if (choice == 1) {
					while (true) {
						int option = 0;
						System.out.println("\n1. show all customers\n" + "2. show all issued cards\n"
								+ "3. create new customer\n" + "4. to Issue new Credit Card\n" + "5.Block CreditCard\n"
								+ "6. view blocked Card\n" + "7. LogOut\n");
						try {
							option = scanner.nextInt();
						} catch (Exception e) {
							System.err.println("pls enter the number properly!!");
							break;
						}
						if (option == 7) {
							break;
						}
						switch (option) {
						case 1: {
							System.out.println("Showing all User");
							bankAdministrator.showAllCustomer();
							;
							break;
						}
						case 2: {
							System.out.println("showing issued card details");
							bankAdministrator.showCard("active");
							break;

						}
						case 3: {
							System.out.println("Creating new User");
							bankAdministrator.addNewCustomer();
							break;
						}
						case 4: {
							System.out.println("Select the Id to issue card");
							bankAdministrator.issueCard();
							break;
						}
						case 5: {
							System.out.print("enter the customer number");
							long customerNumber = scanner.nextLong();
							customer = bankAdministrator.findCustomer(customerNumber);
							System.out.println("Enter the account Number to block the credit card");
							long accountNumber = scanner.nextLong();
							customer.blockCreditCard(accountNumber);
							break;
						}
						case 6: {
							System.out.println("showing blocked card details");
							bankAdministrator.showCard("blocked");
							break;
						}
						}
					}
				} else if (choice == 2) {
					System.out.print("enter the customer number");
					long customerNumber = scanner.nextLong();
					customer = bankAdministrator.findCustomer(customerNumber);
					if (customer == null) {
						System.out.println("User Not Found");
						continue;
					}
					while (true) {
						int option = 0;
						System.out.println("Hello Customer !\n" + "1. apply new card\n" + "2. check balance\n"
								+ "3. block credit card\n" + "4. withdraw\n" + "5. deposit\n"
								+ "6. buyMultiple product\n" + "" + "7. LogOut\n");
						try {
							option = scanner.nextInt();
						} catch (Exception e) {
							System.err.println("pls enter the number properly!!");
							break;
						}
						if (option == 7) {
							break;
						}
						switch (option) {
						case 1: {
							System.out.println("Applying new creditCard");
							customer.addingNewCreditCard();
							break;
						}
						case 2: {
							System.out.println("Enter the account Number to Check Balance");
							long accountNumber = scanner.nextLong();
							System.out.print(customer.checkBalance(accountNumber));
							break;
						}
						case 3: {
							System.out.println("Enter the account Number to block the credit card");
							long accountNumber = scanner.nextLong();
							customer.blockCreditCard(accountNumber);
							break;
						}
						case 4: {
							System.out.println("Enter the amount to withdraw");
							int amount = scanner.nextInt();
							customer.withdrawAndDeposit(amount, "withdraw");
							break;
						}
						case 5: {
							System.out.println("Enter the amount to deposit");
							int amount = scanner.nextInt();
							customer.withdrawAndDeposit(amount, "deposit");
							break;
						}
						case 6: {
							System.out.println("Enter the number of product product");
							int productCount = scanner.nextInt();
							int totalAmount = 0;
							for (int i = 0; i < productCount; i++) {
								System.out.println("Enter the amount of " + i + " product");
								totalAmount += scanner.nextInt();
							}
							customer.withdrawAndDeposit(totalAmount, "withdraw");
							break;
						}
						}
					}
				} else if (choice == 7) {
					main(null);
				} else {
					System.out.println("Enter the proper number");
				}
			}
		}
	}
}
