package simdice.resolver;

import java.util.List;

import simdice.bankroll.Bankroll;
import simdice.generator.Generator;
import simdice.util.History;

public abstract class AbstractResolver implements Resolver {

	protected Bankroll bankroll;
	protected Generator generator;
	protected List<History> historyList;
	protected String nameOfGame;
	
	@Override
	public Bankroll getBankroll() {
		return bankroll;
	}

	@Override
	public Generator getGenerator() {
		return generator;
	}

	@Override
	public List<History> getHistory() {
		return historyList;
	}

	@Override
	public String getNameOfGame() {
		return nameOfGame;
	}

	@Override
	public List<Integer> getLast(int lastNoOfnumbers) {
		return generator.getLast(lastNoOfnumbers);
	}
}
