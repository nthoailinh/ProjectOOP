package controller;

import views.Home;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
            FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("details.fxml"));
            if (selectedEvent != null) {
                Stage currentStage = (Stage) btnChiTiet_SK.getScene().getWindow();
                Scene detailScene = null;
                Scene currentScene = btnChiTiet_SK.getScene();
                try {
                    Parent parent = fxmlLoader.load();
                    SceneManager.setStage(currentStage);
                    SceneManager.addScene("PreScene", currentScene);
                    detailScene = new Scene(parent, 1024, 768);
                    currentStage.setScene(detailScene);
                    currentStage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                Node detailRoot = fxmlLoader.getRoot();

                Label lbl2 = (Label) detailRoot.lookup("#lbl2");
                lbl2.setText("Mô tả");
                Label lbl3 = (Label) detailRoot.lookup("#lbl3");
                lbl3.setText("Triều đại liên quan");
                Label lbl4 = (Label) detailRoot.lookup("#lbl4");
                lbl4.setText("Nhân vật liên quan");
                Label lbl5 = (Label) detailRoot.lookup("#lbl5");
                lbl5.setText("Địa điểm liên quan");
                Label lbl6 = (Label) detailRoot.lookup("#lbl6");
                lbl6.setText("Lễ hội liên quan");

                Label lblTitle = (Label) detailRoot.lookup("#name");
                lblTitle.setText(selectedEvent.getName()+ " (" + selectedEvent.getDates() + ")");
                TextArea TaDescription = (TextArea) detailRoot.lookup("#description");
                TaDescription.setText(selectedEvent.getDescription());
                StringBuilder stringBuilder = new StringBuilder();
                for (int dynID : selectedEvent.getDynastiesID()) {
                    stringBuilder.append(dynasties.get(dynID).getName()).append("\n\n");
                }
                TextArea TaTDLQ = (TextArea) detailRoot.lookup("#ta1");
                TaTDLQ.setText(stringBuilder.toString());
                StringBuilder stringBuilder1 = new StringBuilder();
                for (int figID : selectedEvent.getFiguresID()) {
                    stringBuilder1.append(figures.get(figID).getName()).append("\n\n");
                }
                TextArea TaNVLQ = (TextArea) detailRoot.lookup("#ta2");
                TaNVLQ.setText(stringBuilder1.toString());
                StringBuilder stringBuilder2 = new StringBuilder();
                for (int placeID : selectedEvent.getPlacesID()) {
                    stringBuilder2.append(places.get(placeID).getName()).append("\n\n");
                }
                TextArea TaDDLQ = (TextArea) detailRoot.lookup("#ta3");
                TaDDLQ.setText(stringBuilder2.toString());
                StringBuilder stringBuilder3 = new StringBuilder();
                for (int fesID : selectedEvent.getFestivalsID()) {
                    stringBuilder3.append(festivals.get(fesID).getName()).append("\n\n");
                }
                TextArea TaLHLQ = (TextArea) detailRoot.lookup("#ta4");
                TaLHLQ.setText(stringBuilder3.toString());


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