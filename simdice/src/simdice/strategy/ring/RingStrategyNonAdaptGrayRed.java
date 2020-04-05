package simdice.strategy.ring;

import simdice.bankroll.Bankroll;
import simdice.bet.Bet;
import simdice.bet.BettingSlip;
import simdice.bet.RingBet;
import simdice.resolver.Resolver;
import simdice.strategy.AbstractStrategy;
import simdice.util.RingConstants;

public class RingStrategyNonAdaptGrayRed extends AbstractStrategy {

	
	public RingStrategyNonAdaptGrayRed(String nameOfGame, Bankroll bankroll, Resolver resolver) {
		
		super(nameOfGame, bankroll, resolver);
		
		minBet = RingConstants.BET_MIN;
		nextBet = minBet;
		
		// Set the defaults. Values be overridden later using the setters.
//		maxLoss = bankroll.getAmount() / 2;    // limit loss to half of bank roll
//		maxWin = bankroll.getAmount();         // stop when bank roll has been doubled
	}
	
	@Override
	public BettingSlip nextBet() {
		
		Bet bet1 = new RingBet(minBet * 2, 2);  // Gray
		Bet bet2 = new RingBet(minBet    , 3);  // Red

		BettingSlip bettingSlip = new BettingSlip(nameOfGame);
		bettingSlip.add(bet1);
		bettingSlip.add(bet2);
		
		nextBet = bettingSlip.getBetAmounts();
		if (! doContinue()) {
			return null;
		}
		
		getBetHistory().add(bettingSlip);
		
		return bettingSlip;
	}

}
