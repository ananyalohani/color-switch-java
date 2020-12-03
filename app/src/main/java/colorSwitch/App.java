public class App {
    private static ColorSwitch game;

    public static void main(String[] args) {
        game = new ColorSwitch();
        game._init();
    }

    public static void serialize() throws IOException {
        // GameState gameState = new GameState(this);
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream("ColorSwitch"));
            out.writeObject(game);
        } finally {
            out.close();
        }
    }

    public static void deserialize() throws IOException {
        ObjectInputStream in = null;
        try {
            in = new ObjectInputStream(new FileInputStream("ColorSwitch"));
            game = (ColorSwitch) in.readObject();
        } finally {
            in.close();
        }
    }
}
