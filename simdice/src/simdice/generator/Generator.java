package simdice.generator;

import java.util.List;

/*
 * Manages random seeds and gives access to its numbers. A seed can be saved for reuse. 
 */
public interface Generator {

	public final static long SEED01 = Long.parseLong("1709387451697008768");
	public final static long SEED02 = Long.parseLong("4619428454563587890");
	public final static long SEED03 = Long.parseLong("-8158375727371028308");
	public final static long SEED04 = Long.parseLong("8729016960242810059");
	public final static long SEED05 = Long.parseLong("-1833778665855848094");
	public final static long SEED06 = Long.parseLong("-3255585233674806203");
	public final static long SEED07 = Long.parseLong("7396323612878244500");
	public final static long SEED08 = Long.parseLong("4382201960314466316");
	public final static long SEED09 = Long.parseLong("5193683367345293768");
	public final static long SEED10 = Long.parseLong("-2796329349594283419");
	public final static long SEED11 = Long.parseLong("696384901187277286");
	public final static long SEED12 = Long.parseLong("-172869211123745107");
	public final static long SEED13 = Long.parseLong("-6202406449332913814");
	public final static long SEED14 = Long.parseLong("-5576970751591034763");
	public final static long SEED15 = Long.parseLong("6176928579563627016");
	public final static long SEED16 = Long.parseLong("-7408049755099063240");
	public final static long SEED17 = Long.parseLong("8897998415100101149");
	public final static long SEED18 = Long.parseLong("8534463248578160222");
	public final static long SEED19 = Long.parseLong("-5493598340194411609");
	public final static long SEED20 = Long.parseLong("2422391142763057868");
	
	public Integer getNext();

	public long getSeed();
	
	public String getNameOfGame();
	
	public List<Integer> getLast(int lastNoOfnumbers);
}