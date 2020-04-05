package simdice.bet;

public class DiceBet extends AbstractBet {

	public DiceBet(Double betAmount, Integer prediction, Boolean isRangeOver) {
		this.betAmount = betAmount;
		this.prediction = prediction;
		this.predictionAsString = prediction.toString();
		this.isRangeOver = isRangeOver;
		this.isRangeUnder = ! isRangeOver;
	}
}
