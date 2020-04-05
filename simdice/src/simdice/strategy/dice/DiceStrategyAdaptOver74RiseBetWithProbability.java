package simdice.strategy.dice;

import simdice.bankroll.Bankroll;
import simdice.bet.Bet;
import simdice.bet.BettingSlip;
import simdice.bet.DiceBet;
import simdice.resolver.Resolver;
import simdice.strategy.AbstractStrategy;
import simdice.util.DiceConstants;
import simdice.util.GlobalConstants;

/**
 * Rise the bet amount if the past win rate is below the expected average (25%).
 * Reset bet amount if we start losing again.
 */
public class DiceStrategyAdaptOver74RiseBetWithProbability extends AbstractStrategy {

	private double myBetAmount = 0d;
	
	private final double winPercentageExpected = 0.25;  // 25% for over 74
	private final int inLastBets = 20;
	private int betCounter = 0; 
	private double raiseFactor = 1.5;  // 1.3 - 1.5
	private final double percentageWonToExpectedThreshold = -0.3;
	
	
	public DiceStrategyAdaptOver74RiseBetWithProbability(String nameOfGame, Bankroll bankroll, Resolver resolver) {
		
		super(nameOfGame, bankroll, resolver);
		
		minBet = DiceConstants.BET_MIN;

		maxLoss = bankroll.getAmount() / 2;
		maxWin = bankroll.getAmount() * 0.25;
		
		maxWinsInPastBets = 7;  // 7
		ofLastBetsWins = 21;  // 21
		maxWinsInPastBetsOnlyIfBalanceWin = true;
		
		myBetAmount = minBet;
		nextBet = myBetAmount;
		
	}
	
	@Override
	public BettingSlip nextBet() {
		
		if (betCounter >= inLastBets) {
			
			double percentageWonToExpected = betEvaluator.getPercentageWonToExpected(inLastBets, winPercentageExpected);
			
			// Test only
			System.out.println(GlobalConstants.FORMAT_DOUBLE_PERCENT_FRACTION2.format(percentageWonToExpected));
			
			/*
			 * 0.0 means that expected and actual probability is balanced.
			 * Below 0.0 means that we had more losses than expected ('Lose streak').
			 */
			if (percentageWonToExpected <= percentageWonToExpectedThreshold && betEvaluator.isLastBetLost()) {
				myBetAmount *= raiseFactor;
				
			} else  {
				myBetAmount = minBet;
			}
		}
		
		Bet newBet = new DiceBet(myBetAmount, 74, true);

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
