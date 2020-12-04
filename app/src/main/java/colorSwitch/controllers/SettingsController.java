import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import java.io.*;
import javafx.scene.Group;

public class SettingsController {
    private SettingsScene settingsScene;

    @FXML
    private Button musicBtn;

    @FXML
    private Group backBtn;

    public SettingsController() {}

    public void setup(SettingsScene settingsScene) {
        this.settingsScene = settingsScene;
    }

    public void setButtonOff() {
        String color = "#f5b5b9";
        musicBtn.setStyle("-fx-background-color:" + color + "; -fx-background-radius: 10;");
        musicBtn.setText("OFF");
    }

    public void setButtonOn() {
        String color = "#ace890";
        musicBtn.setStyle("-fx-background-color:" + color + "; -fx-background-radius: 10;");
        musicBtn.setText("ON");
    }

    @FXML
    private void musicBtnClicked() {
        String prevState = musicBtn.getText();

        if (prevState.equals("ON")) {
            Settings.setSoundOn(false);
            setButtonOff();
        } else {
            Settings.setSoundOn(true);
            setButtonOn();
        }
    }

    @FXML
    private void backBtnClicked() {
        settingsScene.goBack();
    }
}
