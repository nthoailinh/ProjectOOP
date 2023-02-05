package controller;

import com.google.gson.reflect.TypeToken;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import models.Event;

import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.List;

public class EventController extends EntityController<Event> {

    public EventController(TextField inputField, Button searchButton, Button detailButton, ListView<Event> listView) throws FileNotFoundException {
        super(inputField, searchButton, detailButton, listView, "data/Event.json");
    }

    @Override
    protected String getTextForListCell(Event entity) {
        return entity.getName();
    }

    protected Type getTypeForListCell() {
        Type type = new TypeToken<List<Event>>() {
        }.getType();
        return type;
    }
}