import java.util.Scanner;

public class Task2 {
	public static void main(String[] args) {

            // ----------------- write your code BELOW this line only --------
	Scanner myScanner = new Scanner(System.in);
	int Num = myScanner.nextInt();
	int counter = 0;
	for(int i = 0; i < Num; i = i+1)
	{
		double x = Math.random();
		double y = Math.random(); 
		// calculate the value of pi
		if( (x*x) + (y*y)  <= 1 )
				counter = counter+1;
	}
	System.out.println((double)4*counter/Num);
	  // ----------------- write your code ABOVE this line only ---------

	}
}
