package yaremax.menu;

import yaremax.SouvenirFacade;
import yaremax.exception.ResourceNotFoundException;

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
        try{
            Scanner scanner = new Scanner(System.in);
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1 -> menuManager.setCurrentState(menuManager.getProducerSouvenirMenuState());
                case 2 -> {
                    System.out.print("Enter producer id: ");
                    Long producerId = Long.parseLong(scanner.nextLine());
                    souvenirFacade.viewSouvenirsByProducer(producerId);
                }
                case 3 -> {
                    System.out.print("Enter country: ");
                    String country = scanner.nextLine();
                    souvenirFacade.viewSouvenirsByCountry(country);
                }
                case 4 -> {
                    System.out.print("Enter price limit: ");
                    double priceLimit = Double.parseDouble(scanner.nextLine());
                    souvenirFacade.viewProducersByPriceLimit(priceLimit);
                }
                case 5 -> souvenirFacade.viewSouvenirsByProducers();
                case 6 -> {
                    System.out.print("Enter souvenir name: ");
                    String souvenirName = scanner.nextLine();
                    System.out.print("Enter souvenir year: ");
                    int year = Integer.parseInt(scanner.nextLine());
                    souvenirFacade.viewProducersBySouvenir(souvenirName, year);
                }
                case 7 -> souvenirFacade.viewSouvenirsByYears();
                case 8 -> {
                    System.out.print("Enter id of the producer you want to delete: ");
                    Long producerId = Long.parseLong(scanner.nextLine());
                    souvenirFacade.deleteProducerAndSouvenirs(producerId);
                }
                case 9 -> menuManager.setCurrentState(menuManager.getExitState());
                default -> System.out.println("🛑🛑🛑 Опції " + choice + " немає в списку запропонованих 🛑🛑🛑");
            }
        } catch (NumberFormatException e) {
            System.out.println("🛑🛑🛑 Цей ввід неможливо перевести в число 🛑🛑🛑");
        } catch (ResourceNotFoundException e) {
            System.out.println("🛑🛑🛑 " + e.getMessage() + " 🛑🛑🛑");
        }
    }
}
