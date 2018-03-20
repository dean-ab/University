public class RegistrationSystem {
		
		// -------------------- constants -------------------- 
		
		private static final int MAX_COURSES = 500;
		private static final int MAX_STUDENTS = 500;
	
		// -------------------- fields -------------------- 
		
		private Student [] studentsList; 
		private Course [] coursesList;
		private int regStudents;    
		private int exCourses;
		
		// -------------------- constructors -------------------- 
		public RegistrationSystem(){
			 studentsList = new Student [MAX_COURSES]; 
			 coursesList = new Course [MAX_STUDENTS];
			 regStudents = 0;    
			 exCourses = 0;
		}
		
		// -------------------- methods --------------------
		
		// adds student to the registered students,
		// returns true if possible, false otherwise
		public boolean addStudent(Student student){
			if (regStudents < 500)
			{
				for (int i = 0; i < regStudents; i = i+1)
					if (student.isEqualTo(studentsList[i]))
						return false;
			studentsList[regStudents] = student;
			regStudents = regStudents +1;
			}
			else return false;
		return true;
		}
	
		// adds course to the existed courses,
		// returns true if possible, false otherwise
		public boolean addCourse(Course course){
			if (exCourses < 500)
			{
				for (int i = 0; i < exCourses; i = i+1)
					if (course.isEqualTo(coursesList[i]))
						return false;
			coursesList[exCourses] = course;
			exCourses = exCourses +1;
			}
			else return false;
		return true;
		}
		
		// adds a given course to the student's courses list
		// returns true if possible, false otherwise
		public boolean register(Student student, Course course){
			if ((isExist(course) && isExist(student)) & (!student.isRegisteredTo(course)))
				return student.registerTo(course);
			return false;
		}
		
		// checks if course exists in the courses list
		private boolean isExist(Course course){
			for (int i = 0; i < exCourses; i = i+1)
				if (coursesList[i] == course)
					return true;
			return false;
		}
		
		// checks if student exists in the students list
		private boolean isExist(Student student){
			for (int i = 0; i < regStudents; i = i+1)
				if (studentsList[i] == student)
					return true;
			return false;
		}
		
		// checks if there are conflicting courses at the registration system
		public boolean [][] findExamConflicts(){
			boolean [][] examConflicts = new boolean [exCourses][exCourses];
			for (int i = 0; i < examConflicts.length ; i = i+1)
				for (int j = 0; j < examConflicts[i].length ; j = j+1)
					if (i == j)
						examConflicts[i][j] = false;
					else
						examConflicts[i][j] = checkConflictingCourses(coursesList[i], coursesList[j]);
			return examConflicts;
		}
		
		// returns true if both courses' exams take place at the same day
		// and a student take both courses
		private boolean checkConflictingCourses(Course c1, Course c2){
			for (int i = 0; i < regStudents; i = i+1)
				if (studentsList[i].isRegisteredTo(c1) && studentsList[i].isRegisteredTo(c2))
					return true;
			return false;
		}
		
	}
