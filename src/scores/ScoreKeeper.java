package scores;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

import base.Board;

public class ScoreKeeper {
	public static final int MAX = 3;
	private static final String filename = "scores.min";
	
	private Score [] scoresNoob;
	private Score [] scoresNormal;
	private Score [] scoresPro;
	
	
	public ScoreKeeper (){
		scoresNoob = new Score [MAX];
		scoresNormal  = new Score [MAX];
		scoresPro  = new Score [MAX];
		try {
			readFile();
		}
		catch (Exception e){
			for (int i=0;i<3;i++){
				scoresNoob[i] = null;
				scoresNormal[i] = null;
				scoresPro[i] = null;
			}
		}	
	}
	
	private void readFile() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(filename));
		
		while (br.ready()){
			String linha = br.readLine();
			String [] splits = linha.split(";");
			int pos = Integer.parseInt(splits[0]);
			int mode = Integer.parseInt(splits[1]);
			String nome = splits[2];
			int tempo = Integer.parseInt(splits[3]);
			Score s = new Score(nome,tempo);
			switch (mode){
				case Board.NOOB: scoresNoob[pos]=s; break;
				case Board.NORMAL: scoresNormal[pos]=s; break;
				case Board.PRO: scoresPro[pos]=s; break;
			}
		}
		
		br.close();
	}
	
	private void writeFile() throws Exception {
		PrintWriter pr = new PrintWriter (new FileWriter(filename));
		
		for (int i=0;i<MAX;i++){
			if (scoresNoob[i]!=null)
				pr.println(i+";"+Board.NOOB+";"+scoresNoob[i].getNome()+";"+scoresNoob[i].getTempo());
			if (scoresNormal[i]!=null)
				pr.println(i+";"+Board.NORMAL+";"+scoresNormal[i].getNome()+";"+scoresNormal[i].getTempo());
			if (scoresPro[i]!=null)
				pr.println(i+";"+Board.PRO+";"+scoresPro[i].getNome()+";"+scoresPro[i].getTempo());
		}
		
		pr.close();
	}
	

	public Score[] getScoresNoob() {
		return scoresNoob;
	}

	public Score[] getScoresNormal() {
		return scoresNormal;
	}

	public Score[] getScoresPro() {
		return scoresPro;
	}

	public boolean checkHighScore (int score, int mode){
		if (mode == Board.NOOB) {
			if (scoresNoob[MAX-1] == null) return true;
			return score < scoresNoob[MAX-1].getTempo();
		}
		
		if (mode == Board.NORMAL) {
			if (scoresNormal[MAX-1] == null) return true;
			return score < scoresNormal[MAX-1].getTempo();
		}
		
		if (mode == Board.PRO) {
			if (scoresPro[MAX-1] == null) return true;
			return score < scoresPro[MAX-1].getTempo();
		}
		
		return false;
	}
	
	public void addHighScore (String name, int score, int mode){
		Score s = new Score (name,score);
		
		if (mode == Board.NOOB){
			for (int i=0;i<MAX;i++){
				if (scoresNoob[i]==null){
					scoresNoob[i] = s;
					save();
					return;
				}
				if (score<scoresNoob[i].getTempo()){
					insert(s,scoresNoob,i);
					save();
					return;
				}
			}
		}
		
		if (mode == Board.NORMAL){
			for (int i=0;i<MAX;i++){
				if (scoresNormal[i]==null){
					scoresNormal[i] = s;
					save();
					return;
				}
				if (score<scoresNormal[i].getTempo()){
					insert(s,scoresNormal,i);
					save();
					return;
				}
			}
		}
		
		if (mode == Board.PRO){
			for (int i=0;i<MAX;i++){
				if (scoresPro[i]==null){
					scoresPro[i] = s;
					save();
					return;
				}
				if (score<scoresPro[i].getTempo()){
					insert(s,scoresPro,i);
					save();
					return;
				}
			}
		}
		
		
	}
	
	private void save(){
		try {
			writeFile();
		}
		catch (Exception e){
			System.out.println("erro a escrever");
		}	
	}
	
	private void insert (Score s, Score [] scores, int pos){
		for (int j=MAX-1;j>pos;j--)
			scores[j]=scores[j-1];
		
		scores[pos]=s;
	}
	
}
