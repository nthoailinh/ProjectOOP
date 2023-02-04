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
        if (sceneKey == null || sceneKey.isEmpty()) {
            sceneKey = String.valueOf(System.currentTimeMillis());
        }
        if (!stack.contains(scene)) {
            scene.setUserData(sceneKey);
            stack.add(scene);
        }
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

    public static Scene getCurrentScene() {
        return stage.getScene();
    }

    public static void printAllScenes() {
        System.out.println("All Scenes in Stack: ");
        for (Scene scene : stack) {
            System.out.println(scene.getUserData() + " và " +scene.toString() + "\n");
        }
    }

    public static void backScene(){
        if (stack.size() > 1) {
            stage.setScene(stack.get(stack.size() - 1));
            stack.remove(stack.size() - 1);
        }
    }

    public static void removeAllEx1(){
        for (int i = stack.size() - 1; i > 0; i--) {
            stack.remove(i);
        }
    }
}
