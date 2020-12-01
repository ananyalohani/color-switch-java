public class Point {
    private Double x;
    private Double y;

    Point(double x, double y) {
        set(x, y);
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }
}
