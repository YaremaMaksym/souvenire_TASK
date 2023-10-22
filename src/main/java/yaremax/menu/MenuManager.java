package yaremax.menu;

import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;

@Getter
@Setter
public class MenuManager {
    private MenuState currentState;
    private final MenuState mainMenuState;
    private final MenuState exitState;

    private final Scanner scanner;

    public MenuManager() {
        this.mainMenuState = new MainMenuState(this);
        this.exitState = new ExitState(this);
        this.currentState = mainMenuState;

        this.scanner = new Scanner(System.in);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            currentState.display();
            if (currentState instanceof ExitState) {
                break;
            }
            String choice = scanner.nextLine();
            currentState.handleInput(choice, scanner);
        }
        scanner.close();
    }
}
