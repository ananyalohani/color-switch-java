import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

public class Sounds {
    private static final String BOUNCE_FILE = "src/main/resources/sounds/bounce.mp3";
    private static final String COLLISION_FILE = "src/main/resources/sounds/collision.mp3";
    private static final String SCORE_FILE = "src/main/resources/sounds/score.mp3";

    private static void play(String file) {
        Media sound = new Media(new File(file).toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }

    public static void bounce() {
        play(BOUNCE_FILE);
    }

    public static void collision() {
        play(COLLISION_FILE);
    }

    public static void score() {
        play(SCORE_FILE);
    }
}
