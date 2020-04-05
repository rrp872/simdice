package simdice.bet;

import simdice.util.MonopolyConstants;

public class DreamCatcherBet extends AbstractBet {

	public DreamCatcherBet(Double betAmount, Integer prediction) {
		this.betAmount = betAmount;
		this.prediction = prediction;
		this.predictionAsString = MonopolyConstants.getNameForElementNumber(prediction);
	}
}
