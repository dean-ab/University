// You may not change or erase any of the lines and comments 
// in this file. You may only add lines.

import java.util.Scanner;

public class Task10 {


    public static void main(String[] args){


            // ----------------- write any code BELOW this line only --------
            Scanner scanner = new Scanner(System.in);
	 int x1 ;
     int x2 ;
     int x3 ;	
     int x4 ;
     int m1=0;int m2=0;int m3=0;int m4=0;
     int ver=0;
     
     for(int i=1;i<15;i=i+1)
     {
    	int res=0;
 		int num=i;	
 		int u=1;
 		while(num>0)
 		{
 			res=num%2*u+res;
 			num=num/2;
 			u=u*10;
 		}
 		x1=res%2;
 		x2=res/10%2;
 		x3=res/100%2;
 		x4=res/1000%2;
            // ----------------- write any code ABOVE this line only ---------




            // -----------------  copy here the code from Task 9 that is between
            // -----------------  the comments "A" and "B"
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
            // -----------------  end of copied code from Task 9




            // ----------------- write any code BELOW this line only --------
            if(x1>x2 || x2>x3 || x3>x4)
     {
    	m1=res%2;
  		m2=res/10%2;
  		m3=res/100%2;
  		m4=res/1000%2;
  		ver=1;
     }
     }
     if(ver>0)
     {
    	 System.out.println(m1);
    	 System.out.println(m2);
    	 System.out.println(m3);
    	 System.out.println(m4);
     }
     else
    	 System.out.println("Verified");
            // ----------------- write any code ABOVE this line only ---------

    } // end of main
} //end of class Task10

