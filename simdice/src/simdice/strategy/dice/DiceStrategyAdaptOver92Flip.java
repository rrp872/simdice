package simdice.strategy.dice;

import simdice.bankroll.Bankroll;
import simdice.bet.Bet;
import simdice.bet.BettingSlip;
import simdice.bet.DiceBet;
import simdice.resolver.Resolver;
import simdice.strategy.AbstractStrategy;
import simdice.util.DiceConstants;

/**
 * Primary strategy is to play "over 92" and ride streaks of high numbers as long as possible.
 * 
 * Secondary strategy is to start with that only if high numbers are 'likely'.
 * Assumption: any number over 92 is a trigger to indicate the beginning of such a streak.
 * 
 * We start with "under 92" and wait for the trigger. If trigger comes, we switch to "over 92".
 * If it is not a streak, we switch back to "under 92".
 * Trigger for discarding the 'streak': not a single win within the last X "over" bets. Let's try X=5. 
 * 
 * bet amounts:
 * - under 92: 10
 * - over  92: 10
 * 
 */
public class DiceStrategyAdaptOver92Flip extends AbstractStrategy {

	private final double BET_AMOUNT_LOW = DiceConstants.BET_MIN;
//	private final double BET_AMOUNT_HIGH = BET_AMOUNT_LOW * 2;
	private final double BET_AMOUNT_HIGH = BET_AMOUNT_LOW;
	
	private double myBetAmount = BET_AMOUNT_HIGH;
	
	private int maxConsLossesToDiscardStreak = 8;
	
	// default: betting "under"
	private boolean lastBetStyleOver = false;
	
	public DiceStrategyAdaptOver92Flip(String nameOfGame, Bankroll bankroll, Resolver resolver) {
		
		super(nameOfGame, bankroll, resolver);
		
		minBet = DiceConstants.BET_MIN;
		
		// Set the defaults. Values be overridden later using the setters.
		maxLoss = bankroll.getAmount() / 3;
		maxWin = bankroll.getAmount() * 1;

		nextBet = myBetAmount;
	}
	
	@Override
	public BettingSlip nextBet() {
		
		boolean betStyleOver = lastBetStyleOver;
		
		// if lastBetStyle was "under" and we lost the last bet, switch to "over"
		if (! lastBetStyleOver && betEvaluator.getNoOfLossesInLastBets(1, null) == 1) {
			betStyleOver = true;
			myBetAmount = BET_AMOUNT_LOW;
		}
		
		// if lastBetStyle was "over" and we lost the last X "over" bets, switch to "under"
		if (lastBetStyleOver && betEvaluator.getNoOfLossesInLastBets(maxConsLossesToDiscardStreak, Boolean.TRUE) == maxConsLossesToDiscardStreak) {
			betStyleOver = false;
			myBetAmount = BET_AMOUNT_HIGH;
		}

		Bet newBet = new DiceBet(myBetAmount, 92, betStyleOver);
		lastBetStyleOver = betStyleOver;

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