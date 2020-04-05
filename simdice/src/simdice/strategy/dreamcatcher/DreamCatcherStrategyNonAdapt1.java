package simdice.strategy.dreamcatcher;

import simdice.bankroll.Bankroll;
import simdice.bet.Bet;
import simdice.bet.BettingSlip;
import simdice.bet.DreamCatcherBet;
import simdice.resolver.Resolver;
import simdice.strategy.AbstractStrategy;
import simdice.util.MonopolyConstants;

public class DreamCatcherStrategyNonAdapt1 extends AbstractStrategy {

	
	public DreamCatcherStrategyNonAdapt1(String nameOfGame, Bankroll bankroll, Resolver resolver) {
		
		super(nameOfGame, bankroll, resolver);
		
		minBet = MonopolyConstants.BET_MIN;
		nextBet = minBet;
		
		// Set the defaults. Values be overridden later using the setters.
//		maxLoss = bankroll.getAmount() / 2;    // limit loss to half of bank roll
//		maxWin = bankroll.getAmount();         // stop when bank roll has been doubled
	}
	
	@Override
	public BettingSlip nextBet() {
		
		Bet bet1 = new DreamCatcherBet(minBet, 1);

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
