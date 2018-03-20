import java.util.Comparator;

public class EntryComparatorByNumber implements Comparator{

	public int compare(Object o1, Object o2) {
		if(!(o1 instanceof PhoneEntry) || !(o2 instanceof PhoneEntry))
			throw new ClassCastException();
		o1= ((PhoneEntry)o1).getNumber();
		o2= ((PhoneEntry)o2).getNumber();
		if(!(o1 instanceof Integer) || !(o2 instanceof Integer))
			throw new IllegalArgumentException();
		Integer num1 = (Integer)o1;
		Integer num2 = (Integer)o2;
		return (num1-num2);
	}

}
