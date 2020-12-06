import javafx.scene.*;
import java.io.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class StartMenu extends Menu {
    private ColorSwitch mainGame;
    private Text scoreText;

    @Override
    public void display() {
        displayMenu();
        System.out.println("jdsfnk " + App.game.getTotalScore());
    }

    @Override
    public void displayMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.Scene.START_MENU));

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            StartMenuController smController = loader.getController();
            smController.setup(this);
        } catch(IOException e) {
            e.printStackTrace();
        }

        scoreText.setText(App.game.getTotalScore() + "");
    }

    @Override
    public void exit() {
        System.exit(0);
    }

    public void setScoreText(Text scoreText) {
        this.scoreText = scoreText;
    }

    public void newGame() {
        Gameplay newGameGameplay = new Gameplay(stage);
        newGameGameplay.display();
        newGameGameplay.playGame(true);
    }

    public void displaySavedGames() {
        SavedGamesScene savedGamesScene = new SavedGamesScene(this);
        savedGamesScene.display();
    }

    public void displaySettings() {
        SettingsScene settingsScene = new SettingsScene(this);
        settingsScene.display();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public Stage getStage() {
        return this.stage;
    }

    StartMenu() {
        super(null);
    }
}
