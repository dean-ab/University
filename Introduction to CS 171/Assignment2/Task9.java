public class Task9 {
	public static int[] solveETP(String[] students, String[] courses, int[][] studentCourses, int k) {
		
		int[] schedule;
		//check legal input
		if(Task0.legalData(students, courses, studentCourses, k)==true)
		{		
		SATSolver.init(courses.length*k);
		int[][] variableNames= Task6.variableTable(courses.length, k);//makes variable table
		Task7.convertInput(variableNames, students, courses, studentCourses, k);
		boolean[] SATsolver= SATSolver.getSolution();
		//check if there is solution
		if(SATsolver.length!=0)
		{
			schedule = Task8.convertOutput(variableNames, SATsolver);
			if(Task3.isLegalSchedule(schedule, students, courses, studentCourses, k)==false)
				throw new AssertionError("Something went wrong");
		}
		else
			schedule=new int[0];//empty array for no solution
		}
		else
			throw new IllegalArgumentException("Illegal Input");
		
		return schedule;
			
		
	}
}
