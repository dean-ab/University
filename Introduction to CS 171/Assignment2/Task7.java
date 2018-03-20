public class Task7 {
	public static void convertInput(int[][] variableNames, String[] students, 
		      String[] courses, int[][] studentCourses, int k) {	

		// part 1
		for (int i = 0; i < variableNames.length; i=i+1) 
		{
			//exactly one of the variables gets true 
			SATSolver.addClauses(Task5.exactlyOne(variableNames[i]));
			
			
		}
		
		// part 2
		
		//make sure that conflicts courses are not in same day
		boolean[][] conflicts = Task4.findExamConflicts(variableNames.length, studentCourses);
		for (int i = 0; i < conflicts.length; i++) {
			for (int j = i+1; j < conflicts[i].length; j++) {

				if(conflicts[i][j]==true)
				SATSolver.addClauses(Task5.notSameDay(variableNames[i],variableNames[j]));

			}
		}
	}
}
