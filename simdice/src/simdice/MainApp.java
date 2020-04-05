package simdice;

import simdice.util.GlobalConstants;

public class MainApp {

	public static void main(String[] args) {
		
//		int maxNoOfBets = 200;
//		int simulationRuns = 4;
		
		int maxNoOfBets = 500;
		int simulationRuns = 1500;
		
		Simdice simulator = new Simdice(GlobalConstants.NAME_OF_GAME_DICE, maxNoOfBets, simulationRuns);
		simulator.run();
	}
	
}
