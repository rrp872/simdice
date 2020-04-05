package simdice.strategy.ring;

import simdice.bankroll.Bankroll;
import simdice.bet.Bet;
import simdice.bet.BettingSlip;
import simdice.bet.RingBet;
import simdice.resolver.Resolver;
import simdice.strategy.AbstractStrategy;
import simdice.util.RingConstants;

public class RingStrategyNonAdaptGray extends AbstractStrategy {

	private double myBetAmount = 0d;
	
	public RingStrategyNonAdaptGray(String nameOfGame, Bankroll bankroll, Resolver resolver) {
		
		super(nameOfGame, bankroll, resolver);
		
		minBet = RingConstants.BET_MIN;
		
		// Set the defaults. Values be overridden later using the setters.
//		maxLoss = bankroll.getAmount() / 2;    // limit loss to half of bank roll
//		maxWin = bankroll.getAmount();         // stop when bank roll has been doubled
		myBetAmount = bankroll.getAmount() / 20; // bet with 5% of bank roll
		nextBet = myBetAmount;
	}
	
	@Override
	public BettingSlip nextBet() {
		
		Bet bet1 = new RingBet(myBetAmount, 2);

		BettingSlip bettingSlip = new BettingSlip(nameOfGame);
		bettingSlip.add(bet1);
		
		nextBet = bettingSlip.getBetAmounts();
		if (! doContinue()) {
			return null;
		}
		
		getBetHistory().add(bettingSlip);
		
		return bettingSlip;
	}

}
