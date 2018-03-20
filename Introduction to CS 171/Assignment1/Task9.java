// You may not change or erase any of the lines and comments 
// in this file. You may only add lines in the designated 
// area.

import java.util.Scanner;

public class Task9 {


    public static void main(String[] args){
            Scanner scanner = new Scanner(System.in);

            int x1 = scanner.nextInt();
            int x2 = scanner.nextInt();
            int x3 = scanner.nextInt();	
            int x4 = scanner.nextInt();

            // ----------------- "A": write your code BELOW this line only --------
                     int tmp;
         if (x1>x4)
         { 
      	   tmp=x4;
      	   x4=x1;
      	   x1=tmp;
         }
         if (x2>x3)
         { 
      	   tmp=x3;
      	   x3=x2;
      	   x2=tmp;
         }
         if (x3>x4)
         { 
      	   tmp=x3;
      	   x3=x4;
      	   x4=tmp;
         }	
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
	// the program prints out the 4 numbers after they got sorted
            // ----------------- "B" write your code ABOVE this line only ---------

            System.out.println(x1);
            System.out.println(x2);
            System.out.println(x3);
            System.out.println(x4);

    } // end of main
} //end of class Task9

