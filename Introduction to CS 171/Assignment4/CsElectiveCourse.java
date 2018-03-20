
public class CsElectiveCourse extends ElectiveCourse { // You need to add inheritance
	
	public CsElectiveCourse(String name, int number, int credit) {
		super(name, number, credit);
	}
	
	// Returns the final grade after calculating the bonus
	public int computeFinalGrade(int grade){
		if ((grade < 0) || (grade > 100))
			throw new IllegalArgumentException("Grade is higher than 100 or lower than 0");
		if (grade < 56)
			return grade;
		else {
			int newGrade = super.computeFinalGrade(grade);
			newGrade = newGrade + 5;
			if (newGrade > 100)
				return (newGrade - newGrade%100);
			else return newGrade;
		}
	}

}
