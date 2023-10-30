package yaremax.menu;

import yaremax.SouvenirFacade;
import yaremax.dto.Producer;
import yaremax.dto.Souvenir;
import yaremax.util.InputManager;

import java.time.LocalDate;

public class ProducerSouvenirMenuState implements MenuState {
    private final MenuManager menuManager;
    private final SouvenirFacade souvenirFacade = new SouvenirFacade();

    public ProducerSouvenirMenuState(MenuManager menuManager) {
        this.menuManager = menuManager;
    }

    @Override
    public void display() {
        System.out.println("""
                \n
                Меню виробників та сувенірів:
                1️⃣ - Переглянути всіх виробників
                2️⃣ - Додати виробника
                3️⃣ - Змінити виробника
                ⏹️
                4️⃣ - Переглянути всі сувеніри
                5️⃣ - Додати сувенір
                6️⃣ - Змінити сувенір
                ⏹️
                7️⃣ - Повернутись до головного меню
                """);
    }

    @Override
    public void handleInput() {
        int choice = InputManager.getInt("Введіть ваш вибір: ");

        switch (choice) {
            case 1 -> souvenirFacade.viewAllProducers();
            case 2 -> {
                Producer producer = inputProducer();
                souvenirFacade.addProducer(producer);
            }
            case 3 -> {
                Long id = InputManager.getLong("Введіть id виробника, який ви хочете змінити: ");
                Producer producer = inputProducer();
                souvenirFacade.editProducer(id, producer);
            }
            case 4 -> souvenirFacade.viewAllSouvenirs();
            case 5 -> {
                Souvenir souvenir = inputSouvenir();
                souvenirFacade.addSouvenir(souvenir);
            }
            case 6 -> {
                Long id = InputManager.getLong("Введіть id сувеніра, який ви хочете змінити: ");
                Souvenir souvenir = inputSouvenir();
                souvenirFacade.editSouvenir(id, souvenir);
            }
            case 7 -> menuManager.setCurrentState(menuManager.getMainMenuState());
            default -> System.out.println("🛑🛑🛑 Опції " + choice + " немає в списку 🛑🛑🛑");
        }
    }

    private Producer inputProducer() {
        String name = InputManager.getString("Введіть ім'я виробника: ");
        String country = InputManager.getString("Введіть країну виробника: ");

        return Producer.builder()
                .name(name)
                .country(country)
                .build();
    }

    private Souvenir inputSouvenir() {
        String name = InputManager.getString("Введіть ім'я сувеніру: ");
        Long producerId = InputManager.getLong("Введіть id виробника: ");
        LocalDate releaseDate = InputManager.getDate("Введіть дату створення (дд-мм-рррр): ");
        Double price = InputManager.getDouble("Введіть ціну сувеніру: ");

        return Souvenir.builder()
                .name(name)
                .producerId(producerId)
                .releaseDate(releaseDate)
                .price(price)
                .build();
    }
}
