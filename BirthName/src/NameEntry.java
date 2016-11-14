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
	private DataPoint[] counts;

	public NameEntry(String name, NameSex.Sex sex, int year, int count){ // this name didn't exist on hashtable, so it was created
		this.nameSex = new NameSex(name, sex);
		counts = new DataPoint[NUM_YEARS_INCLUDED];
		for(int i=0; i<counts.length; i++){
			counts[i] = new DataPoint(0,0);
		}
		
		counts[year-START_YEAR] = new DataPoint(year, count);
	}
	
	/**
	 * Adds a specific entry (year, count) to the internal array of counts for this specific NameEntry.
	 * Years should be between 1880 and 2015 (so no 2016 yet).
	 * @param year the year for which the count corresponds
	 * @param count the number of babies with that name from the specific year
	 */
	public void addYearsInfo(int year, int count){
		counts[(year-START_YEAR)] = new DataPoint(year, count);
	}
	
	/**
	 * Returns a copy of all the counts for each year
	 * @return
	 */
	public DataPoint[] getCounts(){
		return counts.clone();
	}
		public String getName(){
		return this.nameSex.name;
	}
	
	public NameSex.Sex getSex(){
		return this.nameSex.sex;
	}
	
	public int getCount(int year){
		return counts[year-START_YEAR].count;
	}
	
}
