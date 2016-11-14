import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * This class will be used to provide easy methods to print out information from the NameStore for viewing in other applications
 * 
 * @author bhills
 *
 */
public class Exporter {
	
	/**
	 * Writes to file a Comma-Separated-Value document
	 * The document will contain the data for a particular NameSex
	 * 
	 * 
	 * @param filename - name for output file ('.csv' will be automatically appended to the name specified)
	 * @param nameEntry - name entry that will be written to file.
	 */
	public static void exportCSV(String filename, NameEntry nameEntry){
		File f = new File(filename+".csv");
		try {
			PrintWriter writer = new PrintWriter(f);
			writer.printf("%s,%s\n", nameEntry.getName(), nameEntry.getSex());
			writer.println("year, count");
			DataPoint[] data = nameEntry.getCounts();
			
			for(DataPoint dp : data){
				writer.printf("%1$d,%2$d\n", dp.year, dp.count);
			}
			writer.close();
		} catch (Exception e){
			e.printStackTrace();
		}
	}
}
