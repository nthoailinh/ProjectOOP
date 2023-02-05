package services;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;

public class GsonHandler implements IJsonHandler {
    private final Gson gson;

    public GsonHandler() {
        gson = new Gson();
    }

    @Override
    public <T> T fromJson(String filePath, Type typeOfT) {
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, typeOfT);
        } catch (IOException e) {
            throw new IllegalArgumentException("Failed to parse JSON file.", e);
        }
    }


    @Override
    public String toJson(Object object) {
        return gson.toJson(object);
    }
}
