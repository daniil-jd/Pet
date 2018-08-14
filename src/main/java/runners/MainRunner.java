package runners;

import controllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainRunner extends Application {
    public void start(Stage primaryStage) throws Exception {
        //FXMLLoader loader = new FXMLLoader();

        Parent root = FXMLLoader.load(getClass().getResource("/views/Main.fxml"));

        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        /*FXMLLoader.load(getClass().getResource("/views/Main.fxml"));
        MainController controller = (MainController) (FXMLLoader.load(getClass().getResource("/views/Main.fxml")));
        controller.setPrimaryStage(primaryStage);*/
        primaryStage.show();
    }
}
