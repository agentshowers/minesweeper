package base;

import java.util.Observable;

public class Board extends Observable{
	public static final int CUSTOM = 0;
	public static final int NOOB = 1;
	public static final int NORMAL = 2;
	public static final int PRO = 3;
	
	public static final int LOSE = -1;
	public static final int PLAYING = 0;
	public static final int WIN = 1;
	
	public static final int NOOB_W = 9;
	public static final int NOOB_H = 9;
	public static final int NOOB_M = 10;
	
	public static final int NORMAL_W = 16;
	public static final int NORMAL_H = 16;
	public static final int NORMAL_M = 40;
	
	public static final int PRO_W = 30;
	public static final int PRO_H = 16;
	public static final int PRO_M = 99;
	
	public static final int MIN_H = 9;
	public static final int MAX_H = 24;
	public static final int MIN_W = 9;
	public static final int MAX_W = 30;
	public static final int MIN_M = 10;
	public static final int MAX_M = 679;
	
	public static final int B_MINA = -1;
	public static final int B_VAZIO = 0;
	
	public static final int ST_NADA = 0;
	//estados 1-8 indicam o numero correspondente
	public static final int ST_VAZIO = 9;
	public static final int ST_BANDEIRA = 10;
	public static final int ST_BANDEIRA_ERRADA = 11;
	public static final int ST_MINA_NAO_CLICKADA = 12;
	public static final int ST_MINA_CLICKADA = 13;
	
	private int [][] estados;
	private int [][] board;
	private int width;
	private int heigth;
	private int clicks;
	private int totalMines;
	private int totalFlaggedMines;
	private int gameStatus;
	
	public Board (){
		this (NOOB);
	}
	
	public Board (int modo) {
		switch (modo){
			case NOOB: build (NOOB_W,NOOB_H,NOOB_M); break;
			case NORMAL: build (NORMAL_W,NORMAL_H,NORMAL_M); break;
			case PRO: build (PRO_W,PRO_H,PRO_M); break;
		}
	}
	
	public Board (int width, int heigth, int mines) {
		build (width,heigth,mines);
	}
	
	private void build (int width, int heigth, int mines) {
		estados = new int [heigth][width];
		
		for (int i=0;i<heigth;i++)
			for (int j=0;j<width;j++)
				estados[i][j] = ST_NADA;
		
		board = BoardGenerator.generateBoard(width, heigth, mines);
		
		this.width = width;
		this.heigth = heigth;
		totalMines = mines;
		totalFlaggedMines = 0;
		clicks = 0;
		gameStatus = PLAYING;
		
	}
	
	public void reset (){
		build (width,heigth,totalMines);
	}
	
	public int [][] getEstados (){
		return estados;
	}
	
	public int getGameStatus (){
		return gameStatus;
	}
	
	public int getRemainingMines (){
		if (totalFlaggedMines < totalMines) return totalMines - totalFlaggedMines;
		return 0;
	}
	
	public int getHeigth(){
		return heigth;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getMines(){
		return totalMines;
	}
	
	public void leftClick (int i, int j){
		if (estados[i][j]!=ST_NADA) return;
		if (gameStatus!=PLAYING) return;
		
		switch (board[i][j]){
			case B_MINA: 
				estados[i][j]=ST_MINA_CLICKADA; 
				gameStatus=LOSE;
				fillLose();
				break;
			case B_VAZIO: 
				estados[i][j]=ST_VAZIO; 
				liberta(i,j);
				clicks++;
				break;
			default: 
				estados[i][j]=board[i][j];
				clicks++;
				break;
		}


		if(clicks==(width*heigth)-totalMines) {
			gameStatus = WIN;
			fillWin();
		}
		
		this.setChanged();
		this.notifyObservers();
	}
	
	public void rigthClick (int i, int j){
		if (gameStatus!=PLAYING) return;
		
		if (estados[i][j]==ST_BANDEIRA) {
			estados[i][j]=ST_NADA;
			totalFlaggedMines--;
			this.setChanged();
			this.notifyObservers();
		}
		
		else if (estados[i][j]==ST_NADA) {
			estados[i][j]=ST_BANDEIRA;
			totalFlaggedMines++;
			this.setChanged();
			this.notifyObservers();
		}
		
		
	}
	
	private void fillLose (){
		for (int i=0;i<heigth;i++){
			for (int j=0;j<width;j++){
				if (estados[i][j]==ST_BANDEIRA && board[i][j]!=B_MINA)
					estados[i][j]=ST_BANDEIRA_ERRADA;
				if (estados[i][j]==ST_NADA && board[i][j]==B_MINA)
					estados[i][j]=ST_MINA_NAO_CLICKADA;
			}
		}
			
	}
	
	private void fillWin (){
		for (int i=0;i<heigth;i++){
			for (int j=0;j<width;j++){
				if (estados[i][j]==ST_NADA && board[i][j]==B_MINA)
					estados[i][j]=ST_MINA_NAO_CLICKADA;
			}
		}
	}
	
	private void liberta (int i, int j){
		int up = i-1;
		int down = i+1;
		int left = j-1;
		int rigth = j+1;
		
		clear(up,j);
		clear(down,j);
		clear(i,left);
		clear(i,rigth);
		clear(up,left);
		clear(up,rigth);
		clear(down,left);
		clear(down,rigth);
	}
	
	public void clear (int i, int j){
		if (i>=0 && i<heigth && j>=0 && j<width){
			if (estados[i][j]==Board.ST_NADA){
				if (board[i][j]==Board.B_VAZIO) {
					estados[i][j]=Board.ST_VAZIO;
					liberta(i,j);
				}
				else estados[i][j]=board[i][j];
				clicks++;
			}
		}
	}
	
	public static void validateMines (int width, int heigth, int mines) throws IncorrectValueException {
		if (mines < MIN_M) throw new IncorrectValueException ("Número de minas tem de ser no mínimo "+MIN_M,MIN_M);
		int maxMines = calculateMaxMines(width,heigth);
		if (mines > maxMines) throw new IncorrectValueException ("Número de minas para o tamanho escolhido tem de ser no máximo "+maxMines,maxMines);
	}
	
	public static void validateWidth (int width) throws IncorrectValueException {
		if (width < MIN_W) throw new IncorrectValueException ("Largura tem de ser no mínimo "+MIN_W,MIN_W);
		if (width > MAX_W) throw new IncorrectValueException ("Largura tem de ser no máximo "+MAX_W,MAX_W);
	}
	
	public static void validateHeigth (int heigth) throws IncorrectValueException {
		if (heigth < MIN_H) throw new IncorrectValueException ("Altura tem de ser no mínimo "+MIN_H,MIN_H);
		if (heigth > MAX_H) throw new IncorrectValueException ("Altura tem de ser no máximo "+MAX_H,MAX_H);
	}
	
	private static int calculateMaxMines (int width, int heigth) {
		double a = 0.84701;
		double b = 0.00015;
		double x = width * heigth;
		double y = a * Math.exp(b*x);
		
		return (int) Math.round(y * x);
	}
	
	public void print (){
		for (int i=0;i<heigth;i++){
			for (int j=0;j<width;j++)
				System.out.print(board[i][j]+" ");
			System.out.println();
		}
			
	}
	
	
}
