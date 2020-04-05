package simdice.util;

public class DreamCatcherConstants {

	public static final int ELEMENTS = 54;
	public static final int ELEMENT_TYPES = 8;
	
	// how many TRX give 1.0 mBTC?
	public static final double MBTC_CONVERSION_RATE = 444.545213333d;
	
	public static final String ELEMENT_TYPE_1_NAME   = "1";
	public static final String ELEMENT_TYPE_2_NAME   = "2";
	public static final String ELEMENT_TYPE_5_NAME   = "5";
	public static final String ELEMENT_TYPE_10_NAME  = "10";
	public static final String ELEMENT_TYPE_20_NAME  = "20";
	public static final String ELEMENT_TYPE_40_NAME  = "40";
	public static final String ELEMENT_TYPE_200_NAME = "2x";
	public static final String ELEMENT_TYPE_700_NAME = "7x";

	public static final double BET_MIN = 0.01d;
	
	public static String getNameForElementNumber(int elementNumber) {
		
		switch (elementNumber) {
		case 1:
			return DreamCatcherConstants.ELEMENT_TYPE_1_NAME;
		case 2:
			return DreamCatcherConstants.ELEMENT_TYPE_2_NAME;
		case 5:
			return DreamCatcherConstants.ELEMENT_TYPE_5_NAME;
		case 10:
			return DreamCatcherConstants.ELEMENT_TYPE_10_NAME;
		case 20:
			return DreamCatcherConstants.ELEMENT_TYPE_20_NAME;
		case 40:
			return DreamCatcherConstants.ELEMENT_TYPE_40_NAME;
		case 200:
			return DreamCatcherConstants.ELEMENT_TYPE_200_NAME;
		case 700:
			return DreamCatcherConstants.ELEMENT_TYPE_700_NAME;
		default:
			return "N/A";
		}
	}
	
}
