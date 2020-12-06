import javafx.scene.shape.Circle;
import javafx.scene.Node;
import javafx.scene.paint.Paint;

public class Ball extends GameObject {
    private static final transient int JUMP_SIZE = 90; // pixels
    private static final transient int MAX_SCORE = 30;
    private static transient double jumpDuration = 200; // milliseconds
    private static transient double velocity = Physics.velocity(JUMP_SIZE, jumpDuration);
    private static final transient double DOWNWARD_ACCN = Physics.acceleration(velocity, JUMP_SIZE);

    private Colors ballColor;

    public Colors getBallColor() {
        return this.ballColor;
    }

    public void setBallColor(Colors newColor) {
        this.ballColor = newColor;
    }

    public void updateVelocity(int score) {
        if (score <= MAX_SCORE) {
            jumpDuration = 200 - 75 * score / MAX_SCORE;
            velocity = Physics.velocity(JUMP_SIZE, jumpDuration);
        }
    }

    public double move(double duration, double translateOffset) {
        double displacement = Physics.displacement(velocity, duration, DOWNWARD_ACCN);
        double resolvedDisplacement = displacement - translateOffset;
        node.setTranslateY(-resolvedDisplacement > 0 ? 0 : -resolvedDisplacement);
        return resolvedDisplacement;
    }

    @Override
    public Boolean isColliding(Ball ball) { return null; }

    Ball(Node node) {
        super(node);

        int randomIndex = (int) (Math.random() * 4);
        String color = Colors.values()[randomIndex].colorCode;
        ((Circle) node).setFill(Paint.valueOf(color));
    }
}
