import javafx.scene.*;
import java.io.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.text.Text;

public class PauseMenu extends Menu {
    private Stage stage;
    private Text scoreCountNode;

    @Override
    public void display() {
        displayMenu();
    }

    @Override
    public void displayMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(FXMLs.Scene.PAUSE_MENU));

            Scene scene = new Scene(loader.load());
            stage.setScene(scene);

            PauseMenuController pmController = loader.getController();
            pmController.setup(this);
        } catch(IOException e) {
            e.printStackTrace();
        }

        scoreCountNode.setText(game.getState().getScore().toString());
    }

    @Override
    public void exit() {
        StartMenu startMenu = new StartMenu(null);
        startMenu.setStage(stage);
        startMenu.display();
    }

    public void initNodes(Text scoreCountNode) {
        this.scoreCountNode = scoreCountNode;
    }

    public void resumeGame() {
        stage.setScene(game.getScene());
        game.playGame(false);
    }

    public void saveGame() {
        game.saveGame();
    }

    public void endGame() {
        game.endGame();
    }

    PauseMenu(Gameplay game) {
        super(game);
        this.stage = game.getStage();
    }
}
