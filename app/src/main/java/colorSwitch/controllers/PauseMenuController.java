import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import java.io.*;
import javafx.scene.*;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import java.io.*;
import javafx.fxml.FXML;

public class PauseMenuController {
    private PauseMenu pauseMenu;
    private Stage stage;

    @FXML
    private Text scoreCount;

    @FXML
    private Button mainMenuBtn, saveGameBtn, endGameBtn;

    @FXML
    private Group resumeBtn;

    public void initData(PauseMenu pauseMenu, Stage stage) {
        this.pauseMenu = pauseMenu;
        this.stage = stage;
    }
    
    public PauseMenuController() {}

    @FXML
    private void initialize() {}

    @FXML
    private void resumeBtnClicked() {
        pauseMenu.resumeGame();
    }

    @FXML
    private void mainMenuBtnClicked() {
        pauseMenu.exit();
    }

    @FXML
    public void saveGameBtnClicked() {

    }

    @FXML
    private void endGameBtnClicked() {
        EndMenu em = new EndMenu(null, stage);
        em.displayMenu();
    }
}
