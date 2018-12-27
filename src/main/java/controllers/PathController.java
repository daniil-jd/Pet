package controllers;

import javafx.animation.PathTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.CubicCurveTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class PathController {
    @FXML
    AnchorPane pathPane;

    public void thirdSceneButton(ActionEvent actionEvent) {
        pathOut();
    }

    private void pathOut() {
        PathTransition pathTransition = new PathTransition();
        pathTransition.setNode(pathPane);
        pathTransition.setDuration(Duration.millis(1000));

        Path path = new Path();
        path.getElements().add(new MoveTo(20, 20));
        path.getElements().add(new MoveTo(20,20));
        path.getElements().add(new CubicCurveTo(380, 0, 380, 120, 200, 120));
        path.getElements().add(new CubicCurveTo(0, 120, 0, 240, 380, 240));

        pathTransition.setPath(path);
        pathTransition.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    loadFirstScreen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        pathTransition.play();
    }

    private void loadFirstScreen() throws IOException {
        Parent secondView = FXMLLoader.load(getClass().getResource("/views/FadeTransition.fxml"));
        Scene scene = new Scene(secondView);
        Stage currentStage = (Stage) pathPane.getScene().getWindow();
        currentStage.setScene(scene);
    }
}
