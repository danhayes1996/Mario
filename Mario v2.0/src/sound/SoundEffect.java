package sound;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public enum SoundEffect {
	
	JUMP("/sounds/mario_jump-small.wav"),
	STOMP("/sounds/smb_stomp.wav"),
	COLLECT_1UP("/sounds/smb_1-up.wav"),
	COLLECT_POWERUP("/sounds/smb_powerup.wav"),
	COLLECT_COIN("/sounds/smb_coin.wav"),
	PIPE("/sounds/smb_pipe.wav");
	
	public static enum Volume { ON, OFF };
	private static Volume volume = Volume.ON;
	
	private Clip clip;
	
	private SoundEffect(String filepath) {
		try {
			clip = AudioSystem.getClip();
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(SoundEffect.class.getResourceAsStream(filepath));
			clip.open(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void play() {
		if(volume == Volume.ON) {
			clip.setFramePosition(0);
			clip.start();
		}
	}
	
	//UNUSED
	public static void init() {
		values(); //calls constructor for all elements
	}
	
	public static void setVolume(Volume volume) {
		SoundEffect.volume = volume;
	}
}
