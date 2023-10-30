package yaremax.menu;

import lombok.Getter;
import lombok.Setter;
import yaremax.exception.DuplicateResourceException;
import yaremax.exception.ResourceNotFoundException;

import java.time.format.DateTimeParseException;
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
            try {
                currentState.handleInput();
            }  catch (DuplicateResourceException | ResourceNotFoundException e) {
                System.out.println("🛑🛑🛑 " + e.getMessage() + " 🛑🛑🛑");
            }
        }
    }
}
