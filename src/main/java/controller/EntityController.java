package controller;

import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import services.GsonHandler;
import services.IJsonHandler;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.reflect.Type;

public abstract class EntityController<T> {

    private final IJsonHandler gson;
    protected final ObservableList<T> entities;
    private final TextField inputField;
    private final Button searchButton;
    private final Button detailButton;
    private final ListView<T> listView;

    public EntityController(TextField inputField, Button searchButton, Button detailButton, ListView<T> listView, String entityPath) throws FileNotFoundException {
        this.inputField = inputField;
        this.searchButton = searchButton;
        this.detailButton = detailButton;
        this.listView = listView;
        Type type = getTypeForListCell();
        gson = new GsonHandler();
        this.entities = FXCollections.observableList(gson.fromJson(entityPath, type));
    }

    public void initialize() {
        setListView();
        setDetailButton();
        setSearchButton();
    }

    private void setListView() {
        listView.setItems(entities);
        listView.setCellFactory(listView -> new ListCell<T>() {
            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(getTextForListCell(item));
                }
            }
        });
    }

    private void setDetailButton() {
        detailButton.setOnMouseClicked(event -> {
            T selectedEntity = listView.getSelectionModel().getSelectedItem();
            if (selectedEntity != null) {
                DetailController details = new DetailController();
                try {
                    details.showDetailScene(detailButton, selectedEntity);
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void setSearchButton() {
        searchButton.setOnMouseClicked(event -> {
            ObservableList<T> searchResults = FXCollections.observableArrayList();
            String inputName = inputField.getText();
            for (T entity : entities) {
                if (getTextForListCell(entity).contains(inputName)) {
                    searchResults.add(entity);
                }
            }
            listView.setItems(searchResults);
        });
    }

    protected abstract String getTextForListCell(T entity);
    protected abstract Type getTypeForListCell();
}
