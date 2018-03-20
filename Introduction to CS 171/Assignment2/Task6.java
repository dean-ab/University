public class Task6 {
	public static int[][] variableTable (int m, int k){
		int[][] variableNames = new int[m][k];
		int counter = 1;
		
		// counter is the index of the CNF clauses
		for (int i=0; i < m; i=i+1)
			for (int j=0; j<k; j=j+1){
				variableNames[i][j] = counter;
				counter = counter +1;
			}
		return variableNames;
	}
}
