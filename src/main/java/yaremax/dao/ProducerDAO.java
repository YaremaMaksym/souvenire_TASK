package yaremax.dao;

import com.google.gson.reflect.TypeToken;
import yaremax.dto.Producer;
import yaremax.util.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ProducerDAO {
    private static final ProducerDAO producerDaoInstance = new ProducerDAO();

    private static final String PRODUCERS_FILE = "producers.json";

    private List<Producer> producers = new ArrayList<>();
    private Long lastIdOfSequence = 0L;

    public ProducerDAO() {
        if (new File(PRODUCERS_FILE).exists()) {
            try {
                producers = JsonUtils.readAllFromJson(PRODUCERS_FILE, new TypeToken<List<Producer>>() {});
                if (!producers.isEmpty()){
                    lastIdOfSequence = producers.get(producers.size() - 1).getId();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ProducerDAO getInstance() {
        return producerDaoInstance;
    }

    public List<Producer> getAllProducers() {
        return new ArrayList<>(producers);
    }

    public Optional<Producer> getProducerById(Long id) {
        return producers.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public void addProducer(Producer producer) {
        if (producer.getId() == null) {
            Producer producerWithId = new Producer(++lastIdOfSequence, producer.getName(), producer.getCountry());
            producers.add(producerWithId);
        }
        else {
            producers.add(producer);
        }
        saveAllProducers();
    }

    public void updateProducerById (Producer newProducer) {
        deleteProducerById(newProducer.getId());
        producers.add(newProducer);
        saveAllProducers();
    }

    public void deleteProducerById(Long id) {
        producers.removeIf(p -> p.getId() == id);
        saveAllProducers();
    }

    private void saveAllProducers() {
        try {
            producers = producers.stream()
                    .sorted(Comparator.comparingLong(Producer::getId))
                    .collect(Collectors.toList());
            JsonUtils.writeAllToJson(producers, PRODUCERS_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
