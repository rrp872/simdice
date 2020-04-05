package simdice.bankroll;

import simdice.token.Token;
import simdice.util.GlobalConstants;
import simdice.util.MonopolyConstants;

public class Bankroll {
	
	private Token token;
	private double amount;
	private double startAmount;
	private double balance = 0d;
	
	public Bankroll(Double startAmount, Token token) {
		this.startAmount = startAmount > 0 ? startAmount : GlobalConstants.DEFAULT_BANKROLL_AMOUNT;
		this.amount = this.startAmount;
		this.token = token;
	}
	
	public void add(double amount) {
		this.amount += amount;
		this.balance += amount;
	}
	
	public double getAmount() {
		return amount;
	}
	
	public double getStartAmount() {
		return startAmount;
	}
	
	public double getMiningCostPerToken() {
		
		if (balance > 0) {
			return 0d;
		}
		
		if (token.getNameOfGame().equals(GlobalConstants.NAME_OF_GAME_DREAMCATCHER)) {
			return balance * MonopolyConstants.USD_CONVERSION_RATE / (token.getAmount() - token.getStartAmount());
		} else {
			return balance / (token.getAmount() - token.getStartAmount());
		}
	}
	
	@Override
	public String toString() {
		return (new StringBuilder()
		  .append("amount: ").append(GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION6.format(amount))
		  .append("; start amount: ").append(GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION6.format(startAmount))
		  .append("; win amount: ").append(GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION6.format(balance))
		  .append("; win amount %: ").append(GlobalConstants.FORMAT_DOUBLE_PERCENT_FRACTION2.format(balance * 100 / startAmount))
		  .append("; mining cost per token: ").append(GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION4.format(getMiningCostPerToken()))
		  ).toString();
	}

	public double getBalance() {
		return balance;
	}
	
	/**
	 * Gives the balance as ratio to the start amount.
	 * 
	 * @return
	 */
	public double getBalancePercent() {
		return balance * 100 / startAmount;
	}
	
	public Token getToken() {
		return token;
	}
}
