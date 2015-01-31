package sk.majkl.metronome.stuff;

public class Tempo {

	public static final int DEFAULT_TEMPO = 60;
	private int tempo;
	
	
	public Tempo() {
		this.tempo = DEFAULT_TEMPO;
	}
	
	public Tempo(int tempo) throws InvalidTempoException
	{
		if(tempo<=0 || tempo>230)
			throw new InvalidTempoException(tempo);
		
		this.tempo = tempo;		
	}
	
	
	
	
	public int getTempo() {
		return tempo;
	}
	
	public void setTempo(int tempo) {
		this.tempo = tempo;
	}
	
	
	private class InvalidTempoException extends Exception
	{
		int tempo = 0;
		
		public InvalidTempoException(int tempo) {
			this.tempo = tempo;
		}
		
		@Override
		public String getMessage() {
			return "Invalid tempo was given! ("+tempo+")";
		}
	}
}
