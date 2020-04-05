package simdice.generator;

import java.util.List;

public abstract class AbstractGenerator implements Generator {

	protected List<Integer> numbers;
	protected String nameOfGame;
	protected int counter = 0;
	protected long seed = 0;

	public AbstractGenerator() {
		super();
	}

	@Override
	public Integer getNext() {
		return numbers.get(counter++);
	}

	@Override
	public long getSeed() {
		return seed;
	}

	@Override
	public String getNameOfGame() {
		return nameOfGame;
	}

	@Override
	public List<Integer> getLast(int lastNoOfnumbers) {
		int firstIndex = Math.max(0, counter - lastNoOfnumbers);
		return numbers.subList(firstIndex, counter);
	}
}