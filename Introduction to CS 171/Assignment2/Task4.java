public class Task4 {
	public static boolean[][] findExamConflicts(int m, int[][] studentCourses) {
		boolean[][] table = new boolean[m][m];
		for(int j=0;j<table.length;j=j+1)
		{
			//makes table for the conflict exams 
			for(int k=0;k<table[j].length;k=k+1)
			{
				table[j][k]=false;
			}
		}
		//if student studies 2 courses, they are conflicts
		for (int i=0;i<studentCourses.length;i=i+1)
		{			
			for(int j=0;j<studentCourses[i].length;j=j+1)
			{
				for(int k=j+1;k<studentCourses[i].length;k=k+1)
				{
					table[studentCourses[i][j]][studentCourses[i][k]]=true;
					table[studentCourses[i][k]][studentCourses[i][j]]=true;
				}
			}
			
		}
		
		return table;
	}		
}
