package yaremax.menu;

import yaremax.SouvenirFacade;
import yaremax.dto.Producer;
import yaremax.dto.Souvenir;
import yaremax.exception.DuplicateResourceException;
import yaremax.exception.ResourceNotFoundException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
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
    public void handleInput() {
        try{
            Scanner scanner = new Scanner(System.in);
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> souvenirFacade.viewAllProducers();
                case 2 -> {
                    Producer producer = inputProducer(scanner);
                    souvenirFacade.addProducer(producer);
                }
                case 3 -> {
                    System.out.print("Введіть id виробника, який ви хочете змінити: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    Producer producer = inputProducer(scanner);
                    souvenirFacade.editProducer(id, producer);
                }
                case 4 -> souvenirFacade.viewAllSouvenirs();
                case 5 -> {
                    Souvenir souvenir = inputSouvenir(scanner);
                    souvenirFacade.addSouvenir(souvenir);
                }
                case 6 -> {
                    System.out.print("Введіть id сувеніра, який ви хочете змінити: ");
                    Long id = Long.parseLong(scanner.nextLine());
                    Souvenir souvenir = inputSouvenir(scanner);
                    souvenirFacade.editSouvenir(id, souvenir);
                }
                case 7 -> menuManager.setCurrentState(menuManager.getMainMenuState());
                default -> System.out.println("🛑🛑🛑 Опції " + choice + " немає в списку 🛑🛑🛑");
            }
        } catch (NumberFormatException e) {
            System.out.println("🛑🛑🛑 Цей ввід неможливо перевести в число 🛑🛑🛑");
        } catch (DateTimeParseException e) {
            System.out.println("🛑🛑🛑 Цей ввід неможливо перевести в дату 🛑🛑🛑");
        } catch (DuplicateResourceException | ResourceNotFoundException e) {
            System.out.println("🛑🛑🛑 " + e.getMessage() + " 🛑🛑🛑");
        }
    }

    private static Producer inputProducer(Scanner scanner) {
        System.out.print("Введіть ім'я виробника: ");
        String name = scanner.nextLine();

        System.out.print("Введіть країну виробника: ");
        String country = scanner.nextLine();

        return Producer.builder()
                .name(name)
                .country(country)
                .build();
    }

    private static Souvenir inputSouvenir(Scanner scanner) {
        System.out.print("Введіть ім'я сувеніру: ");
        String name = scanner.nextLine();

        System.out.print("Введіть id виробника: ");
        Long producerId = Long.parseLong(scanner.nextLine());

        System.out.print("Введіть дату створення (дд-мм-рррр): ");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy").withLocale(Locale.UK);
        LocalDate releaseDate = LocalDate.parse(scanner.nextLine(), formatter);

        System.out.print("Введіть ціну сувеніру: ");
        Double price = scanner.nextDouble();

        return Souvenir.builder()
                .name(name)
                .producerId(producerId)
                .releaseDate(releaseDate)
                .price(price)
                .build();
    }
}
