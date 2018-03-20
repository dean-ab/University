
public class MathElectiveCourse extends ElectiveCourse { // You need to add inheritance

	public MathElectiveCourse(String name, int number, int credit) {
		super(name, number, credit);
	}
	
	// Returns the final grade after calculating the bonus
	public int computeFinalGrade(int grade){
		if ((grade < 0) || (grade > 100))
			throw new IllegalArgumentException("Grade is higher than 100 or lower than 0");
		if (grade < 56)
			return grade;
		int newGrade = grade + 5;
		if (newGrade > 100)
			return (newGrade - newGrade%100);
		else return newGrade;
	}

}
