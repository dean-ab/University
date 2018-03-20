public class PhoneEntry {
	
	private String name;
	private Integer number;
	
	public PhoneEntry(String name, Integer number) {
		if(name == null || name.length() == 0){
			throw new IllegalArgumentException();
		}
		if(number <= 0){
			throw new IllegalArgumentException();
		}
		this.name = name;
		this.number = number;
	}
	
	public String getName(){
		return name;
	}
	
	public Integer getNumber(){
		return number;
	}
	
	public String toString(){
		return "Name: "+name+", Number: "+number;
	}

}
