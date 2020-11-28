import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class ShortestPath extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        // Set Title and size
        stage.setTitle("Shortest Path");
        stage.setWidth(550);
        stage.setHeight(550);
        stage.setResizable(false);

        // Load the root from the fxml file
        VBox root = FXMLLoader.load(getClass().getResource(("/resources/mainLayout.fxml")));

        // Set the scene and the style for the scene
        Scene scene = new Scene(root);
        scene.getStylesheets().add("resources/styles.css");

        // The scene to the stage and display the stage
        stage.setScene(scene);
        stage.show();
    }

}
