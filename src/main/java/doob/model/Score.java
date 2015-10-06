package doob.model;

/**
 * Created by Shane on 6-10-2015.
 */
public class Score {

    String name;
    int score;

    /**
     *
     * @param name The name of the player
     * @param score The score of the player
     */
    public Score(String name, int score){
        this.name = name;
        this.score = score;
    }

    public String getName(){
        return this.name;
    }

    public int getScore(){
        return this.score;

    }
}
