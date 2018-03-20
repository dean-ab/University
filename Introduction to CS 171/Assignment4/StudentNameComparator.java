
public class StudentNameComparator implements Comparator {

	@Override
	public int compare(Object obj1, Object obj2) {
		if ((!(obj1 instanceof Student)) || (!(obj2 instanceof Student)))
				throw new ClassCastException ("At least one of the objects are not 'Student' type");
		else {
			Student s1 = (Student)obj1;
			Student s2 = (Student)obj2;
			// Compare the last names of the students first
			if (s1.getLastName().compareTo(s2.getLastName()) == 0)
				// If the last names are equal, compare their first names
				return (s1.getFirstName().compareTo(s2.getFirstName()));
			else return (s1.getLastName().compareTo(s2.getLastName()));
		}
	}

}
