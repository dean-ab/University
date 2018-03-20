import java.util.Scanner;

public class Task6 {
	public static void main(String[] args) {

            // ----------------- write your code BELOW this line only --------
            

	Scanner myScanner = new Scanner(System.in);
		
		
	     int x = myScanner.nextInt();
	     int m = 5;
	     int n = 1;
	     int r = 0;
	     
	    
	     
	     for(int i=0;i<x;i=i+1)
	     {
	        r=n;
	        n=m; 
	        m=r+n;
	     }
	     System.out.println(n);
	     System.out.println(m);




            // ----------------- write your code ABOVE this line only ---------

	}
}
