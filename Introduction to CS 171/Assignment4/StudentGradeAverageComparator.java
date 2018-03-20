
public class StudentGradeAverageComparator implements Comparator {

	@Override
	public int compare(Object obj1, Object obj2) {
		if ((!(obj1 instanceof Student)) || (!(obj2 instanceof Student)))
			throw new ClassCastException ("At least one of the objects are not 'Student' type");
		double avgS1 = ((Student)obj1).calculateAverage();
		double avgS2 = ((Student)obj2).calculateAverage();
		// Cases:
		// avgS1 = avgS2, returns 0
		// avgS2 > avgS1, returns a positive difference
		// avgS1 < avgS2, returns a negative difference
		return ((int)(avgS2-avgS1));
		
	}

}
