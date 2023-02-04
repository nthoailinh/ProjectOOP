package controller;

import models.Event;
import models.Festival;
import models.Figure;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class FestivalController {
    @FXML
    private final TextField inputLehoi;
    @FXML
    private final Button BtnTimKiemLehoi;
    @FXML
    private final Button btnChiTietLehoi;
    @FXML
    private final ListView<Festival> listviewlehoi;
    private final ObservableList<Festival> festivals;
    private final List<Event> events;
    private final List<Figure> figures;
    Gson gson = new Gson();

    public FestivalController(TextField input, Button BtnTimKiemLehoi, Button btnChiTiet, ListView<Festival> listviewlehoi) throws FileNotFoundException {
        this.inputLehoi = input;
        this.BtnTimKiemLehoi = BtnTimKiemLehoi;
        this.btnChiTietLehoi = btnChiTiet;
        this.listviewlehoi = listviewlehoi;
        festivals = FXCollections.observableList(gson.fromJson(new FileReader("data/Festival.json"), new TypeToken<List<Festival>>() {
        }.getType()));
        events = gson.fromJson(new FileReader("data/Event.json"), new TypeToken<List<Event>>() {
        }.getType());
        figures = gson.fromJson(new FileReader("data/Figure.json"), new TypeToken<List<Figure>>() {
        }.getType());
    }

    public  void initialize() {
        listviewlehoi.setItems(festivals);
        listviewlehoi.setCellFactory(listView -> new ListCell<Festival>() {
            @Override
            protected void updateItem(Festival item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " (" + item.getDates() + ")");
                }
            }
        });

        btnChiTietLehoi.setOnMouseClicked(event -> {
            Festival selectedFestival = listviewlehoi.getSelectionModel().getSelectedItem();
            if (selectedFestival != null) {
                DetailController details = new DetailController();
                try {
                    details.showDetailScene(btnChiTietLehoi, selectedFestival);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });


        BtnTimKiemLehoi.setOnMouseClicked(event -> {
            ObservableList<Festival> Festival_search = FXCollections.observableArrayList();
            String inputName = inputLehoi.getText();
            for (Festival d : festivals) {
                if (d.getName().contains(inputName)) {
                    Festival_search.add(d);
                }
            }
            listviewlehoi.setItems(Festival_search);
        });
    }
}
