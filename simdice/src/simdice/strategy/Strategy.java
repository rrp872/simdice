package simdice.strategy;

import java.util.List;

import simdice.bet.BettingSlip;
import simdice.util.BetEvaluator;

/**
 * Rules how to bet, possibly to maximize the player's profit.
 */
public interface Strategy {

	public boolean doContinue();
	
	public BettingSlip nextBet();
	
	// set conditions to stop betting
	public void setMaxNoOfBets(int maxNoOfBets);
	public void setMaxLoss(Double maxLoss);
	public void setMaxWin(Double maxWin);
	public void setMaxLossesInPastBets(int maxLossesInPastBets, int ofLastBetsLosses);
	public void setMaxWinsInPastBets(int maxWinsInPastBets, int ofLastBetsWins, boolean onlyIfBalanceWin);
	
	public int getMaxNoOfBets();
	
	public BetEvaluator getBetEvaluator();
	
	public List<BettingSlip> getBetHistory();
	
	public String getAbortReason();
}
