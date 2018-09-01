package runners;

import controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main runner of the app. Load fxml and open it.
 */
public class MainRunner extends Application {
    public void start(Stage primaryStage) throws Exception {
        //FXMLLoader loader = new FXMLLoader();

        Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
}
