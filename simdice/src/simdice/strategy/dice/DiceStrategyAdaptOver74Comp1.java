package simdice.strategy.dice;

import simdice.bankroll.Bankroll;
import simdice.bet.Bet;
import simdice.bet.BettingSlip;
import simdice.bet.DiceBet;
import simdice.resolver.Resolver;
import simdice.strategy.AbstractStrategy;
import simdice.util.DiceConstants;

/**
 * Primary strategy is to play "over 74".
 * 
 * Secondary strategy is to try to compensate every X'th loss with a single(!) bet, using a significantly higher probability.
 * If the compensation bets fails, we accept the loss and return to the primary strategy.
 * 
 */
public class DiceStrategyAdaptOver74Comp1 extends AbstractStrategy {

	private double myBetAmount = 0d;
	private int compensateEveryXthLoss = 999;
	private double myBetAmountComp;
	
	public DiceStrategyAdaptOver74Comp1(String nameOfGame, Bankroll bankroll, Resolver resolver) {
		
		super(nameOfGame, bankroll, resolver);
		
		minBet = DiceConstants.BET_MIN;

		maxLoss = bankroll.getAmount() / 4; // 4
		maxWin = bankroll.getAmount() * 0.2;
		
		// 25% win probability (for over 74)
		maxWinsInPastBets = 10;
		ofLastBetsWins = 30;
		maxWinsInPastBetsOnlyIfBalanceWin = true;
		
		myBetAmount = minBet;
		nextBet = myBetAmount;
		myBetAmountComp = myBetAmount * 3; // fixed at 3.0 for 74% chance
		
		compensateEveryXthLoss = 5; // 4-5
	}
	
	@Override
	public BettingSlip nextBet() {
		
		boolean betOver = true;
		double thisBetAmount = myBetAmount;
		
		if (betEvaluator.isLastBetLost() && betEvaluator.getNoOfLosses() % compensateEveryXthLoss == 0) {
			betOver = false;
			thisBetAmount = myBetAmountComp;
		}
		
		Bet newBet = new DiceBet(thisBetAmount, 74, betOver);

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
