package doob;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Hello world!
 *
 */
public class App extends Application {
    private static Stage stage;
    private static AnchorPane pane;

    @Override
    public void start(Stage stageIn) {
        stage = stageIn;
        stage.setTitle("DooB");
        loadScene("/FXML/menu.fxml");
    }

    /**
     * Loads the given scene.
     * @param path Location of the fxml file
     */
    public static void loadScene(String path) {
        try {
            // Load the anchor pane
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource(path));
            pane = loader.load();

            // Set the pane onto the scene
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            stage.setResizable(true);
            stage.show();
            System.out.println(path + " loaded on the stage");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Something went wrong while loading the fxml file");
        }
    }

    public static Stage getStage() {
        return stage;
    }

    public static void main(String[] args) throws IOException {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        launch(args);
    }
}
