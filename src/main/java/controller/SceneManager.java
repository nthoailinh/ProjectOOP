package controller;

import java.util.ArrayList;
import javafx.stage.Stage;
import javafx.scene.Scene;

public class SceneManager {
    private static Stage stage = null;
    private static ArrayList<Scene> stack = new ArrayList<>();

    public static void setStage(Stage stage) {
        if (SceneManager.stage == null) {
            SceneManager.stage = stage;
        } else {
//            System.out.println("Stage has already been set");
        }
    }
    public static void addScene(String sceneKey, Scene scene) {
        scene.setUserData(sceneKey);
        stack.add(scene);
    }
    public static void switchScene(String sceneKey) {
        Scene targetScene = null;
        // Tìm scene tương ứng với sceneKey trong stack
        for (Scene scene : stack) {
            if (scene.getUserData().equals(sceneKey)) {
                targetScene = scene;
                break;
            }
        }
        // Thay đổi scene hiển thị trên stage
        if (targetScene != null) {
            stage.setScene(targetScene);
        } else {
            System.out.println("Scene not found in stack: " + sceneKey);
        }
    }

}
