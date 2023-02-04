package controller;

import models.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class EventController {
    @FXML
    private final TextField input_SK;
    @FXML
    private final Button btnTimKiem_SK;
    @FXML
    private final Button btnChiTiet_SK;
    @FXML
    private final ListView<Event> listviewSuKien;
    private final ObservableList<Event> events;
    private final List<Dynasty> dynasties;
    private final List<Place> places;
    private final List<Figure> figures;
    private final List<Festival> festivals;
    Gson gson = new Gson();

    public EventController(TextField input_SK, Button btnTimKiem_SK, Button btnChiTiet_SK, ListView<Event> listviewSuKien) throws FileNotFoundException {
        this.input_SK = input_SK;
        this.btnTimKiem_SK = btnTimKiem_SK;
        this.btnChiTiet_SK = btnChiTiet_SK;
        this.listviewSuKien = listviewSuKien;
        events = FXCollections.observableList(gson.fromJson(new FileReader("data/Event.json"), new TypeToken<List<Event>>() {
        }.getType()));
        dynasties = gson.fromJson(new FileReader("data/Dynasty.json"), new TypeToken<List<Dynasty>>() {
        }.getType());
        figures = gson.fromJson(new FileReader("data/Figure.json"), new TypeToken<List<Figure>>() {
        }.getType());
        places = gson.fromJson(new FileReader("data/Place.json"), new TypeToken<List<Place>>() {
        }.getType());
        festivals = gson.fromJson(new FileReader("data/Festival.json"), new TypeToken<List<Festival>>() {
        }.getType());
    }

    public void initialize() {
        listviewSuKien.setItems(events);
        listviewSuKien.setCellFactory(listView -> new ListCell<Event>() {

            protected void updateItem(Event item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " (" + item.getDates() + ")");
                }
            }
        });

        btnChiTiet_SK.setOnMouseClicked(event -> {
            Event selectedEvent = listviewSuKien.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                DetailController details = new DetailController();
                try {
                    details.showDetailScene(btnChiTiet_SK, selectedEvent);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnTimKiem_SK.setOnMouseClicked(event -> {
            ObservableList<Event> event_search = FXCollections.observableArrayList();
            String inputName = input_SK.getText();
            for (Event e : events) {
                if (e.getName().contains(inputName)) {
                    event_search.add(e);
                }
            }
            listviewSuKien.setItems(event_search);
        });
    }
}