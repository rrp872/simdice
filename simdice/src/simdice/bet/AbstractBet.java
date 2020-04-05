package simdice.bet;

public abstract class AbstractBet implements Bet {

	protected double betAmount;
	protected Integer prediction;
	protected String predictionAsString;
	protected Boolean isRangeOver = null;
	protected Boolean isRangeUnder = null;
	protected boolean isWon;
	protected double multiplier;
	
	@Override
	public double getBetAmount() {
		return betAmount;
	}

	@Override
	public Integer getPrediction() {
		return prediction;
	}

	@Override
	public String getPredectionAsString() {
		return predictionAsString;
	}

	@Override
	public Boolean isRangeOver() {
		return isRangeOver;
	}

	@Override
	public Boolean isRangeUnder() {
		return isRangeUnder;
	}

	@Override
	public boolean isWon() {
		return isWon;
	}

	@Override
	public void setWon(boolean won) {
		isWon = new Boolean(won);
	}

	@Override
	public void setMultiplier(double multi) {
		multiplier = multi;
	}

	@Override
	public double getMultiplier() {
		return multiplier;
	}

	@Override
	public boolean equals(Object obj) {
		AbstractBet other = (AbstractBet) obj; 
		return this.betAmount == other.betAmount && this.prediction.equals(other.prediction);
	}
}
