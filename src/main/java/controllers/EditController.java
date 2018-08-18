package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Entity;

public class EditController {
    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField nameTF;

    private Entity entity;

    @FXML
    private void initialize() {

    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
        nameTF.setText((entity.getName()));
    }

    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    public void saveEntity(ActionEvent actionEvent) {
        if (nameTF.getText() != "") {
            entity.setName(nameTF.getText());
        }
        actionClose(actionEvent);
    }

    public void close(ActionEvent actionEvent) {
        actionClose(actionEvent);
    }
}
