public class Course {
	
	// -------------------- fields -------------------------- 
		
		private String name; 
		private int number; 
		          
	// -------------------- constructors -------------------- 	
		
		// constructs new course with given name and number
		public Course(String name, int number) {
			if ((name == null) || !checkCourseName(name) || (number <= 0))
				throw new IllegalArgumentException("Illegal Input");
			else {
				this.name = name;
				this.number = number;}
		}
		
	// -------------------- methods -------------------------- 
		
		// checks if the given course name is legal
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
		
		// returns the course name
		public String getName(){
			return name;
		}
		
		// returns the course number
		public int getCourseNumber(){
			return number;
		}
		
		// prints out the name and the number of the course
		public String toString(){
			String s = ("Course name: " + name + '\n' + "Course number: " + number);
			return s;
		}
		
		// compares between two different courses
		public boolean isEqualTo(Course other){
			if (this.number == other.number)
				return true;
			else return false;
		}
		
}

