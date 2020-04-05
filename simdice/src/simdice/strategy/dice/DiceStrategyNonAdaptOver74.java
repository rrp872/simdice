package simdice.strategy.dice;

import simdice.bankroll.Bankroll;
import simdice.bet.Bet;
import simdice.bet.BettingSlip;
import simdice.bet.DiceBet;
import simdice.resolver.Resolver;
import simdice.strategy.AbstractStrategy;
import simdice.util.DiceConstants;

public class DiceStrategyNonAdaptOver74 extends AbstractStrategy {

	private double myBetAmount = 0d;
	
	public DiceStrategyNonAdaptOver74(String nameOfGame, Bankroll bankroll, Resolver resolver) {
		
		super(nameOfGame, bankroll, resolver);
		
		minBet = DiceConstants.BET_MIN;

		maxLoss = bankroll.getAmount() / 3; // 3
		maxWin = bankroll.getAmount() * 0.4;
		
		// 25% win probability
		maxWinsInPastBets = 10;
		ofLastBetsWins = 30;
		maxWinsInPastBetsOnlyIfBalanceWin = true;
		
		myBetAmount = minBet;
		nextBet = myBetAmount;
		
	}
	
	@Override
	public BettingSlip nextBet() {
		
		Bet newBet = new DiceBet(myBetAmount, 74, true);

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
