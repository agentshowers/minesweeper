package base;

import java.util.Random;

public class BoardGenerator {
	
	public static int [][] generateBoard (int width, int heigth, int mines){
		int [][] board = new int [heigth][width];
		for (int i=0;i<heigth;i++)
			for (int j=0; j<width; j++)
				board [i][j] = Board.B_VAZIO;
		
		int max = width * heigth;
		
		Random random = new Random();
		
		while (mines>0){
			int pos = random.nextInt(max);
			int posH = pos / width;
			int posW = pos % width;
			if (board[posH][posW] == Board.B_VAZIO){
				board[posH][posW] = Board.B_MINA;
				mines--;
			}
		}
		
		for (int i=0;i<heigth;i++)
			for (int j=0; j<width; j++)
				if (board [i][j] == Board.B_VAZIO)
					board [i][j] = calculate (board,i,j,heigth,width);
			
		
		return board;
	}
	
	private static int calculate(int [][] board, int i, int j, int heigth, int width){
		int up = i-1;
		int down = i+1;
		int left = j-1;
		int rigth = j+1;
		
		
		int mines = 0;
		
		if (up>=0)
			if (board[up][j]==Board.B_MINA) mines++;
		
		if (down<heigth)
			if (board[down][j]==Board.B_MINA) mines++;
		
		if (left>=0)
			if (board[i][left]==Board.B_MINA) mines++;
		
		if (rigth<width)
			if (board[i][rigth]==Board.B_MINA) mines++;
		
		if (up>=0 && left>=0)
			if (board[up][left]==Board.B_MINA) mines++;
		
		if (up>=0 && rigth<width)
			if (board[up][rigth]==Board.B_MINA) mines++;
		
		if (down<heigth && left>=0)
			if (board[down][left]==Board.B_MINA) mines++;
		
		if (down<heigth && rigth<width)
			if (board[down][rigth]==Board.B_MINA) mines++;
		
		return mines;
	}
}
