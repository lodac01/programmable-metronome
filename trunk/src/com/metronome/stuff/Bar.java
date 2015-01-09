package com.metronome.stuff;

/**
 * Representation of time BAR. Visual data is not included in this class 
 * @author miso
 *
 */
public class Bar {

	private Tempo tempo;
	private TimeSignature timeSignature;
		
	
	public Bar(Tempo tempo, TimeSignature ts) {
		this.tempo = tempo;
		this.timeSignature = ts;
	}
	
	public Tempo getTempo() {
		return tempo;
	}
	
	public TimeSignature getTimeSignature() {
		return timeSignature;
	}
	
	public long getNoteDuration()
	{
		return timeSignature.getNoteDuration(tempo.getTempo());
	}
	
	public long getBarDuration()
	{
		return timeSignature.getNoteDuration(tempo.getTempo())*timeSignature.getBeats();
	}
	
	
	
}
