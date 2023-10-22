package yaremax;

import yaremax.dto.Producer;
import yaremax.dto.Souvenir;
import yaremax.exception.DuplicateResourceException;
import yaremax.dao.ProducerDAO;
import yaremax.dao.SouvenirDAO;

public class SouvenirFacade {
    private ProducerDAO producerDAO = ProducerDAO.getInstance();
    private SouvenirDAO souvenirDAO = SouvenirDAO.getInstance();

    public void addProducer(Producer producer) throws DuplicateResourceException {
        for (Producer existingProducer : producerDAO.getAllProducers()) {
            if (producer.getName().equals(existingProducer.getName())) {
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
            if (souvenir.getName().equals(existingSouvenir.getName()) &&
                souvenir.getProducerName().equals(existingSouvenir.getProducerName())) {
                throw new DuplicateResourceException("Producer with name \"" + souvenir.getProducerName() + "\" already has souvenir");
            }
        }
        souvenirDAO.addSouvenir(souvenir);
    }
    public void editSouvenir(Souvenir souvenir) {}
    public void viewAllSouvenirs() {
        System.out.println(producerDAO.getAllProducers());
    }

    public void viewSouvenirsByManufacturer(String manufacturerName) {}
    public void viewSouvenirsByCountry(String country) {}
    public void viewManufacturersByPriceLimit(double priceLimit) {}
    public void viewSouvenirsByYear(int year) {}

    public void deleteProducerAndSouvenirs(String producerName) {
    }
}
