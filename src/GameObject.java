package src;

public class GameObject {
    protected Point position;

    public void setPosition(Point point) {

    }

    public Point getPosition() {
        return this.position;
    }
}

class Ball {
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

class Star {
    private Integer value;

    public Integer getValue() {
        return this.value;
    }
}
