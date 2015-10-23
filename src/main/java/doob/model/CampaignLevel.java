package doob.model;

import doob.util.BoundsTuple;

/**
 * A campaign level.
 */
public class CampaignLevel extends Level {

    /**
     * Init new level with size bounds.
     *
     * @param bounds width and height
     */
    public CampaignLevel(BoundsTuple bounds) {
        super(bounds);
    }

    /**
     * Adds one to time.
     * @return one
     */
    @Override
    protected int getTimeMutation() {
        return -1;
    }
}
