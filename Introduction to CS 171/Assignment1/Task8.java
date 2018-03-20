import java.util.Scanner;

public class Task8 {
	public static void main (String args[]){

            // ----------------- write your code BELOW this line only --------

	Scanner myScanner = new Scanner(System.in);
	// the program gets 3 numbers
           int x1=myScanner.nextInt();
           int x2=myScanner.nextInt();
           int x3=myScanner.nextInt();
		   int tmp;
           if (x1>x2)
           { 
        	   tmp=x2;
        	   x2=x1;
        	   x1=tmp;
           }
           if (x2>x3)
           { 
        	   tmp=x3;
        	   x3=x2;
        	   x2=tmp;
           }
           if (x1>x2)
           { 
        	   tmp=x2;
        	   x2=x1;
        	   x1=tmp;
           }
	//print the numbers after they got sorted
           System.out.println(x1);
		   System.out.println(x2);
		   System.out.println(x3);

            // ----------------- write your code ABOVE this line only ---------

	}
}
