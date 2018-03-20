public class Task2 {
	public static void printSchedule(int[] schedule, String[] courses) {

		if ((schedule == null) | (schedule.length != courses.length) | (schedule.length == 0) | (courses.length == 0))
		throw new IllegalArgumentException("Wrong input");
		
		int maxDay = 0;
		boolean isFound = false;
		for (int s=0; s<schedule.length; s=s+1)
			if (schedule[s] > maxDay)
				maxDay = schedule[s];
		
		for (int i=0; i <= maxDay; i=i+1){
			System.out.print("Day " + i + ": ");
			for (int j=0; j < schedule.length; j=j+1 )
				if (schedule[j] == i){
					// check if already found a test in day i.
					if (isFound == false){ 
						System.out.print(courses[j]);
						isFound = true;}
					else System.out.print(", " + courses[j]);
				}
			System.out.println();
			isFound = false;
		}
	}
}
	
