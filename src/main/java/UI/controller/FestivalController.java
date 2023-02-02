package UI.controller;

import UI.views.Home;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
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
import java.io.IOException;
import java.util.List;

public class FestivalController {
    @FXML
    private final TextField inputLehoi;
    @FXML
    private final Button BtnTimKiemLehoi;
    @FXML
    private final Button btnNVLQLehoi ;
    @FXML
    private final Button btnSKLQLehoi ;
    @FXML
    private final Button btnChiTietLehoi;
    @FXML
    private final ListView<Festival> listviewlehoi;
    private final ObservableList<Festival> festivals;
    private final List<Event> events;
    private final List<Figure> figures;
    Gson gson = new Gson();

    public FestivalController(TextField input, Button BtnTimKiemLehoi, Button btnChiTiet, Button btnNVLQ, Button btnSKLQ, ListView<Festival> listviewlehoi) throws FileNotFoundException {
        this.inputLehoi = input;
        this.BtnTimKiemLehoi = BtnTimKiemLehoi;
        this.btnChiTietLehoi = btnChiTiet;
        this.btnNVLQLehoi = btnNVLQ;
        this.btnSKLQLehoi = btnSKLQ;
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
            FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("details.fxml"));
            if (selectedFestival != null) {
                Stage currentstage =(Stage) btnChiTietLehoi.getScene().getWindow();
                Scene detailScene = null ;
                Scene currentScene = btnChiTietLehoi.getScene();
                try{
                    Parent parent = fxmlLoader.load();
                    SceneManager.setStage(currentstage);
                    SceneManager.addScene("PreScene",currentScene);
                    detailScene = new Scene(parent,1024,768);
                    currentstage.setScene(detailScene);
                    currentstage.show();
                } catch ( IOException e){
                    throw new RuntimeException(e);
                }
                Node detailroot = fxmlLoader.getRoot();
                detailroot.lookup("#ta3").setVisible(false);
                detailroot.lookup("#ta4").setVisible(false);
                Label lbl2 = (Label) detailroot.lookup("#lbl2");
                lbl2.setText("Mô tả");
                Label lbl3 = (Label) detailroot.lookup("#lbl3");
                lbl3.setText("Nhân vật liên quan");
                Label lbl4 = (Label) detailroot.lookup("#lbl4");
                lbl4.setText("Sự kiện liên quan");
                Label lblTitle = (Label) detailroot.lookup("#name");
                lblTitle.setText(selectedFestival.getName());
                TextArea TaDescription = (TextArea) detailroot.lookup("#description");
                TaDescription.setText(selectedFestival.getDescription());
                StringBuilder stringBuilder = new StringBuilder();
                for (int figID : selectedFestival.getFiguresID()) {
                    stringBuilder.append(events.get(figID).getName()).append("\n\n");
                }
                TextArea TaNVLQ = (TextArea) detailroot.lookup("#ta1");
                TaNVLQ.setText(stringBuilder.toString());
                StringBuilder stringBuilder1 = new StringBuilder();
                for (int eventID : selectedFestival.getFiguresID()) {
                    stringBuilder1.append(events.get(eventID).getName()).append("\n\n");
                }
                TextArea TaSKLQ = (TextArea) detailroot.lookup("#ta1");
                TaSKLQ.setText(stringBuilder1.toString());

            }
        });

        /*btnSKLQLehoi.setOnMouseClicked(event -> {
            Festival selectedFestival = listviewlehoi.getSelectionModel().getSelectedItem();
            if (selectedFestival != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông tin về triều đại");
                alert.setHeaderText("Sự kiện liên quan");
                StringBuilder stringBuilder = new StringBuilder();
                for (int eventID : selectedFestival.getEventsID()) {
                    stringBuilder.append(events.get(eventID).getName()).append("\n\n");
                }
                alert.setContentText(stringBuilder.toString());
                alert.showAndWait();
            }
        });

        btnNVLQLehoi.setOnMouseClicked(event -> {
            Festival selectedFestival = listviewlehoi.getSelectionModel().getSelectedItem();
            if (selectedFestival != null) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông tin về triều đại");
                alert.setHeaderText("Nhân vật liên quan");
                StringBuilder stringBuilder = new StringBuilder();
                for (int figureID : selectedFestival.getFiguresID()) {
                    stringBuilder.append(figures.get(figureID).getName()).append("\n\n");
                }
                alert.setContentText(stringBuilder.toString());
                alert.showAndWait();
            }
        });*/

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
