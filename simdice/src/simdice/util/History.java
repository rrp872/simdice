package simdice.util;

import simdice.bet.Bet;
import simdice.bet.BettingSlip;

public class History {

	private int index;
	private BettingSlip bettingSlip;
	private double bankrollAmount;
	private String tokenName;
	private double tokenAmount;
	
	public History(int index, BettingSlip bettingSlip, double bankrollAmount, String tokenName, double tokenAmount) {
		super();
		this.index = index;
		this.bettingSlip = bettingSlip;
		this.bankrollAmount = bankrollAmount;
		this.tokenName = tokenName;
		this.tokenAmount = tokenAmount;
		
	}
	
	public String toString(String nameOfGame) {

		String betSlipRewardFormatted = GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION6.format(bettingSlip.getBetReward());
		String betSlipBetAmountsFormatted = GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION6.format(bettingSlip.getBetAmounts());
		String bankrollAmountFormatted = GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION6.format(bankrollAmount);
		String tokenAmountFormatted = GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION6.format(tokenAmount);
		
		StringBuilder sb = new StringBuilder()
		  .append("** bet slip no.: ").append(index).append(" **")
		  .append(" any bet won: ").append(bettingSlip.isAnyBetWon() ? "###YES###" : "NO")

		  .append("; bet OUTCOME: ");
			Integer betResult = bettingSlip.getBetResult();
			String append1 = betResult.toString();
			if (GlobalConstants.NAME_OF_GAME_RING.equals(nameOfGame)) {
				append1 = RingConstants.getNameForElementNumber(betResult);
			}
		  
		  sb.append(append1).append("; bet slip reward: ").append(betSlipRewardFormatted)
		  .append("; bet slip wagered: ").append(betSlipBetAmountsFormatted)
		  .append("; bankroll: ").append(bankrollAmountFormatted)
		  .append("; ").append(tokenName).append(": ").append(tokenAmountFormatted);
		  
		int betCounter = 0;
		for (Bet bet : bettingSlip.getBets()) {
			sb.append("\n     bet no.: ").append(++betCounter)
				.append("; bet prediction: ").append(bet.getPredectionAsString());
				
				if (GlobalConstants.NAME_OF_GAME_DICE.equals(nameOfGame)) {
					sb.append("; bet style: ").append(bet.isRangeOver() ? "+++ over" : "--- under");
				}
				
				sb.append("; bet wagered: ").append(GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION6.format(bet.getBetAmount()))
				.append("; multiplier: ").append(GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION4.format(bet.getMultiplier()));
		}

		return sb.toString();
	}

	public Boolean getFirstBetStyleIsRangeOver() {
		return bettingSlip.getFirstBetStyleIsRangeOver();
	}
	
	public int getIndex() {
		return index;
	}
	public Integer getDraw() {
		return bettingSlip.getBetResult();
	}
	public boolean isWin() {
		return bettingSlip.isAnyBetWon();
	}
	public double getReward() {
		return bettingSlip.getBetReward();
	}
	public double getBankrollAmount() {
		return bankrollAmount;
	}
	public String getTokenName() {
		return tokenName;
	}
	public double getTokenAmount() {
		return tokenAmount;
	}
}
