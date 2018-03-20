public class NQueens {
	public static void printBoard(int[] board){
		if (board.length == 0) // checks if the array is empty or not
			System.out.println("There is no solution");
		else
			for (int i = 0; i < board.length; i = i+1)
				for (int j = 0; j < board.length; j = j+1)
					if (board[i] == j)
						if (j == board.length-1) // checks if its the last place of the row
							System.out.println("Q");
						else
							System.out.print("Q ");
					else 
						if (j == board.length-1) // checks if its the last place of the row
							System.out.println("*");
						else 
							System.out.print("* "); 
	}
	
	public static boolean isLegalSolution(int[] board, int n){
		if(n<4 & n>1 & board.length!=0) //there is no solution for n=2 and n=3
			return false;
		else {
		if(n==1)
			return true;
		boolean isL=true;
		for(int i=0;i<board.length-1;i=i+1)
		{
			for(int j=i+1;j<board.length;j=j+1)
			{
				if(board[i]==board[j]) //check the columns 
					isL=false;
				if(board[i]-i == board[j]-j || board[i]-board[j] == j-i) //check the oblique line
					isL=false;
			
			}
		}
		if(isL) return true;
		}

		return false;
	}

	public static boolean addQueen(int[] board, int row, int col){
		
		for(int i=0;i<row;i=i+1)
		{
			if(board[i]-i == col-row || board[i]-col == row-i || board[i]==col) //check the oblique line and the columns
			{
				return false; 
			}
		}
		board[row]=col;
		return true;
	}
	
	public static int[] nQueens(int n) {
		int[] empty= new int[0]; //empty array
		if(n==2 || n==3) //there is no solution for 2 and 3
			return empty;
		int[] board = new int[n];
		for(int i=0;i<n;i=i+1)
			board[i]=0;
		boolean solution = true;
		solution = nQueens(board,0,0); //send the board to the recursive function 
		return board;
	}

	public static boolean nQueens(int[] board, int row, int col){

		boolean add= addQueen(board, row, col);  //check if it's legal position for queen(if it's legal, put there queen)
		boolean sol= false;
		
		if(!add & col+1==board.length & row+1==board.length) //if we are in the last row and the last column and there is no place for the last queen, return false
			return false;
		
		if(add)
		{
			
			if(board.length-1==row)
			{
				return true; //if we found positions for n queens, return true
			}
			else
			{
				sol= nQueens(board, row+1, 0);	//after we found position in the current row, let's move on to the next row	
				if(!sol)
				{
					if(col+1== board.length)
					{
						return false;
					}
					sol= nQueens(board, row, col+1);
				}
			}
		}
		else
		{
			if(col+1== board.length) //if we are in the last column of the row and we didn't find position for a queen on this row, return false
			{
				return false;
			}
			else
			{
				sol= nQueens(board, row, col+1); //let's move on to the next column on the same row
			}
		}
		
		
		return sol;

	}
}
