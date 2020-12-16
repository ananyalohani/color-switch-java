import java.io.*;
import javafx.scene.layout.*;
import javafx.collections.*;
import javafx.scene.text.Text;
import javafx.scene.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;

public class Statistics implements Serializable {
    private int gameCount;
    private int totalScore;
    private int highscore;
    private int revivalCount;
    private int savedCount;
    private int totalTime;

    public int getStat(Stat stat) {
        switch (stat) {
            case GAME_COUNT: return gameCount;
            case TOTAL_SCORE: return totalScore;
            case HIGHSCORE: return highscore;
            case REVIVAL_COUNT: return revivalCount;
            case SAVED_COUNT: return savedCount;
            case TOTAL_TIME: return totalTime;
        }
        return 0;
    }

    public void setStat(Stat stat, int value, boolean isDelta) {
        switch (stat) {
            case GAME_COUNT:
                gameCount = (isDelta ? gameCount : 0) + value;
                break;
            case TOTAL_SCORE:
                totalScore = (isDelta ? totalScore : 0) + value;
                break;
            case HIGHSCORE:
                highscore = (isDelta ? highscore : 0) + value;
                break;
            case REVIVAL_COUNT:
                revivalCount = (isDelta ? revivalCount : 0) + value;
                break;
            case SAVED_COUNT:
                savedCount = (isDelta ? savedCount : 0) + value;
                break;
            case TOTAL_TIME:
                totalTime = (isDelta ? totalTime : 0) + value;
        }
    }
}

enum Stat {
    GAME_COUNT,
    TOTAL_SCORE,
    HIGHSCORE,
    REVIVAL_COUNT,
    SAVED_COUNT, 
    TOTAL_TIME
}

class StatisticsScene implements IScene {
    private StartMenu startMenu;

    public StatisticsScene(StartMenu startMenu) {
        this.startMenu = startMenu;
    }

    @Override
    public void display() {
        ObservableList<Node> children = null;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.Scene.STATISTICS));

            Scene scene = new Scene(loader.load());
            startMenu.getStage().setScene(scene);

            children = ((AnchorPane) scene.getRoot()).getChildren();
        } catch(IOException e) {
            e.printStackTrace();
        }

        Statistics stats = App.game.getStats();

        int totalSeconds = stats.getStat(Stat.TOTAL_TIME);
        String timePlayed = (totalSeconds / 60) + "m " + (totalSeconds % 60) + "s";

        ((Text) children.get(0)).setText("" + stats.getStat(Stat.TOTAL_SCORE));
        ((Text) children.get(1)).setText("" + stats.getStat(Stat.HIGHSCORE));
        ((Text) children.get(2)).setText("" + stats.getStat(Stat.GAME_COUNT));
        ((Text) children.get(4)).setText("" + stats.getStat(Stat.SAVED_COUNT));
        ((Text) children.get(5)).setText("" + stats.getStat(Stat.REVIVAL_COUNT));
        ((Text) children.get(3)).setText(timePlayed);

        Group backBtn = (Group) children.get(6);
        backBtn.setOnMouseClicked(e -> {
            startMenu.display();
        });
    }
}
