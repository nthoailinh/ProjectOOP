package controller;

import views.Home;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class FigureController {
    @FXML
    private final TextField input_NV;
    @FXML
    private final Button btnTimKiem_NV;
    @FXML
    private final Button btnChiTiet_NV;
    @FXML
    private final ListView<Figure> listviewNhanVat;
    private final ObservableList<Figure> figures;
    private final List<Dynasty> dynasties;
    private final List<Event> events;
    Gson gson = new Gson();

    public FigureController(TextField input_NV, Button btnTimKiem_NV, Button btnChiTiet_NV, ListView<Figure> listviewNhanVat) throws FileNotFoundException {
        this.input_NV = input_NV;
        this.btnTimKiem_NV = btnTimKiem_NV;
        this.btnChiTiet_NV = btnChiTiet_NV;
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
            FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("details.fxml"));
            if (selectedFigure != null) {
                Stage currentStage = (Stage) btnChiTiet_NV.getScene().getWindow();
                Scene detailScene = null;
                Scene currentScene = btnChiTiet_NV.getScene();
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
                detailRoot.lookup("#ta3").setVisible(false);
                detailRoot.lookup("#ta4").setVisible(false);
                Label lbl2 = (Label) detailRoot.lookup("#lbl2");
                lbl2.setText("Mô tả");
                Label lbl3 = (Label) detailRoot.lookup("#lbl3");
                lbl3.setText("Triều đại liên quan");
                Label lbl4 = (Label) detailRoot.lookup("#lbl4");
                lbl4.setText("Sự kiện liên quan");
                Label lblTitle = (Label) detailRoot.lookup("#name");
                lblTitle.setText(selectedFigure.getName()+ " (" + selectedFigure.getDates() + ")");
                TextArea TaDescription = (TextArea) detailRoot.lookup("#description");
                TaDescription.setText(selectedFigure.getDescription());
                StringBuilder stringBuilder = new StringBuilder();
                for (int dynID : selectedFigure.getDynastiesID()) {
                    stringBuilder.append(dynasties.get(dynID).getName()).append("\n\n");
                }
                TextArea TaTDLQ = (TextArea) detailRoot.lookup("#ta1");
                TaTDLQ.setText(stringBuilder.toString());
                StringBuilder stringBuilder1 = new StringBuilder();
                for (int eventID : selectedFigure.getEventsID()) {
                    stringBuilder1.append(events.get(eventID).getName()).append("\n\n");
                }
                TextArea TaSKLQ = (TextArea) detailRoot.lookup("#ta2");
                TaSKLQ.setText(stringBuilder1.toString());

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
