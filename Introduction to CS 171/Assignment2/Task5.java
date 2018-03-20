import java.util.Arrays;

public class Task5 {
	

	public static int[][] atLeastOne(int[] vars)
	{
		//at least one get true
		int numOfClause = 1;
		int numOfLiteralsInClause = vars.length;
		int[][] formula = new int[numOfClause][numOfLiteralsInClause];
		
		for(int i=0;i<formula.length;i=i+1)
		{
			for(int j=0;j<formula[i].length;j=j+1)
				formula[i][j]=vars[j];
		}
		return formula;
	}
	
	
	
	public static int[][] atMostOne(int[] vars)
	{
		//at most one get true
		int n = vars.length;
		int numOfVarsPairs = n * (n-1) / 2;
		int numOfLiteralsInClause = 2;
		int[][] formula = new int[numOfVarsPairs][numOfLiteralsInClause];
		int counter=0;
		for(int i=0;i<vars.length;i=i+1)
		{
			for(int j=i+1;j<vars.length;j=j+1)
			{
				
					formula[counter][0] = -vars[i];
					formula[counter][1] = -vars[j];
					counter=counter+1;
				
			}
		}
		
		return formula;
	}
	public static int[][] exactlyOne(int[] vars)
	{
		//combination of the 2 function above
		int n = vars.length;
		int numOfVarsPairs = n * (n-1) / 2;
		int numOfClauses = numOfVarsPairs + 1;
		int[][] formula = new int[numOfClauses][];
		
		int[] atLeast= (atLeastOne(vars)[0]);	
		int[][] atMost= atMostOne(vars);
		formula[0]=atLeast;
		for(int i=1;i<formula.length;i=i+1)
		{
			formula[i]=atMost[i-1];
		}
		
		return formula;
	}
	public static int[][] notSameDay(int[] vars1, int[] vars2) {
		int numOfClause = vars1.length;
		int numOfLiteralsInClause = 2;
		int[][] formula = new int[numOfClause][numOfLiteralsInClause];
		
		for(int i=0;i<formula.length;i=i+1)
		{
			formula[i][0]= -vars1[i];
			formula[i][1]= -vars2[i];
		}
		
		return formula;
	}
	public static void main(String[] args) {
		
		int[] a = {2,5,7};
		int[] c = {3,6,9};
		int[][] b = notSameDay(a, c);
		for(int i=0;i<b.length;i++)
		System.out.println(Arrays.toString(b[i]));
		
		
	}
}
