package yaremax.menu;

import yaremax.SouvenirFacade;
import yaremax.util.InputManager;

import java.util.Scanner;

public class MainMenuState implements MenuState {
    private final MenuManager menuManager;
    private final SouvenirFacade souvenirFacade = new SouvenirFacade();

    public MainMenuState(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    @Override
    public void display() {
        System.out.println("""
                \n
                Головне меню:
                1️⃣ - Меню виробників та сувенірів
                2️⃣ - Переглянути сувеніри певного виробника
                3️⃣ - Переглянути сувеніри, виготовлені в певній країні
                4️⃣ - Переглянути виробників, чиї ціни менше заданої
                5️⃣ - Переглянути сувеніри за виробниками
                6️⃣ - Переглянути виробників за сувеніром(ім'я, рік)
                7️⃣ - Переглянути сувеніри за роками
                8️⃣ - Видалити заданого виробника та його сувеніри.
                9️⃣ - Завершити роботу
                """);
    }

    @Override
    public void handleInput() {
        int choice = InputManager.getInt("Введіть ваш вибір: ");

        switch (choice) {
            case 1 -> menuManager.setCurrentState(menuManager.getProducerSouvenirMenuState());
            case 2 -> souvenirFacade.viewSouvenirsByProducer(InputManager.getLong("Введіть id виробника: "));
            case 3 -> souvenirFacade.viewSouvenirsByCountry(InputManager.getString("Введіть країну: "));
            case 4 -> souvenirFacade.viewProducersByPriceLimit(InputManager.getDouble("Введіть ціну: "));
            case 5 -> souvenirFacade.viewSouvenirsByProducers();
            case 6 -> souvenirFacade.viewProducersBySouvenir(
                    InputManager.getString("Введіть ім'я сувеніру: "),
                    InputManager.getInt("Введіть рік сувеніру: ")
            );
            case 7 -> souvenirFacade.viewSouvenirsByYears();
            case 8 -> souvenirFacade.deleteProducerAndSouvenirs(InputManager.getLong("Введіть id виробника якого ви хочете видалити: "));
            case 9 -> menuManager.setCurrentState(menuManager.getExitState());
            default -> System.out.println("🛑🛑🛑 Опції " + choice + " немає в списку запропонованих 🛑🛑🛑");
        }
    }
}
