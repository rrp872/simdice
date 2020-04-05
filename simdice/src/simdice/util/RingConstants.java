package simdice.util;

public class RingConstants {

	public static final int ELEMENTS = 54;
	public static final int ELEMENT_TYPES = 4;
	
	public static final String ELEMENT_TYPE_2_NAME = "Gray";
	public static final String ELEMENT_TYPE_3_NAME = "Red";
	public static final String ELEMENT_TYPE_5_NAME = "Blue";
	public static final String ELEMENT_TYPE_50_NAME = "Gold";

	public static final double BET_MIN = 10d;
	
	public static final double BET_MAX_2  = 60000d;
	public static final double BET_MAX_3  = 40000d;
	public static final double BET_MAX_5  = 24000d;
	public static final double BET_MAX_50 =  2400d;
	
	
	public static String getNameForElementNumber(int elementNumber) {
		
		switch (elementNumber) {
		case 2:
			return RingConstants.ELEMENT_TYPE_2_NAME;
		case 3:
			return RingConstants.ELEMENT_TYPE_3_NAME;
		case 5:
			return RingConstants.ELEMENT_TYPE_5_NAME;
		case 50:
			return RingConstants.ELEMENT_TYPE_50_NAME;
		default:
			return "N/A";
		}
	}
	
}
