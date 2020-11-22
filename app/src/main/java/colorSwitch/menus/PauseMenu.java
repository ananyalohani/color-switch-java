import javafx.scene.*;
import java.io.*;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class PauseMenu extends Menu {
    private Scene gameplayScene;
    private GameplayController gpc;

    @Override
    public void displayMenu() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/pauseMenu.fxml"));
            stage.setScene(new Scene(loader.load()));
            PauseMenuController cont = loader.getController();
            cont.initData(this, stage);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exit() {
        StartMenu sm = new StartMenu(null);
        sm.setStage(stage);
        sm.displayMenu();
    }

    public void resumeGame() {
        stage.setScene(gameplayScene);
        gpc.animPlay();
    }

    public void saveGame() {

    }

    PauseMenu(Gameplay game, Scene gameplayScene, GameplayController gpc) {
        super(game);
        this.gameplayScene = gameplayScene;
        this.gpc = gpc;
    }
}
