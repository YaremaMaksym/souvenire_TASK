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
import java.util.Optional;
import java.util.stream.Collectors;

public class SouvenirFacade {
    private ProducerDAO producerDAO = ProducerDAO.getInstance();
    private SouvenirDAO souvenirDAO = SouvenirDAO.getInstance();

    private void checkProducerDuplicate(Producer producer) {
        boolean isDuplicate = producerDAO.getAllProducers().stream()
                .anyMatch(producer::equals);
        if (isDuplicate) {
            throw new DuplicateResourceException("Виробник з ім'ям \"" + producer.getName() + "\" вже існує");
        }
    }

    private void checkSouvenirDuplicate(Souvenir souvenir) {
        boolean isDuplicate = souvenirDAO.getAllSouvenirs().stream()
                .anyMatch(souvenir::equals);
        if (isDuplicate) {
            throw new DuplicateResourceException("Сувенір з такими параметрами вже існує");
        }
    }

    public void addProducer(Producer producer) throws DuplicateResourceException {
        checkProducerDuplicate(producer);
        producerDAO.addProducer(producer);
    }
    public void editProducer(Long id, Producer producerWithoutId) {
        producerDAO.getProducerById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Виробник з id \"" + id + "\" не існує"));

        checkProducerDuplicate(producerWithoutId);

        Producer newProducer = Producer.builder()
                .id(id)
                .name(producerWithoutId.getName())
                .country(producerWithoutId.getCountry())
                .build();
        producerDAO.updateProducerById(newProducer);
    }
    public void viewAllProducers() {
        TablePrinter.displayProducersTable(producerDAO.getAllProducers());
    }

    public void addSouvenir(Souvenir souvenir) {
        checkSouvenirDuplicate(souvenir);

        Optional<Producer> optionalProducer = producerDAO.getProducerById(souvenir.getProducerId());
        if (optionalProducer.isEmpty()) {
            throw new ResourceNotFoundException("Виробник з id \"" + souvenir.getProducerId() + "\" не існує");
        }
        souvenirDAO.addSouvenir(souvenir);
    }
    public void editSouvenir(Long id, Souvenir souvenirWithoutId) {
        souvenirDAO.getSouvenirById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Сувенір з id \"" + id + "\" не існує"));

        checkSouvenirDuplicate(souvenirWithoutId);

        Souvenir newSouvenir = Souvenir.builder()
                .id(id)
                .name(souvenirWithoutId.getName())
                .producerId(souvenirWithoutId.getProducerId())
                .releaseDate(souvenirWithoutId.getReleaseDate())
                .price(souvenirWithoutId.getPrice())
                .build();
        souvenirDAO.updateSouvenirById(newSouvenir);
    }
    public void viewAllSouvenirs() {
        TablePrinter.displaySouvenirsTable(souvenirDAO.getAllSouvenirs());
    }

    public void viewSouvenirsByProducer(Long producerId) {
        producerDAO.getProducerById(producerId)
                .orElseThrow(() -> new ResourceNotFoundException("Виробник з id \"" + producerId + "\" не існує"));

        List<Souvenir> souvenirsByProducer = souvenirDAO.getAllSouvenirs().stream()
                .filter(souvenir -> souvenir.getProducerId().equals(producerId))
                .toList();
        TablePrinter.displaySouvenirsTable(souvenirsByProducer);
    }
    public void viewSouvenirsByCountry(String country) {
        List<Souvenir> souvenirsByCountry = souvenirDAO.getAllSouvenirs().stream()
                .filter(souvenir -> {
                    Producer producer = producerDAO.getProducerById(souvenir.getProducerId()).get();
                    return producer.getCountry().equals(country);
                })
                .toList();
        TablePrinter.displaySouvenirsTable(souvenirsByCountry);
    }
    public void viewProducersByPriceLimit(double priceLimit) {
        List<Producer> producersByPriceLimit = producerDAO.getAllProducers().stream()
                .filter(p -> souvenirDAO.getAllSouvenirs().stream()
                        .anyMatch(s -> s.getProducerId().equals(p.getId()) && s.getPrice() < priceLimit))
                .toList();
        TablePrinter.displayProducersTable(producersByPriceLimit);
    }
    public void viewSouvenirsByProducers() {
        Map<Producer, List<Souvenir>> souvenirsByProducer = souvenirDAO.getAllSouvenirs().stream()
                .collect(Collectors.groupingBy(souvenir -> producerDAO.getProducerById(souvenir.getProducerId()).get()));

        for (Map.Entry<Producer, List<Souvenir>> entry : souvenirsByProducer.entrySet()) {
            System.out.println("\n\nВиробник: " + entry.getKey());
            TablePrinter.displaySouvenirsTable(entry.getValue());
        }
    }
    public void viewProducersBySouvenir(String souvenirName, int year) {
        List<Producer> producersBySouvenir = souvenirDAO.getAllSouvenirs().stream()
                .filter(souvenir -> souvenir.getName().equals(souvenirName))
                .filter(souvenir -> souvenir.getReleaseDate().getYear() == year)
                .map(souvenir -> producerDAO.getProducerById(souvenir.getProducerId()).get())
                .distinct()
                .toList();
        TablePrinter.displayProducersTable(producersBySouvenir);
    }
    public void viewSouvenirsByYears() {
        Map<Integer, List<Souvenir>> souvenirsByYears = souvenirDAO.getAllSouvenirs().stream()
                .collect(Collectors.groupingBy(souvenir -> souvenir.getReleaseDate().getYear()));

        for (Map.Entry<Integer, List<Souvenir>> entry : souvenirsByYears.entrySet()) {
            System.out.println("\n\nРік: " + entry.getKey());
            TablePrinter.displaySouvenirsTable(entry.getValue());
        }
    }

    public void deleteProducerAndSouvenirs(Long producerId) {
        producerDAO.getProducerById(producerId)
                .orElseThrow(() -> new ResourceNotFoundException("Виробник з id \"" + producerId + "\" не існує"));

        souvenirDAO.getAllSouvenirs().stream()
                .filter(souvenir -> souvenir.getProducerId().equals(producerId))
                .forEach(souvenir -> souvenirDAO.deleteSouvenir(souvenir.getId()));

        producerDAO.deleteProducerById(producerId);
    }
}
