public class Task10 {
	public static int[] solveMinDaysETP(String[] students, String[] courses, int[][] studentCourses) {

		int[] schedule;
		//check legal data
		if(Task0.legalData(students, courses, studentCourses, 1)==true)
		{
			boolean nSolved=true;
			int k=0;
			while(k<courses.length & nSolved)
			{//check if the the problem solved for every k
				k=k+1;
				if(Task9.solveETP(students, courses, studentCourses, k).length>0)
					nSolved=false;	
				
			}
			schedule=Task9.solveETP(students, courses, studentCourses, k);
		}
		else
			throw new IllegalArgumentException("Illegal Input");
		return schedule;
	
	}
}
