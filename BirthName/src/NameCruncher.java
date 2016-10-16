import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Scanner;

import com.sun.javafx.runtime.SystemProperties;

/**
 * NameCruncher is responsible for reading the names in and placing them in to
 * the hash table
 * 
 * @author bhills
 * 
 */
public class NameCruncher {
	static int contained = 0;
	public static HashMap<Integer, NameEntry> namesTable; // Integer will be the hash code of the NameSex object
	public static String filenamePrefix = "bin/names/yob";
	static String fileExt = ".txt";
	
	public NameCruncher(){
		namesTable = new HashMap<Integer, NameEntry>();
	}
	public static void main(String[] args) {
		
		int startYear = 1880;
		int endYear = 2015;
		NameCruncher n = new NameCruncher();
		try {
			n.processNames(startYear, endYear);
		} catch (Exception e){
			System.out.println("Exception caught");
			e.printStackTrace();
		}
		System.out.println("contained: " + contained);
		
		Scanner input = new Scanner(System.in);
		System.out.println("Enter a name:");
		String inName = input.nextLine();
		System.out.println("Enter a year:");
		int inYear = Integer.parseInt(input.nextLine());
		n.printCountForEachSex(inName, inYear);
		System.out.println("------------------------");
		n.printAllCountsSinceForEachSex(inName, inYear);
		
	}

	public void printAllCountsSinceForEachSex(String name, int year){
		for (int startYear=year; year<=2015;year++){
		NameSex nsBoy = new NameSex(name, NameSex.Sex.MALE);
		NameSex nsGirl = new NameSex(name, NameSex.Sex.FEMALE);
		int boyCount = 0, girlCount = 0;
		
		if(namesTable.containsKey(getKey(nsBoy))){
			System.out.println("key found for boy");
			boyCount = namesTable.get(getKey(nsBoy)).getCount(year);
		}
		if(namesTable.containsKey(getKey(nsGirl))){
			System.out.println("key found for girl");
			girlCount = namesTable.get(getKey(nsGirl)).getCount(year);
		}
		
		System.out.println("# of boys named "+name+" in U.S. in year "+ year +": " + boyCount);
		System.out.println("# of girls named "+name+" in U.S. in year "+ year +": " + girlCount);
		}
	}
	public void printCountForEachSex(String name, int year){
		NameSex nsBoy = new NameSex(name, NameSex.Sex.MALE);
		NameSex nsGirl = new NameSex(name, NameSex.Sex.FEMALE);
		int boyCount = 0, girlCount = 0;
		
		if(namesTable.containsKey(getKey(nsBoy))){
			System.out.println("key found for boy");
			boyCount = namesTable.get(getKey(nsBoy)).getCount(year);
		}
		if(namesTable.containsKey(getKey(nsGirl))){
			System.out.println("key found for girl");
			girlCount = namesTable.get(getKey(nsGirl)).getCount(year);
		}
		
		System.out.println("# of boys named "+name+" in U.S. in year "+ year +": " + boyCount);
		System.out.println("# of girls named "+name+" in U.S. in year "+ year +": " + girlCount);
	}
	
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
				
				NameSex nameSex = new NameSex(name, sex);
				// Does this name already have an entry in the Hashtable? If it
				// does then we only need to add a new year and count to its
				// info.
				// If the names table has already seen this name, sex combination, then it will add info
				// If has not been seen, then create a new name Entry and add it to the names hashtable
				if(namesTable.containsKey(getKey(nameSex))){
					contained++;
					namesTable.get(getKey(nameSex)).addYearsInfo(yr, count);
				} else {
					
					NameEntry nameEntry = new NameEntry(name, sex, yr, count);
					namesTable.put(getKey(nameSex), nameEntry);
				}
			}
		}
	}
	
	public Integer getKey(NameSex ns){
		return ns.hashCode();
	}
	
	public static void p(){
		Scanner s = new Scanner(System.in);
		s.next();
	}
	public static void h(){
		System.out.println("here");
	}
}
