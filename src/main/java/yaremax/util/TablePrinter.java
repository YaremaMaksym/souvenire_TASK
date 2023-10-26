package yaremax.util;

import yaremax.dto.Producer;
import yaremax.dto.Souvenir;

import java.util.List;

public class TablePrinter {
    public static void displayProducersTable(List<Producer> producers) {
        String nameText = "Name";
        String countryText = "Country";

        int nameWidth = nameText.length();
        int countryWidth = countryText.length();

        for (Producer producer : producers) {
            nameWidth = Math.max(nameWidth, producer.getName().length());
            countryWidth = Math.max(countryWidth, producer.getCountry().length());
        }

        String headerFormat = "| %-" + nameWidth + "s | %-" + countryWidth + "s |";
        String divider = "+" + "-".repeat(nameWidth + 2) + "+" + "-".repeat(countryWidth + 2) + "+";
        String rowFormat = "| %-" + nameWidth + "s | %-"+ countryWidth + "s |";

        System.out.println("\nProducers Information:");
        System.out.println(divider);
        System.out.printf((headerFormat) + "%n", "Name", "Country");
        System.out.println(divider);

        for (Producer producer : producers) {
            System.out.printf((rowFormat) + "%n", producer.getName(), producer.getCountry());
        }

        System.out.println(divider);
    }

    public static void displaySouvenirsTable(List<Souvenir> souvenirs) {
        String nameText = "Name";
        String producerText = "Producer";
        String releaseDateText = "Release Date";
        String priceText = "Price";

        int nameWidth = nameText.length();
        int producerWidth = producerText.length();
        int releaseDateWidth = releaseDateText.length();
        int priceWidth = priceText.length();

        for (Souvenir souvenir : souvenirs) {
            nameWidth = Math.max(nameWidth, souvenir.getName().length());
            producerWidth = Math.max(producerWidth, souvenir.getProducerName().length());
            releaseDateWidth = Math.max(releaseDateWidth, souvenir.getReleaseDate().toString().length());
            priceWidth = Math.max(priceWidth, souvenir.getPrice().toString().length());
        }

        String headerFormat = "| %-" + nameWidth + "s | %-" + producerWidth + "s | %-" + releaseDateWidth + "s | %-" + priceWidth + "s |";
        String divider = "+" + "-".repeat(nameWidth + 2) + "+" + "-".repeat(producerWidth + 2) + "+" + "-".repeat(releaseDateWidth + 2) + "+" + "-".repeat(priceWidth + 2) + "+";
        String rowFormat = "| %-" + nameWidth + "s | %-"+ producerWidth + "s | %-"+ releaseDateWidth + "s | %-"+ priceWidth +"s |";

        System.out.println("\nSouvenirs Information:");
        System.out.println(divider);
        System.out.printf((headerFormat) + "%n", "Name", "Producer", "Release Date", "Price");
        System.out.println(divider);

        for (Souvenir souvenir : souvenirs) {
            System.out.printf((rowFormat) + "%n", souvenir.getName(), souvenir.getProducerName(), souvenir.getReleaseDate(), souvenir.getPrice());
        }

        System.out.println(divider);
    }
}
