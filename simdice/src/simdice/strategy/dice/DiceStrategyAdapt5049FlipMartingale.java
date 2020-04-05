package simdice.strategy.dice;

import simdice.bankroll.Bankroll;
import simdice.bet.Bet;
import simdice.bet.BettingSlip;
import simdice.bet.DiceBet;
import simdice.resolver.Resolver;
import simdice.strategy.AbstractStrategy;

/**
 * Starts with over 50. Every loss switches over 50 --> under 49, under 49 --> over 50.
 */
public class DiceStrategyAdapt5049FlipMartingale extends AbstractStrategy {

	private double myBetAmount = 0d;
	private boolean betOver = true;
	private int flipAfterEveryXthLoss = 999;
	private int martingaleMaxNoOfLosses = 999;
	private int martingaleNoOfLossesCounter = 0;
	
	public DiceStrategyAdapt5049FlipMartingale(String nameOfGame, Bankroll bankroll, Resolver resolver) {
		
		super(nameOfGame, bankroll, resolver);
		
		minBet = bankroll.getStartAmount() * 0.01;
		
		maxLoss = bankroll.getAmount() / 3;
		maxWin = bankroll.getAmount() * 0.5;
		
		// 49% win probability
		maxWinsInPastBets = 20;
		ofLastBetsWins = 30;
		maxWinsInPastBetsOnlyIfBalanceWin = true;
		
		martingaleMaxNoOfLosses = 3;
		flipAfterEveryXthLoss = martingaleMaxNoOfLosses - 1;
		
		myBetAmount = minBet;
		nextBet = myBetAmount;
	}
	
	@Override
	public BettingSlip nextBet() {
		
		// flip over/under
		if (betEvaluator.isLastBetLost() && betEvaluator.getNoOfLosses() % flipAfterEveryXthLoss == 0) {
			betOver = ! betOver;
		}
		
		// Martingale
		if (betEvaluator.isLastBetLost()) {
			if (martingaleNoOfLossesCounter < martingaleMaxNoOfLosses) {
				// double the bet
				martingaleNoOfLossesCounter++;
				myBetAmount *= 2;
			} else {
				// give up Martingale
				myBetAmount = minBet;
				martingaleNoOfLossesCounter = 0;
			}
		}
		
		if (betEvaluator.isLastBetWon()) {
			martingaleNoOfLossesCounter = 0;
			myBetAmount = minBet;
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
