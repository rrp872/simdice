package simdice.strategy.dice;

import simdice.bankroll.Bankroll;
import simdice.bet.Bet;
import simdice.bet.BettingSlip;
import simdice.bet.DiceBet;
import simdice.resolver.Resolver;
import simdice.strategy.AbstractStrategy;
import simdice.util.DiceConstants;

/**
 * Primary strategy is to ride streaks at the edges, e.g. below 20 and/or over 80.
 * A trigger is defined for the (beginning of) a streak.
 * 
 * Secondary strategy, while waiting for the trigger, is to play over 4 with min bet.
 * We start with secondary strategy and wait for the trigger.
 * 
 * Trigger for discarding the 'streak': not a single win within the last X "over" bets. Let's try X=5. 
 * 
 */
public class DiceStrategyAdaptEdgeFlip extends AbstractStrategy {

	private final double BET_AMOUNT_LOW = DiceConstants.BET_MIN;
	private final double BET_AMOUNT_HIGH = BET_AMOUNT_LOW * 3;  // 3
	
	private final int EDGE = 20;  // 20
	private final int EDGE_OVER = 100 - EDGE;
	private final int EDGE_UNDER = EDGE - 1;
	
	private boolean triggerOver = false;
	private boolean triggerUnder = false;
	private int triggerLastBetWonBeforeBets = 0;
	
	private double myBetAmount = BET_AMOUNT_LOW;
	
	private int consHitsForStreakTrigger = 2;  // 2!!
	private int maxConsLossesToDiscardStreak = 5;  // 3-5   // 5
	
	public DiceStrategyAdaptEdgeFlip(String nameOfGame, Bankroll bankroll, Resolver resolver) {
		
		super(nameOfGame, bankroll, resolver);
		
		minBet = DiceConstants.BET_MIN;
		
		maxLoss = bankroll.getAmount() / 3;  // / 3
		maxWin  = bankroll.getAmount() * 1;  // * 1
		
		maxWinsInPastBets = 30; // 30
		ofLastBetsWins    = 40; // 40
		maxWinsInPastBetsOnlyIfBalanceWin = true;

		nextBet = myBetAmount;
	}
	
	@Override
	public BettingSlip nextBet() {
		
		if (triggerOver || triggerUnder) {
			
			if (betEvaluator.isLastBetWon()) {
				triggerLastBetWonBeforeBets = 0;
			} else {
				triggerLastBetWonBeforeBets++;
			}
			
			boolean resetTrigger = triggerLastBetWonBeforeBets >= maxConsLossesToDiscardStreak;
			
			// consider the streak to be ended
			if (resetTrigger) {
				triggerOver = false;
				triggerUnder = false;
				triggerLastBetWonBeforeBets = 0;
			}
		} else {
		
			int lastResultsOver  = betEvaluator.getNoOfBetsOverX(true,  consHitsForStreakTrigger, EDGE_OVER);
			int lastResultsUnder = betEvaluator.getNoOfBetsOverX(false, consHitsForStreakTrigger, EDGE_UNDER);
			
			// consider the streak to start (if trigger is met)
			triggerOver  = lastResultsOver  == consHitsForStreakTrigger;
			triggerUnder = lastResultsUnder == consHitsForStreakTrigger;
		}
		
		Bet newBet;		
		// if no trigger is active, play 'over 4'
		if (! (triggerOver || triggerUnder)) {
			newBet = new DiceBet(BET_AMOUNT_LOW, 4, true);

		// else bet on the streak
		} else {
			if (triggerOver) {
				newBet = new DiceBet(BET_AMOUNT_HIGH, EDGE_OVER, true);
			} else {
				newBet = new DiceBet(BET_AMOUNT_HIGH, EDGE_UNDER, false);
			}
		}
		
		BettingSlip bettingSlip = new BettingSlip(nameOfGame);
		bettingSlip.add(newBet);
		
		nextBet = bettingSlip.getBetAmounts();
		if (! doContinue()) {
			return null;
		}
		
		getBetHistory().add(bettingSlip);
		
		return bettingSlip;
	}
}