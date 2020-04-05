package simdice.strategy.dice;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import simdice.bankroll.Bankroll;
import simdice.bet.Bet;
import simdice.bet.BettingSlip;
import simdice.bet.DiceBet;
import simdice.resolver.Resolver;
import simdice.strategy.AbstractStrategy;
import simdice.util.DiceConstants;

public class DiceStrategyNonAdaptOver92Danielll extends AbstractStrategy {

	private List<Integer> predictionList = new ArrayList<Integer>(15);
	private int predictionListIndex = 0;
	
	public DiceStrategyNonAdaptOver92Danielll(String nameOfGame, Bankroll bankroll, Resolver resolver) {
		
		super(nameOfGame, bankroll, resolver);
		
		minBet = DiceConstants.BET_MIN;
		nextBet = minBet;
		
		populatePredictionList();
	}
	
	@Override
	public BettingSlip nextBet() {
		
		if (predictionListIndex == predictionList.size()) {
			predictionListIndex = 0;
		}
		Integer prediction = predictionList.get(predictionListIndex++);
		
		Double myBetAmount = minBet;
		boolean over = true;
		
		if (prediction.intValue() == 50) {
			Double targetBetAmount = minBet * 14d;
			myBetAmount = bankroll.getAmount() >= targetBetAmount ? targetBetAmount : bankroll.getAmount();
			over = new Random().nextBoolean();
		}
		
		Bet newBet = new DiceBet(myBetAmount, prediction, over);
		
		BettingSlip bettingSlip = new BettingSlip(nameOfGame);
		bettingSlip.add(newBet);
		
		nextBet = bettingSlip.getBetAmounts();
		if (! doContinue()) {
			return null;
		}
		
		getBetHistory().add(bettingSlip);
		
		return bettingSlip;
	}

	private void populatePredictionList() {
		predictionList.add(92);
		predictionList.add(93);
		predictionList.add(94);
		predictionList.add(95);
		predictionList.add(96);
		predictionList.add(97);
		predictionList.add(98);
		predictionList.add(97);
		predictionList.add(96);
		predictionList.add(95);
		predictionList.add(94);
		predictionList.add(93);
		predictionList.add(92);
		predictionList.add(50);
	}
}
