package doob.util;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/**
 * Class to manage the soundeffects in the game.
 */
public final class SoundManager {
	
	private static MediaPlayer tune;
	private static MediaPlayer soundEffect;
	private static double volume;
	
	public static final String DIE_EFFECT = "/Sounds/aww.mp3";
	public static final String POP_EFFECT = "/Sounds/pop.mp3";
	public static final String LEVEL_COMPLETE = "/Sounds/congratulations.mp3";
	public static final String GAME_WON = "/Sounds/applaus.mp3";
	public static final int MAX_VOLUME = 100;
	
	private SoundManager() {
	}
	
	/**
	 * Play the parameter tune on the background.
	 * @param path The path to the tune to play.
	 */
	public static void playTune(String path) {
		tune = new MediaPlayer(new Media(SoundManager.class.getResource(path).toString()));
		tune.setVolume(volume);
		tune.play();
	}
	
	/**
	 * Play the parameter tune as soundeffect.
	 * @param path The path to the tune to play.
	 */
	public static void playSound(String path) {
		System.out.println(volume);
		soundEffect = new MediaPlayer(new Media(SoundManager.class.getResource(path).toString()));
		soundEffect.setVolume(volume);
		soundEffect.play();
	}
	
	/**
	 * Because the volume scale is not linear the value of the volume slider has
	 * to be converted to a convenient value.
	 * @param xIn The value to be converted.
	 * @return The right volume.
	 */
	private static double volumeFunction(double x) {
		double a = x / SoundManager.MAX_VOLUME;
		double b = Math.pow(SoundManager.MAX_VOLUME, a);
		double c = b / SoundManager.MAX_VOLUME;
		return c;
	}
	
	public static double getVolume() {
		return volume;
	}
	
	public static void setVolume(double volume) {
		SoundManager.volume = volumeFunction(volume);
	}

}
