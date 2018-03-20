public class Student {
	// -------------------- constants -----------------------
	private static final int MAX_COURSES = 10;
	
	// -------------------- fields --------------------------
	private String name; 
	private int id; 
	private Course[] list;
	int numOfCourses;
	
	// -------------------- constructors --------------------------
	
	// constructs new student with given name and id
	public Student(String name,int id){
		if(name==null || name=="" || id<1 || !(checkStudentName(name)) ) //check legal input
			throw new IllegalArgumentException("illegal input");
		else
		{
			this.id= id;
			this.name= name;
			numOfCourses=0;
			list= new Course[MAX_COURSES];
		}	
	}
	
	// -------------------- methods --------------------------------
	
	// checks if the given student name is legal
	private boolean checkStudentName(String name){ 
		boolean output = true;
		while ((name.length() != 0) & (output)){
			char c = name.charAt(0);
			if (((c >= 'a') & (c <= 'z')) || ((c >= 'A') & (c <= 'Z')) || (c == ' '))
				output = true;
			else output = false;
			name = name.substring(1);
			}
		return output;
		}

	// returns the student's name
	public String getName(){
		
		return this.name;
	}

	// returns the student's id
	public int getID(){

		return id;
	}
	
	// register the student to the given course
	// returns true if possible, false otherwise
	public boolean registerTo(Course course){
		
		if(isRegisteredTo(course) || numOfCourses==10) 
			return false;
		else
		{
			list[numOfCourses]=course;
			numOfCourses=numOfCourses+1;
			return true;
		}
	}

	//  checks if the student is registered to the given course
	public boolean isRegisteredTo(Course course){
		
		for(int i=0;i<numOfCourses;i=i+1)
		{
			if(course.isEqualTo(list[i]))
				return true;
		}
		return false;
	}
		
	// prints out a description about the student
	public String toString(){
		String st="";
		for(int i=0;i<numOfCourses;i=i+1) 
		{
			st= st + " " + list[i].getName() + " ";
		}
		return "Name: "+name+" ID: " +id+" The Courses: "+st;
	}
	
	// checks if the student's id is equal to the given student's id
	public boolean isEqualTo(Student other){
		if(other.getID()==id) 
			return true;
		return false;
	}
}