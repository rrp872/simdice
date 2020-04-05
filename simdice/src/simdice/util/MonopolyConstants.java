package simdice.util;

public class MonopolyConstants {

	public static final int ELEMENTS = 54;
	public static final int ELEMENT_TYPES = 7;
	
	// how many TRX give 1.0 USD?
	public static final double USD_CONVERSION_RATE = 74.4353d;
	
	public static final String ELEMENT_TYPE_1_NAME   = "1";
	public static final String ELEMENT_TYPE_2_NAME   = "2";
	public static final String ELEMENT_TYPE_5_NAME   = "5";
	public static final String ELEMENT_TYPE_10_NAME  = "10";
	public static final String ELEMENT_TYPE_11_NAME  = "Chance";
	public static final String ELEMENT_TYPE_x2_NAME  = "2 Rolls";
	public static final String ELEMENT_TYPE_x4_NAME  = "4 Rolls";
	
	public static final double BET_MIN = 0.1d;
	
	public static String getNameForElementNumber(int elementNumber) {
		
		switch (elementNumber) {
		case 1:
			return MonopolyConstants.ELEMENT_TYPE_1_NAME;
		case 2:
			return MonopolyConstants.ELEMENT_TYPE_2_NAME;
		case 5:
			return MonopolyConstants.ELEMENT_TYPE_5_NAME;
		case 10:
			return MonopolyConstants.ELEMENT_TYPE_10_NAME;
		case 14:
			return MonopolyConstants.ELEMENT_TYPE_x2_NAME;
		case 41:
			return MonopolyConstants.ELEMENT_TYPE_x4_NAME;
		case 11:
			return MonopolyConstants.ELEMENT_TYPE_11_NAME;
		default:
			return "N/A";
		}
	}
	
}
