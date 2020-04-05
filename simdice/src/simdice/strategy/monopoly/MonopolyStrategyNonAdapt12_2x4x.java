package simdice.strategy.monopoly;

import simdice.bankroll.Bankroll;
import simdice.bet.Bet;
import simdice.bet.BettingSlip;
import simdice.bet.MonopolyBet;
import simdice.resolver.Resolver;
import simdice.strategy.AbstractStrategy;
import simdice.util.MonopolyConstants;

public class MonopolyStrategyNonAdapt12_2x4x extends AbstractStrategy {

	
	public MonopolyStrategyNonAdapt12_2x4x(String nameOfGame, Bankroll bankroll, Resolver resolver) {
		
		super(nameOfGame, bankroll, resolver);
		
		minBet = MonopolyConstants.BET_MIN;
		nextBet = minBet;
		
		maxLoss = bankroll.getAmount();
		maxWin = bankroll.getAmount();
	}
	
	@Override
	public BettingSlip nextBet() {
		
		Bet bet1 = new MonopolyBet(minBet * 12, 1);
		Bet bet2 = new MonopolyBet(minBet *  8, 2);
		Bet bet3 = new MonopolyBet(minBet *  2, 14);
		Bet bet4 = new MonopolyBet(minBet *  1, 41);
		
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
