package UI.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ItemController {

    @FXML
    private Button btnChiTiet;

    @FXML
    private Label description;

    @FXML
    private Label title;

    public void setTitle(String title){
        this.title.setText(title);
    }
    public void setDescription(String description){
        this.description.setText(description);
    }
}