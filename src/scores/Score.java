package scores;

@SuppressWarnings("rawtypes")
public class Score implements Comparable{
	private String nome;
	private int tempo;
	
	public Score(String nome, int tempo) {
		this.nome = nome;
		this.tempo = tempo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getTempo() {
		return tempo;
	}

	public void setTempo(int tempo) {
		this.tempo = tempo;
	}

	@Override
	public int compareTo(Object arg0) {
		Score s = (Score) arg0;
		
		if (s.getTempo()<this.tempo)
			return 1;
		
		return -1;
	}
	
	
}
