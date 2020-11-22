import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.Group;


public class Utils {
    public static void rotate(Group c, int duration, int angle) {
        RotateTransition rt = new RotateTransition(Duration.millis(duration), c);
        rt.setByAngle(angle);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
    }
}
