import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;

import com.sun.javafx.runtime.SystemProperties;

/**
 * NameStore is responsible for reading the names in and placing them in to
 * the hash table, then providing easy access to elementary operations that can be performed on the dataset.
 * 
 * <Name, Sex> is a tuple that describes a baby of sex, Sex, and with name, Name. See NameSex class.
 * 
 * @author Brian Hillsley
 * 
 */
public class NameStore {
	// numNameSexes : the number of <name, sex> tuples
	// numPeople : the number of babies accounted for
	public static int numNameSexes, numPeople;
	
	// The following two constants are dictated by the SSN dataset.
	public static int EARLIEST_YEAR = 1880;
	public static int LATEST_YEAR = 2015;
	
	public static HashMap<Integer, NameEntry> namesTable; // Integer will be the hash code of the NameSex object
	private static String filenamePrefix = "bin/names/yob";
	static String fileExt = ".txt";
	
	/**
	 * Constructs a NameStore instance.
	 * The constructor will attempt to automatically read the input file.
	 */
	public NameStore(){
		namesTable = new HashMap<Integer, NameEntry>();
		numPeople = 0;
		numNameSexes = 0;
		try {
			processNames(EARLIEST_YEAR, LATEST_YEAR);
		} catch (Exception e){
			System.out.println("Error while attempting to process names.");
			e.printStackTrace();
		}
	}
	
	/**
	 * Prints the number of babies named parameter name during the specific year in the United States.
	 * @param name - the name to look up
	 * @param year - the year of which to look up
	 */
	public void printAllCountsSinceForEachSex(String name, int year){
		for (int startYear=year; year<=2015;year++){
		NameSex nsBoy = new NameSex(name, NameSex.Sex.MALE);
		NameSex nsGirl = new NameSex(name, NameSex.Sex.FEMALE);
		int boyCount = 0, girlCount = 0;
		
		if(namesTable.containsKey(getKey(nsBoy))){
			//System.out.println("key found for boy");
			boyCount = namesTable.get(getKey(nsBoy)).getCount(year);
		}
		if(namesTable.containsKey(getKey(nsGirl))){
			//System.out.println("key found for girl");
			girlCount = namesTable.get(getKey(nsGirl)).getCount(year);
		}
		
		System.out.println("# of boys named "+name+" in U.S. in year "+ year +": " + boyCount);
		System.out.println("# of girls named "+name+" in U.S. in year "+ year +": " + girlCount);
		}
	}
	
	public void printNameCount(String name, int year){
		NameSex nsBoy = new NameSex(name, NameSex.Sex.MALE);
		NameSex nsGirl = new NameSex(name, NameSex.Sex.FEMALE);
		// Lookup and print the total count returned
		int total = getNameSexCount(nsBoy, year) + getNameSexCount(nsGirl, year);
		System.out.println("# of babies named " + name + " ("+year+"): " + total);
	}
	
	/**
	 * Get an array of data points for a specific name-sex
	 * @param name - the name of baby
	 * @param sex - the sex of baby
	 * @return an array of data points
	 */
	public DataPoint[] getCountsFor(String name, NameSex.Sex sex){
		return nameEntry(new NameSex(name, sex)).getCounts();
	}
	
	public void printCountForEachSex(String name, int year){
		// Make the bou and girl object
		NameSex nsBoy = new NameSex(name, NameSex.Sex.MALE);
		NameSex nsGirl = new NameSex(name, NameSex.Sex.FEMALE);
		// Lookup and print the count returned
		System.out.println("# of boys named " + name + " ("+year+"): " + getNameSexCount(nsBoy, year));
		System.out.println("# of girls named " + name + " ("+year+"): " + getNameSexCount(nsGirl, year));
	}
	
	/**
	 * 
	 * Should only be called if the Constructor failed to call it itself
	 * 
	 * This function is responsible for reading in all the names for a range of years.
	 * The range is INCLUSIVE.
	 * @param startYear - the year the count starts from (startYear > 1879)
	 * @param endYear - the year the count ends at (endYear < 2016)
	 * @throws IOException
	 */
	public void processNames(int startYear, int endYear) throws IOException{
		for (int yr = startYear; yr <= endYear; yr++) {
			File f = new File(filenamePrefix + yr + fileExt);
			Scanner s = new Scanner(f);
			String line;

			while (s.hasNextLine()) {
				line = s.nextLine();
				String[] strs = line.split(",");
				NameSex.Sex sex = (strs[1].toUpperCase().equals("F")) ? NameSex.Sex.FEMALE
						: NameSex.Sex.MALE;
				String name = strs[0];
				int count = Integer.parseInt(strs[2]);
				numPeople += count;
				
				NameSex nameSex = new NameSex(name, sex);
				// Does this name already have an entry in the Hashtable? If it
				// does then we only need to add a new year and count to its
				// info.
				
				// If the names table has already seen this name, sex combination, then it will add info
				// If has not been seen, then create a new name Entry and add it to the names hashtable
				if(namesTable.containsKey(getKey(nameSex))){
					numNameSexes++;
					nameEntry(nameSex).addYearsInfo(yr, count);
				} else {
					
					NameEntry nameEntry = new NameEntry(name, sex, yr, count);
					namesTable.put(getKey(nameSex), nameEntry);
				}
			}
		}
	}
	
	/**
	 * Get key for hashtable
	 * @param ns - the name-sex object
	 * @return - an integer key
	 */
	private Integer getKey(NameSex ns){
		return ns.hashCode();
	}
	
	/**
	 * 
	 * @param ns - the NameSex object that describes the baby's name and sex
	 * @param year - the year for which to fetch the count
	 * @return number of babies of that name-sex in the year specified.
	 * 	Returns 0 in two cases: #1 the key is not found and #2 the actual count is 0
	 */
	private int getNameSexCount(NameSex ns, int year){
		if(namesTable.containsKey(getKey(ns))){
			return namesTable.get(getKey(ns)).getCount(year);
		} else {
			return 0;
		}
	}
	
	/**
	 * TODO: Should protect the Object before returning it
	 * Quick access to a name entry by name-sex
	 * @param nameSex
	 * @return
	 */
	public NameEntry nameEntry(String name, NameSex.Sex sex){
		return nameEntry(new NameSex(name, sex));
	}
	
	private NameEntry nameEntry(NameSex ns){
		return namesTable.get(getKey(ns));
	}
	
	/**
	 * Debugging method for pausing execution
	 */
	private static void p(){
		Scanner s = new Scanner(System.in);
		s.next();
	}
	
	/**
	 * Used for lazy-style debugging
	 */
	private static void h(){
		System.out.println("here");
	}
}
