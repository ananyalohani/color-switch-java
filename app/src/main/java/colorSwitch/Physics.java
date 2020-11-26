public class Physics {
  // v = s/t;
  public static double velocity(int height, int duration) {
    return (double) height / duration * 1000;
  }

  // a = -u^2/2s
  public static double acceleration(double velocity, int height) {
    return -Math.pow(velocity, 2)  / (2 * height);
  }

  // s = ut * (at^2)/2
  public static double displacement(double velocity, double duration, double accn) {
    return velocity * duration + 0.5 * accn * Math.pow(duration, 2);
  }
}
