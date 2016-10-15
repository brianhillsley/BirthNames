
public class NameSex {
	public enum Sex {MALE, FEMALE};
	public String name;
	public Sex sex;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	public NameSex(String name, Sex sex){
		this.name = name;
		this.sex = sex;
	}
	public String toString(){
		return name+sex;
		
	}
}
