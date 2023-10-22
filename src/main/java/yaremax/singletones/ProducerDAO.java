package yaremax.singletones;

import com.google.gson.reflect.TypeToken;
import yaremax.dto.Producer;
import yaremax.dto.Souvenir;
import yaremax.util.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProducerDAO {
    private static final ProducerDAO producerDaoInstance = new ProducerDAO();

    private static final String PRODUCERS_FILE = "producers.json";

    private Map<Long, Producer> producers = new HashMap<>();
    private Long freeProducerIndex;

    public ProducerDAO() {
        if (new File(PRODUCERS_FILE).exists()) {
            try {
                List<Producer> producersList = JsonUtils.readAllFromJson(PRODUCERS_FILE, new TypeToken<List<Producer>>() {
                });

                for (Producer producer : producersList) {
                    producers.put(producer.getId(), producer);
                }
                freeProducerIndex = producersList.get(producersList.size() - 1).getId() + 1;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static ProducerDAO getInstance() {
        return producerDaoInstance;
    }

    public Map<Long, Producer> getAllProducers() {
        return new HashMap<>(producers);
    }

    public void addProducer(Producer producer) {
        producers.put(freeProducerIndex++, producer);
        saveAllProducers();
    }

    public void updateProducer(Producer producer, Long id) {
        producers.put(id, producer);
        saveAllProducers();
    }

    public void deleteProducerById(Long id) {
        producers.remove(id);
        saveAllProducers();
    }

    private void saveAllProducers() {
        List<Producer> producersList = new ArrayList<>(producers.values());

        try {
            JsonUtils.writeAllToJson(producersList, PRODUCERS_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
