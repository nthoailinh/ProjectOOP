package UI.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class ItemController {

    @FXML
    private Button btnDong;

    @FXML
    private Label lienquan;

    @FXML
    private Label title;

    @FXML
    private VBox vboxlienquan;

    public void setTitle(String title){
        this.title.setText(title);
    }
    public void setlienquan(String lienquan){
        this.lienquan.setText(lienquan);
    }


}
