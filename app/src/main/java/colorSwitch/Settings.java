import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.fxml.FXML;
import java.io.*;
import javafx.scene.Group;
import javafx.scene.*;
import javafx.fxml.FXMLLoader;

public class Settings {
    private static Boolean SOUND_ON = true;

    public static void setSoundOn(Boolean value) {
        SOUND_ON = value;
    }

    public static Boolean isSoundOn() {
        return SOUND_ON;
    }
}

class SettingsScene implements IScene {
    private StartMenu startMenu;
    private SettingsController controller;

    @Override
    public void display() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(Constants.Scene.SETTINGS));
            Scene settingsScene = new Scene(loader.load());
            controller = loader.getController();
            controller.setup(this);
            if (!Settings.isSoundOn()) controller.setButtonOff();
            startMenu.getStage().setScene(settingsScene);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public void goBack() {
        startMenu.display();
    }

    SettingsScene(StartMenu startMenu) {
        this.startMenu = startMenu;
    }
}
