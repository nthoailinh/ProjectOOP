package controller;

import models.Dynasty;
import models.Event;
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

public class FigureController {
    @FXML
    private final TextField input_NV;
    @FXML
    private final Button btnTimKiem_NV;
    @FXML
    private final Button btnTDLQ_NV;
    @FXML
    private final Button btnSKLQ_NV;
    @FXML
    private final Button btnChiTiet_NV;
    @FXML
    private final ListView<Figure> listviewNhanVat;
    private final ObservableList<Figure> figures;
    private final List<Dynasty> dynasties;
    private final List<Event> events;
    Gson gson = new Gson();

    public FigureController(TextField input_NV, Button btnTimKiem_NV, Button btnChiTiet_NV, Button btnTDLQ_NV, Button btnSKLQ_NV, ListView<Figure> listviewNhanVat) throws FileNotFoundException {
        this.input_NV = input_NV;
        this.btnTimKiem_NV = btnTimKiem_NV;
        this.btnChiTiet_NV = btnChiTiet_NV;
        this.btnTDLQ_NV = btnTDLQ_NV;
        this.btnSKLQ_NV = btnSKLQ_NV;
        this.listviewNhanVat = listviewNhanVat;
        figures = FXCollections.observableList(gson.fromJson(new FileReader("data/Figure.json"), new TypeToken<List<Figure>>() {
        }.getType()));
        events = gson.fromJson(new FileReader("data/Event.json"), new TypeToken<List<Event>>() {
        }.getType());
        dynasties = gson.fromJson(new FileReader("data/Dynasty.json"), new TypeToken<List<Dynasty>>() {
        }.getType());
    }

    public void initialize() {
        listviewNhanVat.setItems(figures);
        listviewNhanVat.setCellFactory(listView -> new ListCell<Figure>() {

            protected void updateItem(Figure item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName() + " (" + item.getDates() + ")");
                }
            }
        });

        btnChiTiet_NV.setOnMouseClicked(event -> {
            Figure selectedFigure = listviewNhanVat.getSelectionModel().getSelectedItem();
            if (selectedFigure != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông tin về nhân vật lịch sử");
                alert.setHeaderText(selectedFigure.getName() + " (" + selectedFigure.getDates() + ")");
                alert.setContentText("Mô tả: " + selectedFigure.getDescription());
                alert.showAndWait();
            }
        });

        btnSKLQ_NV.setOnMouseClicked(event -> {
            Figure selectedFigure = listviewNhanVat.getSelectionModel().getSelectedItem();
            if (selectedFigure != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông tin về nhân vật");
                alert.setHeaderText("Sự kiện liên quan");
                StringBuilder stringBuilder = new StringBuilder();
                for (int eventID : selectedFigure.getEventsID()) {
                    stringBuilder.append(events.get(eventID).getName()).append("\n\n");
                }
                alert.setContentText(stringBuilder.toString());
                alert.showAndWait();
            }
        });

        btnTDLQ_NV.setOnMouseClicked(event -> {
            Figure selectedDynasty = listviewNhanVat.getSelectionModel().getSelectedItem();
            if (selectedDynasty != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông tin về triều đại");
                alert.setHeaderText("Nhân vật liên quan");
                StringBuilder stringBuilder = new StringBuilder();
                for (int dynastyID : selectedDynasty.getDynastiesID()) {
                    stringBuilder.append(dynasties.get(dynastyID).getName()).append("\n\n");
                }
                alert.setContentText(stringBuilder.toString());
                alert.showAndWait();
            }
        });

        btnTimKiem_NV.setOnMouseClicked(event -> {
            ObservableList<Figure> figure_search = FXCollections.observableArrayList();
            String inputName = input_NV.getText();
            for (Figure f : figures) {
                if (f.getName().contains(inputName)) {
                    figure_search.add(f);
                }
            }
            listviewNhanVat.setItems(figure_search);
        });
    }
}
