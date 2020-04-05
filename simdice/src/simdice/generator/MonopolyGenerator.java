package simdice.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import simdice.util.GlobalConstants;
import simdice.util.MonopolyConstants;

public class MonopolyGenerator extends AbstractGenerator {
	
	private List<Integer> theRing = new ArrayList<Integer>(MonopolyConstants.ELEMENTS);
	
	public MonopolyGenerator(int size, long seed) {
		
		this.nameOfGame = GlobalConstants.NAME_OF_GAME_MONOPOLY;
		
		// we need some extra reserve because the bonus fields (2x, 14x, 41x) consume, too
		size *= 1.3;
		
		this.seed = seed;
		Random rand = new Random(seed);
		IntStream ints = rand.ints(size <= GlobalConstants.GENERATOR_MAX_NUMBERS
			? size : GlobalConstants.GENERATOR_MAX_NUMBERS, 0, MonopolyConstants.ELEMENTS);  // 0 to 53

		populateRing();
		
		numbers = Arrays.stream(ints.toArray()).boxed().collect(Collectors.toCollection(ArrayList::new));
	}
	
	public MonopolyGenerator(int size) {
		this(size, new Random().nextLong());
	}
	
	@Override
	public Integer getNext() {

		Integer randomNumber = numbers.get(counter++);
		Integer ringNumber = theRing.get(randomNumber);
		
		return ringNumber;
	}

	private void populateRing() {
		
		// 1=1, 2=2, 5=5, 10=10, 11=Chance, 14=x2, 41=x4
		theRing.add(11);
		theRing.add(1);
		theRing.add(2);
		theRing.add(5);
		theRing.add(1);
		theRing.add(10);
		theRing.add(1);
		theRing.add(2);
		theRing.add(1);
		theRing.add(14);
		theRing.add(1);
		theRing.add(2);
		theRing.add(1);
		theRing.add(5);
		theRing.add(2);
		theRing.add(1);
		theRing.add(5);
		theRing.add(2);
		theRing.add(41);
		theRing.add(1);
		theRing.add(2);
		theRing.add(1);
		theRing.add(10);
		theRing.add(2);
		theRing.add(5);
		theRing.add(1);
		theRing.add(2);
		theRing.add(11);
		theRing.add(1);
		theRing.add(2);
		theRing.add(1);
		theRing.add(10);
		theRing.add(1);
		theRing.add(2);
		theRing.add(5);
		theRing.add(1);
		theRing.add(14);
		theRing.add(2);
		theRing.add(1);
		theRing.add(2);
		theRing.add(1);
		theRing.add(5);
		theRing.add(1);
		theRing.add(2);
		theRing.add(1);
		theRing.add(14);
		theRing.add(1);
		theRing.add(2);
		theRing.add(1);
		theRing.add(10);
		theRing.add(1);
		theRing.add(5);
		theRing.add(2);
		theRing.add(1);
		
		/* test only */
//		Map<Integer, Integer> testMap = new HashMap<Integer, Integer>();
//		testMap.put(1, 0);
//		testMap.put(2, 0);
//		testMap.put(5, 0);
//		testMap.put(10, 0);
//		testMap.put(11, 0);
//		testMap.put(14, 0);
//		testMap.put(41, 0);
//		for (Integer i : theRing) {
//			int counter = testMap.get(i);
//			testMap.put(i, ++counter);
//		}
//		System.out.println(testMap.toString());
	}
}
