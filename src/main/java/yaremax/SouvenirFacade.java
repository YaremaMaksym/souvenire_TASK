package yaremax;

import yaremax.dto.Producer;
import yaremax.dto.Souvenir;
import yaremax.exception.DuplicateResourceException;
import yaremax.dao.ProducerDAO;
import yaremax.dao.SouvenirDAO;
import yaremax.exception.ResourceNotFoundException;
import yaremax.util.TablePrinter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SouvenirFacade {
    private ProducerDAO producerDAO = ProducerDAO.getInstance();
    private SouvenirDAO souvenirDAO = SouvenirDAO.getInstance();

    public void addProducer(Producer producer) throws DuplicateResourceException {
        for (Producer existingProducer : producerDAO.getAllProducers()) {
            if (producer.equals(existingProducer)) {
                throw new DuplicateResourceException("Producer with name \"" + producer.getName() + "\" already exits");
            }
        }
        producerDAO.addProducer(producer);
    }
    public void editProducer(Producer producer) {
    }
    public void viewAllProducers() {
        TablePrinter.displayProducersTable(producerDAO.getAllProducers());
    }

    public void addSouvenir(Souvenir souvenir) {
        for (Souvenir existingSouvenir : souvenirDAO.getAllSouvenirs()) {
            if (souvenir.equals(existingSouvenir)) {
                throw new DuplicateResourceException("This souvenir already exists");
            }
        }
        List<Producer> producers = producerDAO.getAllProducers();

        // todo: add optional
        if (producerDAO.getProducerByName(souvenir.getProducerName()) == null) {
            throw new ResourceNotFoundException("This company doesn't exists");
        }
        souvenirDAO.addSouvenir(souvenir);
    }
    public void editSouvenir(Souvenir souvenir) {}
    public void viewAllSouvenirs() {
        TablePrinter.displaySouvenirsTable(souvenirDAO.getAllSouvenirs());
    }

    public void viewSouvenirsByProducer(String producerName) {
        List<Souvenir> souvenirsByProducer = souvenirDAO.getAllSouvenirs().stream()
                .filter(souvenir -> souvenir.getProducerName().equals(producerName))
                .toList();
        TablePrinter.displaySouvenirsTable(souvenirsByProducer);
    }
    public void viewSouvenirsByCountry(String country) {
        List<Souvenir> souvenirsByCountry = souvenirDAO.getAllSouvenirs().stream()
                .filter(souvenir -> {
                    Producer producer = producerDAO.getProducerByName(souvenir.getProducerName());
                    return producer.getCountry().equals(country);
                })
                .toList();
        TablePrinter.displaySouvenirsTable(souvenirsByCountry);
    }
    public void viewProducersByPriceLimit(double priceLimit) {
        List<Producer> producersByPriceLimit = producerDAO.getAllProducers().stream()
                .filter(p -> {
                    long num = souvenirDAO.getAllSouvenirs().stream()
                            .filter(souvenir -> souvenir.getProducerName().equals(p.getName()))
                            .filter(s -> s.getPrice() < priceLimit)
                            .count();
                    return num > 0;
                })
                .toList();
        TablePrinter.displayProducersTable(producersByPriceLimit);
    }
    public void viewSouvenirsByProducers() {
        Map<Producer, List<Souvenir>> souvenirsByProducer = souvenirDAO.getAllSouvenirs().stream()
                .collect(Collectors.groupingBy(souvenir -> producerDAO.getProducerByName(souvenir.getProducerName())));

        for (Map.Entry<Producer, List<Souvenir>> entry : souvenirsByProducer.entrySet()) {
            System.out.println("\n\nProducer:");
            System.out.println(entry.getKey());
            System.out.println("Souvenirs:");
            entry.getValue().forEach(System.out::println);
        }
        // todo: output
    }
    public void viewProducersBySouvenir(String souvenirName, int year) {
        List<Producer> producersBySouvenir = souvenirDAO.getAllSouvenirs().stream()
                .filter(souvenir -> souvenir.getName().equals(souvenirName))
                .filter(souvenir -> souvenir.getReleaseDate().getYear() == year)
                .map(souvenir -> producerDAO.getProducerByName(souvenir.getProducerName()))
                .distinct()
                .toList();
        TablePrinter.displayProducersTable(producersBySouvenir);
    }
    public void viewSouvenirsByYears() {
        Map<Integer, List<Souvenir>> souvenirsByYears = souvenirDAO.getAllSouvenirs().stream()
                .collect(Collectors.groupingBy(souvenir -> souvenir.getReleaseDate().getYear()));

        for (Map.Entry<Integer, List<Souvenir>> entry : souvenirsByYears.entrySet()) {
            System.out.println(entry.getKey());
            entry.getValue().forEach(System.out::println);
        }
        // todo: output
    }

    public void deleteProducerAndSouvenirs(String producerName) {
        List<Souvenir> souvenirsByProducer = souvenirDAO.getAllSouvenirs().stream()
                .filter(souvenir -> souvenir.getProducerName().equals(producerName))
                .toList();

        for (Souvenir souvenir : souvenirsByProducer) {
            souvenirDAO.deleteSouvenir(souvenir);
        }

        producerDAO.deleteProducerById(producerDAO.getProducerByName(producerName));
    }
}
