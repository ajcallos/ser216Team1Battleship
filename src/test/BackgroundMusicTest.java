package test;

import org.junit.Test;
import static org.junit.Assert.*;
import battleship.BackgroundMusic;


public class BackgroundMusicTest {
	
	private final String playThisTrackNULL = null;
	private final String playThisTrack = "a thing 1 minute.wav";
	
	private BackgroundMusic backgroundMusic;

	private BackgroundMusic testBGM;
	
	@Test
	public void testBackgroundMusic() throws Exception {
		testBGM = null;
		
		try {
			testBGM = new BackgroundMusic(playThisTrack);
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		try {
			BackgroundMusic nullBGM = new BackgroundMusic(playThisTrackNULL);
			fail("No Exception thrown on null");
		} catch (Exception ex) {
			assert(true);
		}
		
		testBGM = null;
	}
	
	@Test
	public void testPlay() {
		testBGM = null;
		
		try {
			testBGM = new BackgroundMusic(playThisTrack);
		} catch (Exception ex) {
			fail(ex.getMessage());
		}
		
		if (testBGM == null) {
			fail("testBGM not initialized");
		}
		try {
			testBGM.play();
		} catch (Exception ex) {
			fail(ex.toString());
		}
	}
	
	@Test
	public void testClose() {
		
		try {
			testBGM.close();
		} catch (Exception ex) {
			fail(ex.toString());
		}
	}		
}
