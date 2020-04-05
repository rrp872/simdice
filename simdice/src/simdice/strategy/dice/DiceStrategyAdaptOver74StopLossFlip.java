package simdice.strategy.dice;

import simdice.bankroll.Bankroll;
import simdice.bet.Bet;
import simdice.bet.BettingSlip;
import simdice.bet.DiceBet;
import simdice.resolver.DiceResolver;
import simdice.resolver.Resolver;
import simdice.strategy.AbstractStrategy;
import simdice.util.DiceConstants;

/**
 * If the average percentageWonToExpectedRatio is below x (e.g. -0.4), flip the bet from over to under or vice versa.
 */
public class DiceStrategyAdaptOver74StopLossFlip extends AbstractStrategy {

	private double myBetAmount = 0d;
	
	private final double winPercentageExpectedOver = 0.25;  // 25% for over 74
	private final double winPercentageExpectedUnder = 0.74;  // 74% for under 74
	
	private int maxWinsInPastBetsOver = 8; // 7, 10
	private int maxWinsInPastBetsUnder = 18; // 18, 20
	
	private final int inLastBets = 20;
	private int betCounter = 0; 
	private final double percentageWonToExpectedThreshold = 0.0;  // -0.4 -- 0.0 ??
	
	
	public DiceStrategyAdaptOver74StopLossFlip(String nameOfGame, Bankroll bankroll, Resolver resolver) {
		
		super(nameOfGame, bankroll, resolver);
		
		// default: betting "over"
		betStyleIsOver = Boolean.TRUE;
		
		minBet = DiceConstants.BET_MIN;

		maxLoss = bankroll.getAmount() / 3;
		maxWin = bankroll.getAmount() * 0.25;
		
		maxWinsInPastBets = maxWinsInPastBetsOver;
		ofLastBetsWins = inLastBets;
		maxWinsInPastBetsOnlyIfBalanceWin = true;
		
		myBetAmount = minBet;
		nextBet = myBetAmount;
		
	}
	
	@Override
	public BettingSlip nextBet() {
		
		if (betCounter >= inLastBets) {
			
			int lastBetsWonPotential = ((DiceResolver) resolver).getNoOfWinsPotential(inLastBets, betStyleIsOver, 74);
			
			double percentageWonToExpected = betEvaluator.getPercentageWonToExpectedPotential(
				inLastBets, lastBetsWonPotential, betStyleIsOver ? winPercentageExpectedOver : winPercentageExpectedUnder);
			
			/*
			 * 0.0 means that expected and actual probability is balanced.
			 * Below 0.0 means that we had more losses than expected ('Lose streak').
			 */
			if (percentageWonToExpected < percentageWonToExpectedThreshold && betEvaluator.isLastBetLost()) {
			
				// flip
				betStyleIsOver = ! betStyleIsOver;
				maxWinsInPastBets = betStyleIsOver ? maxWinsInPastBetsOver : maxWinsInPastBetsUnder;
			}
		}
		
		Bet newBet = new DiceBet(myBetAmount, 74, betStyleIsOver);

		BettingSlip bettingSlip = new BettingSlip(nameOfGame);
		bettingSlip.add(newBet);
		betCounter++;
		
		nextBet = bettingSlip.getBetAmounts();
		if (! doContinue()) {
			return null;
		}
		
		getBetHistory().add(bettingSlip);
		
		return bettingSlip;
	}

}
