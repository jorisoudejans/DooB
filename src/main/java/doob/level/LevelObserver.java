package doob.level;

import doob.model.Level;

/**
 * Interface to be implemented by classes that observe Level.
 */
public interface LevelObserver {

    /**
     * What to do when a state changes should be handled in this method.
     * @param state state level changed to.
     */
    void onLevelStateChange(Level.State state);

}