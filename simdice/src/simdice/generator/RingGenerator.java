package simdice.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import simdice.util.GlobalConstants;
import simdice.util.RingConstants;

public class RingGenerator extends AbstractGenerator {
	
	private List<Integer> theRing = new ArrayList<Integer>(RingConstants.ELEMENTS);
	
	public RingGenerator(int size, long seed) {
		
		this.nameOfGame = GlobalConstants.NAME_OF_GAME_RING;
		
		this.seed = seed;
		
		Random rand = new Random(seed);
		IntStream ints = rand.ints(size <= GlobalConstants.GENERATOR_MAX_NUMBERS
			? size : GlobalConstants.GENERATOR_MAX_NUMBERS, 0, RingConstants.ELEMENTS);  // 0 to 53

		populateRing();
		
		numbers = Arrays.stream(ints.toArray()).boxed().collect(Collectors.toCollection(ArrayList::new));
	}
	
	public RingGenerator(int size) {
		this(size, new Random().nextLong());
	}
	
	@Override
	public Integer getNext() {

		Integer randomNumber = numbers.get(counter++);
		Integer ringNumber = theRing.get(randomNumber);
		
		return ringNumber;
	}

	private void populateRing() {
		
		// gray=2, red=3, blue=5, gold=50
		theRing.add(50);
		theRing.add(5);
		theRing.add(2);
		theRing.add(3);
		theRing.add(2);
		theRing.add(3);
		theRing.add(2);
		theRing.add(3);
		theRing.add(2);
		theRing.add(5);
		theRing.add(2);
		theRing.add(5);
		theRing.add(2);
		theRing.add(3);
		theRing.add(2);
		theRing.add(3);
		theRing.add(2);
		theRing.add(3);
		theRing.add(2);
		theRing.add(5);
		theRing.add(2);
		theRing.add(5);
		theRing.add(2);
		theRing.add(3);
		theRing.add(2);
		theRing.add(3);
		theRing.add(2);
		theRing.add(3);
		theRing.add(2);
		theRing.add(3);
		theRing.add(2);
		theRing.add(3);
		theRing.add(2);
		theRing.add(5);
		theRing.add(2);
		theRing.add(5);
		theRing.add(2);
		theRing.add(3);
		theRing.add(2);
		theRing.add(3);
		theRing.add(2);
		theRing.add(3);
		theRing.add(2);
		theRing.add(5);
		theRing.add(2);
		theRing.add(5);
		theRing.add(2);
		theRing.add(3);
		theRing.add(2);
		theRing.add(3);
		theRing.add(2);
		theRing.add(3);
		theRing.add(2);
		theRing.add(5);
	}
}
