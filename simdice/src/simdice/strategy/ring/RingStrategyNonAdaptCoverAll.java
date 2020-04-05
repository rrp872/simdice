package simdice.strategy.ring;

import simdice.bankroll.Bankroll;
import simdice.bet.Bet;
import simdice.bet.BettingSlip;
import simdice.bet.RingBet;
import simdice.resolver.Resolver;
import simdice.strategy.AbstractStrategy;
import simdice.util.RingConstants;

public class RingStrategyNonAdaptCoverAll extends AbstractStrategy {

	
	public RingStrategyNonAdaptCoverAll(String nameOfGame, Bankroll bankroll, Resolver resolver) {
		
		super(nameOfGame, bankroll, resolver);
		
		minBet = RingConstants.BET_MIN;
		nextBet = minBet;
		
		// Set the defaults. Values be overridden later using the setters.
//		maxLoss = bankroll.getAmount() / 2;    // limit loss to half of bank roll
//		maxWin = bankroll.getAmount();         // stop when bank roll has been doubled
	}
	
	@Override
	public BettingSlip nextBet() {
		
		Bet bet1 = new RingBet(260d  , 2);   // Gray
		Bet bet2 = new RingBet(170d  , 3);   // Red
		Bet bet3 = new RingBet(100d  , 5);   // Blue
		Bet bet4 = new RingBet(minBet, 50);  // Gold

		BettingSlip bettingSlip = new BettingSlip(nameOfGame);
		bettingSlip.add(bet1);
		bettingSlip.add(bet2);
		bettingSlip.add(bet3);
		bettingSlip.add(bet4);

		nextBet = bettingSlip.getBetAmounts();
		if (! doContinue()) {
			return null;
		}
		
		getBetHistory().add(bettingSlip);
		
		return bettingSlip;
	}

}
