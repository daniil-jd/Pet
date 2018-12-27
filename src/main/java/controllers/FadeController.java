package controllers;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class FadeController {

    @FXML
    AnchorPane mainPane;

    public void nextSceneButton(ActionEvent actionEvent) {
        fadeOut();
    }

    private void fadeOut() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setNode(mainPane);
        fadeTransition.setDuration(Duration.millis(1000));
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);

        fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    loadSecondScreen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        fadeTransition.play();
    }

    private void loadSecondScreen() throws IOException {
        Parent secondView = FXMLLoader.load(getClass().getResource("/views/PathTransition.fxml"));
        Scene scene = new Scene(secondView);
        Stage currentStage = (Stage) mainPane.getScene().getWindow();
        currentStage.setScene(scene);
    }
}
