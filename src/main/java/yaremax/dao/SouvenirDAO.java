package yaremax.dao;

import com.google.gson.reflect.TypeToken;
import yaremax.dto.Souvenir;
import yaremax.util.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SouvenirDAO {
    private static final SouvenirDAO souvenirDaoInstance = new SouvenirDAO();

    private static final String SOUVENIRS_FILE = "souvenirs.json";

    private List<Souvenir> souvenirs = new ArrayList<>();
    private Long lastIdOfSequence = 0L;

    public SouvenirDAO() {
        if (new File(SOUVENIRS_FILE).exists()) {
            try {
                souvenirs = JsonUtils.readAllFromJson(SOUVENIRS_FILE, new TypeToken<List<Souvenir>>() {});
                if (!souvenirs.isEmpty()) {
                    lastIdOfSequence = souvenirs.get(souvenirs.size() - 1).getId();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static SouvenirDAO getInstance() {
        return souvenirDaoInstance;
    }

    public List<Souvenir> getAllSouvenirs() {
        return new ArrayList<>(souvenirs);
    }

    public Optional<Souvenir> getSouvenirById(Long id) {
        return souvenirs.stream().filter(s -> s.getId().equals(id)).findFirst();
    }

    public void addSouvenir (Souvenir souvenir) {
        if (souvenir.getId() == null) {
            Souvenir souvenirWithId = new Souvenir(++lastIdOfSequence, souvenir.getName(), souvenir.getProducerId(), souvenir.getReleaseDate(), souvenir.getPrice());
            souvenirs.add(souvenirWithId);
        }
        else {
            souvenirs.add(souvenir);
        }
        saveAllSouvenirs();
    }

    public void updateSouvenirById (Souvenir newSouvenir) {
        deleteSouvenir(newSouvenir.getId());
        souvenirs.add(newSouvenir);
        saveAllSouvenirs();
    }

    public void deleteSouvenir(Long id) {
        souvenirs.removeIf(s -> s.getId() == id);
        saveAllSouvenirs();
    }

    private void saveAllSouvenirs() {
        try {
            souvenirs = souvenirs.stream()
                    .sorted(Comparator.comparingLong(Souvenir::getId))
                    .collect(Collectors.toList());
            JsonUtils.writeAllToJson(souvenirs, SOUVENIRS_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
