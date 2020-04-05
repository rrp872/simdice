package simdice.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import simdice.util.GlobalConstants;
import simdice.util.MonopolyConstants;

public class DreamCatcherGenerator extends AbstractGenerator {
	
	private List<Integer> theRing = new ArrayList<Integer>(MonopolyConstants.ELEMENTS);
	
	public DreamCatcherGenerator(int size, long seed) {
		
		this.nameOfGame = GlobalConstants.NAME_OF_GAME_DREAMCATCHER;
		
		// we need some extra reserve because the two bonus fields (2x, 7x) consume, too
		size *= 1.3;
		
		this.seed = seed;
		Random rand = new Random(seed);
		IntStream ints = rand.ints(size <= GlobalConstants.GENERATOR_MAX_NUMBERS
			? size : GlobalConstants.GENERATOR_MAX_NUMBERS, 0, MonopolyConstants.ELEMENTS);  // 0 to 53

		populateRing();
		
		numbers = Arrays.stream(ints.toArray()).boxed().collect(Collectors.toCollection(ArrayList::new));
	}
	
	public DreamCatcherGenerator(int size) {
		this(size, new Random().nextLong());
	}
	
	@Override
	public Integer getNext() {

		Integer randomNumber = numbers.get(counter++);
		Integer ringNumber = theRing.get(randomNumber);
		
		return ringNumber;
	}

	private void populateRing() {
		
		// 1=1, 2=2, 5=5, 10=10, 20=20, 40=40, 2x=200, 7x=700
		theRing.add(200);
		theRing.add(2);
		theRing.add(1);
		theRing.add(5);
		theRing.add(1);
		theRing.add(10);
		theRing.add(2);
		theRing.add(1);
		theRing.add(5);
		theRing.add(1);
		theRing.add(2);
		theRing.add(1);
		theRing.add(2);
		theRing.add(1);
		theRing.add(20);
		theRing.add(1);
		theRing.add(2);
		theRing.add(1);
		theRing.add(10);
		theRing.add(2);
		theRing.add(1);
		theRing.add(5);
		theRing.add(2);
		theRing.add(1);
		theRing.add(5);
		theRing.add(1);
		theRing.add(2);
		theRing.add(700);
		theRing.add(1);
		theRing.add(2);
		theRing.add(1);
		theRing.add(10);
		theRing.add(1);
		theRing.add(2);
		theRing.add(1);
		theRing.add(20);
		theRing.add(1);
		theRing.add(2);
		theRing.add(5);
		theRing.add(1);
		theRing.add(2);
		theRing.add(1);
		theRing.add(5);
		theRing.add(1);
		theRing.add(2);
		theRing.add(10);
		theRing.add(1);
		theRing.add(2);
		theRing.add(1);
		theRing.add(2);
		theRing.add(40);
		theRing.add(1);
		theRing.add(5);
		theRing.add(1);
		
		/* test only */
//		Map<Integer, Integer> testMap = new HashMap<Integer, Integer>();
//		testMap.put(1, 0);
//		testMap.put(2, 0);
//		testMap.put(5, 0);
//		testMap.put(10, 0);
//		testMap.put(20, 0);
//		testMap.put(40, 0);
//		testMap.put(200, 0);
//		testMap.put(700, 0);
//		for (Integer i : theRing) {
//			int counter = testMap.get(i);
//			testMap.put(i, ++counter);
//		}
//		System.out.println(testMap.toString());
	}
}
