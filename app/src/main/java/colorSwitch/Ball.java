public class Ball extends GameObject {
    private static final transient Integer ACCELERATION;
    private static final transient Integer UPWARD_INITIAL_VEL;
    private Color ballColor;

    public Color getBallColor() {
        return this.ballColor;
    }

    public void setBallColor(Color newColor) {
        this.ballColor = newColor;
    }

    public void moveUp() {

    }

    Ball() {

    }
}