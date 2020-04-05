package simdice.token;

public interface Token {
	
	public int getMiningRatio();
	
	public double getStartAmount();
	public double getAmount();
	public double getMinedAmount();
	public double getMinedAmountPercent();
	public String getTokenName();
	
	/**
	 * Add a token amount, considering bet amount and mining ratio.
	 * 
	 * @param betAmount Bet Amount.
	 */
	public void mine(double betAmount);
	
	public String getNameOfGame();
}
