
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

public class Runner {
	public static void main(String[] args) {
		
		int startYear = 1880;
		int endYear = 2015;
		NameStore n = new NameStore();
		System.out.println("contained: " + n.numNameSexes);
		
		Scanner input = new Scanner(System.in);
		System.out.println("Enter a name:");
		String inName = input.nextLine();
		System.out.println("Enter a starting year (>1879):");
		int inYear = Integer.parseInt(input.nextLine());
		input.close();
		n.printCountForEachSex(inName, inYear);
		n.printNameCount(inName, inYear);
		System.out.println("------------------------");
		n.printAllCountsSinceForEachSex(inName, inYear);
		
		Exporter.exportCSV(inName+"_out", n.nameEntry(inName, NameSex.Sex.MALE));
		Exporter.exportCSV(inName+"_out", n.nameEntry(inName, NameSex.Sex.FEMALE));
		
		// Check the local maxima method
		System.out.println("Local Maxima Years");
		ArrayList<Integer> locMax = n.nameEntry(inName, NameSex.Sex.MALE).localMaxima();
		for(Integer i: locMax){
			System.out.println(n.EARLIEST_YEAR+i);
		}
		// Check the absolute max method
		System.out.println("Absolute Max: " + (n.nameEntry(inName, NameSex.Sex.MALE).absoluteMaxIndex()));
		
		// Check the local minima method
		System.out.println("Local Minima Years");
		ArrayList<Integer> locMin = n.nameEntry(inName, NameSex.Sex.MALE).localMinima();
		for(Integer i: locMin){
			System.out.println(n.EARLIEST_YEAR+i);
		}
		
	
	}
}
