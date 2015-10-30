package doob.levelBuilder;

import java.io.File;

/**
 * This class is responsible for making a list of all custom levels to be played.
 * @author Cas
 *
 */
public class LevelReader {
	
	private static String path = "src/main/resources/level/Custom/";
	private String[] customNames;
	
	/**
	 * Checks all custom levels which are saved in the Custom folder and makes a list out of them.
	 * @return the pathname of the list.
	 */
	public String makeCustomLevelList() {
		File folder = new File(path);
		File[] customLevels = folder.listFiles();
		customNames = new String[customLevels.length];
		for (int i = 0; i < customLevels.length; i++) {
			customNames[i] = customLevels[i].getName();
		}
		LevelWriter.writeCustomLevels(customNames);
		String res = "src/main/resources/level/CustomSPLevels.xml";
		return res;
	}

	public void setPath(String p) {
		this.path = p;
	}

}
