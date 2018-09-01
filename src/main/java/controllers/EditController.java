package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Entity;

/**
 * Edit controller. This controller is responsible for editing the name of the entity.
 */
public class EditController {
    @FXML
    private Button confirmButton;

    @FXML
    private Button cancelButton;

    @FXML
    private TextField nameTF;

    /**
     * Entity to edit.
     */
    private Entity entity;

    /**
     * Get the entity.
     * @return entity
     */
    public Entity getEntity() {
        return entity;
    }

    /**
     * Set the entity.
     * @param entity entity
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
        nameTF.setText((entity.getName()));
    }

    /**
     * Determines actions when closing a form.
     * @param actionEvent actionEvent
     */
    public void actionClose(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    /**
     * Saves the entity.
     * @param actionEvent actionEvent
     */
    public void saveEntity(ActionEvent actionEvent) {
        if (nameTF.getText() != "") {
            entity.setName(nameTF.getText());
        }
        actionClose(actionEvent);
    }

    /**
     * Close event.
     * @param actionEvent
     */
    public void close(ActionEvent actionEvent) {
        actionClose(actionEvent);
    }
}
