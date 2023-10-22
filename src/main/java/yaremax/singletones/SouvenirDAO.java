package yaremax.singletones;

import com.google.gson.reflect.TypeToken;
import yaremax.dto.Souvenir;
import yaremax.util.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SouvenirDAO {
    private static final SouvenirDAO souvenirDaoInstance = new SouvenirDAO();

    private static final String SOUVENIRS_FILE = "souvenirs.json";

    private Map<Long, Souvenir> souvenirs = new HashMap<>();
    private Long freeSouvenirIndex;

    public SouvenirDAO() {
        if (new File(SOUVENIRS_FILE).exists()) {
            try {
                List<Souvenir> souvenirsList = JsonUtils.readAllFromJson(SOUVENIRS_FILE, new TypeToken<List<Souvenir>>() {});

                for (Souvenir souvenir : souvenirsList) {
                    souvenirs.put(souvenir.getId(), souvenir);
                }
                freeSouvenirIndex = souvenirsList.get(souvenirsList.size() - 1).getId() + 1;

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static SouvenirDAO getInstance() {
        return souvenirDaoInstance;
    }

    public Map<Long, Souvenir> getAllSouvenirs() {
        return new HashMap<>(souvenirs);
    }

    public void addSouvenir (Souvenir souvenir) {
        souvenirs.put(freeSouvenirIndex++, souvenir);
        saveAllSouvenirs();
    }

    public void updateSouvenir (Souvenir souvenir, Long id) {
        souvenirs.put(id, souvenir);
        saveAllSouvenirs();
    }

    public void deleteSouvenirById(Long id) {
        souvenirs.remove(id);
        saveAllSouvenirs();
    }

    private void saveAllSouvenirs() {
        List<Souvenir> souvenirsList = new ArrayList<>(souvenirs.values());

        try {
            JsonUtils.writeAllToJson(souvenirsList, SOUVENIRS_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
