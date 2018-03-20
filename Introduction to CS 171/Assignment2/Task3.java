public class Task3 {
	public static boolean isLegalSchedule(int[] schedule, String[] students, 
			String[] courses, int[][] studentCourses, int k) {
		boolean isLegal = true;
		int testDay;
		
		// checks condition A: 0 < Test Day < k-1
		for (int i=0; i<schedule.length; i=i+1)
			if ((schedule[i] < 0) | (schedule[i] > k-1)) 
				isLegal = false;
		
		// checks condition B: 
		// one student cannot have two test from different courses at the same day
		for (int j=0; j<studentCourses.length & isLegal ; j=j+1){ 
			for (int s=0; s<studentCourses[j].length & isLegal; s=s+1){
				testDay = schedule[studentCourses[j][s]];
				for (int l=s+1; l<studentCourses[j].length & isLegal; l=l+1)
					if (testDay == schedule[studentCourses[j][l]])
						isLegal = false;
			}		
		}	
		return isLegal;
	}
	
	
	public static int[] findConflictingStudent(int[] schedule, String[] students, 
		String[] courses, int[][] studentCourses, int k) {
		int [] findConflictingStudent = new int [0];
		int [] conflictingStudent = new int [4];
		boolean isLegal = true;
		int testDay;
		for (int j=0; j<studentCourses.length & isLegal ; j=j+1){
			for (int s=0; s<studentCourses[j].length & isLegal; s=s+1){
				testDay = schedule[studentCourses[j][s]];
				for (int l=s+1; l<studentCourses[j].length & isLegal; l=l+1){
					if (testDay == schedule[studentCourses[j][l]])
						isLegal = false;
					conflictingStudent[0] = testDay;
					conflictingStudent[1] = j;
					conflictingStudent[2] = studentCourses[j][s];
					conflictingStudent[3] = studentCourses[j][l];
				}
			}
		}
		if (isLegal) // returns an empty array in case there are no conflicting students
			return findConflictingStudent;
					//otherwise return the "conflictingstudent" array 
		else {
			findConflictingStudent = conflictingStudent;
			return findConflictingStudent;
		}
		
	}		
	public static void printConflictingStudent(int[] schedule, String[] students, 
			String[] courses, int[][] studentCourses, int k) {
		boolean isLegal = true;
		int testDay;
		for (int j=0; j<studentCourses.length & isLegal ; j=j+1){
			for (int s=0; s<studentCourses[j].length & isLegal; s=s+1){
				testDay = schedule[studentCourses[j][s]];
				for (int l=s+1; l<studentCourses[j].length & isLegal; l=l+1){
					if (testDay == schedule[studentCourses[j][l]]){
						isLegal = false;
						System.out.println("Student " + students[j] +  " cannot take exams in " + courses[studentCourses[j][s]] + " and " +  courses[studentCourses[j][l]] + " as scheduled in day " + testDay );
					}
				}
			}
		}
	}
}

