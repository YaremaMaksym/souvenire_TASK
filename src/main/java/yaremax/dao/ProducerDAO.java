package yaremax.dao;

import com.google.gson.reflect.TypeToken;
import yaremax.dto.Producer;
import yaremax.util.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProducerDAO {
    private static final ProducerDAO producerDaoInstance = new ProducerDAO();

    private static final String PRODUCERS_FILE = "producers.json";

    private List<Producer> producers = new ArrayList<>();

    public ProducerDAO() {
        if (new File(PRODUCERS_FILE).exists()) {
            try {
                producers = JsonUtils.readAllFromJson(PRODUCERS_FILE, new TypeToken<List<Producer>>() {});

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

    public void addProducer(Producer producer) {
        producers.add(producer);
        saveAllProducers();
    }

    public void deleteProducerById(Producer producer) {
        producers.remove(producer);
        saveAllProducers();
    }

    private void saveAllProducers() {
        try {
            JsonUtils.writeAllToJson(producers, PRODUCERS_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
