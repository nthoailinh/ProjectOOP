package UI.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.control.TextArea;

public class DetaildynastyController {

    @FXML
    private Button btnBack;

    @FXML
    private Button btnPrePage;

    @FXML
    private TextArea description;

    @FXML
    private Label name;
    Scene preScene;

    public void initialize() {
        btnBack.setOnMouseClicked(event -> {
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(preScene);
            SceneManager.switchScene("PreScene");
            stage.show();
        });
    }

    public void setNameText(String text){
        name.setText(text);
    }
    public void setDescription(String des) { description.setText(des); }
}

