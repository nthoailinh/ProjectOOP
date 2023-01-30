package UI.controller;

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
    private final Button btnTDLQ_SK;
    @FXML
    private final Button btnDTLQ_SK;
    @FXML
    private final Button btnNVLQ_SK;
    @FXML
    private final Button btnLHLQ_SK;
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

    public EventController(TextField input_SK, Button btnTimKiem_SK, Button btnChiTiet_SK, Button btnTDLQ_SK, Button btnNVLQ_SK, Button btnDTLQ_SK, Button btnLHLQ_SK, ListView<Event> listviewSuKien) throws FileNotFoundException {
        this.input_SK = input_SK;
        this.btnTimKiem_SK = btnTimKiem_SK;
        this.btnChiTiet_SK = btnChiTiet_SK;
        this.btnTDLQ_SK = btnTDLQ_SK;
        this.btnNVLQ_SK = btnNVLQ_SK;
        this.btnDTLQ_SK = btnDTLQ_SK;
        this.btnLHLQ_SK = btnLHLQ_SK;
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
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông tin về sự kiện lịch sử");
                alert.setHeaderText(selectedEvent.getName() + " (" + selectedEvent.getDates() + ")");
                alert.setContentText("Mô tả: " + selectedEvent.getDescription());
                alert.showAndWait();
            }
        });

        btnTDLQ_SK.setOnMouseClicked(event -> {
            Event selectedEvent = listviewSuKien.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông tin về sự kiện");
                alert.setHeaderText("Triều đại liên quan");
                StringBuilder stringBuilder = new StringBuilder();
                for (int dynastyID : selectedEvent.getDynastiesID()) {
                    stringBuilder.append(dynasties.get(dynastyID).getName()).append("\n\n");
                }
                alert.setContentText(stringBuilder.toString());
                alert.showAndWait();
            }
        });

        btnNVLQ_SK.setOnMouseClicked(event -> {
            Event selectedEvent = listviewSuKien.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông tin về sự kiện");
                alert.setHeaderText("Nhân vật liên quan");
                StringBuilder stringBuilder = new StringBuilder();
                for (int figureID : selectedEvent.getFiguresID()) {
                    stringBuilder.append(figures.get(figureID).getName()).append("\n\n");
                }
                alert.setContentText(stringBuilder.toString());
                alert.showAndWait();
            }
        });

        btnDTLQ_SK.setOnMouseClicked(event -> {
            Event selectedEvent = listviewSuKien.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông tin về sự kiện");
                alert.setHeaderText("Di tích lịch sử liên quan");
                StringBuilder stringBuilder = new StringBuilder();
                for (int placeID : selectedEvent.getPlacesID()) {
                    stringBuilder.append(places.get(placeID).getName()).append("\n\n");
                }
                alert.setContentText(stringBuilder.toString());
                alert.showAndWait();
            }
        });

        btnLHLQ_SK.setOnMouseClicked(event -> {
            Event selectedEvent = listviewSuKien.getSelectionModel().getSelectedItem();
            if (selectedEvent != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông tin về sự kiện");
                alert.setHeaderText("Lễ hội văn hóa liên quan");
                StringBuilder stringBuilder = new StringBuilder();
                for (int festivalID : selectedEvent.getFestivalsID()) {
                    stringBuilder.append(festivals.get(festivalID).getName()).append("\n\n");
                }
                alert.setContentText(stringBuilder.toString());
                alert.showAndWait();
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