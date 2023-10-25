package yaremax;

import yaremax.dto.Producer;
import yaremax.dto.Souvenir;
import yaremax.exception.DuplicateResourceException;
import yaremax.dao.ProducerDAO;
import yaremax.dao.SouvenirDAO;
import yaremax.exception.ResourceNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    public void viewAllProducer() {
        System.out.println(producerDAO.getAllProducers());
    }

    public void addSouvenir(Souvenir souvenir) {
        for (Souvenir existingSouvenir : souvenirDAO.getAllSouvenirs()) {
            if (souvenir.equals(existingSouvenir)) {
                throw new DuplicateResourceException("This souvenir already exists");
            }
        }
        List<Producer> producers = producerDAO.getAllProducers();
        for (Producer producer : producers) {
            if (souvenir.getProducerName().equals(producer.getName())) {
                throw new ResourceNotFoundException("This company doesn't exists");
            }
        }
        souvenirDAO.addSouvenir(souvenir);
    }
    public void editSouvenir(Souvenir souvenir) {}
    public void viewAllSouvenirs() {
        System.out.println(souvenirDAO.getAllSouvenirs());
    }

    public void viewSouvenirsByProducer(String producerName) {
        List<Souvenir> souvenirsByProducer = souvenirDAO.getAllSouvenirs().stream()
                .filter(souvenir -> souvenir.getProducerName().equals(producerName))
                .toList();
    }
    public void viewSouvenirsByCountry(String country) {
        List<Souvenir> souvenirsByCountry = souvenirDAO.getAllSouvenirs().stream()
                .filter(souvenir -> {
                    Producer producer = producerDAO.getProducerByName(souvenir.getProducerName());
                    return producer.getCountry().equals(country);
                })
                .toList();
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
    }
    public void viewSouvenirsByYear(int year) {
        List<Souvenir> souvenirsByYear = souvenirDAO.getAllSouvenirs().stream()
                .filter(s -> s.getReleaseDate().getYear() == year)
                .toList();
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
