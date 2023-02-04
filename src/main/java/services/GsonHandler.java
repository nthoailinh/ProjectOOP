package services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

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
