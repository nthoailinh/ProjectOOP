package controller;

import models.Dynasty;
import com.google.gson.reflect.TypeToken;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.List;

public class DynastyController extends EntityController<Dynasty> {

    public DynastyController(TextField inputField, Button searchButton, Button detailButton, ListView<Dynasty> listView) throws FileNotFoundException {
        super(inputField, searchButton, detailButton, listView, "data/Dynasty.json");
    }

    @Override
    protected String getTextForListCell(Dynasty entity) {
        return entity.getName();
    }

    protected Type getTypeForListCell() {
        return new TypeToken<List<Dynasty>>() {
        }.getType();
    }
}
