package yaremax.menu;

import yaremax.SouvenirFacade;
import yaremax.dto.Producer;
import yaremax.dto.Souvenir;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

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
    public void handleInput(String input) {
        switch (Integer.parseInt(input)) {
            case 1 -> souvenirFacade.viewAllProducer();
            case 2 -> {
                Producer producer = inputProducer();
                souvenirFacade.addProducer(producer);
            }
            case 3 -> {
                Producer producer = inputProducer();
                souvenirFacade.editProducer(producer);
            }
            case 4 -> souvenirFacade.viewAllSouvenirs();
            case 5 -> {
                Souvenir souvenir = inputSouvenir();
                souvenirFacade.addSouvenir(souvenir);
            }
            case 6 -> {
                Souvenir souvenir = inputSouvenir();
                souvenirFacade.editSouvenir(souvenir);
            }
            case 7 -> menuManager.setCurrentState(menuManager.getMainMenuState());
            default -> System.out.println(input + " not a valid option");
        }
    }

    private static Souvenir inputSouvenir() {
        Scanner in = new Scanner(System.in);
        System.out.print("Введіть ім'я сувеніру: ");
        String name = in.nextLine();

        System.out.print("Введіть ім'я виробника: ");
        String producerName = in.nextLine();

        System.out.print("Введіть дату створення (дд-мм-рррр): ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy").withLocale(Locale.UK);
        LocalDate releaseDate = LocalDate.parse(in.nextLine(), formatter);

        System.out.print("Введіть ціну сувеніру: ");
        Double price = in.nextDouble();

        in.close();
        return new Souvenir(name, producerName, releaseDate, price);
    }

    private static Producer inputProducer() {
        Scanner in = new Scanner(System.in);
        System.out.print("Введіть ім'я виробника: ");
        String name = in.nextLine();

        System.out.print("Введіть країну виробника: ");
        String country = in.nextLine();

        in.close();
        return new Producer(name, country);
    }

}
