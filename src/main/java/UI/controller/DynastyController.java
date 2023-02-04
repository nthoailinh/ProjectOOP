package UI.controller;

import UI.views.Home;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Dynasty;
import models.Event;
import models.Figure;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import models.Place;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class DynastyController {
    @FXML
    private final TextField input;
    @FXML
    private final Button btnTimKiem;
    @FXML
    private final Button btnNVLQ;
    @FXML
    private final Button btnSKLQ;
    @FXML
    private final Button btnChiTiet;
    @FXML
    private final ListView<Dynasty> listviewTrieuDai;
    private final ObservableList<Dynasty> dynasties;
    private final List<Event> events;
    private final List<Figure> figures;
    Gson gson = new Gson();

    public DynastyController(TextField input, Button btnTimKiem, Button btnChiTiet, Button btnNVLQ, Button btnSKLQ, ListView<Dynasty> listviewTrieuDai) throws FileNotFoundException {
        this.input = input;
        this.btnTimKiem = btnTimKiem;
        this.btnChiTiet = btnChiTiet;
        this.btnNVLQ = btnNVLQ;
        this.btnSKLQ = btnSKLQ;
        this.listviewTrieuDai = listviewTrieuDai;
        dynasties = FXCollections.observableList(gson.fromJson(new FileReader("data/Dynasty.json"), new TypeToken<List<Dynasty>>() {
        }.getType()));
        events = gson.fromJson(new FileReader("data/Event.json"), new TypeToken<List<Event>>() {
        }.getType());
        figures = gson.fromJson(new FileReader("data/Figure.json"), new TypeToken<List<Figure>>() {
        }.getType());
    }
    public void initialize() {
        listviewTrieuDai.setItems(dynasties);
        listviewTrieuDai.setCellFactory(listView -> new ListCell<Dynasty>() {
            @Override
            protected void updateItem(Dynasty item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " (" + item.getDates() + ")");
                }
            }
        });

        btnChiTiet.setOnMouseClicked(event -> {
            Dynasty selectedDynasty = listviewTrieuDai.getSelectionModel().getSelectedItem();
            if (selectedDynasty != null) {
                Details details = new Details();
                try {
                    details.showDetailScene(btnChiTiet, selectedDynasty);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        btnTimKiem.setOnMouseClicked(event -> {
            ObservableList<Dynasty> dynasty_search = FXCollections.observableArrayList();
            String inputName = input.getText();
            for (Dynasty d : dynasties) {
                if (d.getName().contains(inputName)) {
                    dynasty_search.add(d);
                }
            }
            listviewTrieuDai.setItems(dynasty_search);
        });
    }
}
