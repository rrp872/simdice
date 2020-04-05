package simdice.strategy.dice;

import simdice.bankroll.Bankroll;
import simdice.bet.Bet;
import simdice.bet.BettingSlip;
import simdice.bet.DiceBet;
import simdice.resolver.Resolver;
import simdice.strategy.AbstractStrategy;
import simdice.util.DiceConstants;

public class DiceStrategyNonAdaptOver92 extends AbstractStrategy {

	private double myBetAmount = 0d;
	
	public DiceStrategyNonAdaptOver92(String nameOfGame, Bankroll bankroll, Resolver resolver) {
		
		super(nameOfGame, bankroll, resolver);
		
		minBet = DiceConstants.BET_MIN;
		
		// Set the defaults. Values be overridden later using the setters.
		maxLoss = bankroll.getAmount() / 3;
		maxWin = bankroll.getAmount() * 1.5;
		
		maxWinsInPastBets = 4;
		ofLastBetsWins = 25;
		

		myBetAmount = minBet;
		nextBet = myBetAmount;
	}
	
	@Override
	public BettingSlip nextBet() {
		
		Bet newBet = new DiceBet(myBetAmount, 92, true);

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
