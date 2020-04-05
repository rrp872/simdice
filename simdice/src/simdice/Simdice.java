package simdice;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simdice.bankroll.Bankroll;
import simdice.bet.BettingSlip;
import simdice.generator.DiceGenerator;
import simdice.generator.DreamCatcherGenerator;
import simdice.generator.MonopolyGenerator;
import simdice.generator.RingGenerator;
import simdice.resolver.DiceResolver;
import simdice.resolver.DreamCatcherResolver;
import simdice.resolver.MonopolyResolver;
import simdice.resolver.Resolver;
import simdice.resolver.RingResolver;
import simdice.strategy.Strategy;
import simdice.strategy.dice.DiceStrategyAdaptEdgeFlip;
import simdice.strategy.dice.DiceStrategyAdaptOver74StopLossFlip;
import simdice.strategy.dreamcatcher.DreamCatcherStrategyNonAdapt12_21;
import simdice.strategy.monopoly.MonopolyStrategyNonAdapt2_4x;
import simdice.strategy.ring.RingStrategyNonAdaptCoverAll;
import simdice.token.DiceToken;
import simdice.token.LiveToken;
import simdice.util.GlobalConstants;
import simdice.util.History;

public class Simdice {

	private String nameOfGame;
	private int simulationRuns;
	private int maxNoOfBets;
	
	/**
	 * For each run, save Strategy (Object[0) and Bank Roll (Object[1).
	 */
	private List<Object[]> runHistory;
	
	protected Map<String, Integer> abortReasons;
	
	public Simdice(String nameOfGame, int maxNoOfBets, int simulationRuns) {
		
		this.nameOfGame = nameOfGame;
		this.maxNoOfBets = maxNoOfBets;
		this.simulationRuns = simulationRuns;
		runHistory = new ArrayList<Object[]>(simulationRuns);
	}
	
	public void run() {
		
		switch (nameOfGame) {
		
		case GlobalConstants.NAME_OF_GAME_DICE:
			simulateDice();
			break;

		case GlobalConstants.NAME_OF_GAME_RING:
			simulateRing();
			break;
			
		case GlobalConstants.NAME_OF_GAME_DREAMCATCHER:
			simulateDreamCatcher();
			break;
			
		case GlobalConstants.NAME_OF_GAME_MONOPOLY:
			simulateMonopoly();
			break;
		}
	}
	
	public void simulateDice() {
		
		double bankrollStart = 500d;
		double tokenStart = 10d;
		
		for (int i = 0; i < simulationRuns; i++) {
			
			Bankroll bankroll = new Bankroll(bankrollStart, new DiceToken(nameOfGame, tokenStart));
			DiceGenerator generator = new DiceGenerator(maxNoOfBets);
			Resolver resolver = new DiceResolver(bankroll, generator);
			
//			Strategy strategy = new DiceStrategyAdaptOver92Flip(nameOfGame, bankroll, resolver.getHistory());
//			Strategy strategy = new DiceStrategyNonAdaptOver92(nameOfGame, bankroll, resolver.getHistory());
			
//			Strategy strategy = new DiceStrategyNonAdaptOver74(nameOfGame, bankroll, resolver.getHistory());
//			Strategy strategy = new DiceStrategyAdaptOver74Comp1(nameOfGame, bankroll, resolver.getHistory());
			
//			Strategy strategy = new DiceStrategyNonAdaptOver4(nameOfGame, bankroll, resolver.getHistory());
			
//			Strategy strategy = new DiceStrategyAdapt5049FlipMartingale(nameOfGame, bankroll, resolver.getHistory());
//			Strategy strategy = new DiceStrategyAdaptOver49Flip(nameOfGame, bankroll, resolver.getHistory());
			
//			Strategy strategy = new DiceStrategyAdaptOver74RiseBetWithProbability(nameOfGame, bankroll, resolver.getHistory());
			
//			Strategy strategy = new DiceStrategyAdaptOver74StopLoss(nameOfGame, bankroll, resolver.getHistory());
//			Strategy strategy = new DiceStrategyAdaptOver74StopLossFlip(nameOfGame, bankroll, resolver);
			
			Strategy strategy = new DiceStrategyAdaptEdgeFlip(nameOfGame, bankroll, resolver);
			
			strategy.setMaxNoOfBets(maxNoOfBets);
			
			while (true) {
				BettingSlip betSlip = strategy.nextBet();
				if (betSlip != null) {
					resolver.bet(betSlip);
				} else {
					break;
				}
			}
			
			runHistory.add(new Object[] {strategy, bankroll, strategy.getAbortReason()});
			
//			printBetDetails(resolver.getHistory());
//			printRunSummary(strategy, bankroll, generator.getSeed());
		}

		printSummary();
	}
	
	public void simulateRing() {
		
		for (int i = 0; i < simulationRuns; i++) {
			
			Bankroll bankroll = new Bankroll(2000d, new DiceToken(nameOfGame, 10d));
			RingGenerator generator = new RingGenerator(maxNoOfBets);
			Resolver resolver = new RingResolver(bankroll, generator);
			
			Strategy strategy = new RingStrategyNonAdaptCoverAll(nameOfGame, bankroll, resolver);
			strategy.setMaxNoOfBets(maxNoOfBets);
//			strategy.setMaxConsWins(11, 100);
			
			while (strategy.doContinue()) {
				BettingSlip betSlip = strategy.nextBet();
				if (betSlip != null) {
					resolver.bet(betSlip);
				}
			}
			
			runHistory.add(new Object[] {strategy, bankroll, strategy.getAbortReason()});
			
//			printBetDetails(resolver.getHistory());
//			printRunSummary(strategy, bankroll, generator.getSeed());
		}

		printSummary();
	}
	
	public void simulateDreamCatcher() {
		
		for (int i = 0; i < simulationRuns; i++) {

			Bankroll bankroll = new Bankroll(2.25d, new LiveToken(nameOfGame, 9951.456d));
			DreamCatcherGenerator generator = new DreamCatcherGenerator(maxNoOfBets);
			Resolver resolver = new DreamCatcherResolver(bankroll, generator);
			
			Strategy strategy = new DreamCatcherStrategyNonAdapt12_21(nameOfGame, bankroll, resolver);
			strategy.setMaxNoOfBets(maxNoOfBets);
			
			while (strategy.doContinue()) {
				BettingSlip betSlip = strategy.nextBet();
				if (betSlip != null) {
					resolver.bet(betSlip);
				}
			}
			
			runHistory.add(new Object[] {strategy, bankroll, strategy.getAbortReason()});
			
//			printBetDetails(resolver.getHistory());
//			printRunSummary(strategy, bankroll, generator.getSeed());
		}

		printSummary();
	}
	
	public void simulateMonopoly() {
		
		for (int i = 0; i < simulationRuns; i++) {

			Bankroll bankroll = new Bankroll(19.26d, new LiveToken(nameOfGame, 9951.456d));
			MonopolyGenerator generator = new MonopolyGenerator(maxNoOfBets);
			Resolver resolver = new MonopolyResolver(bankroll, generator);
			
//			Strategy strategy = new MonopolyStrategyNonAdapt12_2x4x(nameOfGame, bankroll, resolver);
			Strategy strategy = new MonopolyStrategyNonAdapt2_4x(nameOfGame, bankroll, resolver);
			strategy.setMaxNoOfBets(maxNoOfBets);
			
			while (strategy.doContinue()) {
				BettingSlip betSlip = strategy.nextBet();
				if (betSlip != null) {
					resolver.bet(betSlip);
				}
			}
			
			runHistory.add(new Object[] {strategy, bankroll, strategy.getAbortReason()});
			
//			printBetDetails(resolver.getHistory());
//			printRunSummary(strategy, bankroll, generator.getSeed());
		}

		printSummary();
	}
	
	private void printRunSummary(Strategy strategy, Bankroll bankroll, Long seed) {
		
		System.out.println("\n* Seed * " + seed);
		System.out.println("*** Strategy *** " + strategy);		
		System.out.println("*** Bank Roll *** " + bankroll);
		System.out.println("*** Token *** " + bankroll.getToken());
		System.out.println("*** Bets *** " + strategy.getBetEvaluator() + "\n");
	}
	
	private void printSummary() {
		
		populateAbortReasons();
		
		double defaultAvg = 0d;
		double defaultMax = 0d;
		double defaultMin = 999999d;
		
		// bank roll
		Double avgBankrollAmount = defaultAvg;
		Double avgBankrollWinAmount = defaultAvg;
		Double avgBankrollWinAmountPercent = defaultAvg;
		Double avgBankrollMiningCost = defaultAvg;
		Double maxBankrollAmount = defaultMax;
		Double maxBankrollWinAmount = defaultMax;
		Double maxBankrollWinAmountPercent = defaultMax;
		Double maxBankrollMiningCost = defaultMax;
		Double minBankrollAmount = defaultMin;
		Double minBankrollWinAmount = defaultMin;
		Double minBankrollWinAmountPercent = defaultMin;
		Double minBankrollMiningCost = defaultMin;
		
		// token
		Double avgTokenAmount = defaultAvg;
		Double avgTokenMinedAmount = defaultAvg;
		Double avgTokenMinedAmountPercent = defaultAvg;
		
		// bets
		Double avgBetTotal = defaultAvg;
		Double avgBetWon = defaultAvg;
		Double avgBetWonPercent = defaultAvg;
		Double avgBetLost = defaultAvg;
		Double maxBetTotal = defaultMax;
		Double maxBetWon = defaultMax;
		Double maxBetWonPercent = defaultMax;
		Double maxBetLost = defaultMax;
		Double minBetTotal = defaultMin;
		Double minBetWon = defaultMin;
		Double minBetWonPercent = defaultMin;
		Double minBetLost = defaultMin;
		
		int runs = runHistory.size();
		for (int i = 0; i < runs; i++) {
			
			Strategy strategy = (Strategy) runHistory.get(i)[0];
			Bankroll bankroll = (Bankroll) runHistory.get(i)[1];
			
			String abortReason = runHistory.get(i)[2] != null ? (String) runHistory.get(i)[2] : null;
			if (abortReason != null) {
		        int counter = abortReasons.get(abortReason);
		        abortReasons.put(abortReason, new Integer(++counter));
			}
			
			// Bank Roll
			avgBankrollAmount += bankroll.getAmount();
			maxBankrollAmount = Math.max(maxBankrollAmount, bankroll.getAmount());
			minBankrollAmount = Math.min(minBankrollAmount, bankroll.getAmount());
			
			avgBankrollWinAmount += bankroll.getBalance();
			maxBankrollWinAmount = Math.max(maxBankrollWinAmount, bankroll.getBalance());
			minBankrollWinAmount = Math.min(minBankrollWinAmount, bankroll.getBalance());
			
			avgBankrollWinAmountPercent += bankroll.getBalancePercent();
			maxBankrollWinAmountPercent = Math.max(maxBankrollWinAmountPercent, bankroll.getBalancePercent());
			minBankrollWinAmountPercent = Math.min(minBankrollWinAmountPercent, bankroll.getBalancePercent());
			
			avgBankrollMiningCost += bankroll.getMiningCostPerToken();
			maxBankrollMiningCost = Math.max(maxBankrollMiningCost, bankroll.getMiningCostPerToken());
			minBankrollMiningCost = Math.min(minBankrollMiningCost, bankroll.getMiningCostPerToken());
			
			// Token
			avgTokenAmount += bankroll.getToken().getAmount();
			avgTokenMinedAmount += bankroll.getToken().getMinedAmount();
			avgTokenMinedAmountPercent += bankroll.getToken().getMinedAmountPercent();
			
			// Bets
			avgBetTotal += strategy.getBetEvaluator().getNoOfBets();
			maxBetTotal = Math.max(maxBetTotal, strategy.getBetEvaluator().getNoOfBets());
			minBetTotal = Math.min(minBetTotal, strategy.getBetEvaluator().getNoOfBets());
			
			avgBetWon += strategy.getBetEvaluator().getNoOfWins();
			maxBetWon = Math.max(maxBetWon, strategy.getBetEvaluator().getNoOfWins());
			minBetWon = Math.min(minBetWon, strategy.getBetEvaluator().getNoOfWins());
			
			avgBetWonPercent += strategy.getBetEvaluator().getNoOfWinsPercent();
			maxBetWonPercent = Math.max(maxBetWonPercent, strategy.getBetEvaluator().getNoOfWinsPercent());
			minBetWonPercent = Math.min(minBetWonPercent, strategy.getBetEvaluator().getNoOfWinsPercent());
			
			avgBetLost += strategy.getBetEvaluator().getNoOfLosses();
			maxBetLost = Math.max(maxBetLost, strategy.getBetEvaluator().getNoOfLosses());
			minBetLost = Math.min(minBetLost, strategy.getBetEvaluator().getNoOfLosses());
		}
		
		avgBankrollAmount /= runs;
		String avgBankrollAmountFormatted = GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION6.format(avgBankrollAmount);
		
		avgBankrollWinAmount /= runs;
		String avgBankrollWinAmountFormatted = GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION6.format(avgBankrollWinAmount);
		
		avgBankrollWinAmountPercent /= runs;
		String avgBankrollWinAmountPercentFormatted = GlobalConstants.FORMAT_DOUBLE_PERCENT_FRACTION2.format(avgBankrollWinAmountPercent);
		
		avgBankrollMiningCost /= runs;
		String avgBankrollMiningCostFormatted = GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION4.format(avgBankrollMiningCost);
		
		avgTokenAmount /= runs;
		String avgTokenAmountFormatted = GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION4.format(avgTokenAmount);
		
		avgTokenMinedAmount /= runs;
		String avgTokenMinedAmountFormatted = GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION4.format(avgTokenMinedAmount);
		
		avgTokenMinedAmountPercent /= runs;
		String avgTokenMinedAmountPercentFormatted = GlobalConstants.FORMAT_DOUBLE_PERCENT_FRACTION2.format(avgTokenMinedAmountPercent);
		
		avgBetTotal /= runs;
		String avgBetTotalFormatted = GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION4.format(avgBetTotal);
		
		avgBetWon /= runs;
		String avgBetWonFormatted = GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION4.format(avgBetWon);
		
		avgBetWonPercent /= runs;
		String avgBetWonPercentFormatted = GlobalConstants.FORMAT_DOUBLE_PERCENT_FRACTION2.format(avgBetWonPercent);
		
		avgBetLost /= runs;
		String avgBetLostFormatted = GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION4.format(avgBetLost);
		
		System.out.println("\n##### Summary for all " + GlobalConstants.FORMAT_INTEGER.format(runs) + " runs. All values are averages for each single run unless noted otherwise. ##### ");
		
		System.out.println("*** Bank Roll *** amount: " + avgBankrollAmountFormatted + "; win amount: " + avgBankrollWinAmountFormatted
			+ "; WIN AMOUNT %: " + avgBankrollWinAmountPercentFormatted + "; mining cost per token: " + avgBankrollMiningCostFormatted);
		
		System.out.println("*** Token *** amount: " + avgTokenAmountFormatted + "; tokens mined: " + avgTokenMinedAmountFormatted + "; tokens mined %: " + avgTokenMinedAmountPercentFormatted);
		
		System.out.println("*** Bets *** total bets: " + avgBetTotalFormatted + "; bets won: " + avgBetWonFormatted + "; BETS WON %: " + avgBetWonPercentFormatted + "; bets lost: " + avgBetLostFormatted);
		
		System.out.println("*** Ranges for Bank Roll (min/max) *** "
			+ "amount: " + GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION6.format(minBankrollAmount) + "/" + GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION6.format(maxBankrollAmount)
			+ "; win amount: " + GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION6.format(minBankrollWinAmount) + "/" + GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION6.format(maxBankrollWinAmount)
			+ "; win amount %: " + GlobalConstants.FORMAT_DOUBLE_PERCENT_FRACTION2.format(minBankrollWinAmountPercent) + "/" + GlobalConstants.FORMAT_DOUBLE_PERCENT_FRACTION2.format(maxBankrollWinAmountPercent)
			+ "; mining cost per token: " + GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION4.format(minBankrollMiningCost) + "/" + GlobalConstants.FORMAT_DOUBLE_AMOUNT_FRACTION4.format(maxBankrollMiningCost));
		
		System.out.println("*** Ranges for Bets (min/max) *** "
			+ "total bets: " + GlobalConstants.FORMAT_INTEGER.format(minBetTotal) + "/" + GlobalConstants.FORMAT_INTEGER.format(maxBetTotal) 
			+ "; bets won: " + GlobalConstants.FORMAT_INTEGER.format(minBetWon) + "/" + GlobalConstants.FORMAT_INTEGER.format(maxBetWon) 
			+ "; bets won %: " + GlobalConstants.FORMAT_DOUBLE_PERCENT_FRACTION2.format(minBetWonPercent) + "/" + GlobalConstants.FORMAT_DOUBLE_PERCENT_FRACTION2.format(maxBetWonPercent) 
			+ "; bets lost: " + GlobalConstants.FORMAT_INTEGER.format(minBetLost) + "/" + GlobalConstants.FORMAT_INTEGER.format(maxBetLost));
		
		System.out.println("*** Bet run abort REASONS *** " + getAbortReasonStats());
	}
	
	private void printBetDetails(List<History> historyList) {
		for (int i = 0; i < historyList.size(); i++) {
			System.out.println(historyList.get(i).toString(nameOfGame));
		}
	}
	
	private String getAbortReasonStats() {
		
		// pattern: "Reason: 12 (20.0 %)"
		StringBuilder sb = new StringBuilder();
		for (String reason : abortReasons.keySet()) {
			Integer counter = abortReasons.get(reason);
			sb.append("\n").append(reason).append(": ").append(GlobalConstants.FORMAT_INTEGER.format(counter))
				.append(" (").append(GlobalConstants.FORMAT_DOUBLE_PERCENT_FRACTION2.format(counter.intValue()*100d / simulationRuns)).append(" %)");
		}
		return sb.toString();
	}
	
	private void populateAbortReasons() {
	    abortReasons = new HashMap<String, Integer>();
	    for (String reason : GlobalConstants.ABORT_REASONS) {
	            abortReasons.put(reason, 0);
	    }
	}
}
