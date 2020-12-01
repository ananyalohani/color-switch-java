import javafx.util.Duration;
import javafx.animation.*;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.fxml.*;
import javafx.scene.layout.AnchorPane;

public class Utils {
    public static RotateTransition rotate(Node node, int duration, int angle) {
        RotateTransition rt = new RotateTransition(Duration.millis(duration), node);
        rt.setByAngle(angle);
        rt.setCycleCount(Animation.INDEFINITE);
        rt.setInterpolator(Interpolator.LINEAR);
        rt.play();
        return rt;
    }

    public static double getAbsoluteY(Node node) {
        return node.getTranslateY() + node.getLayoutY();
    }

    public static Bounds getBounds(Node node) {
        return node.localToScene(node.getBoundsInLocal());
    }

    public static Boolean intersects(Node a, Node b) {
        Bounds boundsA = getBounds(a);
        Bounds boundsB = getBounds(b);
        return boundsA.intersects(boundsB) && boundsB.intersects(boundsA);
    }

    public static Boolean intersects(Bounds a, Bounds b) {
        return a.intersects(b) && b.intersects(a);
    }

    public static Boolean isInside(Node a, Node b) {
        Bounds boundsA = getBounds(a);
        Bounds boundsB = getBounds(b);
        return (
            boundsA.getMinY() > boundsB.getMinY() &&
            boundsA.getMaxY() < boundsB.getMaxY()
        );
    }

    public static Boolean isInside(Bounds a, Bounds b) {
        return a.getMinY() > b.getMinY() && a.getMaxY() < b.getMaxY();
    }

    public static Node loadObject(String file) {
        try {
            AnchorPane root = FXMLLoader.<AnchorPane>load(Utils.class.getResource(file));
            return root.getChildren().get(0);
        } catch(Exception e) {
            return null;
        }
    }
}
