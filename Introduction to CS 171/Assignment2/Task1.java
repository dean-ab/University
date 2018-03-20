public class Task1 {
	public static void printStudentData(String[] students, String[] courses, int[][] studentCourses, int k) {
		
		if (Task0.legalData(students, courses, studentCourses, k) == false)
		throw new IllegalArgumentException("Wrong input");
		
		for (int i=0; i<students.length; i=i+1)
		{
			System.out.print(students[i] + ": ");
			for (int j=0; j<studentCourses[i].length; j=j+1)
				if (j != studentCourses[i].length-1)
				System.out.print(courses[studentCourses[i][j]] + ", ");
				else System.out.println(courses[studentCourses[i][j]]);
		}
	}
}	
	   