package simdice.bet;

public interface Bet {
	
	public double getBetAmount();
	
	public Integer getPrediction();
	public String getPredectionAsString();
	
	public boolean isWon();
	public void setWon(boolean won);
	
	public void setMultiplier(double multi);
	public double getMultiplier();
	
	public Boolean isRangeOver();
	public Boolean isRangeUnder();
}
