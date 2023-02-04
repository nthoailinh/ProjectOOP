package controller;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class Details {

    @FXML
    private Button btnBack;

    @FXML
    private TextArea description;

    @FXML
    private Label lbl1;

    @FXML
    private Label lbl2;

    @FXML
    private Label lbl3;

    @FXML
    private Label lbl4;

    @FXML
    private Label lbl5;

    @FXML
    private Label lbl6;

    @FXML
    private Label name;

    @FXML
    private Label place;

    @FXML
    private TextArea ta1;

    @FXML
    private TextArea ta2;

    @FXML
    private TextArea ta3;

    @FXML
    private TextArea ta4;

    Scene preScene;

    public void initialize(){
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
