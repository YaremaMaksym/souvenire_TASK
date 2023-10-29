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

    public void addProducer(Producer producer) throws DuplicateResourceException {
        for (Producer existingProducer : producerDAO.getAllProducers()) {
            if (producer.equals(existingProducer)) {
                throw new DuplicateResourceException("Producer with name \"" + producer.getName() + "\" already exits");
            }
        }
        producerDAO.addProducer(producer);
    }
    public void editProducer(Long id, Producer producerWithoutId) {
        Optional<Producer> oldProducerOptional = producerDAO.getProducerById(id);
        if (oldProducerOptional.isEmpty()) {
            throw new ResourceNotFoundException("Producer with id \"" + id + "\" wasn't found");
        }

        for (Producer existingProducer : producerDAO.getAllProducers()) {
            if (producerWithoutId.equals(existingProducer)) {
                throw new DuplicateResourceException("Producer with this params already exists");
            }
        }

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
        for (Souvenir existingSouvenir : souvenirDAO.getAllSouvenirs()) {
            if (souvenir.equals(existingSouvenir)) {
                throw new DuplicateResourceException("Souvenir with this params already exists");
            }
        }
        Optional<Producer> optionalProducer = producerDAO.getProducerById(souvenir.getProducerId());
        if (optionalProducer.isEmpty()) {
            throw new ResourceNotFoundException("Producer with id \"" + souvenir.getProducerId() + "\" doesn't exists");
        }
        souvenirDAO.addSouvenir(souvenir);
    }
    public void editSouvenir(Long id, Souvenir souvenirWithoutId) {
        Optional<Souvenir> oldSouvenirOptional = souvenirDAO.getSouvenirById(id);
        if (oldSouvenirOptional.isEmpty()) {
            throw new ResourceNotFoundException("Souvenir with id \"" + id + "\" wasn't found");
        }

        for (Souvenir existingSouvenir : souvenirDAO.getAllSouvenirs()) {
            if (souvenirWithoutId.equals(existingSouvenir)) {
                throw new DuplicateResourceException("Souvenir with this params already exists");
            }
        }

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
                .filter(p -> {
                    long num = souvenirDAO.getAllSouvenirs().stream()
                            .filter(s -> s.getProducerId().equals(p.getId()))
                            .filter(s -> s.getPrice() < priceLimit)
                            .count();
                    return num > 0;
                })
                .toList();
        TablePrinter.displayProducersTable(producersByPriceLimit);
    }
    public void viewSouvenirsByProducers() {
        Map<Producer, List<Souvenir>> souvenirsByProducer = souvenirDAO.getAllSouvenirs().stream()
                .collect(Collectors.groupingBy(souvenir -> producerDAO.getProducerById(souvenir.getProducerId()).get()));

        for (Map.Entry<Producer, List<Souvenir>> entry : souvenirsByProducer.entrySet()) {
            System.out.println("\n\nProducer: " + entry.getKey());
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
            System.out.println("\n\n Year: " + entry.getKey());
            TablePrinter.displaySouvenirsTable(entry.getValue());
        }
    }

    public void deleteProducerAndSouvenirs(Long producerId) {
        List<Souvenir> souvenirsByProducer = souvenirDAO.getAllSouvenirs().stream()
                .filter(souvenir -> souvenir.getProducerId().equals(producerId))
                .toList();

        for (Souvenir souvenir : souvenirsByProducer) {
            souvenirDAO.deleteSouvenir(souvenir.getId());
        }

        producerDAO.deleteProducerById(producerId);
    }
}
