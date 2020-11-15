package src;

public abstract class Menu {
    protected Gameplay game;

    Menu(Gameplay game) {
        this.game = game;
    }

    public void displayMenu();
    public void exit();
}

class StartMenu extends Menu{
    private ColorSwitch mainGame;

    StartMenu(ColorSwitch game) {
        this.mainGame = game;
    }

    @Override
    public void displayMenu() {

    }

    @Override
    public void exit() {

    }

    public void viewLeaderboard() {

    }

    public void newGame() {

    }

    public void displaySavedGames() {

    }
}

class PauseMenu extends Menu{
    
    @Override
    public void displayMenu() {

    }

    @Override
    public void exit() {
        
    }

    public void resumeGame() {

    }

    public void saveGame() {

    }
}

class EndMenu extends Menu{

    @Override
    public void displayMenu() {

    }

    @Override
    public void exit() {
        
    }

    public void saveToLeaderboard() {

    }

    public void newGame() {

    }
}