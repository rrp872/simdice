package simdice.strategy.dice;

import simdice.bankroll.Bankroll;
import simdice.bet.Bet;
import simdice.bet.BettingSlip;
import simdice.bet.DiceBet;
import simdice.resolver.Resolver;
import simdice.strategy.AbstractStrategy;
import simdice.util.DiceConstants;

public class DiceStrategyNonAdaptOver4 extends AbstractStrategy {

	private double myBetAmount = 0d;
	
	public DiceStrategyNonAdaptOver4(String nameOfGame, Bankroll bankroll, Resolver resolver) {
		
		super(nameOfGame, bankroll, resolver);
		
		minBet = DiceConstants.BET_MIN;

		maxLoss = bankroll.getAmount() / 3; // 3
		
		maxWinsInPastBets = 30; 			// 30 good (between 22-30)
		ofLastBetsWins = maxWinsInPastBets;
		
		myBetAmount = minBet;
		nextBet = myBetAmount;
		
	}
	
	@Override
	public BettingSlip nextBet() {
		
		Bet newBet = new DiceBet(myBetAmount, 4, true);

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
