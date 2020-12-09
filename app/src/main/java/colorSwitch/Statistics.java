import java.io.*;

public class Statistics implements Serializable {
    private int gameCount;
    private int totalScore;
    private int highscore;
    private int revivalCount;
    private int savedCount;

    public int getStat(Stat stat) {
        switch (stat) {
            case GAME_COUNT: return gameCount;
            case TOTAL_SCORE: return totalScore;
            case HIGHSCORE: return highscore;
            case REVIVAL_COUNT: return revivalCount;
            case SAVED_COUNT: return savedCount;
        }
        return 0;
    }

    public void setStat(Stat stat, int value, boolean isDelta) {
        switch (stat) {
            case GAME_COUNT:
                gameCount = (isDelta ? gameCount : 0) + value;
                break;
            case TOTAL_SCORE:
                totalScore = (isDelta ? totalScore : 0) + value;
                break;
            case HIGHSCORE:
                highscore = (isDelta ? highscore : 0) + value;
                break;
            case REVIVAL_COUNT:
                revivalCount = (isDelta ? revivalCount : 0) + value;
                break;
            case SAVED_COUNT:
                savedCount = (isDelta ? savedCount : 0) + value;
                break;
        }
    }
}

enum Stat {
    GAME_COUNT,
    TOTAL_SCORE,
    HIGHSCORE,
    REVIVAL_COUNT,
    SAVED_COUNT
}

class StatisticsScene implements IScene {
    @Override
    public void display() {

    }
}
