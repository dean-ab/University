public class Task8 {
	public static int[] convertOutput(int[][] variableNames, boolean[] assignment) {
		int[] schedule = new int[variableNames.length];
		
		// Checks which day (j) for course (i) will be the test day
		for (int i=0; i < variableNames.length; i=i+1)
			for (int j=0; j < variableNames[i].length; j=j+1)
				if (assignment[variableNames[i][j]] == true)
					schedule [i] = j;
		
		return schedule;
	}
}
