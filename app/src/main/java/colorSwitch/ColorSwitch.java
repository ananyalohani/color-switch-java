import java.io.Serializable;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.*;
import javafx.scene.*;
import javafx.stage.*;

public class ColorSwitch extends Application implements Serializable {
    public static final long serialVersionUID = 40L;
    private transient StartMenu startMenu;
    private ArrayList<SavedGame> savedGames;
    private int totalScore;

    public ColorSwitch() {}

    public SavedGame getSavedGames(Integer id) {
        return null;
    }

    public void _init() {
        launch();
    }

    public int getTotalScore() {
        return this.totalScore;
    }

    public void setTotalScore(int newScore) {
        this.totalScore = newScore;
    }

    public void serialize() {
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream("src/main/data/ColorSwitch"));
            System.out.println(totalScore);
            out.writeObject(this);
        } catch(Exception e) {
            e.printStackTrace();
            // System.out.println("error in serializing");
        } finally {
            try { out.close(); } catch (IOException err) {}
        }
    }

    @Override
    public void start(Stage stage) {
        startMenu = new StartMenu();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/startMenu.fxml"));
            stage.setScene(new Scene(loader.load()));
        } catch(IOException e) {
            e.printStackTrace();
        }

        stage.setTitle("Color Switch");
        stage.show();

        startMenu.setStage(stage);
        startMenu.display();
    }
}
