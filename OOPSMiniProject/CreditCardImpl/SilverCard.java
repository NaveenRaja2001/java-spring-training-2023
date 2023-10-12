package CreditCardImpl;

import com.example.credit_management_system.CreditCard;

public class SilverCard extends CreditCard {
	public SilverCard(int balance, String password, long accountNumber, String creditCardStatus, String cardType) {
		super(balance, password, accountNumber, creditCardStatus, cardType);
	}

	@Override
	public int limit() {
		return 10000;
	}
}
