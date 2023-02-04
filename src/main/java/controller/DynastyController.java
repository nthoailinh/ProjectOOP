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

public class DynastyController {
    @FXML
    private final TextField input;
    @FXML
    private final Button btnTimKiem;
    @FXML
    private final Button btnChiTiet;
    @FXML
    private final ListView<Dynasty> listviewTrieuDai;
    private final ObservableList<Dynasty> dynasties;
    private final List<Event> events;
    private final List<Figure> figures;
    Gson gson = new Gson();

    public DynastyController(TextField input, Button btnTimKiem, Button btnChiTiet, ListView<Dynasty> listviewTrieuDai) throws FileNotFoundException {
        this.input = input;
        this.btnTimKiem = btnTimKiem;
        this.btnChiTiet = btnChiTiet;
        this.listviewTrieuDai = listviewTrieuDai;
        dynasties = FXCollections.observableList(gson.fromJson(new FileReader("data/Dynasty.json"), new TypeToken<List<Dynasty>>() {
        }.getType()));
        events = gson.fromJson(new FileReader("data/Event.json"), new TypeToken<List<Event>>() {
        }.getType());
        figures = gson.fromJson(new FileReader("data/Figure.json"), new TypeToken<List<Figure>>() {
        }.getType());
    }
//    private void loadStage(String fxml) {
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource(fxml));
//            Stage stage = new Stage();
//            stage.setScene(new Scene(root));
//            stage.initModality(Modality.APPLICATION_MODAL);
////            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
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
            FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("details.fxml"));
            if (selectedDynasty != null) {
                Stage currentStage = (Stage) btnChiTiet.getScene().getWindow();
                Scene detailScene = null;
                Scene currentScene = btnChiTiet.getScene();
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
                lbl3.setText("Sự kiện liên quan");
                Label lblTitle = (Label) detailRoot.lookup("#name");
                lblTitle.setText(selectedDynasty.getName()+ " (" + selectedDynasty.getDates() + ")");
                TextArea TaDescription = (TextArea) detailRoot.lookup("#description");
                TaDescription.setText(selectedDynasty.getDescription());
                StringBuilder stringBuilder = new StringBuilder();
                for (int eventID : selectedDynasty.getEventsID()) {
                    stringBuilder.append(events.get(eventID).getName()).append("\n\n");
                }
                TextArea TaSKLQ = (TextArea) detailRoot.lookup("#ta1");
                TaSKLQ.setText(stringBuilder.toString());
                Label lbl4 = (Label) detailRoot.lookup("#lbl4");
                lbl4.setText("Nhân vật liên quan");
                StringBuilder stringBuilder1 = new StringBuilder();
                for (int figureID : selectedDynasty.getFiguresID()) {
                    stringBuilder1.append(figures.get(figureID).getName()).append("\n\n");
                }
                TextArea TaNVLQ = (TextArea) detailRoot.lookup("#ta2");
                TaNVLQ.setText(stringBuilder1.toString());
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
