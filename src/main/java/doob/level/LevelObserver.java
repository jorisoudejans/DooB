package doob.level;

import doob.model.Level;

/**
 * Interface to be implemented by classes that observe Level.
 */
public interface LevelObserver {

    /**
     * What to do when a event changes should be handled in this method.
     * @param event event level changed to.
     */
    void onLevelStateChange(Level.Event event);

}