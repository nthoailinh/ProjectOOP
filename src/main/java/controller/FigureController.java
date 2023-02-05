package controller;

import com.google.gson.reflect.TypeToken;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.Figure;

import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.List;

public class FigureController extends EntityController<Figure> {

    public FigureController(TextField inputField, Button searchButton, Button detailButton, ListView<Figure> listView) throws FileNotFoundException {
        super(inputField, searchButton, detailButton, listView, "data/Figure.json");
    }

    @Override
    protected String getTextForListCell(Figure entity) {
        return entity.getName();
    }

    protected Type getTypeForListCell() {
        return new TypeToken<List<Figure>>() {
        }.getType();
    }
}
