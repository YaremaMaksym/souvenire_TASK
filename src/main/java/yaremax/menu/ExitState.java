package yaremax.menu;

import java.util.Scanner;

public class ExitState implements MenuState {
    private final MenuManager menuManager;

    public ExitState(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    @Override
    public void display() {
        System.out.println("Завершення роботи...");
    }

    @Override
    public void handleInput() {

    }
}
