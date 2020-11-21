// import javafx.scene.Parent;
import javafx.scene.*;
import java.io.*;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.application.Application;
import javafx.stage.Stage;

public class StartMenu extends Menu {
    private ColorSwitch mainGame;

    @Override
    public void displayMenu(Stage stage) {
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

    }

    public void displaySavedGames() {

    }

    StartMenu(ColorSwitch game) {
        super(null);
        this.mainGame = game;
    }
}
