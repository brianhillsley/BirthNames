

public class NameEntry {
	public enum Sex {MALE, FEMALE};
	private String name;
	private Sex sex;
	
	// counts holds all of the years of recorded data starting from 1880. so index 0 is 1880, index 1 is 1881, ...
	private int[] counts;
	
	public enum whyNoRecognize {why, not};
	
	public NameEntry(String name, Sex sex, int numYears){
		this.name = name;
		this.sex = sex;
		counts = new int[numYears];
	}
	
}
