package simdice.bet;

import simdice.util.MonopolyConstants;

public class MonopolyBet extends AbstractBet {

	public MonopolyBet(Double betAmount, Integer prediction) {
		this.betAmount = betAmount;
		this.prediction = prediction;
		this.predictionAsString = MonopolyConstants.getNameForElementNumber(prediction);
	}
}
