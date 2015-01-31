package sk.majkl.metronome.stuff;

public class TimeSignature {

	private int notesCount;	// for example 3 in 3/4
	private int noteValue; // for example 4 in 3/4
	
	public enum TS
	{
		T34(3,4),
		T44(4,4),
		T54(5,4),
		T78(7,8),
		T88(8,8),
		T98(9,8);
		
		private final int count;
		private final int value;
		private final String str; 
		
		private TS(int notes, int beats) {
			this.count = notes;
			this.value = beats;
			str = notes+"/"+beats;
		}
		
		public int getCount() {
			return count;
		}
		public int getValue() {
			return value;
		}
		public String getStr() {
			return str;
		}
	}

        //for cloning
        public TimeSignature(TimeSignature ts) {
            if(ts==null)
            {
                this.noteValue=0;
                this.notesCount=0;
            }
            else
            {
                this.noteValue=ts.getNotes();
                this.notesCount=ts.getBeats();
            }
        }
	
        
	
	public TimeSignature(int count, int noteValue) throws InvalidTimeSignatureException {
		
		if(count==0 || noteValue==0) throw new InvalidTimeSignatureException(count+"/"+noteValue);
		this.notesCount = count;
		this.noteValue = noteValue;
		
	}
	
	public TimeSignature(TS ts) {
		this.notesCount = ts.getCount();
		this.noteValue = ts.getCount();
	}
	
	/**
	 * Calculate the note duration in this time signature, but tempo must be provided
	 * @param tempo tempo to calculate the note duration
	 * @return note duration in milliseconds
	 */
	public long getNoteDuration(int tempo)
	{
		float sec = 60;
		return (long)((sec/(tempo*noteValue))*4)*1000; 
	}
	
	
	public int getBeats() {
		return notesCount;
	}
	
	
	public int getNotes() {
		return noteValue;
	}
	
	private class InvalidTimeSignatureException extends Exception
	{
		String ts;
		
		public InvalidTimeSignatureException(String ts) {
			this.ts = ts;
		}
		
		@Override
		public String getMessage() {
			return "Invalid time signature: "+ts;
		}
	}

    @Override
    public String toString() {
        return noteValue+"/"+notesCount;
    }
        
        

    


        
	
	
}
