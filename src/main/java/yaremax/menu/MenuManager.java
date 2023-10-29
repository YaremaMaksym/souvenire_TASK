package yaremax.menu;

import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;

@Getter
@Setter
public class MenuManager {
    private MenuState currentState;
    private final MenuState mainMenuState;
    private final MenuState producerSouvenirMenuState;
    private final MenuState exitState;

    public MenuManager() {
        this.mainMenuState = new MainMenuState(this);
        this.producerSouvenirMenuState = new ProducerSouvenirMenuState(this);
        this.exitState = new ExitState(this);
        this.currentState = mainMenuState;
    }

    public void run() {
        while (true) {
            currentState.display();
            if (currentState instanceof ExitState) {
                break;
            }
            System.out.print("Вибір: ");
            currentState.handleInput();
        }
    }
}
