public class Task5 {
	public static void main(String[] args) {
            final int m = Integer.MAX_VALUE;

            // ----------------- write your code BELOW this line only --------
           

	
			
			int lFib=0;
			int mFib=0;
			int fFib=1;
			int tmp=0;
			
			while((m-fFib-mFib)>0)
			{
				tmp=fFib;
				fFib=fFib + mFib;
				lFib=mFib;
				mFib=tmp;
			}
			// print out the values
			System.out.println(mFib);
			System.out.println(fFib);
	


            // ----------------- write your code ABOVE this line only ---------
		
		
	}
}
