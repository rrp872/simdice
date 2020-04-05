package simdice.resolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import simdice.bankroll.Bankroll;
import simdice.bet.Bet;
import simdice.bet.BettingSlip;
import simdice.generator.RingGenerator;
import simdice.util.History;
import simdice.util.RingConstants;

public class RingResolver extends AbstractResolver {

	private Map<Integer, Double> rewards = new HashMap<Integer, Double>(RingConstants.ELEMENT_TYPES);
	private int betCounter = 0;
	
	
	public RingResolver(Bankroll bankroll, RingGenerator generator) {
		this.bankroll = bankroll;
		this.generator = generator;
		historyList = new ArrayList<History>();
		
		populateRewards();
	}
	
	@Override
	public void bet(BettingSlip bets) {
		
		bankroll.add(- bets.getBetAmounts());
		bankroll.getToken().mine(bets.getBetAmounts());
		
		Integer betResult = generator.getNext();
		bets.setBetResult(betResult);
		
		for (Bet aBet : bets.getBets()) {
		
			Integer prediction = aBet.getPrediction();
			
			boolean isWin = betResult.equals(prediction);
			aBet.setWon(isWin);
			
			double multi = rewards.get(prediction);
			aBet.setMultiplier(multi);
			
			if (isWin) {
				double reward = aBet.getBetAmount() * multi;
				bankroll.add(reward);
				bets.addToBetReward(reward);
				bets.addToNoOfBetsWon();
			} else {
				bets.addToNoOfBetsLost();
			}
		}

		historyList.add(new History(++betCounter, bets, bankroll.getAmount(), bankroll.getToken().getTokenName(), bankroll.getToken().getAmount()));
	}

	private void populateRewards() {
		
		rewards.put(2, 2d);
		rewards.put(3, 3d);
		rewards.put(5, 5d);
		rewards.put(50, 50d);
	}
}
