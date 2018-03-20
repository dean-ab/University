import java.util.Iterator;

public class StudentManagementSystem {
	
	private LinkedList studentList;
	private LinkedList courseList;
	private int regStudents; // number of registered students
	private int exCourses; // number of existing courses
	
	public StudentManagementSystem() {
		this.studentList = new LinkedList();
		this.courseList = new LinkedList();
		this.regStudents = 0;
		this.exCourses = 0;
	}
	
	// Adds a new student to the system
	// Returns true if adding successfully made, false otherwise
	public boolean addStudent(Student student){
		if (!(checkStudent(student))) {
			studentList.add(student);
			regStudents ++;
			return true;
		}
		else
			return false;
	}
	
	// Adds a new course to the system
	// Returns true if adding successfully made, false otherwise
	public boolean addCourse(Course course){
		if (!(checkCourse(course))) {
			courseList.add(course);
			exCourses ++;
			return true;
		}
		else
			return false;
	}
	
	// Checks if the student is registered to the system
	// Returns true if student registered, false otherwise
	private boolean checkStudent(Student student){
		Iterator myIterator = studentList.iterator();
		while (myIterator.hasNext()) {
			Student otherStudent = (Student)myIterator.next();
			if (otherStudent.equals(student))
				return true;
			}
		return false;
	}
	
	// Checks if the course existed in the system
	// Returns true if course existed, false otherwise
	private boolean checkCourse(Course course){
		Iterator myIterator = courseList.iterator();
		while (myIterator.hasNext()) {
				Course otherCourse = (Course)myIterator.next();
				if (otherCourse.equals(course))
					return true;
		}
		return false;
	}
	
	// Register a given student to the given course
	// Returns true if registration successfully made, false otherwise
	public boolean register(Student student, Course course){
		if ((checkCourse(course)) & (checkStudent(student)) & (!(student.isRegisteredTo(course)))) {
			student.registerTo(course);
			return true;
		}
		else
			return false;
	}
	
	// Adds a grade to the given student in a given course
	// Returns true if adding successfully made, false otherwise
	public boolean addGradeToStudent(Student student, Course course, int grade){
		if ((checkCourse(course)) & (checkStudent(student))) {
			student.addGrade(course, grade);
			return true;
		}
		else return false;
	}
	
	// Returns the first k students that sorted by the given Comparator method
	public LinkedList getFirstKStudents(Comparator comp, int k){
		if ((comp == null) || (k < 0) || (studentList.size() < k))
			throw new IllegalArgumentException ("comp is null or " + k + " is not a natural number or there are less than " + k + " registered students");
		studentList.sortBy(comp);
		int index = 0;
		LinkedList kStudents = new LinkedList();
		Iterator myIterator = studentList.iterator();
		while (myIterator.hasNext() & index < k) {
			kStudents.add(myIterator.next());
			index ++;
		}
		return kStudents;
	} 
	
	public double computeFinalGrade(Student student){
		Iterator iterator = studentList.iterator();
		boolean check = false;
		while(iterator.hasNext() && !check)
		{
			if(iterator.next().equals(student))
				check = true;;
		}
		if(!check)
			return -1;
		
		return (student.computeFinalGrade());
	}
	
	// Returns the system's registered students number
	public int getNumberOfStudents() {
		return regStudents;
	}
	
	// Returns the system's existed courses number
	public int getNumberOfCourses() {
		return exCourses;
	}
}
