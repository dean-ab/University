
public class FinalGradeComparator implements Comparator {

	@Override
	public int compare(Object obj1, Object obj2) {
		if(!(obj1 instanceof Grade) || !(obj2 instanceof Grade))
			throw new ClassCastException();
		int grade1 = ((Grade)obj1).computeFinalGrade();
		int grade2 = ((Grade)obj2).computeFinalGrade();
		// Cases:
			// grade1 = grade2, returns 0
			// grade2 > grade1, returns a positive difference
			// grade1 < grade2, returns a negative difference
		return (grade2 - grade1);
	}

}
