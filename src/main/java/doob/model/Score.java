package doob.model;

/**
 * Created by Shane on 6-10-2015.
 */
public class Score {

    private String name;
    private int score;

    /**
     *
     * @param name The name of the player
     * @param score The score of the player
     */
    public Score(String name, int score) {
        this.name = name;
        this.score = score;
    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;

    }

    /**
     * Checks if two Scores are equal. 
     * Used for testing reasons.
     * @param obj The object the compare.
     * @return wheter they are equal or not.
     */
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Score other = (Score) obj;
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		if (score != other.score) {
			return false;
		}
		return true;
	}
}
