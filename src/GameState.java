package src;
import java.util.ArrayList;

public class GameState {
    private Ball ball;
    private Integer score;
    private Boolean stateSaved;
    private ArrayList<Obstacle> obstacles;
    private ArrayList<Star> stars;
    private Long lastTapMs;

    public Integer getScore() {
        return this.score;
    }

    public void setSavedState(Boolean newState) {
        this.stateSaved = newState;
    }

    public Boolean isStateSaved() {
        return stateSaved;
    }

    public void updateState() {

    }

    private void setScore(Integer delta) {
        this.score += delta;
    }

    private Boolean checkForCollision() {

    }

    private void collisionWithStar() {

    }

    private void collisionWithObstacle() {

    }
}
