package simdice.bet;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import simdice.util.GlobalConstants;
import simdice.util.RingConstants;

public class BettingSlip {

	private String nameOfGame;
	private List<Bet> betSet = new ArrayList<Bet>(10);
	private Integer betResult;
	private Double betReward = 0d;
	private double allAmounts = 0d;
	private Set<Integer> allPredictions = new HashSet<Integer>(10);
	private Set<String> allPredictionsWithNames = new HashSet<String>(10);
	private Set<Double> allBetAmounts = new HashSet<Double>(10);
	private int noOfBetsWon = 0;
	private int noOfBetsLost = 0;

	public BettingSlip(String nameOfGame) {
		this.nameOfGame = nameOfGame;
	}
	
	public Set<Double> getAllBetAmounts() {
		return allBetAmounts;
	}
	
	public Set<String> getAllPredictionsWithNames() {
		return allPredictionsWithNames;
	}

	public Set<Integer> getAllPredictions() {
		return allPredictions;
	}

	public double getBetAmounts() {
		return allAmounts;
	}
	
	public double getBetReward() {
		return betReward.doubleValue();
	}

	public void addToBetReward(double betReward) {
		this.betReward += betReward;
	}

	public Integer getBetResult() {
		return betResult;
	}

	public void setBetResult(Integer betResult) {
		this.betResult = betResult;
	}
	
	public void add(Bet bet) {
		betSet.add(bet);
		Integer prediction = bet.getPrediction();
		allPredictions.add(prediction);
		allBetAmounts.add(bet.getBetAmount());
		allAmounts += bet.getBetAmount();
		
		if (GlobalConstants.NAME_OF_GAME_RING.equals(nameOfGame)) {
			String aPredictionsWithName = RingConstants.getNameForElementNumber(prediction);
			allPredictionsWithNames.add(aPredictionsWithName);
			
		} else {
			allPredictionsWithNames.add(prediction.toString());
		}
	}
	
	public List<Bet> getBets() {
		return betSet;
	}
	
	public int getNoOfBets() {
		return betSet.size();
	}
	
	public int getNoOfBetsWon() {
		return noOfBetsWon;
	}
	
	public void addToNoOfBetsWon() {
		noOfBetsWon++;
	}
	
	public boolean isAnyBetWon() {
		return noOfBetsWon > 0;
	}
	
	public Boolean getFirstBetStyleIsRangeOver() {
		return betSet.get(0).isRangeOver();
	}
	
	public int getNoOfBetsLost() {
		return noOfBetsLost;
	}
	
	public void addToNoOfBetsLost() {
		noOfBetsLost++;
	}
}
