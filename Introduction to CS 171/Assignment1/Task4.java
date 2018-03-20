import java.util.Scanner;

public class Task4 {
	public static void main(String[] args) {

            // ----------------- write your code BELOW this line only --------
          

			Scanner myScanner = new Scanner(System.in);
			int m = myScanner.nextInt();
			int lFib=0;
			int mFib=0;
			int fFib=1;
			int tmp=0;
			
			while((m-fFib-mFib)>=0)
			{
				tmp=fFib;
				fFib=fFib + mFib;
				lFib=mFib;
				mFib=tmp;
			}
			
			System.out.println(mFib);
			System.out.println(fFib);

            // ----------------- write your code ABOVE this line only ---------

	}
}
