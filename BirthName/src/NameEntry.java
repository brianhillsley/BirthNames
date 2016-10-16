import java.util.Arrays;


/**
 * 
 * @author bhills
 *
 */
public class NameEntry {
	
	public static int NUM_YEARS_INCLUDED = 136;
	public static int START_YEAR = 1880;
	private NameSex nameSex;
	
	
	// counts holds all of the years of recorded data starting from 1880. so index 0 is 1880, index 1 is 1881, ...
	private int[] counts;

	public NameEntry(String name, NameSex.Sex sex, int year, int count){ // this name didn't exist on hashtable, so it was created
		this.nameSex = new NameSex(name, sex);
		counts = new int[NUM_YEARS_INCLUDED];
		counts[year-START_YEAR] = count;
	}
	
	/**
	 * Adds a specific entry (year, count) to the internal array of counts.
	 * Years should be between 1880 and 2015 (so no 2016 yet).
	 * @param year
	 * @param count
	 */
	public void addYearsInfo(int year, int count){
		
		counts[(year-START_YEAR)] = count;
		
		System.out.println("----");
		System.out.println(nameSex);
		int startYear = 1880;
		int endYear = 2015;
//		for(int i = 0; i <= (endYear-startYear)+1; i++)
//			System.out.println(i+" : "+counts[i]);
//		System.out.println("----");
	}
	public int getCount(int year){
		return counts[year-START_YEAR];
	}
	
}
