package controller;

import models.Dynasty;
import models.Event;
import models.Dynasty;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
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
        Type type = new TypeToken<List<Dynasty>>() {
        }.getType();
        return type;
    }
}
