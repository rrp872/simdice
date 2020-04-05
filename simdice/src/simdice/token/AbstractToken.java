package simdice.token;

import simdice.util.GlobalConstants;
import simdice.util.MonopolyConstants;

public abstract class AbstractToken implements Token {

	protected double amount;
	protected double startAmount;
	protected int miningRatio;
	protected double miningBonus;
	protected String tokenName;
	protected String nameOfGame;

	@Override
	public int getMiningRatio() {
		return miningRatio;
	}
	
	@Override
	public double getStartAmount() {
		return startAmount;
	}
	
	@Override
	public double getAmount() {
		return amount;
	}
	
	@Override
	public double getMinedAmount() {
		return amount - startAmount;
	}
	
	@Override
	public double getMinedAmountPercent() {
		return getMinedAmount() * 100 / startAmount;
	}
	
	@Override
	public void mine(double betAmount) {
		
		switch (nameOfGame) {
			case GlobalConstants.NAME_OF_GAME_MONOPOLY:
			case GlobalConstants.NAME_OF_GAME_DREAMCATCHER:
			
				amount += (betAmount * MonopolyConstants.USD_CONVERSION_RATE / miningRatio * miningBonus);
				break;

			default:
				
				amount += (betAmount / miningRatio * miningBonus);
				break;
		}
	}

	@Override
	public String getTokenName() {
		return tokenName;
	}
	
	@Override
	public String getNameOfGame() {
		return nameOfGame;
	}

	@Override
	public String toString() {
		return (new StringBuilder()
		  .append("name: ").append(tokenName)
		  .append("; amount: ").append(GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION4.format(amount))
		  .append("; start amount: ").append(GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION4.format(startAmount))
		  .append("; tokens mined: ").append(GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION4.format(getMinedAmount()))
		  .append("; tokens mined %: ").append(GlobalConstants.FORMAT_DOUBLE_PERCENT_FRACTION2.format(getMinedAmountPercent()))
		  .append("; mining ratio: 1:").append(GlobalConstants.FORMAT_INTEGER.format(miningRatio))
		  .append("; mining bonus: ").append(GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION4.format(miningBonus))
		  ).toString();
	}
}
