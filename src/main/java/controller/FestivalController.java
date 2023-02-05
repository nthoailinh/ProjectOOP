package controller;

import com.google.gson.reflect.TypeToken;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.Festival;

import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.List;

public class FestivalController extends EntityController<Festival> {

    public FestivalController(TextField inputField, Button searchButton, Button detailButton, ListView<Festival> listView) throws FileNotFoundException {
        super(inputField, searchButton, detailButton, listView, "data/Festival.json");
    }

    @Override
    protected String getTextForListCell(Festival entity) {
        return entity.getName();
    }

    protected Type getTypeForListCell() {
        return new TypeToken<List<Festival>>() {
        }.getType();
    }
}
