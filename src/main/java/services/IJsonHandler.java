package services;

import java.io.FileNotFoundException;

public interface IJsonHandler {
    <T> T fromJson(String filePath) throws FileNotFoundException;
    String toJson(Object object);
}
