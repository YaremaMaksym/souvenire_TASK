package yaremax.menu;

import java.util.Scanner;

public interface MenuState {
    void display();
    void handleInput(String input);
}
