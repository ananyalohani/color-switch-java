import javafx.scene.shape.Circle;
import javafx.scene.Node;
import javafx.scene.paint.Paint;

public class Ball extends GameObject {
    private final int JUMP_SIZE = 90; // pixels
    private final int MAX_SCORE = 30;
    private double jumpDuration = 200; // milliseconds
    private double velocity = Physics.velocity(JUMP_SIZE, jumpDuration);
    private final double DOWNWARD_ACCN = Physics.acceleration(velocity, JUMP_SIZE);

    private Colors ballColor;

    public Colors getColor() {
        return this.ballColor;
    }

    public void setColor(Colors newColor) {
        this.ballColor = newColor;
        ((Circle) node).setFill(Paint.valueOf(newColor.colorCode));
    }

    public void updateVelocity(int score) {
        if (score <= MAX_SCORE) {
            jumpDuration = 200 - 75 * score / MAX_SCORE;
            velocity = Physics.velocity(JUMP_SIZE, jumpDuration);
        }
    }

    public void reset() {
        jumpDuration = 200;
        velocity = Physics.velocity(JUMP_SIZE, jumpDuration);
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
        ballColor = Colors.values()[randomIndex];
        ((Circle) node).setFill(Paint.valueOf(ballColor.colorCode));
    }
}
