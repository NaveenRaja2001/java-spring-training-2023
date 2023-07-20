package CreditCardImpl;

import com.example.credit_management_system.CreditCard;

public class PlatinumCard extends CreditCard {
	public PlatinumCard(int balance, String password, long accountNumber, String creditCardStatus, String cardType) {
		super(balance, password, accountNumber, creditCardStatus, cardType);
	}

	@Override
	public int limit() {
		return 100000;
	}
}
