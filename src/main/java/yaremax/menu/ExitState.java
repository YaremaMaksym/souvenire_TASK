package yaremax.menu;

public class ExitState implements MenuState {
    private final MenuManager menuManager;

    public ExitState(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    @Override
    public void display() {
        System.out.println("Exiting...");
    }

    @Override
    public void handleInput(String input) {

    }
}
