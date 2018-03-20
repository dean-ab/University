
public class Grade {
	
	private Course course;
	private int grade;
	
	public Grade(Course course, int grade){
		if (inputCheck(course, grade))
			throw new IllegalArgumentException("Invalid input");
		else {
			this.course = course;
			this.grade = grade;
		}
	}
	
	// Checks if the given input is legal
	private boolean inputCheck(Course course, int grade){
		return ((course == null) || (grade < 0) || (grade > 100));
	}
	
	// Returns the grade value
	public int getGrade() {
		return grade;
	}
	
	// Sets grade to Grade object
	public int setGrade(int grade) {
		int temp;
		if ((grade < 0) || (grade > 100))
			throw new IllegalArgumentException("Grade is higher than 100 or lower than 0");
		else 
			temp = this.grade;
			this.grade = grade;
			return temp;
	}
	
	// Returns the course grade
	public Course getCourse() {
		return course;
	}
	
	// Prints out a description about the grade
	public String toString(){
		String sGrade = "The grade in " + course.getCourseName() + " is " + grade ;
		return sGrade;
	}
	
	// Compares two grades by their grade values and by their courses
	public boolean equals(Object other){
		if (!(other instanceof Grade)) 
			return false;
		else {
			Grade otherGrade = ((Grade) other);
			if ((this.course == otherGrade.getCourse()) & (this.grade == otherGrade.getGrade()))
				return true;
			else return false;	
		}
	}
	
	// Returns the final grade of the course  
	public int computeFinalGrade(){
		return course.computeFinalGrade(grade);
	}
}
