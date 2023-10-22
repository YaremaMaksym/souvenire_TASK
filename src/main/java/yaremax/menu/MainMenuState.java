package yaremax.menu;

public class MainMenuState implements MenuState {
    private final MenuManager menuManager;

    public MainMenuState(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    @Override
    public void display() {
        System.out.println("""
                \n
                Main Menu:
                1️⃣ - 
                2️⃣ - 
                3️⃣ - 
                4️⃣ - Exit
                """);
    }

    @Override
    public void handleInput(String input) {
        switch (Integer.parseInt(input)) {
            case 1 -> {}
            case 2 -> {}
            case 3 -> {}
            case 4 -> menuManager.setCurrentState(menuManager.getExitState());
            default -> System.out.println(input + " not a valid option");
        }
    }
}