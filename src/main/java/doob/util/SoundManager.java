package doob.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Class to manage the soundeffects in the game.
 */
public final class SoundManager {
	
	private static MediaPlayer tune;
	private static MediaPlayer soundEffect;
	
	public static final String GAME_TUNE = "/Sounds/tune.mp3";
	public static final String DIE_EFFECT = "/Sounds/die.mp3";
	public static final String DOOB_EFFECT = "/Sounds/doob.mp3";
	
	private SoundManager() {
	}
	/**
	 * Play the parameter tune on the background.
	 * @param path The path to the tune to play.
	 */
	public static void playTune(String path) {
		tune = new MediaPlayer(new Media(SoundManager.class.getResource(path).toString()));
		tune.setVolume(0.1);
		tune.play();
	}
	
	/**
	 * Play the parameter tune as soundeffect.
	 * @param path The path to the tune to play.
	 */
	public static void playSound(String path) {
		soundEffect = new MediaPlayer(new Media(SoundManager.class.getResource(path).toString()));
		soundEffect.play();
	}

}
