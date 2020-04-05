package simdice.bet;

import simdice.util.RingConstants;

public class RingBet extends AbstractBet {

	public RingBet(Double betAmount, Integer prediction) {
		this.betAmount = betAmount;
		this.prediction = prediction;
		this.predictionAsString = RingConstants.getNameForElementNumber(prediction);
	}
}
