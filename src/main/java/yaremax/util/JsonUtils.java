package yaremax.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import yaremax.dto.IDto;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class JsonUtils {
    private static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
            .create();

    public static <T extends IDto> List<T> readAllFromJson(String filename, TypeToken<List<T>> typeToken) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get(filename)));
        return GSON.fromJson(content, typeToken.getType());
    }

    public static <T extends IDto> void writeAllToJson(List<T> objects, String filename) throws IOException {
        String json = GSON.toJson(objects);
        Files.write(Paths.get(filename), json.getBytes());
    }
}
