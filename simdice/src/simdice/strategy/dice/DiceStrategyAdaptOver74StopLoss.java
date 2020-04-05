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
 * Stop betting if the average percentageWonToExpectedRatio is below x (e.g. -0.4).
 */
public class DiceStrategyAdaptOver74StopLoss extends AbstractStrategy {

	private double myBetAmount = 0d;
	
	private final double winPercentageExpected = 0.25;  // 25% for over 74
	private final int inLastBets = 20;
	private int betCounter = 0; 
	private final double percentageWonToExpectedThreshold = -0.3;
	
	
	public DiceStrategyAdaptOver74StopLoss(String nameOfGame, Bankroll bankroll, Resolver resolver) {
		
		super(nameOfGame, bankroll, resolver);
		
		minBet = DiceConstants.BET_MIN;

		maxLoss = bankroll.getAmount() / 3;
		maxWin = bankroll.getAmount() * 0.5;
		
		maxWinsInPastBets = 7;  // 7
		ofLastBetsWins = inLastBets;
		maxWinsInPastBetsOnlyIfBalanceWin = true;
		
		myBetAmount = minBet;
		nextBet = myBetAmount;
		
	}
	
	@Override
	public BettingSlip nextBet() {
		
		if (betCounter >= inLastBets) {
			
			double percentageWonToExpected = betEvaluator.getPercentageWonToExpected(inLastBets, winPercentageExpected);
			
			// Test only
//			System.out.println(GlobalConstants.FORMAT_DOUBLE_PERCENT_FRACTION2.format(percentageWonToExpected));
			
			/*
			 * 0.0 means that expected and actual probability is balanced.
			 * Below 0.0 means that we had more losses than expected ('Lose streak').
			 */
			if (percentageWonToExpected < percentageWonToExpectedThreshold) {
			
				abortReason = GlobalConstants.ABORT_REASON_PERCENTAGE_WON_TO_EXPECTED_IS_BELOW_THRESHOLD;
				abortMessage = abortReason + ". Threshold: " + percentageWonToExpectedThreshold + ", win: "
					+ GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION6.format(bankroll.getBalance());
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
