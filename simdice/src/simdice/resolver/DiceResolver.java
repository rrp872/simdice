package simdice.resolver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import simdice.bankroll.Bankroll;
import simdice.bet.Bet;
import simdice.bet.BettingSlip;
import simdice.generator.DiceGenerator;
import simdice.util.History;

public class DiceResolver extends AbstractResolver {

	private Map<Integer, Double> rewardsOver = new HashMap<Integer, Double>(100);
	private Map<Integer, Double> rewardsUnder = new HashMap<Integer, Double>(100);
	private int betCounter = 0;
	
	
	public DiceResolver(Bankroll bankroll, DiceGenerator generator) {
		this.bankroll = bankroll;
		this.generator = generator;
		historyList = new ArrayList<History>();
		
		populateRewards();
		
		// Test only
//		System.out.println("+++ Seed +++: " + generator.getSeed());
	}
	
	@Override
	public void bet(BettingSlip bets) {
		
		bankroll.add(- bets.getBetAmounts());
		bankroll.getToken().mine(bets.getBetAmounts());
		
		Integer betResult = generator.getNext();
		bets.setBetResult(betResult);
		
		for (Bet aBet : bets.getBets()) {
		
			Integer prediction = aBet.getPrediction();
			
			boolean isWin = aBet.isRangeOver() ? betResult > prediction : betResult < prediction;
			aBet.setWon(isWin);
			
			Double multi = aBet.isRangeOver() ? rewardsOver.get(prediction) : rewardsUnder.get(prediction);
			aBet.setMultiplier(multi);
			
			if (isWin) {
				Double reward = aBet.getBetAmount() * multi;
				bankroll.add(reward);
				bets.addToBetReward(reward);
				bets.addToNoOfBetsWon();
			} else {
				bets.addToNoOfBetsLost();
			}
		}

		historyList.add(new History(++betCounter, bets, bankroll.getAmount(), bankroll.getToken().getTokenName(), bankroll.getToken().getAmount()));
	}
	
	public int getNoOfWinsPotential(int lastNoOfnumbers, boolean isStyleOver, int prediction) {
		
		List<Integer> numbers = getLast(lastNoOfnumbers);
		int counter = 0;
		
		for (Integer number : numbers) {
			if (isStyleOver ? number > prediction : number < prediction) {
				counter++;
			}
		}
		return counter;
	}

	private void populateRewards() {
		
		rewardsOver.put(4, 1.0368);		// 95%
		rewardsOver.put(5, 1.0478);		// 94%
		rewardsOver.put(6, 1.0591);		// 93%
		rewardsOver.put(7, 1.0706);		// 92%
		rewardsOver.put(8, 1.0824);		// 91%
		rewardsOver.put(9, 1.0944);		// 90%
		rewardsOver.put(10, 1.1067);	// 89%
		rewardsOver.put(11, 1.1193);
		rewardsOver.put(12, 1.1321);
		rewardsOver.put(13, 1.1453);	// 86%
		rewardsOver.put(14, 1.1588);
		rewardsOver.put(15, 1.1726);
		rewardsOver.put(16, 1.1867);
		rewardsOver.put(17, 1.2012);
		rewardsOver.put(18, 1.216);
		rewardsOver.put(19, 1.2312);
		rewardsOver.put(20, 1.2468);
		rewardsOver.put(21, 1.2628);
		rewardsOver.put(22, 1.2792);
		rewardsOver.put(23, 1.296);		// 76%
		rewardsOver.put(24, 1.3133);
		rewardsOver.put(25, 1.331);
		rewardsOver.put(26, 1.3493);
		rewardsOver.put(27, 1.368);
		rewardsOver.put(28, 1.3873);
		rewardsOver.put(29, 1.4071);
		rewardsOver.put(30, 1.4275);
		rewardsOver.put(31, 1.4485);
		rewardsOver.put(32, 1.4701);
		rewardsOver.put(33, 1.4924);	// 66%
		rewardsOver.put(34, 1.5153);
		rewardsOver.put(35, 1.539);
		rewardsOver.put(36, 1.5634);
		rewardsOver.put(37, 1.5887);
		rewardsOver.put(38, 1.6147);
		rewardsOver.put(39, 1.6416);
		rewardsOver.put(40, 1.6694);
		rewardsOver.put(41, 1.6982);
		rewardsOver.put(42, 1.728);
		rewardsOver.put(43, 1.7589);	// 56%
		rewardsOver.put(44, 1.7909);	// 55%
		rewardsOver.put(45, 1.824);		// 54%
		rewardsOver.put(46, 1.8584);	// 53%
		rewardsOver.put(47, 1.8942);	// 52%
		rewardsOver.put(48, 1.9313);	// 51%
		rewardsOver.put(49, 1.97);		// 50%
		rewardsOver.put(50, 2.0102);	// 49%
		rewardsOver.put(51, 2.052);		// 48%
		rewardsOver.put(52, 2.0957);	// 47%
		rewardsOver.put(53, 2.1413);	// 46%
		rewardsOver.put(54, 2.1888);
		rewardsOver.put(55, 2.2386);
		rewardsOver.put(56, 2.2906);
		rewardsOver.put(57, 2.3452);
		rewardsOver.put(58, 2.4024);
		rewardsOver.put(59, 2.4625);
		rewardsOver.put(60, 2.5256);
		rewardsOver.put(61, 2.5921);
		rewardsOver.put(62, 2.6621);
		rewardsOver.put(63, 2.7361);	// 36%
		rewardsOver.put(64, 2.8142);
		rewardsOver.put(65, 2.897);
		rewardsOver.put(66, 2.9848);
		rewardsOver.put(67, 3.0781);
		rewardsOver.put(68, 3.1774);
		rewardsOver.put(69, 3.2833);
		rewardsOver.put(70, 3.3965);
		rewardsOver.put(71, 3.5178);
		rewardsOver.put(72, 3.6481);
		rewardsOver.put(73, 3.7884);	// 26%
		rewardsOver.put(74, 3.94);		// 25%
		rewardsOver.put(75, 4.1041);
		rewardsOver.put(76, 4.2826);
		rewardsOver.put(77, 4.4772);
		rewardsOver.put(78, 4.6904);
		rewardsOver.put(79, 4.925);
		rewardsOver.put(80, 5.1842);
		rewardsOver.put(81, 5.4722);
		rewardsOver.put(82, 5.7941);
		rewardsOver.put(83, 6.1562);	// 16%
		rewardsOver.put(84, 6.5666);
		rewardsOver.put(85, 7.0357);
		rewardsOver.put(86, 7.5769);
		rewardsOver.put(87, 8.2083);
		rewardsOver.put(88, 8.9545);
		rewardsOver.put(89, 9.85);
		rewardsOver.put(90, 10.9444);
		rewardsOver.put(91, 12.3125);
		rewardsOver.put(92, 14.0714);
		rewardsOver.put(93, 16.4166);	// 6%
		rewardsOver.put(94, 19.7);		// 5%
		rewardsOver.put(95, 24.625);	// 4%
		rewardsOver.put(96, 32.8333);	// 3%
		rewardsOver.put(97, 49.25);		// 2%
		rewardsOver.put(98, 98.5);		// 1%
		
		for (int i = 4; i <= 98; i++) {
			rewardsUnder.put(99-i, rewardsOver.get(i));
		}

	}
}
