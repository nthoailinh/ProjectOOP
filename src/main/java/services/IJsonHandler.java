package services;

import java.io.FileNotFoundException;
import java.lang.reflect.Type;

public interface IJsonHandler {
    <T> T fromJson(String filePath, Type typeOfT) throws FileNotFoundException;
    String toJson(Object object);
}
