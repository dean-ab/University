public class Task0 {
	
	public static void main(String[] args) {
		
	}	
	public static boolean legalData(String[] students, String[] courses, int[][] studentCourses, int k) {
		//check if the arrays null and the size of the arrays
		if(students==null || courses==null || studentCourses==null || students.length<1  || courses.length<1 ||  studentCourses.length<1 || k<1 || studentCourses.length != students.length){
			return false;
		}
		int[] atL = new int[courses.length];
		//array to check that every student studies at least one course
		for(int j=0;j<atL.length;j=j+1)
		{
			atL[j]=0;
							
		}
		for(int i=0;i<studentCourses.length;i=i+1)
		{
			if(studentCourses[i]==null || studentCourses[i].length<1)
				return false;
			
			int[] only1= new int[courses.length];
			//make sure that every course appears only one 
			for(int j=0;j<only1.length;j=j+1)
			{
				only1[j]=0;
								
			}
			for(int m=0;m<studentCourses[i].length;m=m+1)
			{
				if(studentCourses[i][m]>courses.length-1 || studentCourses[i][m]<0)
					return false;
				only1[studentCourses[i][m]] = only1[studentCourses[i][m]] + 1;
				atL[studentCourses[i][m]] = atL[studentCourses[i][m]] + 1;
			}
			for(int u=0;u<only1.length;u=u+1)
			{
				if(only1[u]>1)
					return false;
			}
			
		}
		for(int j=0;j<atL.length;j=j+1)
		{
			if(atL[j]<1)
				return false;							
		}
		for(int j=0;j<students.length;j=j+1)
		{
			if(students[j]==null || students[j].length()==0)   
				return false;				
			for(int i=j+1;i<students.length;i=i+1)
			{
				if(students[i].equals(students[j])) 
					return false;
			}
		}
		for(int j=0;j<courses.length;j=j+1)
		{
			if(courses[j]==null || courses[j].length()==0)  
				return false;				
			for(int i=j+1;i<courses.length;i=i+1)
			{
				if(courses[i].equals(courses[j]))   
					return false;
			}
		}		
		return true;		
	}			
}

