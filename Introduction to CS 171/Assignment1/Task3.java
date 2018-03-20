import java.util.Scanner;

public class Task3 {
	public static void main(String[] args) {


            // ----------------- write your code BELOW this line only --------
    
			//gets number != 0
			Scanner myScanner = new Scanner(System.in);
			int n = myScanner.nextInt();
			int lFib=0;
			int mFib=0;
			int fFib=1;
			int tmp=0;
			for(int i=1;i<n+2;i=i+1)
			{
				tmp=fFib;
				fFib=fFib + mFib;
				lFib=mFib;
				mFib=tmp;
			}
			// print out the value
			System.out.println(mFib);
			System.out.println(fFib);


            // ----------------- write your code ABOVE this line only ---------


	}
}
