package yaremax;

import yaremax.dto.Producer;
import yaremax.dto.Souvenir;
import yaremax.exception.DuplicateResourceException;
import yaremax.dao.ProducerDAO;
import yaremax.dao.SouvenirDAO;
import yaremax.exception.ResourceNotFoundException;

import java.util.List;

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

    public void viewSouvenirsByManufacturer(String manufacturerName) {}
    public void viewSouvenirsByCountry(String country) {}
    public void viewManufacturersByPriceLimit(double priceLimit) {}
    public void viewSouvenirsByYear(int year) {}

    public void deleteProducerAndSouvenirs(String producerName) {
    }
}
