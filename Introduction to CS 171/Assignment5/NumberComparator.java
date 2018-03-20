import java.util.Comparator;

//This comparator is used for demonstrations in Test classes.
public class NumberComparator implements Comparator{

	public NumberComparator() {
		super();
	}

	@Override
	public int compare(Object o1, Object o2) {
		if(!(o1 instanceof Integer) | !(o2 instanceof Integer))
			throw new ClassCastException();		
		return Integer.compare((Integer)o1, (Integer)o2);
	}
}
