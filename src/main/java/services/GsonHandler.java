package services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class GsonHandler implements IJsonHandler {
    private final Gson gson;

    public GsonHandler() {
        gson = new Gson();
    }

    @Override
    public <T> T fromJson(String filePath) throws FileNotFoundException {
        return gson.fromJson(new FileReader(filePath), new TypeToken<List<Object>>() {}.getType());
    }

    @Override
    public String toJson(Object object) {
        return gson.toJson(object);
    }
}
