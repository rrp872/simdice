package simdice.strategy.dice;

import simdice.bankroll.Bankroll;
import simdice.bet.Bet;
import simdice.bet.BettingSlip;
import simdice.bet.DiceBet;
import simdice.resolver.Resolver;
import simdice.strategy.AbstractStrategy;
import simdice.util.DiceConstants;

public class DiceStrategyNonAdaptOver95 extends AbstractStrategy {

	private double myBetAmount = 0d;
	
	public DiceStrategyNonAdaptOver95(String nameOfGame, Bankroll bankroll, Resolver resolver) {
		
		super(nameOfGame, bankroll, resolver);
		
		minBet = DiceConstants.BET_MIN;
		
		// Set the defaults. Values be overridden later using the setters.
		maxLoss = bankroll.getAmount() / 3;
		maxWin = bankroll.getAmount() * 2;  // 800
		
		maxWinsInPastBets = 2;  // win 492.5 | min. bank roll is 267, so bank roll is at least 759.5 here
		ofLastBetsWins = 10;
		
//		maxConsWins = 3;  // win 738.75 | min. bank roll is 267, so bank roll is at least 1005.75 here
//		ofLastBetsWins = 20;
		

		myBetAmount = minBet;
		nextBet = myBetAmount;
	}
	
	@Override
	public BettingSlip nextBet() {
		
		Bet newBet = new DiceBet(myBetAmount, 95, true);

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
