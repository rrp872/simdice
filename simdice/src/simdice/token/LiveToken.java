package simdice.token;

import simdice.util.GlobalConstants;

public class LiveToken extends AbstractToken {
	
	public static final int MINING_RATIO = 362;
	public static final double MINING_BONUS = 1.5d;

	public LiveToken(String nameOfGame, Double startAmount) {
		this.startAmount = startAmount > 0 ? startAmount : GlobalConstants.DEFAULT_TOKEN_AMOUNT;
		this.amount = this.startAmount;
		this.miningRatio = MINING_RATIO;
		this.miningBonus = MINING_BONUS;
		this.tokenName = "Live";
		this.nameOfGame = nameOfGame;
	}
}
