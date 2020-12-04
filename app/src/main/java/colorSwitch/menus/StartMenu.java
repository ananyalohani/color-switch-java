import javafx.scene.*;
import java.io.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class StartMenu extends Menu {
    private ColorSwitch mainGame;

    @Override
    public void display() {
        displayMenu();
    }

    @Override
    public void displayMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLs.Scene.START_MENU));

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            StartMenuController smController = loader.getController();
            smController.setup(this);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exit() {
        System.exit(0);
    }

    public void viewLeaderboard() {

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

    StartMenu(ColorSwitch game) {
        super(null);
        this.mainGame = game;
    }
}
