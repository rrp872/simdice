package simdice.generator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import simdice.util.DiceConstants;
import simdice.util.GlobalConstants;

public class DiceGenerator extends AbstractGenerator {
	
	public DiceGenerator(int size, long seed) {
		
		this.nameOfGame = GlobalConstants.NAME_OF_GAME_DICE;
		
		this.seed = seed;
		
		Random rand = new Random(seed);
		IntStream ints = rand.ints(size <= GlobalConstants.GENERATOR_MAX_NUMBERS ? size : GlobalConstants.GENERATOR_MAX_NUMBERS, DiceConstants.OUTCOME_MIN, DiceConstants.OUTCOME_MAX + 1);
		numbers = Arrays.stream(ints.toArray()).boxed().collect(Collectors.toCollection(ArrayList::new));
	}
	
	public DiceGenerator(int size) {
		this(size, new Random().nextLong());
	}
}
