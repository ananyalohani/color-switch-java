public abstract class Menu {
    protected Gameplay game;

    Menu(Gameplay game) {
        this.game = game;
    }

    public abstract void displayMenu();
    public abstract void exit();
}
