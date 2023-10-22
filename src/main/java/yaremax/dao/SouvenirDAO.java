package yaremax.dao;

import com.google.gson.reflect.TypeToken;
import yaremax.dto.Souvenir;
import yaremax.util.JsonUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SouvenirDAO {
    private static final SouvenirDAO souvenirDaoInstance = new SouvenirDAO();

    private static final String SOUVENIRS_FILE = "souvenirs.json";

    private List<Souvenir> souvenirs = new ArrayList<>();

    public SouvenirDAO() {
        if (new File(SOUVENIRS_FILE).exists()) {
            try {
                List<Souvenir> souvenirs = JsonUtils.readAllFromJson(SOUVENIRS_FILE, new TypeToken<List<Souvenir>>() {});
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

    public void addSouvenir (Souvenir souvenir) {
        souvenirs.add(souvenir);
        saveAllSouvenirs();
    }

    public void deleteSouvenir(Souvenir souvenir) {
        souvenirs.remove(souvenir);
        saveAllSouvenirs();
    }

    private void saveAllSouvenirs() {
        List<Souvenir> souvenirsList = new ArrayList<>(souvenirs);

        try {
            JsonUtils.writeAllToJson(souvenirsList, SOUVENIRS_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
