package simdice.resolver;

import java.util.List;

import simdice.bankroll.Bankroll;
import simdice.bet.BettingSlip;
import simdice.generator.Generator;
import simdice.util.History;

/**
 * Determines the consequence of the bet outcome and applies it to bank roll and token balance. 
 */
public interface Resolver {

	public void bet(BettingSlip bets);
	
	public Bankroll getBankroll();
	
	public Generator getGenerator();
	
	public List<History> getHistory();
	
	public String getNameOfGame();
	
	public List<Integer> getLast(int lastNoOfnumbers);
}
