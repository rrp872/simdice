package simdice.util;

import java.util.ArrayList;
import java.util.List;

import simdice.strategy.Strategy;

public class BetEvaluator {

	private List<History> historyList;
	
	public BetEvaluator(List<History> historyList, Strategy strategy) {
		this.historyList = historyList;
	}
	
	public boolean isLastBetWon() {
		return getNoOfWinsInLastBets(1, null) == 1;
	}
	
	public boolean isLastBetLost() {
		return getNoOfLossesInLastBets(1, null) == 1;
	}
	
	public int getNoOfWinsInLastBets(int inLastBets, Boolean considerOnlyIsStyleOver) {
		return getNoOfWinsOrLossesInLastBets(inLastBets, true, considerOnlyIsStyleOver);
	}
	
	public int getNoOfWins() {
		return getNoOfWinsInLastBets(historyList.size(), null);
	}
	
	public double getWonPercentage(int inLastBets) {
		int won = getNoOfWinsInLastBets(inLastBets, null);
		return (won * 100d) / inLastBets;
	}
	
	public double getPercentageWonToExpected(int inLastBets, double expectedProbability) {
		return (getWonPercentage(inLastBets) / (expectedProbability * 100)) - 1;
	}
	
	public double getPercentageWonToExpectedPotential(int inLastBets, int lastBetsWonPotential, double expectedProbability) {
		return ((lastBetsWonPotential * 100d / inLastBets) / (expectedProbability * 100)) - 1;
	}
	
	public double getNoOfWinsPercent() {
		return getNoOfWins() * 100d / getNoOfBets();
	}
	
	public int getNoOfLossesInLastBets(int inLastBets, Boolean considerOnlyIsStyleOver) {
		return getNoOfWinsOrLossesInLastBets(inLastBets, false, considerOnlyIsStyleOver);
	}
	
	public int getNoOfLosses() {
		return getNoOfLossesInLastBets(historyList.size(), null);
	}
	
	public int getNoOfBets() {
		return historyList.size();
	}
	
	public int getNoOfBetsSinceLastWin() {
		int noOfBets = historyList.size();
		if (noOfBets == 0) {
			return 0;
		}
		
		for (int i = noOfBets - 1; i >= 0; i--) {
			if (historyList.get(i).isWin()) {
				return noOfBets - i - 1;
			}
		}
		return 0;
	}
	
	public List<Integer> getLastResults(int noOfLastResults) {
	
		int noOfBets = historyList.size();
		if (noOfBets < noOfLastResults) {
			return new ArrayList<Integer>(0);
		}
		
		List<Integer> lastResults = new ArrayList<Integer>(noOfLastResults);
		
		for (int i = noOfBets - 1; i >= noOfBets - noOfLastResults; i--) {
			lastResults.add(historyList.get(i).getDraw());
		}
		return lastResults;
	}
	
	public int getNoOfBetsOverX(boolean over, int noOfLastResults, int x) {
	
		List<Integer> lastResults = getLastResults(noOfLastResults);
		int counter = 0;
		
		for (Integer result : lastResults) {
			if (over) {
				counter += result > x ? 1 : 0;
				
			} else {
				counter += result < x ? 1 : 0;
			}
		}
		
		return counter;
	}
	
	/**
	 * Gets number of wins or losses within the past 'inLastBets' bets (betting slips).
	 * 
	 * @param inLastBets
	 * @param wins set true for wins, false for losses
	 * @param considerOnlyIsStyleOver if != null, then for counting consider only bets with the given style (over or under). 
	 * @return number of wins or losses within the past 'inLastBets' bets (betting slips).
	 */
	private int getNoOfWinsOrLossesInLastBets(int inLastBets, boolean wins, Boolean considerOnlyIsStyleOver) {
		if (historyList.size() < inLastBets) {
			return 0;
		}
		
		int histSize = historyList.size();
		int counter = 0;
		for (int i = histSize - inLastBets; i < histSize; i++) {
			
			History history = historyList.get(i);
			
			if (wins && history.isWin()) {
				if (considerOnlyIsStyleOver != null && history.getFirstBetStyleIsRangeOver().equals(considerOnlyIsStyleOver)) {
					counter++;
				} else if (considerOnlyIsStyleOver == null) {
					counter++;
				}
			}
			
			if (!wins && !history.isWin()) {
				if (considerOnlyIsStyleOver != null && history.getFirstBetStyleIsRangeOver().equals(considerOnlyIsStyleOver)) {
					counter++;
				} else if (considerOnlyIsStyleOver == null) {
					counter++;
				}
			}
		}
		return counter;
	}

	@Override
	public String toString() {
		return (new StringBuilder()
		  .append("total bets: ").append(GlobalConstants.FORMAT_INTEGER.format(getNoOfBets()))
		  .append("; bets won: ").append(GlobalConstants.FORMAT_INTEGER.format(getNoOfWins()))
		  .append("; bets won %: ").append(GlobalConstants.FORMAT_DOUBLE_PERCENT_FRACTION2.format(getNoOfWinsPercent()))
		  .append("; bets lost: ").append(GlobalConstants.FORMAT_INTEGER.format(getNoOfLosses()))
		  ).toString();
	}
}
