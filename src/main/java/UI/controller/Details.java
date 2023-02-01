package UI.controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;


public class Details {
    @FXML
    Button btnBack;

    Scene preScene;

    @FXML
    Button btnPrePage;

    @FXML
    Label name;

    public void initialize(){
        btnBack.setOnMouseClicked(event -> {
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(preScene);
            SceneManager.switchScene("PreScene");
            stage.show();
        });

        btnPrePage.setOnMouseClicked(event -> {
            Stage stage = (Stage) btnBack.getScene().getWindow();
            stage.setScene(preScene);
            SceneManager.switchScene("PreScene");
            stage.show();
        });
    }

    public void setNameText(String text){
        name.setText(text);
    }
}
