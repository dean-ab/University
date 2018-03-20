
public class Course {
	
	private String name;
	private int number;
	private int credit;
	
	public Course(String name, int number, int credit){
		if (inputCheck(name, number, credit))
			throw new  IllegalArgumentException("Invalid input");
		else {
			this.name = name;
			this.number = number;
			this.credit = credit;
		}
	}
	
	// Checks if the given input is legal
	private boolean inputCheck(String name, int number, int credit){
		return ((name == null) || (name == "") || (credit <= 0) || (number <= 0) || (!checkCourseName(name)));
	}
	
	// Checks if the given name is legal (A-Z, a-z, 0-9, " ")
	private boolean checkCourseName(String name){
		boolean output = true;
		while ((name.length() != 0) & (output)){
			char c = name.charAt(0);
			if (((c >= 'a') & (c <= 'z')) | ((c >= 'A') & (c <= 'Z')) | ((c >= '0') & (c <= '9')) | (c == ' '))
				output = true;
			else output = false;
			name = name.substring(1);
			}
		return output;
		}
	
	// returns course name
	public String getCourseName(){
		return name;
	}
	
	// returns course number
	public int getCourseNumber(){
		return number;
	}

	// returns course credit points number
	public int getCourseCredit(){
		return credit;
	}
	
	// prints out a description about the course
	public String toString(){
		String sCourse = "Course Name: " + name + "\n" + "Course Number: " + number + "\n" + "Course Credit Points: "+ credit ;
		return sCourse;
	}
	
	// compares two courses by their course number
	public boolean equals(Object other){
		if (!(other instanceof Course)) 
			return false;
		else 
			return (number == ((Course)other).getCourseNumber());
	}
	
	// calculate the final grade of the course by a given grade
	public int computeFinalGrade(int grade){
		if ((grade < 0) || (grade > 100))
			throw new IllegalArgumentException("Grade is higher than 100 or lower than 0");
		return grade;
	}
}
