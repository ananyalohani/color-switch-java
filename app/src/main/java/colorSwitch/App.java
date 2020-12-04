import java.io.*;

public class App {
    private static ColorSwitch game;

    public static void main(String[] args) {
        game = new ColorSwitch();
        game._init();
    }

    public static void serialize() {
        // GameState gameState = new GameState(this);
        // ObjectOutputStream out = null;
        // try {
        //     out = new ObjectOutputStream(new FileOutputStream("ColorSwitch"));
        //     out.writeObject(game);
        // } catch(IOException e) {
        //     System.out.println("error in serializing");
        // } finally {
        //     out.close();
        // }
    }

    public static void deserialize() {
        // ObjectInputStream in = null;
        // try {
        //     in = new ObjectInputStream(new FileInputStream("ColorSwitch"));
        //     game = (ColorSwitch) in.readObject();
        // } catch(IOException e) {
        //     System.out.println("error in deserializing");
        // } finally {
        //     in.close();
        // }
    }
}
