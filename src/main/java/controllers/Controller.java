package controllers;

import javafx.scene.layout.AnchorPane;
import models.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private Button DangXuat;

    @FXML
    private Button btnChiTiet;

    @FXML
    private Button btnChiTiet_NV;

    @FXML
    private Button btnChiTiet_SK;

    @FXML
    private Button btnChiTietLehoi;

    @FXML
    private Button btnDiTichLichSu;

    @FXML
    private Button btnLeHoiVanHoa;

    @FXML
    private Button btnNhanVatLichSu;

    @FXML
    private Button btnSuKienLichSu;


    @FXML
    private Button btnTimKiem;

    @FXML
    private Button btnTimKiem_NV;

    @FXML
    private Button btnTimKiem_SK;
    @FXML
    private Button btnTimKiemLehoi;

    @FXML
    private Button btnTimKiem_DT;

    @FXML
    private Button btnTrangChu;

    @FXML
    private Button btnTrieuDai;

    @FXML
    private BorderPane contentNhanVat;
    @FXML
    private BorderPane contentLehoi;

    @FXML
    private AnchorPane contentTrangChu;

    @FXML
    private BorderPane contentTrieuDai;

    @FXML
    private BorderPane contentSuKien;

    @FXML
    private BorderPane contentDiTich;

    @FXML
    private TextField input;

    @FXML

    private TextField input_NV;

    @FXML
    private TextField input_SK;

    @FXML
    private TextField input_DT;

    @FXML
    private ListView<Dynasty> listviewTrieuDai;

    @FXML
    private TextField inputLehoi;

    @FXML
    private ListView<Festival> listviewlehoi;

    @FXML
    private ListView<Figure> listviewNhanVat;

    @FXML
    private ListView<Event> listviewSuKien;

    @FXML
    private ListView<Place> listviewDiTich;

    @FXML
    private Button btnChiTiet_DT;

    @FXML
    void handleClicksSidebar(ActionEvent event) throws FileNotFoundException {
        if (event.getSource() == btnTrangChu) {
            resetVisible();
            contentTrangChu.setVisible(true);
        } else if (event.getSource() == btnTrieuDai) {
            resetVisible();
            contentTrieuDai.setVisible(true);
            btnTrieuDai.setStyle("-fx-background-color: #3C2C2D");
        }
        else if(event.getSource() == btnNhanVatLichSu) {
            resetVisible();
            contentNhanVat.setVisible(true);
            btnNhanVatLichSu.setStyle("-fx-background-color: #3C2C2D");
        }
        else if(event.getSource() == btnSuKienLichSu) {
            resetVisible();
            contentSuKien.setVisible(true);
            btnSuKienLichSu.setStyle("-fx-background-color: #3C2C2D");
        }
        else if(event.getSource() == DangXuat) {
            Stage stage = (Stage) DangXuat.getScene().getWindow();
            stage.close();
        }else if(event.getSource()==btnLeHoiVanHoa){
            resetVisible();
            contentLehoi.setVisible(true);
            btnLeHoiVanHoa.setStyle("-fx-background-color: #3C2C2D");
        }else if(event.getSource() == btnDiTichLichSu){
            resetVisible();
            contentDiTich.setVisible(true);
            btnDiTichLichSu.setStyle("-fx-background-color: #3C2C2D");
        }
    }

    void resetVisible() {
        contentTrangChu.setVisible(false);
        contentTrieuDai.setVisible(false);
        contentNhanVat.setVisible(false);
        contentSuKien.setVisible(false);
        contentLehoi.setVisible(false);
        contentDiTich.setVisible(false);
        btnTrieuDai.setStyle("-fx-background-color: #9A6619");
        btnDiTichLichSu.setStyle("-fx-background-color: #9A6619");
        btnLeHoiVanHoa.setStyle("-fx-background-color: #9A6619");
        btnSuKienLichSu.setStyle("-fx-background-color: #9A6619");
        btnNhanVatLichSu.setStyle("-fx-background-color: #9A6619");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            DynastyController dynastyController = new DynastyController(input, btnTimKiem, btnChiTiet, (ListView<Dynasty>) listviewTrieuDai);
            dynastyController.initialize();
            FigureController figurecontroller = new FigureController(input_NV, btnTimKiem_NV, btnChiTiet_NV, listviewNhanVat);
            figurecontroller.initialize();
            PlaceController placecontroller = new PlaceController(input_DT, btnTimKiem_DT, btnChiTiet_DT, (ListView<Place>) listviewDiTich);
            placecontroller.initialize();
            EventController eventcontroller = new EventController(input_SK, btnTimKiem_SK, btnChiTiet_SK, listviewSuKien);
            eventcontroller.initialize();
            FestivalController festivalController = new FestivalController(inputLehoi,btnTimKiemLehoi,btnChiTietLehoi, (ListView<Festival>) listviewlehoi);
            festivalController.initialize();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}