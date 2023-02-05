package controller;

import com.google.gson.reflect.TypeToken;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.Place;

import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.List;

public class PlaceController extends EntityController<Place> {

    public PlaceController(TextField inputField, Button searchButton, Button detailButton, ListView<Place> listView) throws FileNotFoundException {
        super(inputField, searchButton, detailButton, listView, "data/Place.json");
    }

    @Override
    protected String getTextForListCell(Place entity) {
        return entity.getName();
    }

    protected Type getTypeForListCell() {
        return new TypeToken<List<Place>>() {
        }.getType();
    }
}
