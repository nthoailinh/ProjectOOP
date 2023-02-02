package UI.controller;

import UI.views.Home;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import models.Dynasty;
import models.Event;
import models.Figure;
import models.Place;
import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class PlaceController {
    @FXML
    private final TextField input_DT;
    @FXML
    private final Button btnTimKiem_DT;
    @FXML
    private final Button btnChiTiet_DT;
    @FXML
    private final ListView<Place> listviewDiTich;
    private final ObservableList<Place> places;
    private final List<Dynasty> dynasties;
    private final List<Event> events;

    Gson gson = new Gson();

    public PlaceController(TextField input_DT, Button btnTimKiem_DT, Button btnChiTiet_DT, ListView<Place> listviewDiTich) throws FileNotFoundException {
        this.input_DT = input_DT;
        this.btnTimKiem_DT = btnTimKiem_DT;
        this.btnChiTiet_DT = btnChiTiet_DT;
        this.listviewDiTich = listviewDiTich;
        places = FXCollections.observableList(gson.fromJson(new FileReader("data/Place.json"), new TypeToken<List<Place>>() {
        }.getType()));
        events = gson.fromJson(new FileReader("data/Event.json"), new TypeToken<List<Event>>() {
        }.getType());
        dynasties = gson.fromJson(new FileReader("data/Dynasty.json"), new TypeToken<List<Dynasty>>() {
        }.getType());
    }

    public void initialize() {
        listviewDiTich.setItems(places);
        listviewDiTich.setCellFactory(listView -> new ListCell<Place>() {

            protected void updateItem(Place item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        btnChiTiet_DT.setOnMouseClicked(event -> {
            Place selectedPlace = listviewDiTich.getSelectionModel().getSelectedItem();
            FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("details.fxml"));
            if (selectedPlace != null) {
                Stage currentStage = (Stage) btnChiTiet_DT.getScene().getWindow();
                Scene detailScene = null;
                Scene currentScene = btnChiTiet_DT.getScene();
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
            }
            Node detailRoot = fxmlLoader.getRoot();
            detailRoot.lookup("#ta2").setVisible(false);
            detailRoot.lookup("#ta3").setVisible(false);
            detailRoot.lookup("#ta4").setVisible(false);
            Label lblTitle = (Label) detailRoot.lookup("#name");
            lblTitle.setText(selectedPlace.getName());
            Label lbl1 = (Label) detailRoot.lookup("#lbl1");
            lbl1.setText("Địa điểm");
            Label lbl2 = (Label) detailRoot.lookup("#lbl2");
            lbl2.setText("Mô tả");
            Label lbl3 = (Label) detailRoot.lookup("#lbl3");
            lbl3.setText("Sự kiện liên quan");
//            Label lbl4 = (Label) detailRoot.lookup("#lbl4");
//            lbl4.setText("Triều đại liên quan");

            Label lblLocation = (Label) detailRoot.lookup("#place");
            lblLocation.setText(selectedPlace.getLocation());

            TextArea TaDescription = (TextArea) detailRoot.lookup("#description");
            TaDescription.setText(selectedPlace.getDescription());

            StringBuilder stringBuilder = new StringBuilder();
            for (int eventID : selectedPlace.getEventsID()) {
                stringBuilder.append(events.get(eventID).getName()).append("\n\n");
            }
            TextArea TaSKLQ = (TextArea) detailRoot.lookup("#ta1");
            TaSKLQ.setText(stringBuilder.toString());

        });


        btnTimKiem_DT.setOnMouseClicked(event -> {
            ObservableList<Place> place_search = FXCollections.observableArrayList();
            String inputName = input_DT.getText();
            for (Place p : places) {
                if (p.getName().contains(inputName)) {
                    place_search.add(p);
                }
            }
            listviewDiTich.setItems(place_search);
        });
    }
}
