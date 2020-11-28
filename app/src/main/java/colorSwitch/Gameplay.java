import java.io.*;
import java.util.*;
import javafx.animation.*;
import javafx.fxml.*;
import javafx.scene.control.Button;
import javafx.scene.Group;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Gameplay implements IScene {
    // Constants
    private static final int FPS = 60;
    private static final long FPS_NS_PERIOD = 1_000_000_000 / FPS;

    // State and variables
    private boolean paused = false;
    private Stage stage;
    private Scene scene;

    // Menus and state
    private GameState currentState;
    private PauseMenu pauseMenu;
    private EndMenu endMenu;
    private GameplayController gpController;
    private AnimationTimer renderLoop;

    public Gameplay(SavedGame savedGame, Stage stage) {
        commonSetup(stage);
        // DO GAMESTATE THING HERE
    }

    public Gameplay(Stage stage) {
        commonSetup(stage);
        this.currentState = new GameState(this);
    }

    // Setting menus
    private void commonSetup(Stage stage) {
        this.stage = stage;

        pauseMenu = new PauseMenu(this);
        endMenu = new EndMenu(this);

        renderLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                currentState.updateState(now);
            }
        };
    }

    @Override
    public void display() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("scenes/gameplay.fxml"));
            scene = new Scene(loader.load());
            stage.setScene(scene);
            gpController = loader.getController();
        } catch(IOException e) {
            e.printStackTrace();
        }

        currentState.setup();
    }

    public Scene getScene() {
        return this.scene;
    }

    public Stage getStage() {
        return this.stage;
    }

    public GameplayController getController() {
        return this.gpController;
    }

    public Boolean getPaused() {
        return this.paused;
    }

    public void playGame() {
        paused = false;
        renderLoop.start();
        currentState.playState();
    }

    public void pauseGame() {
        paused = true;
        renderLoop.stop();
        currentState.pauseState();
        pauseMenu.display();
    }

    public void saveGame() {

    }

    public void endGame() {
        endMenu.display();
    }
}
