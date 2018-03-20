import java.util.Comparator;

public class EntryComparatorByName implements Comparator{

	public int compare(Object o1, Object o2) {
		if(!(o1 instanceof PhoneEntry) || !(o2 instanceof PhoneEntry))
			throw new ClassCastException();
		o1= ((PhoneEntry)o1).getName();
		o2= ((PhoneEntry)o2).getName();
		if(!(o1 instanceof String) || !(o2 instanceof String))
			throw new IllegalArgumentException();
		String name1 = (String)o1;
		String name2 = (String)o2; 
		return name1.compareTo(name2);
	}

}
