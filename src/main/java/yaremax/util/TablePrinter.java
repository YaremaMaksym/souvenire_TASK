package yaremax.util;

import yaremax.dto.Producer;
import yaremax.dto.Souvenir;

import java.util.List;

public class TablePrinter {
    public static void displayProducersTable(List<Producer> producers) {
        String idText = "Id";
        String nameText = "Ім'я";
        String countryText = "Країна";

        int idWidth = idText.length();
        int nameWidth = nameText.length();
        int countryWidth = countryText.length();

        for (Producer producer : producers) {
            idWidth = Math.max(idWidth, producer.getId().toString().length());
            nameWidth = Math.max(nameWidth, producer.getName().length());
            countryWidth = Math.max(countryWidth, producer.getCountry().length());
        }

        String headerFormat = "| %-" + idWidth + "s | %-" + nameWidth + "s | %-" + countryWidth + "s |";
        String divider = "+" + "-".repeat(idWidth + 2) + "+" + "-".repeat(nameWidth + 2) + "+" + "-".repeat(countryWidth + 2) + "+";
        String rowFormat =  "| %-"+ idWidth + "s | %-" + nameWidth + "s | %-"+ countryWidth + "s |";

        System.out.println("\nВиробники:");
        System.out.println(divider);
        System.out.printf((headerFormat) + "%n", idText, nameText, countryText);
        System.out.println(divider);

        for (Producer producer : producers) {
            System.out.printf((rowFormat) + "%n", producer.getId(), producer.getName(), producer.getCountry());
        }

        System.out.println(divider);
    }

    public static void displaySouvenirsTable(List<Souvenir> souvenirs) {
        String idText = "Id";
        String nameText = "Ім'я";
//        String producerText = "Producer";
        String producerIdText = "Id виробника";
        String releaseDateText = "Дата випуску";
        String priceText = "Ціна";

        int idWidth = idText.length();
        int nameWidth = nameText.length();
        int producerIdWidth = producerIdText.length();
        int releaseDateWidth = releaseDateText.length();
        int priceWidth = priceText.length();

        for (Souvenir souvenir : souvenirs) {
            idWidth = Math.max(idWidth, String.valueOf(souvenir.getId()).length());
            nameWidth = Math.max(nameWidth, souvenir.getName().length());
            producerIdWidth = Math.max(producerIdWidth, souvenir.getProducerId().toString().length());
            releaseDateWidth = Math.max(releaseDateWidth, souvenir.getReleaseDate().toString().length());
            priceWidth = Math.max(priceWidth, souvenir.getPrice().toString().length());
        }

        String headerFormat = "| %-" + idWidth + "s | %-" + nameWidth + "s | %-" + producerIdWidth + "s | %-" + releaseDateWidth + "s | %-" + priceWidth + "s |";
        String divider = "+" + "-".repeat(idWidth + 2) + "+" + "-".repeat(nameWidth + 2) + "+" + "-".repeat(producerIdWidth + 2) + "+" + "-".repeat(releaseDateWidth + 2) + "+" + "-".repeat(priceWidth + 2) + "+";
        String rowFormat = "| %-" + idWidth + "d | %-" + nameWidth + "s | %-"+ producerIdWidth + "s | %-"+ releaseDateWidth + "s | %-"+ priceWidth +"s |";

        System.out.println("\nСувеніри:");
        System.out.println(divider);
        System.out.printf((headerFormat) + "%n", idText, nameText, producerIdText, releaseDateText, priceText);
        System.out.println(divider);

        for (Souvenir souvenir : souvenirs) {
            System.out.printf((rowFormat) + "%n", souvenir.getId(), souvenir.getName(), souvenir.getProducerId(), souvenir.getReleaseDate(), souvenir.getPrice());
        }

        System.out.println(divider);
    }
}
