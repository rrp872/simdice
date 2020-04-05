package simdice.util;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Set;

public class GlobalConstants {
	
	public static final String NAME_OF_GAME_DICE = "DiceGame";
	public static final String NAME_OF_GAME_RING = "RingGame";
	public static final String NAME_OF_GAME_DREAMCATCHER = "DreamCatcherGame";
	public static final String NAME_OF_GAME_MONOPOLY = "MonopolyGame";
	
	public static final int GENERATOR_MAX_NUMBERS = 500000;
	
	public static final double DEFAULT_BANKROLL_AMOUNT = 500d;
	public static final double DEFAULT_TOKEN_AMOUNT = 0d;
	
	public static final DecimalFormat FORMAT_DOUBLE_AMOUNT_FRACTION6 = new DecimalFormat("#,##0.000000");
	public static final DecimalFormat FORMAT_DOUBLE_AMOUNT_FRACTION4 = new DecimalFormat("#,##0.0000");
	public static final DecimalFormat FORMAT_DOUBLE_PERCENT_FRACTION2 = new DecimalFormat("#,##0.00");
	public static final DecimalFormat FORMAT_INTEGER = new DecimalFormat("#,###");
	
	public static final String ABORT_REASON_BANKROLL_EMPTY = "Bank roll is empty";
	public static final String ABORT_REASON_BANKROLL_TOO_LOW_NEXT_BET = "Bank roll is too low for the next bet";
	public static final String ABORT_REASON_BETS_MAX_NO_REACHED = "Max. number of bets is reached";
	public static final String ABORT_REASON_BALANCE_LOSS_MAX_REACHED = "Max. loss is reached";
	public static final String ABORT_REASON_BALANCE_LOSS_MAX_IN_PAST_BETS_REACHED = "Max. losses in past bets are reached";
	public static final String ABORT_REASON_BALANCE_WIN_MAX_REACHED = "Max. win is reached";
	public static final String ABORT_REASON_BALANCE_WIN_MAX_IN_PAST_BETS_REACHED = "Max. wins in past bets are reached";
	public static final String ABORT_REASON_BALANCE_WIN_MAX_IN_PAST_BETS_REACHED_AND_BALANCE_WIN = "Max. wins in past bets are reached and balance win";
	public static final String ABORT_REASON_PERCENTAGE_WON_TO_EXPECTED_IS_BELOW_THRESHOLD = "PercentageWonToExpected ratio is below threshold";
	
	public static final Set<String> ABORT_REASONS = new HashSet<String>(){

		private static final long serialVersionUID = -6974754790709051114L;
	{
		add(ABORT_REASON_BANKROLL_EMPTY);
		add(ABORT_REASON_BANKROLL_TOO_LOW_NEXT_BET);
		add(ABORT_REASON_BETS_MAX_NO_REACHED);
		add(ABORT_REASON_BALANCE_LOSS_MAX_REACHED);
		add(ABORT_REASON_BALANCE_LOSS_MAX_IN_PAST_BETS_REACHED);
		add(ABORT_REASON_BALANCE_WIN_MAX_REACHED);
		add(ABORT_REASON_BALANCE_WIN_MAX_IN_PAST_BETS_REACHED);
		add(ABORT_REASON_BALANCE_WIN_MAX_IN_PAST_BETS_REACHED_AND_BALANCE_WIN);
		add(ABORT_REASON_PERCENTAGE_WON_TO_EXPECTED_IS_BELOW_THRESHOLD);
	}};
	
}
