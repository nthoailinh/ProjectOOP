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
    private final Button btnTDLQ_DT;
    @FXML
    private final Button btnSKLQ_DT;
    @FXML
    private final Button btnChiTiet_DT;
    @FXML
    private final ListView<Place> listviewDiTich;
    private final ObservableList<Place> places;
    private final List<Dynasty> dynasties;
    private final List<Event> events;

    Gson gson = new Gson();

    public PlaceController(TextField input_DT, Button btnTimKiem_DT, Button btnChiTiet_DT, Button btnTDLQ_DT, Button btnSKLQ_DT, ListView<Place> listviewDiTich) throws FileNotFoundException {
        this.input_DT = input_DT;
        this.btnTimKiem_DT = btnTimKiem_DT;
        this.btnChiTiet_DT = btnChiTiet_DT;
        this.btnTDLQ_DT = btnTDLQ_DT;
        this.btnSKLQ_DT = btnSKLQ_DT;
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
            Label lblTitle = (Label) detailRoot.lookup("#name");
            lblTitle.setText(selectedPlace.getName());
            Label lblLocation = (Label) detailRoot.lookup("#location");
            lblLocation.setText(selectedPlace.getLocation());
            Label lblDescription = (Label) detailRoot.lookup("#description");
            lblDescription.setText(selectedPlace.getDescription());
        });

        btnSKLQ_DT.setOnMouseClicked(event -> {
            Place selectedPlace = listviewDiTich.getSelectionModel().getSelectedItem();
            if (selectedPlace != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông tin về di tích");
                alert.setHeaderText("Sự kiện liên quan");
                StringBuilder stringBuilder = new StringBuilder();
                for (int eventID : selectedPlace.getEventsID()) {
                    stringBuilder.append(events.get(eventID).getName()).append("\n\n");
                }
                alert.setContentText(stringBuilder.toString());
                alert.showAndWait();
            }
        });

//---------TRIỀU ĐẠI LIÊN QUAN------------
//        btnTDLQ_DT.setOnMouseClicked(event -> {
//            Figure selectedDynasty = listviewDiTich.getSelectionModel().getSelectedItem();
//            if (selectedDynasty != null) {
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Thông tin về triều đại");
//                alert.setHeaderText("Di tích liên quan");
//                StringBuilder stringBuilder = new StringBuilder();
//                for (int dynastyID : selectedDynasty.getDynastiesID()) {
//                    stringBuilder.append(dynasties.get(dynastyID).getName()).append("\n\n");
//                }
//                alert.setContentText(stringBuilder.toString());
//                alert.showAndWait();
//            }
//        });

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
