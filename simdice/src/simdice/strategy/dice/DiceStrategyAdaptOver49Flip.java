package simdice.strategy.dice;

import simdice.bankroll.Bankroll;
import simdice.bet.Bet;
import simdice.bet.BettingSlip;
import simdice.bet.DiceBet;
import simdice.resolver.Resolver;
import simdice.strategy.AbstractStrategy;
import simdice.util.DiceConstants;

/**
 * Starts with over 50. Every loss switches over 50 --> under 49, under 49 --> over 50.
 */
public class DiceStrategyAdaptOver49Flip extends AbstractStrategy {

	private double myBetAmount = 0d;
	private boolean betOver = true;
	private int flipAfterEveryXthLoss = 2;
	
	public DiceStrategyAdaptOver49Flip(String nameOfGame, Bankroll bankroll, Resolver resolver) {
		
		super(nameOfGame, bankroll, resolver);
		
		minBet = Math.max(DiceConstants.BET_MIN, bankroll.getStartAmount() * 0.01);
		
		maxLoss = bankroll.getAmount() / 3;
		maxWin = bankroll.getAmount() * 0.2;
		
		// 49% win probability
		maxWinsInPastBets = 17; // 17
		ofLastBetsWins = 30;    // 30
		maxWinsInPastBetsOnlyIfBalanceWin = true;
		
		myBetAmount = minBet;
		nextBet = myBetAmount;
	}
	
	@Override
	public BettingSlip nextBet() {
		
		// flip over/under
		if (betEvaluator.isLastBetLost() && betEvaluator.getNoOfLosses() % flipAfterEveryXthLoss == 0) {
			betOver = ! betOver;
		}
		
		int prediction = betOver ? 50 : 49;
		Bet newBet = new DiceBet(myBetAmount, prediction, betOver);

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
