import java.util.Iterator;

public class Student {
	
	private static final int CREDIT_POINTS = 120;
	private String firstName;
	private String lastName;
	private int id;
	private LinkedList courseList;
	private LinkedList gradeList;

	public Student(String firstName, String lastName, int id) {
		if (inputCheck(firstName, lastName, id))
			throw new IllegalArgumentException("Invalid input");
		else {
			this.firstName = firstName;
			this.lastName = lastName;
			this.id = id;
			courseList = new LinkedList();
			gradeList = new LinkedList();
		}
	}
	
	// Checks if the given input is legal
	private boolean inputCheck(String firstName, String lastName, int id){
		return ((firstName == null) || (firstName == "") || (lastName == null) || (!checkName(firstName) || (lastName == "") || (!checkName(lastName)) || (id <= 0)));
	}
	
	// Checks if the given name is legal (A-Z, a-z, 0-9, " ")
	private boolean checkName(String name){
		boolean output = true;
		while ((name.length() != 0) & (output)){
			char c = name.charAt(0);
			if (((c >= 'a') & (c <= 'z')) | ((c >= 'A') & (c <= 'Z')) | (c == ' '))
				output = true;
			else output = false;
			name = name.substring(1);
			}
		return output;
		}

	// Returns student's first name
	public String getFirstName() {
		return firstName;
	}
	
	// Returns student's last name
	public String getLastName() {
		return lastName;
	}

	// Returns student's ID number
	public int getId() {
		return id;
	}
	
	// Return true if the student is registered to the given course, 
	// or false otherwise
	public boolean isRegisteredTo(Course course){
	if (courseList.isEmpty())
		return false;
	else {
			Iterator myIterator = courseList.iterator();
			while (myIterator.hasNext()) {
				Course currCourse = (Course) myIterator.next();
				if (currCourse.equals(course))
					return true;
			}
			return false;
		}
	}

	// Register student to the given course
	// Returns true if registration successfully made, false otherwise
	public boolean registerTo(Course course){
		if (isRegisteredTo(course))
			return false;
		courseList.add(course);
		return true;
	}
	
	// Calculates the average grade of the students
	// returns 0 if student has no grades in courses
	public double calculateAverage(){
		if(gradeList.isEmpty())
			return 0;
		double avg1 = 0;
		double avg2 = 0;
		for (Object grade : gradeList){
			Course currCourse = ((Grade)grade).getCourse();
			int currGrade = ((Grade)grade).getGrade();
			avg1 = avg1 + (currGrade * currCourse.getCourseCredit());
			avg2 = avg2 + currCourse.getCourseCredit();
			}
			return (avg1/avg2);
		}
	
	// Adds a grade to the given course
	// Returns true if adding successfully made, false otherwise
	public boolean addGrade(Course course, int grade){
		if (grade < 0 || grade > 100)
			throw new IllegalArgumentException("Grade is higher than 100 or lower than 0");
		if (!isRegisteredTo(course))
			return false;
		if (!gotGradeInCourse(course)){
			Grade studentGrade = new Grade(course, grade);
			gradeList.add(studentGrade);
			return true;	
		}
		return false;
	}
	
	// (!) must check before that student is registered to the given course
	private boolean gotGradeInCourse (Course course) { 
			Iterator myIterator = gradeList.iterator();
			while (myIterator.hasNext()) {
				Grade otherGrade = (Grade)myIterator.next();
				if (otherGrade.getCourse().equals(course))
					return true;
			}
			return false;
		}
	
	// Sets a new grade to the given course
	// Returns true if setting successfully made, false otherwise
	public int setGrade(Course course, int grade){
		if (!isRegisteredTo(course))
			throw new IllegalArgumentException("Student is not registerd to this course");
		boolean found = false;
		int temp = 0;
		Iterator myIterator = gradeList.iterator();
		while (myIterator.hasNext()) {
			Grade otherGrade = (Grade)myIterator.next();
			if (otherGrade.getCourse().equals(course)) {
				found = true;
				temp = otherGrade.getGrade();
				otherGrade.setGrade(grade);
			}
		}
		if (!found)
			throw new IllegalArgumentException("Student doesn't have a grade in this course");
		return temp;
	}
	// Compares two students by their ID numbers
	public boolean equals(Object other){
		if (!(other instanceof Student)) 
			return false;
		else 
			return (id == ((Student)other).getId());
	}
	
	// Returns the total credit points required for degree
	public int getTotalCreditRequired(){
		return CREDIT_POINTS;
	}
	
	// Computes the final grade of the student in degree (bonus included)
	// Returns the grade if the student reached the required credit points, or -1 otherwise
	public double computeFinalGrade(){
		Comparator comp = new FinalGradeComparator ();		
		gradeList.sortBy(comp);
		LinkedList gradesBonus = new LinkedList();
		Iterator iterator1 = gradeList.iterator();
		while(iterator1.hasNext()) //makes list of the grades + bonus
		{
			Grade G = (Grade)iterator1.next();
			Grade G2 = new Grade(G.getCourse(), G.getGrade());
			G2.setGrade(G2.computeFinalGrade());
			gradesBonus.add(G2);
		}
		
		int sumOfCredit = 0;
		LinkedList finalGrades = new LinkedList();
		Iterator iterator2 = gradesBonus.iterator();		
		while(sumOfCredit < getTotalCreditRequired() && iterator2.hasNext()) //makes list of the best grades
		{
			Grade G = (Grade)iterator2.next();
			if(G.getGrade() < 56)
				return -1;
			sumOfCredit = sumOfCredit + G.getCourse().getCourseCredit(); //counting the credit
			finalGrades.add(G);
		}
		if(sumOfCredit < getTotalCreditRequired())
			return -1;
		Iterator myIterator = finalGrades.iterator();
		double ave = 0;
		double sum = 0;
		while(myIterator.hasNext()) //calculating the average 
		{
			Grade G = (Grade) myIterator.next();
			ave = ave + G.getGrade() * G.getCourse().getCourseCredit();
			sum = sum + G.getCourse().getCourseCredit();
		}
		return ave/sum;
	}
}
