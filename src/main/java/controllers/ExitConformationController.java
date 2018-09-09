package controllers;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import models.Entity;
import tools.SaverTool;

import java.util.List;

public class ExitConformationController {

    @FXML
    private Button confirmButton;

    /**
     * Entity to save.
     */
    private Entity entity;

    /**
     * Entities to save.
     */
    private List<Entity> entities;

    /**
     * Try save data and quit.
     * @param actionEvent action event
     */
    public void confirm(ActionEvent actionEvent) {
        if (entity != null) {
            SaverTool.saveEntity(entity);
        } else if (entities != null) {
            SaverTool.saveAllEntities(entities);
        }
        cancel(actionEvent);
    }

    /**
     * Handle cancel (close) event.
     * @param actionEvent
     */
    public void cancel(ActionEvent actionEvent) {
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.hide();
    }

    /**
     * Set entity.
     * @param entity entity
     */
    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    /**
     * Set entities.
     * @param entities entities
     */
    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }
}
