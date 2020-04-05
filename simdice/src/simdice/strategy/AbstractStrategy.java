package simdice.strategy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import simdice.bankroll.Bankroll;
import simdice.bet.Bet;
import simdice.bet.BettingSlip;
import simdice.resolver.Resolver;
import simdice.util.BetEvaluator;
import simdice.util.GlobalConstants;
import simdice.util.History;

public abstract class AbstractStrategy implements Strategy {

	protected String nameOfGame;
	
	protected BetEvaluator betEvaluator;

	protected List<BettingSlip> betHistory = new ArrayList<BettingSlip>();
	protected List<History> resolverHistory;
	protected Resolver resolver;
	
	protected Bankroll bankroll;
	protected Double minBet;
	protected Double nextBet;
	
	protected Double maxLoss;
	protected Double maxWin;
	
	protected String abortReason;
	protected String abortMessage;
	
	protected int maxLossesInPastBets = GlobalConstants.GENERATOR_MAX_NUMBERS;
	protected int ofLastBetsLosses = GlobalConstants.GENERATOR_MAX_NUMBERS;
	
	protected int maxWinsInPastBets = GlobalConstants.GENERATOR_MAX_NUMBERS;
	protected int ofLastBetsWins = GlobalConstants.GENERATOR_MAX_NUMBERS;
	protected boolean maxWinsInPastBetsOnlyIfBalanceWin = false;
	protected Boolean betStyleIsOver = null;
	
	protected int maxNoOfBets = Integer.MAX_VALUE;

	
	public AbstractStrategy(String nameOfGame, Bankroll bankroll, Resolver resolver) {
		
		this.nameOfGame = nameOfGame;
		this.bankroll = bankroll;
		this.resolver = resolver;
		this.resolverHistory = resolver.getHistory();
		this.betEvaluator = new BetEvaluator(resolverHistory, this);
	}
	
	@Override
	public boolean doContinue() {
		
		String abortReason = null;

		/*
		 * If the strategy instance already gave a reason, take that one (when abortReason != null).
		 * Else evaluate the generic reasons (inside the IF clause below).  
		 */
		if (this.abortReason == null) {
			
			double bankrollAmount = bankroll.getAmount();
			double bankrollBalance = bankroll.getBalance();
			
			String bankrollAmountFormatted = GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION6.format(bankrollAmount);
			String bankrollBalanceFormatted = GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION6.format(bankrollBalance);
			String nextBetFormatted = GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION6.format(nextBet);
			String maxLossFormatted = GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION6.format(maxLoss);
			String maxWinFormatted = GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION6.format(maxWin);
			
			if (bankrollAmount <= 0) {
				abortReason = GlobalConstants.ABORT_REASON_BANKROLL_EMPTY;
				abortMessage = "\nSTOP betting because " + GlobalConstants.ABORT_REASON_BANKROLL_EMPTY + ". Bank roll: " + bankrollAmountFormatted;
			}
			
			if (bankrollAmount < nextBet) {
				abortReason = GlobalConstants.ABORT_REASON_BANKROLL_TOO_LOW_NEXT_BET;
				abortMessage = "\nSTOP betting because " + GlobalConstants.ABORT_REASON_BANKROLL_TOO_LOW_NEXT_BET + ". Bank roll: " + bankrollAmountFormatted + ", next bet: " + nextBetFormatted;
			}
			
			if (betHistory != null) {
				if (maxNoOfBets == betHistory.size()) {
					abortReason = GlobalConstants.ABORT_REASON_BETS_MAX_NO_REACHED;
					abortMessage = "\nSTOP betting because " + GlobalConstants.ABORT_REASON_BETS_MAX_NO_REACHED + ". Max. number of bets: " + maxNoOfBets;
				}
			}
			
			if (maxLoss != null) {
				if (bankrollBalance <= (maxLoss * -1)) {
					abortReason = GlobalConstants.ABORT_REASON_BALANCE_LOSS_MAX_REACHED;
					abortMessage = "\nSTOP betting because " + GlobalConstants.ABORT_REASON_BALANCE_LOSS_MAX_REACHED + ". Max. loss: " + maxLossFormatted + " (loss: " + bankrollBalanceFormatted + ")";
				}
			}
			
			if (maxWin != null) {
				if (bankrollBalance >= maxWin) {
					abortReason = GlobalConstants.ABORT_REASON_BALANCE_WIN_MAX_REACHED;
					abortMessage = "\nSTOP betting because " + GlobalConstants.ABORT_REASON_BALANCE_WIN_MAX_REACHED + ". Max. win: " + maxWinFormatted + " (win: " + bankrollBalanceFormatted + ")";
				}
			}
			
			if (ofLastBetsLosses <= resolverHistory.size()) {
				if (betEvaluator.getNoOfLossesInLastBets(ofLastBetsLosses, betStyleIsOver) >= maxLossesInPastBets) {
					abortReason = GlobalConstants.ABORT_REASON_BALANCE_LOSS_MAX_IN_PAST_BETS_REACHED;
					abortMessage = "\nSTOP betting because " + GlobalConstants.ABORT_REASON_BALANCE_LOSS_MAX_IN_PAST_BETS_REACHED + ". " + maxLossesInPastBets + " losses within last " + ofLastBetsLosses + " bets";
				}
			}
			
			if (ofLastBetsWins <= resolverHistory.size()) {
				if (betEvaluator.getNoOfWinsInLastBets(ofLastBetsWins, betStyleIsOver) >= maxWinsInPastBets) {
					
					if (! maxWinsInPastBetsOnlyIfBalanceWin) {
						abortReason = GlobalConstants.ABORT_REASON_BALANCE_WIN_MAX_IN_PAST_BETS_REACHED;
						abortMessage = "\nSTOP betting because " + GlobalConstants.ABORT_REASON_BALANCE_WIN_MAX_IN_PAST_BETS_REACHED + ". " + maxWinsInPastBets + " wins within last " + ofLastBetsWins + " bets";
					
					} else if (bankrollBalance > 0) {
						abortReason = GlobalConstants.ABORT_REASON_BALANCE_WIN_MAX_IN_PAST_BETS_REACHED_AND_BALANCE_WIN;
						abortMessage = "\nSTOP betting because " + GlobalConstants.ABORT_REASON_BALANCE_WIN_MAX_IN_PAST_BETS_REACHED_AND_BALANCE_WIN
							+ ". " + maxWinsInPastBets + " wins within last " + ofLastBetsWins + " bets (win: " + bankrollBalanceFormatted + ")";
					}
				}
			}
			
			this.abortReason = abortReason;
		}

		if (this.abortMessage != null) {
			System.out.println(this.abortMessage);
		}
		
		return this.abortReason == null;
	}

	@Override
	public String getAbortReason() {
		return abortReason;
	}

	@Override
	public String toString() {
		
		Set<String> allPredictionsWithNames = new HashSet<String>();
		Set<Double> allAmounts = new HashSet<Double>();
		Set<String> allStyles = new HashSet<String>();
		
		for (BettingSlip bets : betHistory) {
			allPredictionsWithNames.addAll(bets.getAllPredictionsWithNames());
			allAmounts.addAll(bets.getAllBetAmounts());
			Bet firstBet = bets.getBets().iterator().next();
			allStyles.add(firstBet.isRangeOver() != null ? (firstBet.isRangeOver() ? "over" : "under") : "N/A");
		}
		
		return (new StringBuilder()
		  .append("name: ").append(this.getClass().getSimpleName())
		  .append("; bet prediction(s): ").append(allPredictionsWithNames.toString())
		  .append("; bet style(s): ").append(allStyles.toString())
		  .append("; bet amount(s): ").append(allAmounts.toString())
		  .append("; stop max. bets: ").append(GlobalConstants.FORMAT_INTEGER.format(maxNoOfBets))
		  .append("; stop max. loss: ").append(GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION6.format(maxLoss))
		  .append("; stop max. win: ").append(GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION6.format(maxWin))
		  .append("; stop max. cons. losses: ").append(GlobalConstants.FORMAT_INTEGER.format(maxLossesInPastBets)).append(" of last ").append(GlobalConstants.FORMAT_INTEGER.format(ofLastBetsLosses))
		  .append("; stop max. cons. wins: ").append(GlobalConstants.FORMAT_INTEGER.format(maxWinsInPastBets)).append(" of last ").append(GlobalConstants.FORMAT_INTEGER.format(ofLastBetsWins))
		  ).toString();
	}

	@Override
	public void setMaxLossesInPastBets(int maxLossesInPastBets, int ofLastBetsLosses) {
		
		maxLossesInPastBets = maxLossesInPastBets < 1 ? 1 : maxLossesInPastBets;
		ofLastBetsLosses = ofLastBetsLosses < maxLossesInPastBets ? maxLossesInPastBets : ofLastBetsLosses;
		
		this.maxLossesInPastBets = maxLossesInPastBets;
		this.ofLastBetsLosses = ofLastBetsLosses;
	}

	@Override
	public void setMaxWinsInPastBets(int maxWinsInPastBets, int ofLastBetsWins, boolean maxWinsInPastBetsOnlyIfBalanceWin) {
		
		maxWinsInPastBets = maxWinsInPastBets < 1 ? 1 : maxWinsInPastBets;
		ofLastBetsWins = ofLastBetsWins < maxWinsInPastBets ? maxWinsInPastBets : ofLastBetsWins;
		
		this.maxWinsInPastBets = maxWinsInPastBets;
		this.ofLastBetsWins = ofLastBetsWins;
		this.maxWinsInPastBetsOnlyIfBalanceWin = maxWinsInPastBetsOnlyIfBalanceWin;
	}

	@Override
	public List<BettingSlip> getBetHistory() {
		return betHistory;
	}

	@Override
	public void setMaxLoss(Double maxLoss) {
		this.maxLoss = maxLoss;
	}

	@Override
	public void setMaxWin(Double maxWin) {
		this.maxWin = maxWin;
	}

	@Override
	public int getMaxNoOfBets() {
		return maxNoOfBets;
	}

	@Override
	public void setMaxNoOfBets(int maxNoOfBets) {
		this.maxNoOfBets = maxNoOfBets < GlobalConstants.GENERATOR_MAX_NUMBERS ? maxNoOfBets : GlobalConstants.GENERATOR_MAX_NUMBERS;
	}

	public BetEvaluator getBetEvaluator() {
		return betEvaluator;
	}
}
