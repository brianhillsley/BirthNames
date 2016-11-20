import java.util.ArrayList;
import java.util.Arrays;


/**
 * 
 * @author Brian Hillsley
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
	 * @return a copy of the counts for each year in an array starting at year 1880 (index 0 is 1880)
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
	
	/**
	 * Gets the indexes of all local maxima.
	 * TODO: Does not include local maxima for endpoints currently. Add them.
	 * 
	 * @return the indices for all local maxima
	 */
	public ArrayList<Integer> localMaxima(){
		int prev = counts[1].count;
		int change = prev - counts[0].count;
		boolean increasing = (change > 0);
		ArrayList<Integer> locMaximaIndices = new ArrayList<Integer>();
		
		for(int i=2; i<NUM_YEARS_INCLUDED; i++){
			change = counts[i].count - prev;
			if(increasing && (change <= 0)){
				locMaximaIndices.add(i-1); // Add the local maximum
			}
			increasing = (change > 0);
			prev = counts[i].count;
		}
		return locMaximaIndices;
	}
	
	/**
	 * Returns the index for the absolute maximum count
	 * @return
	 */
	public int absoluteMaxIndex(){
		int maxIndex = 0;
		for(int i=1; i<NUM_YEARS_INCLUDED; i++){
			if(counts[i].count > counts[maxIndex].count){
				maxIndex = i;
			}
		}
		return maxIndex;
	}
	
	public ArrayList<Integer> localMinima(){
		int prev = counts[1].count;
		int change = prev - counts[0].count;
		boolean decreasing = (change < 0);
		ArrayList<Integer> locMinimaIndices = new ArrayList<Integer>();
		
		for(int i=2; i<NUM_YEARS_INCLUDED; i++){
			change = counts[i].count - prev;
			if(decreasing && (change > 0)){
				locMinimaIndices.add(i-1); // Add the local minimum
			}
			decreasing = (change < 0);
			prev = counts[i].count;
		}
		return locMinimaIndices;
	}
	
	public int getCount(int year){
		return counts[year-START_YEAR].count;
	}
	
}
