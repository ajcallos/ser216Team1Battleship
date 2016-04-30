package battleship;

import java.io.File;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Simple class which plays background music in a loop
 *
 */
public class BackgroundMusic {
	// Placeholder for volume until volume control added
	private final static int bgmVOLUME = 0;
	
	private final static String BGM_DIRECTORY = "src/res/bgm/";
	private final static String BGM_ERROR_MESSAGE = "Error playing Background Music:";
	private final static String BGM_CLOSE_ERROR_MESSAGE = "Error closing Background Music File:";
	
	private String currentTrack;
	private AudioInputStream myAudioInputStream;
	private Clip myClip;
	
		
	
	public BackgroundMusic(String playThisTrack) throws Exception {
		this.currentTrack = BGM_DIRECTORY + playThisTrack;
		myAudioInputStream = null;
		
		try {
			myAudioInputStream = AudioSystem.getAudioInputStream(new File(currentTrack).getAbsoluteFile());
		} catch (Exception ex) {
			System.out.println(BGM_ERROR_MESSAGE);
			ex.printStackTrace();
		}
		
		AudioFormat trackAudioFormat = myAudioInputStream.getFormat();
		
		try {
			int streamLength = myAudioInputStream.available();
			byte[] streamBytesArray = new byte[streamLength];
			myAudioInputStream.read(streamBytesArray, 0, streamLength);
			myClip = AudioSystem.getClip();
			myClip.open(trackAudioFormat, streamBytesArray, 0, streamLength);
		} catch (Exception ex) {
			System.out.println(BGM_ERROR_MESSAGE);
			ex.printStackTrace();
		}
		
	}
	
	public void play() throws Exception {
		myClip.loop(myClip.LOOP_CONTINUOUSLY);
		myClip.start();
	}
	
	public void close() throws Exception {
		myClip.stop();
		myClip.close();
		try {
			myAudioInputStream.close();
		} catch (Exception ex) {
			System.out.println(BGM_CLOSE_ERROR_MESSAGE);
			ex.printStackTrace();
		}
	}
}
