package simdice.resolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import simdice.bankroll.Bankroll;
import simdice.bet.Bet;
import simdice.bet.BettingSlip;
import simdice.generator.DreamCatcherGenerator;
import simdice.util.DreamCatcherConstants;
import simdice.util.History;

public class DreamCatcherResolver extends AbstractResolver {

	private Map<Integer, Double> rewards = new HashMap<Integer, Double>(DreamCatcherConstants.ELEMENT_TYPES);
	private int betCounter = 0;
	
	
	public DreamCatcherResolver(Bankroll bankroll, DreamCatcherGenerator generator) {
		this.bankroll = bankroll;
		this.generator = generator;
		this.nameOfGame = generator.getNameOfGame();
		historyList = new ArrayList<History>();
		
		populateRewards();
	}
	
	@Override
	public void bet(BettingSlip bets) {
		
		bankroll.add(- bets.getBetAmounts());
		bankroll.getToken().mine(bets.getBetAmounts());
		double bonusMulti = 1d;
		
		Integer betResult;
		
		do {
			betResult = generator.getNext();
			
			if (betResult.intValue() == 200) {
				bonusMulti *= 2d;
			}
			if (betResult.intValue() == 700) {
				bonusMulti *= 7d;
			}
		} while (betResult.intValue() >= 200);
		
		bets.setBetResult(betResult);
		
		for (Bet aBet : bets.getBets()) {
		
			Integer prediction = aBet.getPrediction();
			
			boolean isWin = betResult == prediction;
			aBet.setWon(isWin);
			
			Double multi = rewards.get(prediction);
			multi *= bonusMulti;
			aBet.setMultiplier(multi);
			
			if (isWin) {
				Double reward = aBet.getBetAmount() * multi;
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
		
		rewards.put(1, 2d);
		rewards.put(2, 3d);
		rewards.put(5, 6d);
		rewards.put(10, 11d);
		rewards.put(20, 21d);
		rewards.put(40, 41d);
	}
}
