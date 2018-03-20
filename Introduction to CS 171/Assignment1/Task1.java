import java.util.Scanner;

public class Task1 {
	public static void main(String[] args) {
            // gets three numbers
		Scanner myScanner = new Scanner(System.in);
		int num1 = myScanner.nextInt();
		int num2 = myScanner.nextInt();
		int num3 = myScanner.nextInt();
		int max;
		if (num1 > num2)
			max=num1;
		else
			max = num2;
		if (max > num3)
			System.out.println(max);
		else
			System.out.println(num3);
            // print out the max number of the three.
	}
}
