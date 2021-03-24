/*******************************************************************************
 * This code or part of it is licensed under MIT License by Vulcalien
 ******************************************************************************/
package vulc.dantegame.sfx;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Sound {

	public static final int TYPE_MUSIC = 0;
	public static final int TYPE_EFFECT = 1;

	public static final boolean[] CHANNELS = {
	    true, true
	};

	public static final Sound THEME = new Sound("theme", TYPE_MUSIC);

	public static final Sound MENU_SOUND = new Sound("menu_sound", TYPE_EFFECT);
	public static final Sound CHECKPOINT = new Sound("checkpoint", TYPE_EFFECT);
	public static final Sound SAND = new Sound("sand", TYPE_EFFECT);

	private Clip clip;
	private final int type;

	public Sound(String file, int type) {
		this.type = type;
		try {
			AudioInputStream ais = AudioSystem.getAudioInputStream(Sound.class.getResource("/sfx/" + file + ".wav"));
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			this.clip = clip;
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void play() {
		if(clip == null || !CHANNELS[type]) return;
		if(clip.isRunning()) clip.stop();
		clip.setFramePosition(0);
		clip.start();
	}

	public void loop() {
		if(clip == null || !CHANNELS[type]) return;
		if(clip.isRunning()) clip.stop();
		clip.setFramePosition(0);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}

	public void stop() {
		if(clip == null) return;
		clip.stop();
	}

	public static void init() {
	}

}
