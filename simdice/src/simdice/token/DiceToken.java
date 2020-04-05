package simdice.token;

import simdice.util.GlobalConstants;

public class DiceToken extends AbstractToken {
	
	public static final int MINING_RATIO = 432;
	public static final double MINING_BONUS = 1.0d;

	public DiceToken(String nameOfGame, Double startAmount) {
		this.startAmount = startAmount > 0 ? startAmount : GlobalConstants.DEFAULT_TOKEN_AMOUNT;
		this.amount = this.startAmount;
		this.miningRatio = MINING_RATIO;
		this.miningBonus = MINING_BONUS;
		this.tokenName = "Dice";
		this.nameOfGame = nameOfGame;
	}
}
