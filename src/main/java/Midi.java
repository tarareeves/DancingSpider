import java.io.File;
import java.io.IOException;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Track;


public class Midi {
	private static Midi instance = null;
	private File midiFile;
	private Sequencer seq;
	private double BPM;
	private Track[] tracks;

	private final int NOTE_ON = 0x90;
    private final int NOTE_OFF = 0x80;
    
	protected Midi(){
		
	}
	
	public static Midi getInstance(){
		if(instance == null){
			instance = new Midi();
		}
		
		return instance;
	}
	
	public void play(){
		if(!(this.midiFile == null) && !(seq.isRunning())){
			seq.start();
		}
	}

	public File getMidiFile() {
		return midiFile;
	}

	public void setMidiFile(File midiFile) {
		if(!midiFile.exists()){
			System.out.println("Error: The file doesn't exist.");
			return;
		}
		
		this.midiFile = midiFile;
		
		//Create the sequencer and open it
		try {
			seq = MidiSystem.getSequencer();
					
			//Set the sequence to be played
			seq.setSequence(MidiSystem.getSequence(midiFile));
			
			//Get the song's BPM
			BPM = seq.getTempoInBPM();
					
			seq.open();	
		} catch (MidiUnavailableException e2) {
			e2.printStackTrace();
			return;
		} catch (InvalidMidiDataException e1) {
			e1.printStackTrace();
			return;
		} catch (IOException e1) {
			e1.printStackTrace();
			return;
		}
				
		//Obtain the tracks from the file
		tracks = seq.getSequence().getTracks();
		
		//When the last note has played, stop the sequencer and
		//close it
		MetaEventListener listener = new MetaEventListener(){

			@Override
			public void meta(MetaMessage e) {
				if(e.getType() == 47){
					seq.stop();
					seq.close();
				}
			}
				
		};
				
		seq.addMetaEventListener(listener);
	}

	public Sequencer getSeq() {
		return seq;
	}

	public void setSeq(Sequencer seq) {
		this.seq = seq;
	}

	public double getBPM() {
		if(BPM == 0){
			System.out.println("No midi file has been supplied yet. "
					+ "Therefore, BPM will be 0.");
		}
		return BPM;
	}

	public Track[] getTracks() {
		return tracks;
	}

	public void setTracks(Track[] tracks) {
		this.tracks = tracks;
	}
}
