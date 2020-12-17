import java.io.*;

public class App {
    public static ColorSwitch game;

    public static void main(String[] args) {
        File colorSwitchData = new File(Constants.DataFiles.COLOR_SWITCH);
        if (colorSwitchData.exists()) {
            game = deserializeColorSwitch();
        } else {
            game = new ColorSwitch();
        }
        game._init();
    }

    public static ColorSwitch deserializeColorSwitch() {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream(Constants.DataFiles.COLOR_SWITCH));
            return (ColorSwitch) in.readObject();
        } catch (Exception e) {
            System.out.println("error in deserializing");
        } finally {
            try { in.close(); } catch (IOException err) {}
        }

        return null;
    }
}
