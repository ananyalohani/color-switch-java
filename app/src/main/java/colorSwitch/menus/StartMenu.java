import javafx.scene.*;
import java.io.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class StartMenu extends Menu {
    private ColorSwitch mainGame;

    @Override
    public void displayMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/startMenu.fxml"));
            stage.setScene(new Scene(loader.load()));
            StartMenuController cont = loader.getController();
            cont.initData(this);
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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/gameplay.fxml"));

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            GameplayController cont = loader.getController();

            Gameplay newGameGameplay = new Gameplay(stage, scene);
            cont.init(newGameGameplay);
            newGameGameplay.start();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void displaySavedGames() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/savedGames.fxml"));
            stage.setScene(new Scene(loader.load()));
            SavedGamesController cont = loader.getController();
            cont.setStage(stage);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    StartMenu(ColorSwitch game) {
        super(null);
        this.mainGame = game;
    }
}
