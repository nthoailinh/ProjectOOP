package controllers;

import com.google.gson.reflect.TypeToken;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.*;
import services.GsonHandler;
import services.IJsonHandler;
import views.Home;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class DetailController {

    private final IJsonHandler gson;
    @FXML
    private Button btnBack;
    @FXML
    private Button btnBackDetails;
    private List<Dynasty> dynasties;
    private List<Event> events;
    private List<Festival> festivals;
    private List<Figure> figures;
    private List<Place> places;

    public DetailController() {
        gson = new GsonHandler();
    }

    public void initialize() {
        btnBack.setOnMouseClicked(event -> {
            SceneManager.switchScene("PreScene");
            SceneManager.removeAllEx1();
        });

        btnBackDetails.setOnMouseClicked(event -> {
            SceneManager.backScene();

        });
    }

    public void showDetailScene(Node button, Object selectedObject) throws FileNotFoundException {
        try {
            this.places = gson.fromJson("data/Place.json", new TypeToken<List<Place>>() {
            }.getType());
            this.dynasties = gson.fromJson("data/Dynasty.json", new TypeToken<List<Dynasty>>() {
            }.getType());
            this.events = gson.fromJson("data/Event.json", new TypeToken<List<Event>>() {
            }.getType());
            this.figures = gson.fromJson("data/Figure.json", new TypeToken<List<Figure>>() {
            }.getType());
            this.festivals = gson.fromJson("data/Festival.json", new TypeToken<List<Festival>>() {
            }.getType());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        FXMLLoader fxmlLoader = new FXMLLoader(Home.class.getResource("details.fxml"));
        Scene detailScene = null;
        Stage currentStage = (Stage) button.getScene().getWindow();
        Scene currentScene = button.getScene();
        try {
            Parent parent = fxmlLoader.load();
            detailScene = new Scene(parent, 1024, 768);
            SceneManager.setStage(currentStage);
            SceneManager.addScene("PreScene", currentScene);
            currentStage.setScene(detailScene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String name = "";
        String description = "";
        List<Integer> eventIDs = null;
        List<Integer> figuresIDs = null;
        List<Integer> dynastyIDs = null;
        List<Integer> placeIDs = null;
        List<Integer> festivalIDs = null;
        String optionalText = "";
        String title = "Thông tin chi tiết ";
        String MaHienThi = "";

        if (selectedObject instanceof Place place) {
            title += "di tích lịch sử";
            name = place.getName();
            description = place.getDescription();
            eventIDs = place.getEventsID();
            optionalText = "Địa điểm: " + place.getLocation();
            MaHienThi = "PLACE";
        } else if (selectedObject instanceof Dynasty dynasty) {
            title += "triều đại lịch sử";
            name = dynasty.getName();
            description = dynasty.getDescription();
            eventIDs = dynasty.getEventsID();
            figuresIDs = dynasty.getFiguresID();
            optionalText = "Thời gian: " + dynasty.getDates();
            MaHienThi = "DYNASTY";
        } else if (selectedObject instanceof Event event) {
            title += "sự kiện lịch sử";
            name = event.getName();
            description = event.getDescription();
            figuresIDs = event.getFiguresID();
            dynastyIDs = event.getDynastiesID();
            placeIDs = event.getPlacesID();
            festivalIDs = event.getFestivalsID();
            optionalText = null;
            MaHienThi = "EVENT";
        } else if (selectedObject instanceof Festival festival) {
            title += "lễ hội";
            name = festival.getName();
            description = festival.getDescription();
            eventIDs = festival.getEventsID();
            figuresIDs = festival.getFiguresID();
            optionalText = "Thời gian: " + festival.getDates();
            MaHienThi = "FESTIVAL";
        } else if (selectedObject instanceof Figure figure) {
            title += "nhân vật lịch sử";
            name = figure.getName();
            description = figure.getDescription();
            eventIDs = figure.getEventsID();
            dynastyIDs = figure.getDynastiesID();
            optionalText = "Năm sinh - năm mất: " + figure.getDates();
            MaHienThi = "FIGURE";
        }
        Node detailRoot = fxmlLoader.getRoot();
        Label lblName = (Label) detailRoot.lookup("#name");
        lblName.setText(name);
        Label lblTitle = (Label) detailRoot.lookup("#title");
        lblTitle.setText(title);

        Label lblDescription = (Label) detailRoot.lookup("#description");
        lblDescription.setText(description);

        VBox relatedEvents = (VBox) detailRoot.lookup("#relatedEvents");
        VBox relatedFigures = (VBox) detailRoot.lookup("#relatedFigures");
        VBox relatedDynasty = (VBox) detailRoot.lookup("#relatedDynasty");
        VBox relatedPlace = (VBox) detailRoot.lookup("#relatedPlace");
        VBox relatedFestival = (VBox) detailRoot.lookup("#relatedFestival");
        VBox optionalInfo = (VBox) detailRoot.lookup("#optionalInfo");


        if (optionalText != null) {
            Label lblOptionalInfo = new Label();
            lblOptionalInfo.setText(optionalText);
            lblOptionalInfo.setId("h2");
            optionalInfo.getChildren().add(lblOptionalInfo);
        } else optionalInfo.setVisible(false);

        // sự kiện liên quan
        if (MaHienThi.equals("DYNASTY") || MaHienThi.equals("FESTIVAL") || MaHienThi.equals("FIGURE") || MaHienThi.equals("PLACE")) {
            Label lblH2 = new Label();
            lblH2.setText("Sự kiện liên quan");
            lblH2.setId("h2");
            relatedEvents.getChildren().add(lblH2);
            for (int eventID : eventIDs) {
                Label relatedEvent = new Label();
                relatedEvent.setId("relatedEvent");
                relatedEvent.setText(events.get(eventID).getName());
                relatedEvent.setOnMouseClicked(eventRelatedEvent -> {
                    DetailController details = new DetailController();
                    try {
                        SceneManager.addScene(null, SceneManager.getCurrentScene());
                        details.showDetailScene(relatedEvents, events.get(eventID));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                });
                relatedEvents.getChildren().add(relatedEvent);
            }
        }

        if (MaHienThi.equals("DYNASTY") || MaHienThi.equals("EVENT") || MaHienThi.equals("FESTIVAL")) {
            Label lblH2 = new Label();
            lblH2.setText("Nhân vật liên quan");
            lblH2.setId("h2");
            relatedFigures.getChildren().add(lblH2);
            for (int figureID : figuresIDs) {
                Label relatedEvent = new Label();
                relatedEvent.setId("relatedEvent");
                relatedEvent.setText(figures.get(figureID).getName());
                relatedEvent.setOnMouseClicked(eventRelatedEvent -> {
                    DetailController details = new DetailController();
                    try {
                        SceneManager.addScene(null, SceneManager.getCurrentScene());
                        details.showDetailScene(relatedEvents, figures.get(figureID));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                });
                relatedFigures.getChildren().add(relatedEvent);
            }
        }

        if (MaHienThi.equals("EVENT") || MaHienThi.equals("FIGURE")) {
            Label lblH2 = new Label();
            lblH2.setText("Triều đại liên quan");
            lblH2.setId("h2");
            relatedDynasty.getChildren().add(lblH2);
            for (int dynastyID : dynastyIDs) {
                Label relatedEvent = new Label();
                relatedEvent.setId("relatedEvent");
                relatedEvent.setText(dynasties.get(dynastyID).getName());
                relatedEvent.setOnMouseClicked(eventRelatedEvent -> {
                    DetailController details = new DetailController();
                    try {
                        SceneManager.addScene(null, SceneManager.getCurrentScene());
                        details.showDetailScene(relatedEvents, dynasties.get(dynastyID));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                });
                relatedDynasty.getChildren().add(relatedEvent);
            }
        }

        if (MaHienThi.equals("EVENT")) {
            Label lblH2 = new Label();
            lblH2.setText("Di tích liên quan");
            lblH2.setId("h2");
            relatedPlace.getChildren().add(lblH2);
            for (int placeID : placeIDs) {
                Label relatedEvent = new Label();
                relatedEvent.setId("relatedEvent");
                relatedEvent.setText(places.get(placeID).getName());
                relatedEvent.setOnMouseClicked(eventRelatedEvent -> {
                    DetailController details = new DetailController();
                    try {
                        SceneManager.addScene(null, SceneManager.getCurrentScene());
                        details.showDetailScene(relatedEvents, places.get(placeID));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                });
                relatedPlace.getChildren().add(relatedEvent);
            }
        }

        if (MaHienThi.equals("EVENT")) {
            Label lblH2 = new Label();
            lblH2.setText("Lễ hội liên quan");
            lblH2.setId("h2");
            relatedFestival.getChildren().add(lblH2);
            for (int festivalID : festivalIDs) {
                Label relatedEvent = new Label();
                relatedEvent.setId("relatedEvent");
                relatedEvent.setText(festivals.get(festivalID).getName());
                relatedEvent.setOnMouseClicked(eventRelatedEvent -> {
                    DetailController details = new DetailController();
                    try {
                        SceneManager.addScene(null, SceneManager.getCurrentScene());
                        details.showDetailScene(relatedEvents, festivals.get(festivalID));
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }

                });
                relatedFestival.getChildren().add(relatedEvent);
            }
        }

    }

}
